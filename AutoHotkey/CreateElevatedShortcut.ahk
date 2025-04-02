#Requires AutoHotkey v2.1-alpha.16+
/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Template]
/*
 ────── THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ──────

 This script creates a shortcut to run an app with elevated privileges.

*/
; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Script Directives]

/** Allows only one instance of this script to run at a time. */
#SingleInstance Force

/** Sets the path of #Include statements to my library of AHK Functions. */
#Include %A_MyDocuments%\Repos\AutoHotkey\lib
/** A Filepath is an object with properties beneficial for working with file paths. */
#Include [Class]Filepath.ahk
/** A class used for timekeeping. */
#Include [Class]Timer.ahk

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Other Script Settings]

/** Starts a timer for the main events in this program. */
ScriptTimer := Timer(8)

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Escape Sequence]

/** Breaks execution of script if needed. */
^Esc::ExitApp()

/** Reloads script if needed. */
#Esc::Reload()

/** This method is executed upon termination of the script. */
OnExit((ExitReason, ExitCode) {
    ; use global variables
    global
    ; make this thread critical
    Critical("On")
    ; show script execution information (requires "Timer" and "DebugKit" classes)
    ScriptInformation := "Script Runtime: " ScriptTimer.Now("Full") "`nExit Code: " ExitCode " (" ExitReason ")"
    TrayTip(ScriptInformation, A_ScriptName, 0x10)
    ; allow the TrayTip to be read before exiting
    Sleep(2800)
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Run As Admin]

tries := 0
full_command_line := DllCall("GetCommandLine", "str")
while !(A_IsAdmin || RegExMatch(full_command_line, " /restart(?!\S)")) {
    try Run '*RunAs "' A_AhkPath '" /restart "' A_ScriptFullPath '"'
    if tries++ >= 10 {
        ExitApp
    }
}

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Main]

; prompt user to select file
targetFile := Filepath(FileSelect(3, "C:\Windows\System32", "Select file to create Elevated Shortcut", "Executable Files (*.exe)"))
if !targetFile.Path {
    MsgBox "The dialog was canceled."
    ExitApp
} else if targetFile.Ext != "exe" {
    MsgBox("WRONG FILETYPE!`n" targetFile.Path)
    ExitApp
} else {
    MsgBox "The following file was selected:`n" targetFile.Path
}

; create task scheduler task
service := ComObject("Schedule.Service")
service.Connect()
rootFolder := service.GetFolder("\")
taskDefinition := service.NewTask(0) 
; set the registration info for the task by creating the RegistrationInfo object
regInfo := taskDefinition.RegistrationInfo
regInfo.Description := "This task runs " targetFile.ShortName " with elevated privileges."
regInfo.Author := "AutoHotkey"
; run task with the highest privileges
taskDefinition.Principal.RunLevel := 1
; set the task setting info for the Task Scheduler by creating a TaskSettings object.
settings := taskDefinition.Settings
settings.StopIfGoingOnBatteries := false
settings.DisallowStartIfOnBatteries := false
; create the action for the task to execute
Action := taskDefinition.Actions.Create(0)
Action.Path := "C:\windows\system32\cmd.exe"
Action.Arguments := "/c start `"`" `"" targetFile.Path "`""
; register (create) the task
registeredTask := rootFolder.RegisterTaskDefinition(taskName := (targetFile.ShortName " - Elevated Shortcut"), taskDefinition, 6, "", "", 3)
; create a new shortcut for new app
FileCreateShortcut("schtasks", A_Desktop "\" taskName ".lnk",, "/run /tn `"" taskName "`"",, targetFile.Path)

ExitApp

; @endregion
