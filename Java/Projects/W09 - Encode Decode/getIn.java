/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 */

import java.util.*;

public class getIn {
    static final Scanner KB = new Scanner(System.in);

    /**
     * This method returns only a valid "int" from a user's input.
     *
     * @param outLength length to trim output int to
     * @return is the user's input as a valid int
     */
    public static int getInt(int outLength) {
        String uStr;
        int uInt;
        while (true) {
            System.out.print("| Please enter a number: ");
            uStr = KB.nextLine().replaceAll("[^0-9]", "");
            if (!uStr.isEmpty()) {
                if (outLength > 0) {
                    uInt = Integer.parseInt(uStr.substring(0, outLength));
                } else {
                    uInt = Integer.parseInt(uStr);
                }
                break;
            }
        }
        return uInt;
    }

    // Overload - no inputs
    public static int getInt() {
        return getInt(0);
    }

    /**
     * This method returns only a valid "char" from a user's input.
     *
     * @return is the user's input as a valid char
     */
    public static char getChar(String message) {
        String uStr;
        char uChar;
        while (true) {
            System.out.print(message);
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
        return getChar("| Please enter a letter: ");
    }

    /**
     * This method returns only a valid "String" from a user's input.
     *
     * @return is the user's input as a valid String
     */
    public static String getString(String message, boolean upperOnly, boolean noSpace) {
        String uStr = "";
        while (uStr.isEmpty()) {
            System.out.print(message);
            if (upperOnly) {
                uStr = KB.nextLine().toUpperCase();
            } else {
                uStr = KB.nextLine();
            }
        }
        if (noSpace) {
            return uStr.replaceAll(" ", "");
        } else {
            return uStr;
        }
    }

    // Overload - two inputs (boolean, boolean)
    public static String getString(boolean upperOnly, boolean noSpace) {
        return getString("| Please enter a string: ", upperOnly, noSpace);
    }

    // Overload - one input (String)
    public static String getString(String message) {
        return getString(message, false, false);
    }

    // Overload - no inputs
    public static String getString() {
        return getString("| Please enter a string: ", false, false);
    }
}
