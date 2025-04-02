/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 */

public class print {
    static final int WIDTH = 120;   // constant output width
    static final char LB = '(';     // left border character
    static final char RB = ')';     // right border character
    static final char BC = '*';     // default line break character

    /**
     * This method aligns a string with the chosen direction.
     *
     * @param input     is the string input to be aligned
     * @param alignment is the alignment of the output text
     * @param spacer    character used for string padding
     * @param silent    determines whether output is printed or not
     * @return and prints out the complete string of aligned text
     */
    public static String er(String input, String alignment, char spacer, boolean silent) {
        String output;
        String s = "" + spacer;
        // build string based on alignment given
        switch (alignment.toLowerCase()) {
            case "center":
                int bufferL = (Math.abs(WIDTH - input.length()) - 2) / 2;
                String buffer = s.repeat(bufferL);
                output = LB + buffer + input + RB;
                while (output.length() <= WIDTH) {
                    output = output.substring(0, output.length() - 1) + s + RB;
                }
                break;
            case "right":
                // add a spacer between input and right border if absent
                if (input.isEmpty() || input.charAt(input.length() - 1) != spacer) {
                    output = LB + input + s + RB;
                } else {
                    output = LB + input + RB;
                }
                while (output.length() <= WIDTH) {
                    output = LB + s + output.substring(1);
                }
                break;
            case "left":
                // add a spacer between left border and input if absent
                if (input.isEmpty() || input.charAt(0) != spacer) {
                    output = LB + s + input + RB;
                } else {
                    output = LB + input + RB;
                }
                while (output.length() <= WIDTH) {
                    output = output.substring(0, output.length() - 1) + s + RB;
                }
                break;
            default:
                // otherwise, assume left aligned
                output = er(input, "left", spacer, true);
        }
        if (!silent) {
            System.out.println(output);
        }
        return output;
    }

    // Overload - three inputs (String, String, char)
    public static String er(String input, String alignment, char spacer) {
        return er(input, alignment, spacer, false);
    }

    // Overload - three inputs (String, String, boolean)
    public static String er(String input, String alignment, boolean silent) {
        return er(input, alignment, ' ', silent);
    }

    // Overload - two inputs (String, String)
    public static String er(String input, String alignment) {
        return er(input, alignment, ' ', false);
    }

    // Overload - two inputs (String, char)
    public static String er(String input, char spacer) {
        return er(input, "left", spacer, false);
    }

    // Overload - two inputs (String, boolean)
    public static String er(String input, boolean silent) {
        return er(input, "left", ' ', silent);
    }

    // Overload - one input (String)
    public static String er(String input) {
        return er(input, "left", ' ', false);
    }

    /**
     * This method is a specific version of "print.er" that prints a line break across the screen.
     *
     * @param spacer character used for string padding
     * @param silent determines whether output is printed or not
     * @return and prints out the complete string of aligned text
     */
    public static String brk(char spacer, boolean silent) {
        return er(Character.toString(spacer).repeat(WIDTH - 2), "left", spacer, silent);
    }

    // Overload - one input (char)
    public static String brk(char spacer) {
        return brk(spacer, false);
    }

    // Overload - one input (boolean)
    public static String brk(boolean silent) {
        return brk(BC, silent);
    }

    // Overload - no inputs
    public static String brk() {
        return brk(BC, false);
    }

    /**
     * This method prints a "new screen" with a title
     *
     * @param title  string of the title to be displayed
     * @param spacer character used for string padding
     * @param silent determines whether output is printed or not
     * @return and prints out the complete string of aligned text
     */
    public static String head(String title, char spacer, boolean silent) {
        String output = brk(spacer, true) + "\n" + er(title.toUpperCase(), "center", true) + "\n" + brk(spacer, true);
        if (!silent) {
            System.out.println("\n".repeat(40));
            System.out.println(output);
        }
        return output;
    }

    // Overload - two inputs (String, char)
    public static String head(String title, char spacer) {
        return head(title, spacer, false);
    }

    // Overload - two inputs (String, boolean)
    public static String head(String title, boolean silent) {
        return head(title, BC, silent);
    }

    // Overload - one input (String)
    public static String head(String title) {
        return head(title, BC, false);
    }
}
