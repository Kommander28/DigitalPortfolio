/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Fliepath Class File]

/* ────── THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ────── */
#Requires AutoHotkey v2.1-alpha.16+
#Include %A_MyDocuments%\Repos\AutoHotkey\lib\Sideline.ahk

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Filepath Class]

/** This class adds methods for easier filepath manipulation. */
class Filepath {
    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Variables]

    /** @var {Boolean} CheckPath If true (default), filepath will be validated upon creation and when any change is made to it. */
    CheckPath := false

    /** @var {String} Path This is the string representation of this Filepath. */
    Path := ""

    /** @var {Boolean} Touch If true, will attempt to create the file/path that this filepath points to. */
    Touch := false

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Properties]

    /** @var {String} Container The name of the folder that this filepath target resides within. */
    Container {
        get {
            SplitPath(this.Path,, &FilepathDirectory)
            return SubStr(FilepathDirectory, InStr(FilepathDirectory, "\",, -1) + 1)
        }
        set {
            testValue := Filepath(this.Dir).Dir "\" Value "\" this.LongName
            this.Path := this.CheckPath ? Filepath.Validate(testValue, this.Touch) : testValue
        }
    }

    /** @var {String} Contents Returns the contents of the file. If this path leads to a directory, returns a list of the containing folders. */
    Contents => (this.IsDir) ? this.FindAll(, "D", false).ToString("`n", false) : FileRead(this.Path)

    /** @var {Integer} CountFiles Returns the number of files in a directory (recursive). */
    CountFiles[MatchExt?, Recurse := true] => this.FindAll(MatchExt ?? unset, Recurse ? "FR" : "F", false).Length

    /** @var {Integer} CountFolders Returns the number of folders in a directory. */
    CountFolders[Recurse := true] => this.FindAll(, Recurse ? "DR" : "D", false).Length

    /** @var {Integer} CountLines Returns the number of lines in a file. */
    CountLines => StrSplit(this.Contents, "`n").Length

    /** @var {String} Dir The directory that contains this filepath target. */
    Dir {
        get {
            SplitPath(this.Path,, &FilepathDirectory)
            return FilepathDirectory
        }
        set {
            testValue := Value (SubStr(Value, -1) = "\" ? "" : "\") this.LongName
            this.Path := this.CheckPath ? Filepath.Validate(testValue, this.Touch) : testValue
        }
    }

    /** @var {Filepath} Dir Returns the directory of this filepath target as a Filepath object. */
    DirPath => Filepath(this.Dir, this.Touch, this.CheckPath)

    /** @var {String} Drive The drive that contains this filepath target. */
    Drive {
        get {
            SplitPath(this.Path,,,,, &FilepathParentDrive)
            return FilepathParentDrive
        }
        set {
            testValue := SubStr(RegExReplace(StrUpper(Value), '[^A-Z]'), 1, 1) SubStr(this.Path, InStr(this.Path, ":\"))
            this.Path := this.CheckPath ? Filepath.Validate(testValue, this.Touch) : testValue
        }
    }

    /** @var {Boolean} IsReal Returns true if the file/folder that this filepath points to exists. */
    Exists => FileExist(this.Path)

    /** @var {String} Ext The file extension of this filepath target. */
    Ext {
        get {
            SplitPath(this.Path,,, &FilepathFileExtension)
            return FilepathFileExtension
        }
        set {
            testValue := this.Dir "\" this.ShortName "." StrLower(RegExReplace(Value, '\W'))
            this.Path := this.CheckPath ? Filepath.Validate(testValue, this.Touch) : testValue
        }
    }

    /** @var {Boolean} IsDir Returns true if this filepath is a directory. */
    IsDir => (this.Path) ? InStr(FileGetAttrib(this.Path), "D") : ""

    /** @var {Boolean} IsEmpty If filepath is a directory, returns true if there are no files in the folder. If filepath is a file, returns true if the file contains no text. */
    IsEmpty => (this.IsDir) ? (!this.FindAll(, "FDR", false).Length) : (!FileGetSize(this.Path))

    /** @var {Boolean} IsHidden Returns true if this file is hidden. Can also set whether this file/folder is hidden or not. */
    IsHidden {
        get => (this.Path) ? InStr(FileGetAttrib(this.Path), "H") : ""
        set => (this.Path) ? FileSetAttrib(Value ? "+H" : "-H", this.Path) : ""
    }

    /** @var {String} LongName The file name (including extension) of this filepath target. */
    LongName {
        get {
            SplitPath(this.Path, &FilepathFullFileName)
            return FilepathFullFileName
        }
        set {
            testValue := this.Dir "\" Value
            this.Path := this.CheckPath ? Filepath.Validate(testValue, this.Touch) : testValue
        }
    }

    /** @var {String} ShortName This is just the file name of this filepath target, not including the extension. */
    ShortName {
        get {
            SplitPath(this.Path,,,, &FilepathFileName)
            return FilepathFileName
        }
        set {
            testValue := this.Dir "\" Value "." this.Ext
            this.Path := this.CheckPath ? Filepath.Validate(testValue, this.Touch) : testValue
        }
    }

    /** @var {String} ShortPath Returns the 8.3 short name version of this path. */
    ShortPath {
        get {
            loop files this.Path {
                return A_LoopFileShortPath
            }
        }
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [this.__New]

    /**______
     * Creates a new Filepath string and validates it.
     * ______
     * @param {String} Path The file path of this Filepath object.
     * @param {Boolean} Touch If true, will create files and folders based on the input filepath.
     * @param {Boolean} CheckPath If true (default), filepath will be validated upon creation and when any change is made to it.
     * ______
     * @returns {Filepath} Returns the new Filepath object.
     */
    __New(Path, Touch := false, CheckPath := true) {
        Path := Path is Filepath ? Path.Path : Path
        this.Touch := Touch
        this.CheckPath := CheckPath
        ; validate filepath if needed
        this.Path := this.CheckPath ? Filepath.Validate(Path, this.Touch) : Path
        ; return new Filepath object
        return this
    }

    ; @endregion

    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [this.__Item]

    /**______
     * Returns information about this filepath.
     * ______
     * @param {String} PathComponent The part of this string to get information about.
     * ______
     * @returns {String} Returns the requested information about this filepath.
     */
    __Item[PathComponent?] {
        get {
            switch RegExReplace(PathComponent ?? "", '\s') {
                case "Name", "FullName", "FName", "NameExt": return this.LongName
                case "Dir", "Directory", "Container", "Folder", "ContainingFolder", "Parent": return this.Dir
                case "Ext", "Extension", "X": return this.Ext
                case "ShortName", "SName", "JustName": return this.ShortName
                case "Drive": return this.Drive
                default: return this.Path
            }
        }
        set {
            switch PathComponent ?? "" {
                case "FullName", "FName", "NameExt": this.FullName := Value
                case "Dir", "Directory", "Container", "Folder", "ContainingFolder", "Parent": this.Dir := Value
                case "Ext", "Extension", "X": this.Ext := Value
                case "Name", "ShortName", "SName": this.Name := Value
                case "Drive": this.Drive := Value
                default: this.Path := this.CheckPath ? Filepath.Validate(Value, this.Touch) : Value
            }
        }
    }

    ; @endregion

    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [this.ToString]

    /**______
     * Returns this Filepath as a string.
     * @returns {String} Returns the new Filepath string.
     */
    ToString() => this.Path

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [this.Clean]

    /**______
     * This function either deletes empty files/folders (if this path leads to a directory) or removes empty lines from within a file (if this path leads to a file).
     * ______
     * @param {Boolean} IncludeFiles (Only applies to directories) If true, will also delete empty files from within this directory.
     * @param {Boolean} RecurseSearch (Only applies to directories) If true (default), will recursively search into folders and delete empty files/folders.
     * ______
     * @returns {Integer} Returns the total amount of files and folders deleted or the amount of empty lines removed from the file.
     */
    Clean(IncludeFiles := false, RecurseSearch := true) {
        CloseWindows(this.ShortName)
        cleanAmountCount := 0
        if this.IsDir {
            ; delete all empty files/folders
            for testEmptyFile in this.FindAll(, "D" (IncludeFiles ? "F" : "") (RecurseSearch ? "R" : ""), true) {
                if testEmptyFile.IsEmpty {
                    testEmptyFile.Erase()
                    cleanAmountCount++
                }
            }
            ; recursively delete files/folders
            if RecurseSearch && cleanAmountCount {
                cleanAmountCount += this.Clean(IncludeFiles, RecurseSearch)
            }
        } else {
            ; remove all empty lines from file
            cleanAmountCount := this.CountLines
            FileWrite(RegExReplace(this.Contents, 'm)\s+$'), this.Path)
            cleanAmountCount -= this.CountLines
        }
        return cleanAmountCount
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [this.Erase]

    /**______
     * Erases the file/folder that this path leads to.
     * @param {Boolean} PermanentDelete If this is true or if recycling the file/folder fails, the target will be permanently deleted.
     * @param {Boolean} Restore If true, will restore a blank folder or empty file at this location after erasure.
     */
    Erase(PermanentDelete := false, Restore := false) {
        erasingDirectory := this.IsDir
        CloseWindows(this.ShortName)
        ; erase target
        if this.Exists {
            try {
                PermanentDelete ? Throw() : FileRecycle(this.Path)
            } catch {
                erasingDirectory ? DirDelete(this.Path, 1) : FileDelete(this.Path)
            }
        }
        ; restore target
        if Restore {
            erasingDirectory ? DirCreate(this.Path) : FileAppend("", this.Path)
        }
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [this.FindAll]

    /**______
     * Creates an array of all the files and/or folders in this directory.
     * ______
     * @param {String} MatchExt (Optional) If included, will only add files that match this extension to the return array.
     * @param {String} Mode Include any of the following: D=Get Directories, F=Get Files, R=Recurse (defaults to "FR" if unset).
     * @param {Boolean} ConvertPaths If true (default), converts each file and/or folder found into a Filepath object before adding it to the return array. Otherwise, will return file/folder names (with extension).
     * ______
     * @returns {Array} Returns the array of files/folders found.
     */
    FindAll(MatchExt := "", Mode?, ConvertPaths := true) {
        filesFoldersArray := []
        if this.IsDir {
            searchPathString := this.Path "\*" ((MatchExt := StrLower(RegExReplace(MatchExt, '\W'))) ? "." MatchExt : "")
            if ConvertPaths {
                loop files searchPathString, (Mode ?? "FR") {
                    filesFoldersArray.Push(Filepath(A_LoopFileFullPath))
                }
            } else {
                loop files searchPathString, (Mode ?? "FR") {
                    filesFoldersArray.Push(A_LoopFileName)
                }
            }
        }
        return filesFoldersArray
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [this.Validate]

    /**______
     * Ensures that the inputted file path is a valid file (or a valid directory).
     * ______
     * @param {String, Filepath} TestPath The filepath to validate.
     * @param {Boolean} Create If true (default), creates the necessary folders and files leading up to (and including) the target file.
     * ______
     * @returns {String} Returns the validated filepath, or an empty string if the filepath is invalid.
     */
    static Validate(TestPath, Create := false) {
        TestPath := TestPath is Filepath ? TestPath.Path : TestPath
        SplitPath(TestPath,, &FilepathDirectory, &FilepathFileExtension)
        ; create files and folders if needed
        if Create {
            ; try to validate the path without creation
            if (trialPath := Filepath.Validate(TestPath, false)) {
                return trialPath
            } else {
                ; ensure file/directory exists
                try DirCreate(FilepathDirectory)
                if FilepathFileExtension {
                    try FileAppend("", TestPath)
                } else {
                    try DirCreate(TestPath)
                }
                return Filepath.Validate(TestPath, false)
            }
        }
        ; convert to full file path
        loop files TestPath, (FilepathFileExtension ? "F" : "D") {
            newPath := A_LoopFileFullPath
        }
        ; return new valid path
        return newPath ?? ""
    }

    ; @endregion
}

; @endregion
