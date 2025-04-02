/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 */

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class simplifies outputs to the console by keeping text into frames.
 */
public class Frame {
    // initialize variables
    static final int WIDTH = MountainPeterson.FOOT + 4;
    static final String border = "\u2551";
    static final String topBorder = "\u2554" + "\u2550".repeat(WIDTH) + "\u2557";
    static final String midBorder = "\u2560" + "\u2550".repeat(WIDTH) + "\u2563";
    static final String botBorder = "\u255A" + "\u2550".repeat(WIDTH) + "\u255D";

    /**
     * This method prints out all the frames in the input.
     *
     * @param frameArray string array list of all the frames to print
     */
    public static void draw(String[] frameArray) {
        // create output string of frames
        ArrayList<String> frames = new ArrayList<>(Arrays.asList(frameArray));
        StringBuilder output = new StringBuilder(topBorder).append('\n');
        for (String frame : frames) {
            for (String line : frame.split("\n")) {
                output.append(border).append(line).append(border).append('\n');
            }
            if (!frame.equals(frames.getLast())) {
                output.append(midBorder).append('\n');
            } else {
                output.append(botBorder);
            }
        }
        // "clear screen" then print output
        System.out.println("\n".repeat(80));
        System.out.println(output);
    }

    /**
     * This method prints one frame with a title.
     *
     * @param input       is the string to add to the frame
     * @param title       is the title frame for the single frame
     * @param formatInput is a boolean value of whether to pass the input string to Frame.format() or not
     *                    if the string is a formatted frame, use false
     */
    public static void one(String input, String title, boolean formatInput) {
        String[] out = new String[2];
        // check to see if title needs formatting
        if (title.trim().length() < WIDTH && title.charAt(0) == border.charAt(0) && title.charAt(title.length() - 1) == border.charAt(0)) {
            out[0] = title(title);
        } else {
            out[0] = title;
        }
        // format input string if needed
        if (formatInput) {
            out[1] = format(input);
        } else {
            out[1] = input;
        }
        // print out / draw the resultant frame
        draw(out);
    }

    /**
     * This method creates a formatted frame from a string.
     *
     * @param input     string to put into a frame
     * @param alignment is the alignment of the output text
     * @param spacer    character used for string padding
     */
    public static String format(String input, String alignment, char spacer) {
        String blank = " ".repeat(WIDTH - 2);
        if (input.equalsIgnoreCase(String.valueOf(spacer))) {
            // string is just the spacer
            return String.valueOf(spacer).repeat(WIDTH - 2);
        } else if (input.replaceAll("\n", "").isEmpty()) {
            // input is only made of newlines
            return (blank + "\n").repeat(input.length() - 1) + blank;
        } else if (input.isEmpty() || input.replaceAll("\\s", "").isEmpty()) {
            // input is only whitespace characters
            return blank;
        }
        // correct any invalid parts of input string
        StringBuilder newInput = new StringBuilder();
        for (String line : input.split("\n")) {
            if (line.isEmpty() || input.replaceAll("\\s", "").isEmpty()) {
                // input line is empty
                newInput.append(blank);
            } else if (line.length() > WIDTH - 2) {
                // input line is too long
                StringBuilder newLine = new StringBuilder();
                for (String word : line.split("\\s")) {
                    if (newLine.length() + word.length() > WIDTH - 2) {
                        // add the contents of newLine to the newInput string
                        if (newLine.charAt(newLine.length() - 1) == ' ') {
                            newLine.deleteCharAt(newLine.length() - 1);
                        }
                        newInput.append(newLine).append('\n');
                        // reset newLine
                        newLine.setLength(0);
                    }
                    newLine.append(word).append(' ');
                }
                newInput.append(newLine);
            } else {
                // otherwise add line to corrected input string
                newInput.append(line);
            }
            newInput.append('\n');
        }
        if (newInput.charAt(newInput.length() - 1) == '\n') {
            newInput.deleteCharAt(newInput.length() - 1);
        }
        // format each line in the input string
        StringBuilder frameOut = new StringBuilder();
        for (String line : newInput.toString().split("\n")) {
            StringBuilder output;
            // check if line is empty
            if (line.isEmpty() || input.replaceAll("\\s", "").isEmpty()) {
                output = new StringBuilder(blank);
            } else {
                output = new StringBuilder(line);
            }
            // build string based on alignment given
            switch (alignment.toLowerCase()) {
                case "center":
                    while (output.length() < WIDTH) {
                        if (output.length() % 2 == 0) {
                            output.insert(0, spacer);
                        } else {
                            output.append(spacer);
                        }
                    }
                    break;
                case "right":
                    // add a spacer between input and right border if absent
                    if (line.charAt(line.length() - 1) != spacer) {
                        output.append(spacer);
                    }
                    while (output.length() < WIDTH) {
                        output.insert(0, spacer);
                    }
                    break;
                case "left":
                    // add a spacer between left border and input if absent
                    if (line.charAt(0) != spacer) {
                        output.insert(0, spacer);
                    }
                    while (output.length() < WIDTH) {
                        output.append(spacer);
                    }
                    break;
                default:
                    return format(line, "left", spacer);
            }
            // add formatted string to output frame
            frameOut.append(output).append("\n");
        }
        // remove last carriage return, then return frame string
        if (frameOut.lastIndexOf("\n") == frameOut.length() - 1) {
            frameOut.deleteCharAt(frameOut.length() - 1);
        }
        return frameOut.toString();
    }

