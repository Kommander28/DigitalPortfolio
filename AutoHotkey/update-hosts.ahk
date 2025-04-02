/*─════════════════════════════─════════════════════════════─════════════════════════════─*/
/*
 ────── THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ──────

 update-hosts.ahk
 v1.2

 This script updates the HOSTS file with the latest from someone who cares.

*/
/*─════════════════════════════─════════════════════════════─════════════════════════════─*/
/* Auto-execute thread */

; directives
#Requires AutoHotkey v2+        ; use AHK V2
#SingleInstance Force           ; allow only 1 instance of this script to run at a time

/*─════════════════════════════─════════════════════════════─════════════════════════════─*/
/* Initialize Variables */

hostsPath := "C:\Windows\System32\drivers\etc\hosts"
newHostsURL := "https://someonewhocares.org/hosts/hosts"

/*─════════════════════════════─════════════════════════════─════════════════════════════─*/
/* Main Function */

; wait 1 min
Sleep (60 * 1000)

; run script as admin
full_command_line := DllCall("GetCommandLine", "str")
if not (A_IsAdmin or RegExMatch(full_command_line, " /restart(?!\S)")) {
    try {
        if A_IsCompiled {
            Run '*RunAs "' A_ScriptFullPath '" /restart'
        } else {
            Run '*RunAs "' A_AhkPath '" /restart "' A_ScriptFullPath '"'
        }
    }
    ExitApp
}

; get hosts file
hosts := FileOpen(hostsPath, "w")
hosts.Write("192.168.40.6`tmarvin.server`tmarvin`n`n")

; get updated hosts file contents
whr := ComObject("WinHttp.WinHttpRequest.5.1")
whr.Open("GET", newHostsURL, true)
whr.Send()
; Using 'true' above and the call below allows the script to remain responsive.
whr.WaitForResponse()
hosts.Write(whr.ResponseText)

; exit script
hosts.Close
TrayTip("Hosts File Updated!", A_ScriptName, 0x10)
Sleep 6000
ExitApp
