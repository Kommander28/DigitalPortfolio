/*
 * THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR STATED BELOW (Tim Peterson).
 *        AUTHOR:    Tim Peterson
 */

import java.util.Objects;

/**
 * This class prints a line of text built to certain dimensions.
 */
public class PrettyPrinter {
    /**
     * This method prints text to a given length.
     */
    public static void lp(String text, char spacer, int lineSize, String alignment) {
        // check string length
        if (text.length() > lineSize) {
            lineSize = text.length();
        }
        // build the string
        StringBuilder textOut = new StringBuilder(text);
        if (Objects.equals(alignment, "center") || Objects.equals(alignment, "centre")) {
            while (textOut.length() != lineSize) {
                if (textOut.length() > lineSize) {
                    textOut.deleteCharAt(textOut.length() - 1);
                } else {
                    textOut.insert(0, spacer);
                    textOut.append(spacer);
                }
            }
        } else {
            while (textOut.length() < lineSize) {
                if (Objects.equals(alignment, "right")) {
                    textOut.insert(0, spacer);
                } else {
                    textOut.append(spacer);
                }
            }
        }
        // print output
        System.out.println(textOut);
    }

    // Overload - 3 args
    public static void lp(String text, char spacer, int lineSize) {
        lp(text, spacer, lineSize, "left");
    }

    public static void lp(String text, char spacer, String alignment) {
        lp(text, spacer, 30, alignment);
    }

    public static void lp(String text, int lineSize, String alignment) {
        lp(text, ' ', lineSize, alignment);
    }

    public static void lp(char spacer, int lineSize, String alignment) {
        lp("", spacer, lineSize, alignment);
    }

    // Overload - 2 args
    public static void lp(String text, char spacer) {
        lp(text, ' ', 30, "left");
    }

    public static void lp(String text, int lineSize) {
        lp(text, ' ', lineSize, "left");
    }

    public static void lp(String text, String alignment) {
        lp(text, ' ', 30, alignment);
    }

    public static void lp(char spacer, int lineSize) {
        lp("", spacer, lineSize, "left");
    }

    public static void lp(char spacer, String alignment) {
        lp("", spacer, 30, alignment);
    }

    public static void lp(int lineSize, String alignment) {
        lp("", ' ', lineSize, alignment);
    }

    // Overload - 1 args
    public static void lp(String text) {
        lp(text, ' ', 30, "left");
    }

    public static void lp(char spacer) {
        lp("", spacer, 30, "left");
    }

    public static void lp(int lineSize) {
        lp("", ' ', lineSize, "left");
    }
}
