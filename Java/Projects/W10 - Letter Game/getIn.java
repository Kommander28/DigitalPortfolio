/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 */

import java.util.Scanner;

/**
 * This class requests for an input and returns a value based on the function called.
 */
public class getIn {
    static final Scanner KB = new Scanner(System.in);   // keyboard Scanner object for user input
    static final char LB = print.LB;                    // left border character

    /**
     * This method returns a valid positive int from a user's input.
     * This method only returns positive integers.
     *
     * @param rangeLow  is the lower bound (inclusive) for valid return values
     * @param rangeHigh is the upper bound (inclusive) for valid return values
     * @param message   is the prompt to be displayed to the user
     * @param outLength length to trim output int to
     * @return is the user's input as a valid int
     */
    public static int getPosInt(int rangeLow, int rangeHigh, String message, int outLength) {
        // initialize variables
        String uStr;
        int uInt;
        // check to make sure rangeLow < rangeHigh
        if (rangeLow > rangeHigh) {
            int temp = rangeLow;
            rangeLow = rangeHigh;
            rangeHigh = temp;
        }
        // loop until a valid input is achieved
        while (true) {
            // add a space between message and input if absent
            if (message.charAt(message.length() - 1) != ' ') {
                System.out.print(LB + " " + message + " ");
            } else {
                System.out.print(LB + " " + message);
            }
            // get a string from the user, removing non-numeric characters
            uStr = KB.nextLine().replaceAll("[^0-9]", "");
            // return 0 if string is empty, otherwise return an int that is not longer than outLength
            if (uStr.isEmpty()) {
                uInt = 0;
            } else if (uStr.length() > outLength) {
                uInt = Integer.parseInt(uStr.substring(uStr.length() - (outLength + 1)));
            } else {
                uInt = Integer.parseInt(uStr);
            }
            // check to make sure int value is between the requested values
            if (rangeLow == rangeHigh) {
                return uInt;
            } else {
                if (uInt >= rangeLow && uInt <= rangeHigh) {
                    return uInt;
                } else {
                    print.er(String.format("Error: value must be between %d and %d. Please try another value.",
                            rangeLow, rangeHigh));
                }
            }
        }
    }

    // Overload - three inputs (int, int, String)
    public static int getPosInt(int rangeLow, int rangeHigh, String message) {
        return getPosInt(rangeLow, rangeHigh, message, 0);
    }

    // Overload - two inputs (int, int)
    public static int getPosInt(int rangeLow, int rangeHigh) {
        return getPosInt(rangeLow, rangeHigh, "Please enter a number:", 0);
    }

    // Overload - two inputs (String, int)
    public static int getPosInt(String message, int outLength) {
        return getPosInt(0, 0, message, outLength);
    }

    // Overload - one input (String)
    public static int getPosInt(String message) {
        return getPosInt(0, 0, message, 0);
    }

    // Overload - one input (int)
    public static int getPosInt(int outLength) {
        return getPosInt(0, 0, "Please enter a number:", outLength);
    }

    // Overload - no inputs
    public static int getPosInt() {
        return getPosInt(0, 0, "Please enter a number:", 0);
    }

    /**
     * This method returns a valid char from a user's input.
     *
     * @param message is the prompt to be displayed to the user
     * @return is the user's input as a valid char
     */
    public static char getChar(String message) {
        String uStr;
        char uChar;
        // loop until a valid input is achieved
        while (true) {
            // add a space between message and input if absent
            if (message.charAt(message.length() - 1) != ' ') {
                System.out.print(LB + " " + message + " ");
            } else {
                System.out.print(LB + " " + message);
            }
            // get user input and return if a valid character is entered
            uStr = KB.nextLine().toUpperCase();
            if (!uStr.isEmpty()) {
                uChar = uStr.toCharArray()[0];
                break;
            }
        }
        return uChar;
    }

    // Overload - no inputs
    public static char getChar() {
        return getChar("Please enter a letter:");
    }

