/*
 * THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson
 */

import java.util.Objects;

/**
 * "pretty print"
 * This class prints lines in particular, pretty ways.
 */
public class PP {
    /**
     * "print line"
     * This class prints a line of text built to certain dimensions.
     */
    public static void pl(String text, int lineSize, char spacer, String alignment, boolean newLine) {
        // check string length
        if (text.length() > lineSize) lineSize = text.length();
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
                if (Objects.equals(alignment, "right")) textOut.insert(0, spacer);
                else textOut.append(spacer);
            }
        }
        // print output
        if (newLine) System.out.println(textOut);
        else System.out.print(textOut);
    }

    // overload
    public static void pl(String text, int lineSize, char spacer, String alignment) {
        pl(text, lineSize, spacer, alignment, true);
    }

    public static void pl(String text, int lineSize, String alignment, boolean newLine) {
        pl(text, lineSize, ' ', alignment, newLine);
    }

    public static void pl(String text, int lineSize, char spacer) {
        pl(text, lineSize, spacer, "center", true);
    }

    public static void pl(String text, int lineSize, String alignment) {
        pl(text, lineSize, ' ', alignment, true);
    }

    public static void pl(String text, int lineSize, boolean newLine) {
        pl(text, lineSize, ' ', "center", newLine);
    }

    public static void pl(String text, int lineSize) {
        pl(text, lineSize, ' ', "center", true);
    }

    /**
     * "line break"
     * This prints a line break.
     */
    public static void lb(char spacer, int lineSize) {
        System.out.println((spacer + "").repeat(lineSize));
    }

    // overload
    public static void lb(int lineSize) {
        lb('~', lineSize);
    }

    public static void lb(char spacer) {
        lb(spacer, 1);
    }

    public static void lb() {
        lb('~', 1);
    }

    public static void lb(String spacer, int lineSize) {
        lb(spacer.charAt(0), lineSize);
    }
}