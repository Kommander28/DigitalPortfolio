/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Timer]

/* ────── THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ────── */
#Requires AutoHotkey v2.1-alpha.16+
#Include %A_MyDocuments%\Repos\AutoHotkey\lib\Sideline.ahk

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Timer Class]
/**______
 * This class contains functions pertaining with time and time tracking. The accuracy of this class is governed by the performance counter, allowing for high precision (<1us) timing.
 * ______
 * Different units of time are defined for this class as follows:
 ** [1] "microsecond" (base unit) is exacly equal to [1] "microsecond" and is represented by the letter "u"
 ** [1] "millisecond" is exactly equal to [10³] "microseconds" and is represented by the letter "l"
 ** [1] "second" is exactly equal to [10⁶] "microseconds" and is represented by the letter "s"
 ** [1] "minute" is exactly equal to [60] "seconds" and is represented by the letter "m"
 ** [1] "hour"  is exactly equal to [60] "minutes" and is represented by the letter "h"
 ** [1] "day" is exactly equal to [24] "hours" and is represented by the letter "d"
 ** [1] "week" is exactly equal to [7] "days" and is represented by the letter "w"
 ** [1] "month" is exactly equal to [30] "days" and is represented by the letter "n"
 ** [1] "year" is exactly equal to  [365] "days" and is represented by the letter "y"
 */