    // overload - 2 inputs (String, String)
    public static String format(String input, String alignment) {
        return format(input, alignment, ' ');
    }

    // overload - 1 input (String)
    public static String format(String input) {
        return format(input, "center", ' ');
    }

    /**
     * This method creates a frame for the screen's title.
     *
     * @param title string of the title for the frame
     */
    public static String title(String title) {
        return format(title.trim().toUpperCase(), "center", ' ');
    }

    /**
     * This method creates a frame that shows progress completed.
     *
     * @param progress is the percentage of progress completed
     *                 must be an int in the range [0, 100]
     * @param stat     is the name of the progress being displayed
     */
    public static String progress(int progress, String stat) {
        // initialize variables
        char[] partial = {' ', '\u258F', '\u258E', '\u258D', '\u258C', '\u258B', '\u258A', '\u2589', '\u2588'};
        StringBuilder output = new StringBuilder(title(stat));
        // constrain percentage to acceptable range
        if (progress < 0) {
            progress = 0;
        } else if (progress > 100) {
            progress = 100;
        }
        // convert percentage value to bar count
        int bars = (int) ((double) progress * WIDTH) / 100;
        char part = partial[(int) (((double) progress * WIDTH) % 100) % (partial.length)];
        // create progress bar string
        for (int i = 0; i <= output.length(); i++) {
            if (i < bars && output.charAt(i) == ' ') {
                output.replace(i, i + 1, String.valueOf(partial[partial.length - 1]));
            } else if (i == bars && output.charAt(i) == ' ') {
                output.replace(i, i + 1, String.valueOf(part));
            }
        }
        return output.toString();
    }

    // overload - 1 input (int);
    public static String progress(int progress) {
        return progress(progress, "PROGRESS");
    }

    /**
     * This method creates a frame that shows the player's health.
     *
     * @param health    is the player's current level of health in relation to "maxHealth"
     * @param maxHealth is the value when the player has full health
     *                  "health" can be greater, equal, or less than "maxHealth"
     * @param split     is the division of health symbols
     *                  1: uses whole values (1 or 0)
     *                  2: uses half values (1, 1/2, or 0)
     *                  4: uses quarter values (1, 3/4, 1/2, 1/4, or 0)
     * @param stat      is the name of the health bar to display
     */
    public static String health(int health, int maxHealth, int split, String stat) {
        // initialize variables
        char[] canArray = {'\u25CF', '\u25D5', '\u25D1', '\u25D4', '\u25EF'};
        StringBuilder output = new StringBuilder(stat + ": ");
        int cans, full, part;
        // convert health value to health bar value based on split
        if (!(split == 1 || split == 2 || split == 4)) {
            split = 1;
        }
        cans = maxHealth / split;
        full = health / split;
        if (health % split == 0) {
            part = 0;
        } else {
            part = ((canArray.length - 1) / split) / (health % split);
        }
        // create health bar string
        output.append(String.valueOf(canArray[canArray.length - 1]).repeat(full));
        if (part != 0) {
            output.append(canArray[part]);
        }
        if (full < cans) {
            output.append(("" + canArray[0]).repeat(cans - (full + 1)));
        }
        return title(output.toString());
    }

    // overload - 3 inputs ()
    public static String health(int health, int maxHealth, int split) {
        return health(health, maxHealth, split, "HEALTH");
    }

    /**
     * This method creates a frame that shows a player's item stats.
     *
     * @param amount    is the player's current amount of item
     * @param maximum   is the value when the player has full amount of item
     *                  "amount" can be greater, equal, or less than this value
     * @param iconArray is the array containing the symbols to use
     *                  assumes that "0" is an empty version and the final value is a full version
     * @param name      is the name of the item bar to display
     */
    public static String item(int amount, int maximum, String[] iconArray, String name) {
        // initialize variables
        StringBuilder output = new StringBuilder(name + ": ");
        int empt, full, part;
        // convert value to icon quantity
        empt = maximum / (iconArray.length - 1);
        full = amount / (iconArray.length - 1);
        if (amount % (iconArray.length - 1) == 0) {
            part = 0;
        } else {
            part = 1 / (amount % (iconArray.length - 1));
        }
        // create health bar string
        output.append(iconArray[iconArray.length - 1].repeat(full));
        if (part != 0) {
            output.append(iconArray[part]);
        }
        while (output.length() < empt) {
            output.append(iconArray[0]);
        }
        return title(output.toString());
    }
}
