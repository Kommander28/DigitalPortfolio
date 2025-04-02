#Requires AutoHotkey v2.1-alpha.16+
/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Sideline]
/*
 ────── THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ──────

 This script contains a handful of useful scripts that are standalone
 functions or added to existent AHK Objects.

*/
; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [ObjDefProp]

/** Indirectly assigns a Property to an object (useful for "primitive" prototypes such as "String" and "Any"). */
ObjDefProp := Object().DefineProp

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Any.Show]

/**______
 * Shows information about the variable. Useful for debugging.
 * @param {Any} this The variable to display information of.
 */
Show(this?) {
    global
    ; attempt to gather variable information
    RegExMatch(FileReadLines(Error(, -1).File, Error(, -1).Line), '(?|\W?(?<Var>\w+)\.Show\(\)|(?<Var>\S*\(.+\))\.Show\(\)|Show\((?<Var>.+?)\))', &VariableMatch)
    VariableShowOutput := "Name: " VariableMatch["Var"] "`n`n"
    if !IsSet(this) {
        VariableShowOutput .= "[[unset]]"
    } else {
        VariableShowOutput .= "Type:`n(( " Type(this) " ))`n`n"
        try VariableShowOutput .= "Value:`n" String(this)
    }
    ; show gathered variable information
    ; MsgBox(VariableShowOutput ? VariableShowOutput : "Error in parsing variable.", "Show Variable: " VariableMatch["Var"])
    MsgBox(VariableShowOutput, "Show Variable: " VariableMatch["Var"])
    ; try to return value to continue execution
    return this ?? unset
}