class Timer {

    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Variables & Properties]

    /** @var {Integer} T0 This is the start time of when this timer was created or last reset. */
    T0 := 0

    /** @var {Number} Now The amount of time that has passed since this timer has started (up to when this function was called).
     */
    Now[TimeFormat?, TimeUnit?] => this.Now(TimeFormat ?? unset, TimeUnit ?? unset)

    /** @var {Array<Integer>} Bookmarks Timestamps created by "this.Mark" are added to this array. */
    Bookmarks := []

    /** @var {Boolean} RunningTime If true (default), times recorded to Bookmarks are timestamps of the current running time. If false, the timer is reset on every call to "Mark" after being recorded in Bookmarks. */
    RunningTime := true

    /** @var {Float} Average Calculates the average of the times saved in Bookmarks. */
    Average => this.Bookmarks.Length ? (this.RunningTime ? this.Bookmarks.AverageDiff : this.Bookmarks.Average) : 0

    /** @var {Integer} ToolTipNumber The number of ToolTip for this Timer object to use (defaults to 3, range is from 1-20). */
    ToolTipNumber := 6

    /** @var {Numper} Freq This is the tick frequency of the system. */
    static Freq := 0

    /** @var {Array<String>} TimeUnits An array containing the different denominations of time that this script uses, in order from smallest (microseconds) to largest (years). */
    static TimeUnits := ["u", "l", "s", "m", "h", "d", "w", "n", "y"]

    /** @var {Map<Integer>} TimeUnitFactors An array containing the multiplicitive factors for converting different denominations of time to/from microseconds. */
    static TimeUnitFactors := Map("u", 1, "l", 1000, "s", 1000000, "m", 60000000, "h", 3600000000, "d", 86400000000, "w", 604800000000, "n", 2592000000000, "y", 31536000000000)

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Timer Meta-Functions]

    /**______
     * Provides the option to assign a different ToolTipNumber than the default, as well as returning the new Timer object.
     * ______
     * @param {Boolean} RunningTime (Optional) If true (default), times recorded to Bookmarks are timestamps of the current running time.
     * @param {Integer} ToolTipNumber (Optional) The number of ToolTip for this Timer object to use (defaults to 3, range is from 1-20).
     * ______
     * @returns {Timer} Returns the newly created Timer object.
     */
    __New(RunningTime?, ToolTipNumber?) {
        ; initialise variables
        DllCall("QueryPerformanceFrequency", "Int64*", &SysFreq := 0)
        Timer.Freq := SysFreq // 1000000
        this.RunningTime := RunningTime ?? true
        this.ToolTipNumber := ToolTipNumber ?? 6
        ; [re]set timer before returning
        this.Reset()
        return this
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [this.Now]

    /**______
     * Returns the amount of time that has passed since this timer has started (up to when this function was called).
     * ______
     * @param {String} TimeFormat (Optional) If included, formats the output of the time (see "Timer.Format" for options).
     * @param {String} TimeUnit (Optional) The character string that determines the units of the number returned (defaults to microseconds).
     * ______
     * @returns {Number} The time that has passed since this timer was created.
     */
    Now(TimeFormat?, TimeUnit?) {
        DllCall("QueryPerformanceCounter", "Int64*", &Tnow := 0)
        Tnow := Round((Tnow - this.T0) / Timer.Freq, 1)
        if IsSet(TimeFormat) {
            ; format the running time
            Tnow := Timer.Format(TNow, TimeFormat)
        } else if IsSet(TimeUnit) {
            ; convert time unit
            Tnow := Timer.Convert(Tnow, "u", TimeUnit)
        }
        ; return elapsed time
        return Tnow
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [this.Reset]

    /** Resets the start of the timer (and returns quickly). */
    Reset() {
        DllCall("QueryPerformanceCounter", "Int64*", &SysQPC := 0)
        this.T0 := SysQPC
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [this.Mark]

    /**______
     * Adds a timestamp (in milliseconds) to the bookmark array.
     * ______
     * @param {Boolean} ResetTimer If true, clears the Bookmarks array before adding the timestamp (if running time is enabled, adds "0" as the initial value).
     * ______
     * @returns {Integer} Returns the captured timestamp value.
     */
    Mark(ResetTimer := false) {
        markTime := this.Now()
        if ResetTimer {
            ; reset bookmarks array and timer
            Array(this.RunningTime ? 0 : unset)
            this.Reset()
        } else {
            ; push marked time to bookmarks array
            this.Bookmarks.Push(markTime)
        }
        ; return bookmarks array or recorded time
        return (ResetTimer ? this.Bookmarks : markTime)
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [this.ToolTip]

    /**______
     * Displays a tool tip displaying the runtime of the program.
     * ______
     * @param {String} TipTimeFormat Formats the output of the time (see "Timer.Format" for more options). If set to "Now", displays the current time instead of the running time. Optional: to format the ouput time, add a space separator and the format string (in accordance to "Timer.Time") after "Now" (ex. "Now yyyy MM dd").
     * @param {Integer} TimerPeriod The period of the timer being called by this function.
     * @param {Integer} ToolTipNumber (Optional) The tool tip number for this timer's tool tip to use. Setting this value does not change this Timer's ToolTipNumber value.
     */
    ToolTip(TipTimeFormat?, TimerPeriod := 10, ToolTipNumber?) {
        if SubStr(TipTimeFormat ?? "", 1, 3) = "Now" {
            SetTimer(() {
                ToolTip(Timer.Time(,, SubStr(TipTimeFormat, 4) = " " ? "" : (SubStr(TipTimeFormat, 4) ? SubStr(TipTimeFormat, 5) : unset)),,, ToolTipNumber ?? this.ToolTipNumber)
            }, TimerPeriod)
        } else {
            SetTimer(() {
                ToolTip(Timer.Format(this.Now(), TipTimeFormat?),,, ToolTipNumber ?? this.ToolTipNumber)
            }, TimerPeriod)
        }
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Timer.Norm]

    /**______
     * This function converts different equivalent input values into the same consistent output value.
     * ______
     * @param {String} Input The string to convert into a normalised output unit.
     * ______
     * @returns {String} A single character string corresponding to the following:
     ** "u" - microseconds (default)
     ** "l" - milliseconds
     ** "s" - seconds
     ** "m" - minutes
     ** "h" - hours
     ** "d" - days
     ** "w" - weeks
     ** "n" - months
     ** "y" - years
     */
    static Norm(Input) {
        switch (Input := RegExReplace(StrLower(Input), '[^a-z]')) {
            case "", "u", "us", "micro", "micros", "usec", "useconds", "microseconds": return "u"
            case "l", "ms", "mill", "mills", "milli", "millis", "milliseconds", "msec", "mseconds": return "l"
            case "s", "sec", "second", "seconds": return "s"
            case "m", "min", "minute", "minutes": return "m"
            case "h", "hr", "hrs", "hours": return "h"
            case "d", "day", "days": return "d"
            case "w", "wk", "wks", "week", "weeks": return "w"
            case "n", "mo", "mos", "mon", "month", "months": return "n"
            case "y", "yr", "yrs", "year", "years": return "y"
            default:
                if StrLen(Input) = 1 {
                    return "u"
                } else {
                    return Timer.Norm(SubStr(Input, 1, 1))
                }
        }
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Timer.Proper]

    /**______
     * This function converts different input values into their proper abbreviations.
     * ______
     * @param {String} InputUnit The string to convert into a proper unit.
     * @param {String} FullName If true, will instead convert the input value to the full unit name.
     * ______
     * @returns {String} Returns the unit/label equivalent to the input unit.
     */
    static Proper(InputUnit, FullName := false) {
        switch Timer.Norm(InputUnit) {
            case "u": return FullName ? "microseconds" : "us"
            case "l": return FullName ? "milliseconds" : "ms"
            case "s": return FullName ? "seconds" : "s"
            case "m": return FullName ? "minutes" : "min"
            case "h": return FullName ? "hours" : "hr"
            case "d": return FullName ? "days" : "day"
            case "w": return FullName ? "weeks" : "wk"
            case "n": return FullName ? "months" : "mo"
            case "y": return FullName ? "years" : "yr"
        }
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Timer.Convert]

    /**______
     * Converts an amount of time to the same time in another unit.
     * ______
     * @param {Number} TimeIn The time to convert the unit of.
     * @param {String} UnitIn (Optional) Specifies what unit to treat the input time as. If omitted, "TimeIn" is assumed to be in microseconds.
     * @param {String} UnitOut (Optional) Specifies the unit of the output of this function. If omitted, returned time will be in milliseconds.
     * ______
     * @returns {Number} Returns an Integer if able, otherwise returns a float.
     */
    static Convert(TimeIn, UnitIn?, UnitOut := "l") => Round(+RegExReplace(TimeIn, '[^\d.-]') * Timer.TimeUnitFactors[Timer.Norm(UnitIn ?? "u")], 1) / Timer.TimeUnitFactors[Timer.Norm(UnitOut ?? "u")]

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Timer.Format]

    /**______
     * Returns a formatted version of the elapsed time (up to when this function was called).
     * ______
     * @param {Number} ElapsedTime The time (in microseconds) to format.
     * @param {String} ReturnFormat (Optional) Formats the output of the time based on the values below:
     ** "Full"  -   (n)yr (n)mo (n)wk (n)day (n)hr (n)min (n)s (n)ms (n)us
     ** "ISO"   -   P(n)Y(n)M(n)DT(n)H(n)M(n)S
     ** "Least" -   ~(n) unit
     ** ""      -   (Default) H*:MM:SS[.LLLUUU]
     * ______
     * @returns {String} The string representing the amount of time that has passed.
     */
    static Format(ElapsedTime, ReturnFormat?) {
        tArray := [], timeUnitIndex := 0
        ; format elapsed time
        while ++timeUnitIndex <= Timer.TimeUnits.Length {
            if timeUnitIndex = Timer.TimeUnits.Length {
                tArray.Push(Integer(Timer.Convert(ElapsedTime,, Timer.TimeUnits[timeUnitIndex])))
            } else {
                tArray.Push(Integer(Mod(Timer.Convert(ElapsedTime,, Timer.TimeUnits[timeUnitIndex]), Max(Timer.Convert(1, Timer.TimeUnits[timeUnitIndex + 1], Timer.TimeUnits[timeUnitIndex]), 1))))
            }
        }
        ; convert and return desired time string
        switch (ReturnFormat ?? "") {
            case "Full":
                timeStringOut := ""
                for i, t in tArray.Reverse {
                    timeStringOut .= t ? (t Timer.Proper(Timer.TimeUnits.Reverse[i]) " ") : ""
                }
                return RTrim(timeStringOut)
            case "ISO":
                tArray[6] += Integer(Timer.Convert(tArray[7], "w", "d"))
                tArray[3] += Timer.Convert(tArray[2], "l", "s") + Timer.Convert(tArray[1], "u", "s")
                return (
                    (IfAny(SubArray(tArray, "9-6")*) ? "P" : "") (tArray[9] ? tArray[9] "Y" : "") (tArray[8] ? tArray[8] "M" : "") (tArray[6] ? tArray[6] "D" : "")
                    (IfAny(SubArray(tArray, "5-1")*) ? "T" : "") (tArray[5] ? tArray[5] "H" : "") (tArray[4] ? tArray[4] "M" : "") (tArray[3] ? Format("{:.6f}S", tArray[3]) : "")
                )
            case "Least":
                for i, t in tArray.Reverse {
                    if t {
                        return Format("{1:.4g} {2:s}", Timer.Convert(ElapsedTime,, Timer.TimeUnits.Reverse[i]), Timer.Proper(Timer.TimeUnits.Reverse[i]))
                    }
                }
            default:
                timeStringOut := ""
                for i, t in tArray.Reverse {
                    switch {
                        case i = Timer.TimeUnits.Length, i = (Timer.TimeUnits.Length - 1): timeStringOut .= Format("{:03i}", t)  ; ms, us
                        case i = (Timer.TimeUnits.Length - 2): timeStringOut .= Format("{:02i}.", t)  ; s
                        case t, timeStringOut: timeStringOut .= Format("{:02i}:", t)  ; else
                    }
                }
                return timeStringOut
        }
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Timer.TimeFunction]

    /**______
     * Tracks the execution time of a function over a repeated number of times and returns the average (in milliseconds).
     * ______
     * @param {Function} TimeFunction The function to test.
     * @param {Integer} Iterations The number of times to execute the function.
     * @param {Boolean} ShowProgress If true (default), displays a tool til showing the progress of total function executions.
     * @param {VarRef} FuncOutput (Optional) The output from the executed function will be stored here.
     * @param {Any*} FuncParams Enter any parameters for the executed function.
     * ______
     * @returns {Number} Returns the average execution time (in microseconds) of the function over every iteration.
     */
    static TimeFunction(TimeFunction, Iterations := 1000, ShowProgress := true, &FuncOutput?, FuncParams*) {
        funcTimer := Timer()
        if ShowProgress {
            loop Iterations {
                ; display progress
                ToolTip(Format("{:i}% Complete", Floor(100 * A_Index / Iterations)),,, funcTimer.ToolTipNumber)
                ; log function execution time
                funcTimer.Reset()
                FuncOutput := TimeFunction(FuncParams*)
                funcTimer.Mark()
            }
        } else {
            loop Iterations {
                ; log function execution time
                funcTimer.Reset()
                FuncOutput := TimeFunction(FuncParams*)
                funcTimer.Mark()
            }
        }
        ; clean up and return results
        ToolTip(,,, funcTimer.ToolTipNumber)
        return funcTimer.Average
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Timer.Stress]

    /**______
     * Rapidly repeatedly performs Collatz calculations. Useful for warming-up/normalising CPU/Memory before timing functions.
     * ______
     * @param {Number} TimeLength Length of time to run this loop for.
     * @param {String} TimeUnits Unit of the length of time to run this function for (defaults to seconds).
     */
    static Stress(TimeLength := 15, TimeUnits?) {
        CollatzTimer := Timer()
        TestDigit := 1
        resetDigit := 0
        while (timeRemaining := Timer.Convert(TimeLength, TimeUnits ?? "s", "u") - CollatzTimer.Now()) > 0 {
            ToolTip(resetDigit ":" TestDigit "`nWarming up... " Timer.Format(timeRemaining),,, CollatzTimer.ToolTipNumber)
            TestDigit := Mod(TestDigit, 2) ? (TestDigit // 2): (3 * TestDigit + 1)
            resetDigit := (TestDigit < 100) ? (resetDigit + 1) : (0)
            if (TestDigit = 1) || (resetDigit >= 100) {
                ; 9223372036854775807 is the largest 64-bit integer
                TestDigit := Random(1, 2**63 - 1)
            }
        }
        ToolTip(,,, CollatzTimer.ToolTipNumber)
    }

    ; @endregion


    /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    ; @region [Timer.Time]

    /**______
     * Gives you the current time of the current day, optionally shifted by an amount of time.
     * ______
     * @param {String} Shift (Optional) An amount of time (dictated by ShiftUnits) to add to the current time. Set to a negative number to go back that far in time.
     * @param {String} ShiftUnits (Optional) The units associated with Shift that determine how much time is being added to the current time.
     * @param {String} Format (Optional) Format the return string in accordance with FormatTime.
     ** Set this to "ISO" to output the result in ISO standard time format (for Pacific / UTC-8 time).
     ** If Format is set to an empty string, the result will be the default format for FormatTime when format is unset.
     ** If this argument is unset, the function will return the resultant time in AHK standard YYYYMMDDHH24MISS format.
     * ______
     * @returns {String} Returns the time string.
     */
    static Time(Shift := 0, ShiftUnits := "u", Format?) {
        if IsSet(Format) {
            Format := Format = "ISO" ? "yyyy-MM-ddTHH:mm:ss-08:00" : (Format ? Format : unset)
            return FormatTime(DateAdd(A_Now, Timer.Convert(Shift, ShiftUnits, "s"), "Seconds"), Format?)
        } else {
            return DateAdd(A_Now, Timer.Convert(Shift, ShiftUnits, "s"), "Seconds")
        }
    }

    ; @endregion
}

; @endregion
