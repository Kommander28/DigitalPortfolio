/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 */

public class print {
    static final int WIDTH = EncodeDecodePeterson.WIDTH;

    /**
     * This method aligns a string with the chosen direction.
     *
     * @param input     is the string input to be aligned
     * @param alignment is the alignment of the output text
     * @return is the complete string of aligned text
     */
    public static String er(String input, String alignment) {
        String output;
        // build string if center aligned
        if (alignment.equalsIgnoreCase("center")) {
            int bufferL = (WIDTH - input.length() - 2) / 2;
            String buffer = " ".repeat(bufferL);
            output = "|" + buffer + input + "|";
            while (output.length() <= WIDTH) {
                output = output.substring(0, output.length() - 1) + " |";
            }
            // build string if right aligned
        } else if (alignment.equalsIgnoreCase("right")) {
            output = "|" + input + "|";
            while (output.length() <= WIDTH) {
                output = "| " + output.substring(1);
            }
            // otherwise, assume left aligned
        } else {
            output = "|" + input + "|";
            while (output.length() <= WIDTH) {
                output = output.substring(0, output.length() - 1) + " |";
            }
        }
        return output;
    }

    // Overload - one input (String)
    public static String er(String input) {
        return er(input, "left");
    }
}