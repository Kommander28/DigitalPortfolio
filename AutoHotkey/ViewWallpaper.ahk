/* ─════════════════════════════─ [Wallpaper Path] ─════════════════════════════─ */
/*
 ────── THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ──────

 This script retrieves the path of the desktop's current wallpaper and opens the image and containing folder.

 Hotkeys in use:
    - Ctrl + Win + W: execute the script
    - Ctrl + Shift + Win + W: reload current script

*/
/* ─════════════════════════════─ [Compiler Directives] ─════════════════════════════─ */
;@Ahk2Exe-ExeName ViewWallpaper.exe  ; Set name of script upon compilation
;@Ahk2Exe-AddResource %A_MyDocuments%\Repos\AutoHotkey\icons\picture.ico  ; Includes icon file in script compilation
;@Ahk2Exe-SetMainIcon %A_MyDocuments%\Repos\AutoHotkey\icons\picture.ico  ; Overrides main icon file of the compiled script


/* ─════════════════════════════─ [Script Options] ─════════════════════════════─ */
#Requires AutoHotkey v2.0-  ; Ensures use of proper AutoHotkey version
#SingleInstance Force  ; Allows only one instance of this script to run at a time
Persistent  ; Keeps script running until told to close


/* ─════════════════════════════─ [Aesthetics] ─════════════════════════════─ */
#NoTrayIcon  ; Hides the tray icon and prevents it from ever showing.


/* ─════════════════════════════─ [Escape Sequence] ─════════════════════════════─ */
; Ctrl + Shift + Win + W
^+#W::Reload  ; Reloads script if needed.


/* ─════════════════════════════─ [Main Execution] ─════════════════════════════─ */
; Ctrl + Win + W
^#W::{
    ; get registry value
    regValue := RegRead("HKEY_CURRENT_USER\Control Panel\Desktop", "TranscodedImageCache")
    ; convert binary to chr
    out := "", i := 1
    while RegExMatch(regValue, '..', &char, i) {
        if char[] != "00" {
            out .= Chr(Integer("0x" char[]))
        }
        i := char.Pos + char.Len
    }
    ; trim and covert to usable filepath
    SplitPath(A_ScriptDir,,,,, &driveName)
    imgPath := SubStr(out, InStr(out, driveName))
    SplitPath(imgPath, &fullName, &dirName,, &niceName)
    ; open image
    Run(imgPath)
    ; ask if user wants to open containing folder
    WinWait(fullName)
    WinWaitClose()
    if MsgBox("Open image location?", niceName, 0x1124) = "yes" {
        Run(dirName)
        WinWait(dirName " - File Explorer",, 2)
        Send(niceName)
    }
}