/** Adds this function to all Any type objects. */
ObjDefProp(Any.Prototype, "Show", {
    get: Show,
    call: Show
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Array.Average]

/**______
 * Calculates the average of the numbers in the array.
 * ______
 * @param {Array} this The array to find the average of.
 * @param {VarRef<Array>} RunningAverageArray (Optional) The running averages of the array are stored in this variable.
 * ______
 * @returns {Number} The average of the numerical values in this array. Returns an empty string if no numerical values were found.
 */
ArrayAverage(this, &RunningAverageArray?) {
    NumbersCountedInArray := 0
    RunningAverageOfNumbersCounted := 0.0
    RunningAverageArray := []
    ; calculate the running average of the array
    for TestArrayValue in this {
        if IsNumber(TestArrayValue ?? "") {
            NumbersCountedInArray++
            RunningAverageArray.Push(RunningAverageOfNumbersCounted := (RunningAverageOfNumbersCounted * (NumbersCountedInArray - 1) + Float(TestArrayValue)) / NumbersCountedInArray)
        }
    }
    return NumbersCountedInArray ? (RunningAverageOfNumbersCounted = Integer(RunningAverageOfNumbersCounted) ? Integer(RunningAverageOfNumbersCounted) : RunningAverageOfNumbersCounted) : ""
}

/** Adds this function to all Array type objects. */
Array.Prototype.DefineProp("Average", {
    get: ArrayAverage,
    call: ArrayAverage
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Array.AverageDiff]

/**______
 * Calculates the average of the difference of the numbers in the array.
 * ______
 * @param {Array} this The array to find the average difference of.
 * @param {VarRef<Array>} RunningAvgDiffArray (Optional) The running average differences of the array are stored in this variable.
 * ______
 * @returns {Float} The average of the difference of the numerical values in this array. Returns an empty string if no numerical values were found.
 */
ArrayAverageDiff(this, &RunningAvgDiffArray?) {
    RunningDifferenceArray := []
    ; take the difference of numbers in array
    for TestArrayValue in this {
        if IsNumber(TestArrayValue ?? "") {
            if IsSet(LastNumberCounted) {
                RunningDifferenceArray.Push(Abs(LastNumberCounted - Float(TestArrayValue)))
            }
            LastNumberCounted := Float(TestArrayValue)
        }
    }
    ; return the average of the array of differences
    if RunningDifferenceArray.Length = 0 {
        return IsSet(LastNumberCounted) ? (LastNumberCounted = Integer(LastNumberCounted) ? Integer(LastNumberCounted) : LastNumberCounted) : ""
    } else {
        return ArrayAverage(RunningDifferenceArray, &RunningAvgDiffArray)
    }
}

/** Adds this function to all Array type objects. */
Array.Prototype.DefineProp("AverageDiff", {
    get: ArrayAverageDiff,
    call: ArrayAverageDiff
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Array.Count]

/**______
 * Counts the number of existing and non-false values in this array.
 * ______
 * @param {Array} this The array to count the values of.
 * ______
 * @returns {Float} Returns the count of set, non-empty, non-zero values in the array.
 */
Array_Count(this) {
    CountOfArrayValues := 0
    for ValueInArray in this {
        CountOfArrayValues += !!(ValueInArray ?? 0)
    }
    return CountOfArrayValues
}

/** Adds this function to all Array type objects. */
Array.Prototype.DefineProp("Count", {
    get: Array_Count,
    call: Array_Count
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Array.HasValue]

/**______
 * Compares a value to an array, returns true if value is found in the input array.
 * ______
 * @param {Array} this The array to search for the target in.
 * @param {Any} Target Value to compare against contents of the input array.
 * @param {VarRef<Integer>} Index (Optional) This VarRef stores the index of the first place where the target value was located (returns 0 if target was not found).
 * @param {Boolean} Strict Set to true for strict comparison (string case and variable type must match to yield true).
 * ______
 * @returns {Boolean} Returns true if value is found anywhere in array.
 */
Array_HasValue(this, Target?, &Index?, Strict := false) {
    Index := 0
    ; compare value to array values
    if IsSet(Target) {
        for searchIndex, arrayValueAtIndex in this {
            if (!Strict && (Target = arrayValueAtIndex)) || (Strict && (Target == arrayValueAtIndex) && (Type(Target) == Type(arrayValueAtIndex))) {
                Index := searchIndex
                return true
            }
        }
    } else {
        ; look for unset value in array
        for searchIndex, arrayValueAtIndex in this {
            if !IsSet(arrayValueAtIndex) {
                Index := searchIndex
                return true
            }
        }
    }
    ; target not found in array
    return false
}

/** Adds this function to all Array type objects. */
Array.Prototype.DefineProp("HasValue", {
    get: Array_HasValue,
    call: Array_HasValue
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Array.Reverse]

/**______
 * Reverses all of the values in this array.
 * ______
 * @param {Array} this The array to reverse the order of the values of.
 * ______
 * @returns {Float} Returns the reversed array.
 */
Array_Reverse(this) {
    arrayToReverse := this.Clone()
    reversedArray := []
    reversedArray.Capacity := arrayToReverse.Length
    while arrayToReverse.Length {
        reversedArray.Push(arrayToReverse.Pop())
    }
    return reversedArray
}

/** Adds this function to all Array type objects. */
Array.Prototype.DefineProp("Reverse", {
    get: Array_Reverse,
    call: Array_Reverse
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Array.Sort]

/**______
 * Sorts the values of this array in an order corresponding with "Options".
 * ______
 * @param {Array} this The array to sort the order of.
 * @param {String} Options A string consisting of zero or more of the following letters (in any order, the letters can be separated by spaces):
 ** C: Case-sensitive sorting (if there is an N option, this option is ignored). If both C and CL are omitted, the uppercase letters A-Z are considered equivalent to their corresponding lowercase forms in the sorting.
 ** CL: Case-insensitive sorting based on the current user locale. For example, most English and Western European regions equate the letters AZ and ANSI letters (such as Ä and Ü) to their lowercase forms. This method also uses " Word sorting", it handles hyphens and apostrophes in this way (words like "coop" and "co-op" are kept together). According to the content of the sorted item, its execution performance is indistinguishable than the default The method is 1 to 8 times worse.
 ** Dx: Specify x as the delimiter, which determines the start and end position of each item. If this option does not exist, x defaults to a newline character (`n), so when the string is lined with LF (`n) Or CR+LF(`r`n) can be sorted correctly at the end.
 ** N: Number sorting: each item is assumed to be sorted as a number instead of a string (for example, if this option does not exist, the string 233 is considered to be less than the string 40 according to the alphabetical order). Decimal and hexadecimal Strings (such as 0xF1) are considered to be numbers. Strings that do not start with a number are treated as zeros in sorting. The number is treated as a 64-bit floating point value, so that each digit in the fractional part ( If there is).
 ** Pn: Sort items according to character position n (not using hexadecimal n) (each item is compared from the nth character). If this option is not used, n defaults to 1, which is the first The position of the character. Sorting will start from the nth character to compare each string with other strings. If n is greater than the length of any string, the string will be treated as blank when sorting. When with the option When N (number sort) is used together, the character position of the string will be used, which is not necessarily the same as the number position of the number.
 ** R: Reverse sorting (alphabetical or numerical sorting according to other options).
 ** Random: Random sorting. This option will cause other options except D, Z and U to be ignored (Nevertheless, N, C and CL will still affect the detection of duplicates).
 ** U: Remove duplicate items in the list so that each item is unique. If the C option is valid, the case of the items must match to be considered equivalent. If the N option is valid, then items like 2 Will be considered as a duplicate of 2.0. If the Pn or \(backslash) option is valid, the entire item must be the same to be regarded as a duplicate, not just a substring used for sorting. If the Random option or custom sorting Effective, the duplicates will be deleted only when there are adjacent duplicates in the sorting result. For example, when "A|B|A" is sorted randomly, the result may contain one or two A's.
 ** Z: To understand this option, please consider the variable whose content is RED`nGREEN`nBLUE`n. If the Z option does not exist, the last newline character (`n) will be considered as part of the last item, so the variable There are only three items. But if option Z is specified, the last `n (if it exists) will be considered to separate the last empty item in the list, so there are four items in the variable (the last one is empty).
 ** \: Sort according to the substring after the last backslash in each item. If the item does not contain a backslash, the entire item is used as the sorted substring. This option can be used to sort individual file names (I.e. does not include the path)
 * ______
 * @returns {Array} The sorted version of this array. This function will only return a value if "InPlace" is false.
 */
Array_Sort(this, Options?) {
    outStr := ""
    for v in this {
        outStr .= v "`n"
    }
    return StrSplit(Sort(RTrim(outStr, "`n"), Options?), "`n")
}

/** Adds this function to all Array type objects. */
Array.Prototype.DefineProp("Sort", {
    get: Array_Sort,
    call: Array_Sort
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Array.SubArr]

/**______
 * Returns an array of values from another array.
 * ______
 * @param {Array} this The array to extract values from.
 * @param {Integer, String} ArrayIndicies Any amount of values (or ranges of values) to extract from this array (valid input examples: "1", "7-8", "6-4", "3", "3", etc.).
 * ______
 * @returns {Array} an array of the extracted values designated by the input indicies.
 */
SubArray(this, ArrayIndicies*) {
    OutputSubArray := []
    ; add all input indicies to output array
    for thisArrayIndex in ArrayIndicies {
        ; validate index values and ranges
        thisArrayIndex := RegExReplace(thisArrayIndex, '[^\d-]')
        if (hyphenChrIndex := InStr(thisArrayIndex, "-")) {
            SubArrayLoopIndexCount := +SubStr(thisArrayIndex, 1, hyphenChrIndex - 1)
            SubArrayLoopTargetIndex := +SubStr(thisArrayIndex, hyphenChrIndex + 1)
            ; push a range of values to the output array
            if SubArrayLoopIndexCount < SubArrayLoopTargetIndex {
                while SubArrayLoopIndexCount <= SubArrayLoopTargetIndex {
                    OutputSubArray.Push(this[SubArrayLoopIndexCount++])
                }
            } else {
                while SubArrayLoopIndexCount >= SubArrayLoopTargetIndex {
                    OutputSubArray.Push(this[SubArrayLoopIndexCount--])
                }
            }
        } else {
            ; push value at index to output array
            OutputSubArray.Push(this[+thisArrayIndex])
        }
    }
    ; return new subarray
    return OutputSubArray
}

/** Adds this function to all Array type objects. */
Array.Prototype.DefineProp("SubArr", {
    get: SubArray,
    call: SubArray
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Array.ToString]

/**______
 * Returns the array as a string.
 * ______
 * @param {Array} this This array, to be converted to a string.
 * @param {String} Delimiter The string that separates the values of the input array in the return string.
 ** Default = ", "
 * @param {Boolean} Pretty If true, surrounds the return string in brackets.
 * @param {Integer} Truncate (Optional) If included, limits the size of the array before string-ifying.
 * ______
 * @returns {String} Returns the inputted array represented as a string.
 */
Array_ToString(this, Delimiter := ", ", Pretty := false, Truncate?) {
    _array := this.Clone()
    ; truncate length (if requested)
    if IsSet(Truncate) && (_array.Length > Truncate) {
        _array.Length := Truncate
    } else {
        Truncate := unset
    }
    arrayString := ""
    for _arrayValue in _array {
        if !IsSet(_arrayValue) {
            arrayString .= "[[unset]]"
        } else {
            try {
                arrayString .= String(_arrayValue)
            } catch {
                arrayString .= "(( " Type(_arrayValue) " ))"
            }
        }
        arrayString .= Delimiter
    }
    arrayString := (Pretty && IsSet(Truncate)) ? arrayString "..." : RTrim(arrayString, Delimiter)
    return Pretty ? "[" arrayString "]" : arrayString
}

/** Adds this function to all Array type objects. */
Array.Prototype.DefineProp("ToString", {
    get: Array_ToString,
    call: Array_ToString
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Array.Transform]

/**______
 * Alters every value in an array according to an inputted formula.
 * ______
 * @param {Array} this This array, to be transformed.
 * @param {Function} TransformFunction The function to enact on every value ithin the array.
 * ______
 * @returns {Array} Returns the altered array.
 */
Array.Prototype.Transform := (this, TransformFunction) {
    TransformedOutputArray := []
    for InputArrayValue in this {
        TransformedOutputArray.Push(TransformFunction(InputArrayValue))
    }
    return TransformedOutputArray
}

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Array.Unique]

/**______
 * Reduces all values in an array to only unique values.
 * ______
 * @param {Array} this The array to extract unique values from.
 * ______
 * @returns {Array} Returns an array of unique values.
 */
Array.Prototype.Unique := (this) {
    UniqueOutputArray := []
    for InputArrayValue in this {
        if !UniqueOutputArray.HasValue(InputArrayValue) {
            UniqueOutputArray.Push(InputArrayValue)
        }
    }
    return UniqueOutputArray
}

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [CloseWindows]

/**______
 * Closes a file if it is open in any window.
 * @param {String} PartialFileName The partial match of a file name to search for in the titles of all open windows.
 */
CloseWindows(PartialFileName) {
    if WinExist(PartialFileName) {
        WinActivate
        try {
            WinClose
        } catch {
            try {
                WinKill
            } catch {
                throw MethodError("Unable to close target window: `"" PartialFileName "`"", -1)
            }
        }
        CloseWindows(PartialFileName)
    }
}

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [File.ReadLines]

/**______
 * Read a line of text from the file and move the file pointer forward.
 * @param {File, String} TargetThe File object or a path to a file to read the next lines from.
 * @param {Integer} LineNumber (Optional) If included, will advance the file pointer and return that line from the file.
 * @param {Boolean} AllLines If true, will return all the lines read as a single string. Otherwise if false (default) will only return the single line requested.
 * ______
 * @returns {String} Returns a line (or lines) of text from the file.
 */
FileReadLines(Target, LineNumber?, AllLines := false) {
    ; open file if Target is a filepath
    Target := Target is String ? FileOpen(Target, "r") : Target
    ; read one line if no line number specified
    if !IsSet(LineNumber) {
        return Target.ReadLine()
    }
    returnFileText := ""
    ; advance the number of lines requested and return the last line
    loop (LineNumber) {
        ; record and return one or all lines read
        returnFileText := (AllLines ? returnFileText : "") Target.ReadLine() "`n"
        ; check if there are any remaining lines to read
        if Target.AtEOF {
            break
        }
    }
    return RTrim(returnFileText, "`n")
}

/** Adds this function to all File type objects. */
File.Prototype.DefineProp("ReadLines", {
    get: FileReadLines,
    call: FileReadLines
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [FileWrite]

/**______
 * Writes a string to a text file, overriding any existing file.
 * ______
 * @param {String} FileTextString The text to be written to the file.
 * @param {String} FilePathName A path to a file to be written to. This will create the file if necessary, and will overwrite an existing file of the same name.
 */
FileWrite(FileTextString, FilePathName) {
    CloseWindows(FilePathName)
    targetWriteFile := FileOpen(FilePathName, "w")
    targetWriteFile.Write(FileTextString)
    targetWriteFile.Close()
}

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [IfAll]

/**______
 * Checks a variable amount of parameters and returns true only if all parameters are true.
 * ______
 * @param {Any*} CheckStatements Any parameters to evaluate if they are true or false.
 * ______
 * @returns {Boolean} Returns true if all values inputted are true.
 */
IfAll(CheckStatements*) {
    for CheckValue in CheckStatements {
        if !CheckValue {
            return false
        }
    }
    return true
}

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [IfAny]

/**______
 * Checks a variable amount of parameters and returns true if any parameter is true.
 * ______
 * @param {Any*} CheckStatements Any parameters to evaluate if they are true or false.
 * ______
 * @returns {Boolean} Returns true if any value inputted is true.
 */
IfAny(CheckStatements*) {
    for CheckValue in CheckStatements {
        if CheckValue {
            return true
        }
    }
    return false
}

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Map.GetKeys]

/**______
 * Gets all of the Keys from a map.
 * ______
 * @param {Map} this The map to extract the keys from.
 * ______
 * @returns {Array} Returns an array of the keys of this map.
 */
Map_GetKeys(this) => [this*]

/** Adds this function to all Map type objects. */
Map.Prototype.DefineProp("GetKeys", {
    get: Map_GetKeys,
    call: Map_GetKeys
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Map.GetValues]

/**______
 * Gets all of the Values from a map.
 * ______
 * @param {Map} this The map to extract the values from.
 * ______
 * @returns {Array} Returns an array of the values of this map.
 */
Map_GetValues(this) {
    MapValueArrayOutputArray := []
    for MapKeyInThisMap in [this*] {
        MapValueArrayOutputArray.Push(this.Get(MapKeyInThisMap))
    }
    return MapValueArrayOutputArray
}

/** Adds this function to all Map type objects. */
Map.Prototype.DefineProp("GetValues", {
    get: Map_GetValues,
    call: Map_GetValues
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Map.Mirror]

/**______
 * Swaps the keys/values in an map. If values are not unique, the Count of the map will reduce and the latest key of the repeating value will become the value of that key.
 * ______
 * @param {Map} this The map to perform the mirror transformation on.
 * ______
 * @returns {Map} Returns the mirrored map.
 */
Map_Mirror(this) {
    mirror := Map()
    for keyInValueOut, valueInKeyOut in this.Clone() {
        mirror.Set(valueInKeyOut, keyInValueOut)
    }
    return mirror
}

/** Adds this function to all Map type objects. */
Map.Prototype.DefineProp("Mirror", {
    get: Map_Mirror,
    call: Map_Mirror
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Map.ToString]

/**______
 * Returns the Map as a string.
 * ______
 * @param {Map} this This Map, to be converted to a string.
 * @param {String} KeyValueDelimiter The string that separates the keys from the values of the input map in the return string.
 ** Default = ": "
 * @param {String} PairDelimiter The string that separates the key/value pairs of the input map in the return string.
 ** Default = ", "
 * @param {Boolean} Pretty (Default) If true, surrounds the return string in braces.
 * @param {Integer} Truncate (Optional) If included, limits the size of the Map before string-ifying.
 * ______
 * @returns {String} Returns the inputted Map represented as a string.
 */
Map_ToString(this, KeyValueDelimiter := ": ", PairDelimiter := ", ", Pretty := false, Truncate?) {
    ; truncate capacity (if requested)
    if IsSet(Truncate) {
        _map := this.Clone()
        _map.Capacity := Truncate
    }
    mapString := ""
    for stringMapKey, stringMapValue in (_map ?? this) {
        mapString .= stringMapKey KeyValueDelimiter 
        if !IsSet(stringMapValue) {
            mapString .= "[[unset]]"
        } else {
            try {
                mapString .= String(stringMapValue)
            } catch {
                mapString .= "(( " Type(stringMapValue) " ))"
            }
        }
        mapString .= PairDelimiter
    }
    ; return resultant string
    return (Pretty ? "{" : "") (IsSet(Truncate) ? (mapString "...") : RTrim(mapString, PairDelimiter)) (Pretty ? "}" : "")
}

/** Adds this function to all Map type objects. */
Map.Prototype.DefineProp("ToString", {
    get: Map_ToString,
    call: Map_ToString
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Object.ToString]

/**______
 * Returns the properties of the object as a string.
 * ______
 * @param {Map} this This object, to be converted to a string.
 * @param {String} KeyValueDelimiter The string that separates the keys from the values of the input object in the return string.
 ** Default = ": "
 * @param {String} PairDelimiter The string that separates the key/value pairs of the input object in the return string.
 ** Default = ", "
 * @param {Boolean} Pretty (Default) If true, surrounds the return string in braces.
 * @param {Integer} Truncate (Optional) If included, limits the size of the object before string-ifying.
 * ______
 * @returns {String} Returns the inputted object represented as a string.
 */
Object_ToString(this, KeyValueDelimiter := ": ", PairDelimiter := ", ", Pretty := true, Truncate?) {
    ; truncate capacity (if requested)
    if IsSet(Truncate) {
        _object := this.Clone()
        _object.Capacity := Truncate
    }
    objectString := ""
    for stringObjectKey, stringObjectValue in (_object ?? this).OwnProps() {
        objectString .= stringObjectKey KeyValueDelimiter 
        if !IsSet(stringMapValue) {
            objectString .= "[[unset]]"
        } else {
            try {
                objectString .= String(stringObjectValue)
            } catch {
                objectString .= "(( " Type(stringObjectValue) " ))"
            }
        }
        objectString .= PairDelimiter
    }
    ; return resultant string
    return (Pretty ? "{" : "") (IsSet(Truncate) ? (objectString "...") : RTrim(objectString, PairDelimiter)) (Pretty ? "}" : "")
}

/** Adds this function to all Map type objects. */
Object.Prototype.DefineProp("ToString", {
    get: Object_ToString,
    call: Object_ToString
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [String.Length]

/**______
 * Makes using StrLen ever so slightly more convienient.
 * ______
 * @param {String} this The string to measure the length of.
 * @param {Integer} Value The length to set this string to (see "SubStr" for more information).
 * ______
 * @returns {Integer} Returns the length of the string.
 */
ObjDefProp(String.Prototype, "Length", {
    get: StrLen,
    set: (this, Value) {
        global
        RegExMatch(FileReadLines(Error(, -1).File, Error(, -1).Line), '\W?(?<Var>\w+)\.Length|(?<Str>\S*".+")\.Length', &StringLengthMatchObject)
        %StringLengthMatchObject.Var% := SubStr(thisRef := this, 1, Value)
    },
    call: StrLen
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [String.Repeat]

/**______
 * Repeats the string.
 * ______
 * @param {String} this The string to be repeated.
 * @param {Integer} Times The amount of times to repeat the string (defaults to 2 if not included).
 * @param {String} Delimiter (Optional) If included, inserts this value between each repetition of the main string.
 * ______
 * @returns {String} Returns the repeated string.
 */
StrRpt(this, Times := 2, Delimiter?) => RTrim(StrReplace(Format("{:" Times "}", ""), " ", this (Delimiter ?? "")), Delimiter ?? "")

/** Adds this function to all String type objects. */
ObjDefProp(String.Prototype, "Repeat", {
    get: StrRpt,
    call: StrRpt
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [String.Reverse]

/**______
 * Reverses this string.
 * ______
 * @param {String} this The string to be reversed.
 * ______
 * @returns {String} Returns the reversed string.
 */
StrRev(this) {
	loop parse this {
        ReversedStringOutString := A_LoopField (ReversedStringOutString ?? "")
    }
    return ReversedStringOutString
}

/** Adds this function to all String type objects. */
ObjDefProp(String.Prototype, "Reverse", {
    get: StrRev,
    call: StrRev
})

; @endregion


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━/━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
; @region [Sum]

/**______
 * Calculates the sum of the numbers in a given set of values.
 * ______
 * @param {Any*} InputValues The values to find the sum of.
 * ______
 * @returns {Number} The sum of the numerical compunents of the input values. Returns 0 if no numerical values were found.
 */
Sum(InputValues*) {
    InputValuesTotal := 0
    for TestInputValue in InputValues {
        switch Type(TestInputValue ?? "") {
            case "Array", "Map": InputValuesTotal += TestInputValue.Sum()
            default:
                if IsNumber(TestInputValue ?? "") {
                    InputValuesTotal += Number(TestInputValue)
                }
        }
    }
    return InputValuesTotal
}

/** Adds this function to all Array type objects. */
Array.Prototype.DefineProp("Sum", {
    get: Sum,
    call: (this) => Sum(this*)
})

/** Adds this function to all Map type objects. */
Map.Prototype.DefineProp("Sum", {
    get: Sum,
    call: (this) => Sum((this.GetValues)*)
})

; @endregion
