/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Debug Kit]

/* ────── THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ────── */
#Requires AutoHotkey v2.1-alpha.16+
#Include %A_MyDocuments%\Repos\AutoHotkey\lib\Sideline.ahk

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Bug Class]

/** This class contains useful debugging functions. */
class Bug {
    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Variables & Properties]

    /** @var {String} BugLogPath This is the default path for the Debug Log. */
    static BugLogPath := ".\" A_ScriptName ".bug.log"

    /** @var {Integer} ToolTipNumber This is the number that any tool tips produced by this object will use. */
    static ToolTipNumber := 16

    /** @var {Object} ToolTipObject This is the... */
    static ToolTipObject := ToolTip.Bind("",,, Bug.ToolTipNumber)

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Bug.Note]

    /**______
     * Shows a notification that goes away after a while.
     * ______
     * @param {String} Str String of what the tool tip will say.
     * @param {Integer} Dur Duration of how long the tool tip will stay on screen for.
     * @param {Boolean} Record If true, will record this note in the log.
     */
    static Note(Str, Dur := 2800, Record := false) {
        SetTimer(Bug.ToolTipObject, 0)
        ToolTip(Str,,, Bug.ToolTipNumber)
        SetTimer(Bug.ToolTipObject, -1 * Abs(Dur))
        Record ? Bug.Log(Str, false) : ""
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Bug.Prog]

    /**______
     * Shows a notification indicating completion progress of an event.
     * ______
     * @param {Number} CurrentIndexValue The current index of the event being completed.
     * @param {Number} TotalCountValue The total or maximum value that the index will reach upon completion of the event. If unset, make sure CurrentIndexValue is a percentage less than 1.
     * @param {String} CountTitle (Optional) A string to display in front of the progress percentage.
     * @param {Integer} PercentagePrecision (Optional) The amount of places after the decimal point to round the percentage value to (default is 0).
     */
    static Prog(CurrentIndexValue, TotalCountValue := 1, CountTitle?, PercentagePrecision := 0) {
        if PercentagePrecision {
            progressPercentage := Format("{1:02i}.{2:0" PercentagePrecision "i}", StrSplit(Round(Floor(10**PercentagePrecision * 100 * CurrentIndexValue / TotalCountValue) / 10**PercentagePrecision, PercentagePrecision), ".")*)
        } else {
            progressPercentage := Format("{1:02d}", 100 * CurrentIndexValue / TotalCountValue)
        }
        Bug.Note((IsSet(CountTitle) ? CountTitle ": " : "") progressPercentage "%" (IsSet(CountTitle) ? " Complete" : ""))
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Bug.Log]

    /**______
     * This function records strings in a text file.
     * ______
     * @param {String} Str The string to save to the log file.
     * @param {Boolean} Notify If true (default), shows a brief tool tip of the texts saved to the log file.
     * @param {Boolean} TimeStamp (Optional) Set this value to include the value as a timestamp in the log file.
     */
    static Log(Str, Notify := true, TimeStamp?) {
        if IsSet(TimeStamp) {
            FileAppend("[" TimeStamp "] >>`n" Str "`n`n", Bug.BugLogPath)
        } else {
            FileAppend(Str "`n", Bug.BugLogPath)
        }
        Notify ? Bug.Note(Str) : ""
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Bug.Tip]

    /**______
     * Takes in any value and displays the result in a tool tip.
     * ______
     * @param {Any} Var The variable to obtain information on.
     * @param {Integer} Frequency The frequency
     * ______
     * @returns {String} Containins information about the input variable.
     */
    static Tip(Var?, Frequency := 10) {
        SetTimer((*) {
            ToolTip(String(Var),,, Bug.ToolTipNumber)
        }, Frequency, -1600)
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Bug.Box]

    /**______
     * Returns 
     */
    static Box(Variable?, Title?, Show := true) {

; MsgBox(
;     "is set: " (!IsSet(Variable)) "`n"
;     "is obj: " (!(Variable is Map) && !(Variable is Array)) "`n"
;     "is map: " (!(Variable is Array)) "`n"
;     "is arr: " (Variable is Array) "`n"
;     , "((" Type(Variable) "))Bug.Box Tests"
; )

        ; catch unset cases
        if !IsSet(Variable) {
            try {
                outString := "[unset]"
                goto JumpReturn
            }
        }
        ; try mapping object/params
        if !(Variable is Map) && !(Variable is Array) {
            try {
                outString := ""
                for variableKey, variableValue in Variable.OwnProps() {
                    outString .= variableKey " => " Bug.Box(variableValue,, false) "`n"
                }
                goto JumpReturn
            }
        }
        ; try mapping as a map-like
        if !(Variable is Array) {
            try {
                outString := ""
                for variableKey, variableValue in Variable {
                    outString .= "`"" variableKey "`": " Bug.Box(variableValue,, false) "`n"
                }
                goto JumpReturn
            }
        }
        ; try mapping as an array-like
        if Variable is Array {
            try {
                outString := Variable.ToString()
                goto JumpReturn
            }
        }
        ; try mapping as a value
        try {
            outString := "" (Variable is String ? "`"" : "") Variable (Variable is String ? "`"" : "") "`n"
            goto JumpReturn
        }
        JumpReturn:
        ; fix indentation
        finalOutString := ""
        loop parse outString, "`n" {
            finalOutString .= "`t" A_LoopField "`n"
        }
        finalOutString := "((" Type(Variable) ")) - " Trim(finalOutString, "`n`t")
        ; show and return output
        if Show || IsSet(Title) {
            ; get title if needed
            if (addTitle := !IsSet(Title)) {
                errStr := Error().Stack
                finder := (errStr ~= "i)" A_ThisFunc "\(") + 7
                Title := "[" (foundName := RegExReplace(SubStr(errStr, finder, (SubStr(errStr, finder) ~= "[^.\w]") - 1), "\s")) "] BugBox - Line " A_LineNumber
            }
            MsgBox((addTitle ? foundName "`n`n" : "") finalOutString, Title)
        }
        return finalOutString
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Bug.BugBox]

    /**______
     * Takes in any value and displays the result in a string or MsgBox.
     * @param {Any} var The variable to obtain information on.
     * @param {Boolean} show If true, displays a MsgBox containing information about the input variable.
     * - Default = true
     * @param {String} title (Optional) If included, sets the title for the output MsgBox.
     * ______
     * @returns {String} Containins information about the input variable.
     */
    static BugBox(var?, title?, show := true, indent := 0) {
        s := "  "
        i := StrReplace(Format("{:" indent "}", ""), " ", s)
        ; catch unset cases
        try {
            if !IsSet(var) {
                result := "[unset]"
                goto ProcessResults
            }
        }
        ; catch false / empty string cases
        try {
            if (!var) || (var = "") || (var = 0) {
                ; show a blank string if needed
                result := var is String ? ("`"`" (Empty String)") : (var " (" Type(var) ")")
                goto ProcessResults
            }
        }
        ; conduct tests by type
        switch Type(var) {
            case "VarRef": 
            try {
                if var is VarRef {
                    result := "(" Type(var) ") {`n" i Bug.BugBox(%var%,, false, ++indent) s "}"
                }
            }
            case "Array": 
            try {
                result := "(" Type(var) ") {`n"
                for index, value in var {
                    result .= i "[" index "] " Bug.BugBox(value,, false, ++indent) "`n"
                }
                result := RTrim(result, "`n") s "}"
            }
            case "Map": 
            try {
                result := "(" Type(var) ") {`n"
                for key, value in var {
                    result .= i key "=> " Bug.BugBox(value,, false, ++indent) "`n"
                }
                result := RTrim(result, "`n") s "}"          
            }
            default:
            ; test as object type
            try {
                result := "(" Type(var) (var is Object ? "" : " Object") ") {`n"
                for name, value in var.OwnProps() {
                    result .= i name " = " Bug.BugBox(value,, false, ++indent) "`n"
                }
                result := RTrim(result, "`n") s "}"
                goto ProcessResults
            }
            ; test string / concatenation techniques
            try result := var " (" Type(var) ")"
            catch {
                try result := String(var) " (" Type(var) " - ToString)"
                catch {
                    try result := var.Get() " (" Type(var.Get()) "/" Type(var) " - Get)"
                    catch {
                        try result := var.Call() " (" Type(var.Call()) "/" Type(var) " - Call)"
                    }
                }
            }
        }
        ; process results
        ProcessResults:
        result := (StrLen(result ?? "") = 0) ? "- (" Type(var) ")" : result
        if show || IsSet(title) {
            ; get title if needed
            if (addTitle := !IsSet(title)) {
                errStr := Error().Stack
                finder := (errStr ~= "i)" A_ThisFunc "\(") + 7
                title := "[" (foundName := RegExReplace(SubStr(errStr, finder, (SubStr(errStr, finder) ~= "[^.\w]") - 1), "\s")) "] BugBox - Line " A_LineNumber
            }
            MsgBox((addTitle ? foundName "`n`n" : "") result, title)
        }
        return result
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Bug.Vars]

    /**______
     * Displays all of the active variables in a window.
     * @param {Boolean} quit If true, quits upon window deactivation.
     */
    static Vars(quit := true) {
        ListVars
        WinWaitNotActive(WinActivate(WinWait(A_ScriptFullPath " - AutoHotkey v" A_AhkVersion)))
        quit ? ExitApp() : ""
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Bug.RX]

    /**______
     * A debug tool for RegEx matching.
     * ______
     * @param {RegExMatchInfo} MatchObj RegEx match object.
     * @param {String} Haystack String of text to search through.
     * @param {String} NeedleRegEx RegEx pattern to match within Haystack.
     */
    static RX(MatchObj, Haystack?, NeedleRegEx?) {
        i := 0
        loop {
            outStr := ""
            try outStr .= "Haystack:`t"     (Haystack) "`n"
            try outStr .= "NeedleRegEx:`t"  (NeedleRegEx) "`n"
            if !i {
                try outStr .= "Match.Pos:`t"    (MatchObj.Pos) "`n"
                catch {
                    try outStr .= "Match.Pos[]:`t"      (MatchObj.Pos[]) "`n"
                }
                try outStr .= "Match.Len:`t"    (MatchObj.Len) "`n"
                catch {
                    try outStr .= "Match.Len[]:`t"      (MatchObj.Len[]) "`n"
                }
                try outStr .= "Match.Name:`t"   (MatchObj.Name) "`n"
                catch {
                    try outStr .= "Match.Name[]:`t"     (MatchObj.Name[]) "`n"
                }
                try outStr .= "Match.Count:`t"  (MatchObj.Count) "`n"
                catch {
                    try outStr .= "Match.Count[]:`t"    (MatchObj.Count[]) "`n"
                }
                try outStr .= "Match.Mark:`t"   (MatchObj.Mark) "`n"
                catch {
                    try outStr .= "Match.Mark[]:`t"     (MatchObj.Mark[]) "`n"
                }
                try outStr .= "Match:`t`t"      (MatchObj) "`n"
                catch {
                    try outStr .= "Match[]:`t`t"        (MatchObj[]) "`n"
                }
            } else {
                try outStr .= "Match.Pos[" i "]:`t"     (MatchObj.Pos[i]) "`n"
                try outStr .= "Match.Len[" i "]:`t"     (MatchObj.Len[i]) "`n"
                try outStr .= "Match.Name[" i "]:`t"    (MatchObj.Name[i]) "`n"
                try outStr .= "Match.Count[" i "]:`t"   (MatchObj.Count[i]) "`n"
                try outStr .= "Match.Mark[" i "]:`t"    (MatchObj.Mark[i]) "`n"
                try outStr .= "Match[" i "]:`t`t"       (MatchObj[i]) "`n"
            }
            MsgBox(outStr, "Match " i "/" MatchObj.Count)
            i++
        } until i > MatchObj.Count
    }

    ; @endregion
}

; @endregion