    /**
     * This method returns a valid String from a user's input.
     *
     * @param message   is the prompt to be displayed to the user
     * @param upperOnly returns an input converted entirely to uppercase
     * @param noSpace   removes all whitespace characters from user input
     * @return is the user's input as a valid String
     */
    public static String getString(String message, boolean upperOnly, boolean noSpace) {
        String uStr = "";
        // loop until a valid input is achieved
        while (uStr.isEmpty()) {
            // add a space between message and input if absent
            if (message.charAt(message.length() - 1) != ' ') {
                System.out.print(LB + " " + message + " ");
            } else {
                System.out.print(LB + " " + message);
            }
            // get user input, then convert it to uppercase if requested
            if (upperOnly) {
                uStr = KB.nextLine().toUpperCase();
            } else {
                uStr = KB.nextLine();
            }
        }
        // remove whitespace characters if requested
        if (noSpace) {
            return uStr.replaceAll("\\s", "");
        } else {
            return uStr;
        }
    }

    // Overload - two inputs (boolean, boolean)
    public static String getString(boolean upperOnly, boolean noSpace) {
        return getString("Please enter a string:", upperOnly, noSpace);
    }

    // Overload - one input (String)
    public static String getString(String message) {
        return getString(message, false, false);
    }

    // Overload - no inputs
    public static String getString() {
        return getString("Please enter a string:", false, false);
    }

    /**
     * This method turns a yes/no, true/false, or y/n type value into a boolean value.
     *
     * @param message     is the prompt to be displayed to the user
     * @param invert      this will invert the returned value
     * @param acceptEmpty accepts an empty string and returns the default value of false
     * @param strict      enables a strict requirement for either a yes-type or no-type answer
     * @return is the user's input as a boolean value
     */
    public static boolean getBoolean(String message, boolean invert, boolean acceptEmpty, boolean strict) {
        // initialize variables
        String uStr;
        String[] trueValues = {"true", "yes", "y", "okay", "sure"};
        String[] falseValues = {"false", "no", "n", "nope", "nah"};
        // loop until a valid input is achieved
        while (true) {
            // add a space between message and input if absent
            if (message.charAt(message.length() - 1) != ' ') {
                System.out.print(LB + " " + message + " ");
            } else {
                System.out.print(LB + " " + message);
            }
            // get a string from the user, converting the string to lowercase
            uStr = KB.nextLine();
            // return true based on yes-type input match
            for (String s : trueValues) {
                if (uStr.equalsIgnoreCase(s)) {
                    return !invert;  // return true
                }
            }
            // Check: if string is empty, defaults to no
            if (uStr.isEmpty()) {
                if (acceptEmpty) {
                    return invert;  // return false
                } else {
                    print.er("Error: value must not be empty. Please enter a value.");
                    continue;
                }
            }
            // Check: if strict is enabled, only return false based on no-type input match
            if (strict) {
                for (String s : falseValues) {
                    if (uStr.equalsIgnoreCase(s)) {
                        return invert;  // return false
                    }
                }
                // if no match, display a message
                print.er("Error: value must be either a \"yes\" or \"no\" answer. Please try again.");
                continue;
            }
            // by default, return false
            return invert;  // return false
        }
    }

    // Overload - three inputs (boolean, boolean, boolean)
    public static boolean getBoolean(boolean invert, boolean acceptEmpty, boolean strict) {
        return getIn.getBoolean("Please enter yes or no:", invert, acceptEmpty, strict);
    }

    // Overload - two inputs (String, boolean)
    public static boolean getBoolean(String message, boolean invert) {
        return getIn.getBoolean(message, invert, true, false);
    }

    // Overload - one input (String)
    public static boolean getBoolean(String message) {
        return getIn.getBoolean(message, false, true, false);
    }

    // Overload - one input (boolean)
    public static boolean getBoolean(boolean invert) {
        return getIn.getBoolean("Please enter yes or no:", invert, true, false);
    }

    /**
     * This method does not return any value, but instead waits for the "Enter" key to be pressed by the user.
     *
     * @param message is the prompt to be displayed to the user
     */
    public static void getNull(String message) {
        // add a space between message and input if absent
        if (message.charAt(message.length() - 1) != ' ') {
            System.out.print(LB + " " + message + " ");
        } else {
            System.out.print(LB + " " + message);
        }
        // wait until the Enter key is pressed, then return
        KB.nextLine();
    }

    // Overload - no inputs
    public static void getNull() {
        getNull("Press the \"Enter\" key to continue...");
    }
}
