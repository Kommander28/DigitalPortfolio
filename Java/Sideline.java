/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * This class holds several useful functions.
 */
public class Sideline {
    /**
     * This method rounds numbers to a specific number of decimal places.
     *
     * @param input         number to round
     * @param decimalPlaces number of decimal places to round to
     * @return (double) a rounded version of the input
     */
    public static double round(double input, int decimalPlaces) {
        return (double) Math.round(input * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
    }

    // overload - 2 input (int, int)
    public static double round(int input, int decimalPlaces) {
        return round((double) input, decimalPlaces);
    }

    // overload - 1 input (double)
    public static double round(double input) {
        return round(input, 2);
    }

    // overload - 1 input (int)
    public static double round(int input) {
        return round((double) input, 2);
    }

    /**
     * This method capitalizes the first word of every letter in a string.
     *
     * @param input String to convert to proper/title case
     * @return (String) corrected string
     */
    public static String proper(String input) {
        StringBuilder output = new StringBuilder();
        int target = 0;
        Pattern p = Pattern.compile("\\s");
        Matcher m = p.matcher(input);
        do {
            output.append(input.substring(target++, target).toUpperCase());
            if (m.find()) {
                output.append(input, target, m.end());
                target = m.end();
            } else {
                output.append(input, target, input.length());
                break;
            }
        } while (true);
        return output.toString();
    }
}
