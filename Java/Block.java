/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 */

import java.util.*;

/**
 * This class simplifies outputs to the console by keeping text into blocks.
 */
public class Block {
    // initialize variables
    final int width;        // width of block, not including borders
    String contents;        // string of text that makes up the block

    // constructor
    public Block(String contents, int width) {
        this.contents = contents;
        this.width = width;
    }

    // constructor overload - 2 inputs (String, int)
    public Block(String contents, int width) {
        this(contents, "custom", width);
    }

    // constructor overload - 2 inputs (String, String)
    public Block(String contents, String type) {
        this(contents, type, contents.length());
    }

    // constructor overload - 1 input (String)
    public Block(String contents) {
        this(contents, "custom", contents.length());
    }

    // constructor overload - 0 inputs
    public Block() {
        this(" ", "blank", 1);
    }

    // toString override
    @Override
    public String toString() {
        return contents;
    }

    /**
     * This method formats a block.
     *
     * @param alignment is the alignment of the output text
     * @param spacer    character used for string padding
     */
    public static Block formatted(String input, String alignment, char spacer, int width) {
        String output;
        String s = "" + spacer;
        // build string based on alignment given
        switch (alignment.toLowerCase()) {
            case "center":
                int bufferL = (Math.abs(width - this.contents.length()) - 2) / 2;
                String buffer = s.repeat(bufferL);
                output = buffer + this.contents;
                while (output.length() < width) {
                    output = output.substring(0, output.length() - 1) + s;
                }
                break;
            case "right":
                // add a spacer between input and right border if absent
                if (this.contents.isEmpty() || this.contents.charAt(this.contents.length() - 1) != spacer) {
                    output = this.contents + s;
                } else {
                    output = this.contents;
                }
                while (output.length() < width) {
                    output = s + output.substring(1);
                }
                break;
            case "left":
                // add a spacer between left border and input if absent
                if (this.contents.isEmpty() || this.contents.charAt(0) != spacer) {
                    output = s + this.contents;
                } else {
                    output = this.contents;
                }
                while (output.length() < width) {
                    output = output.substring(0, output.length() - 1) + s;
                }
                break;
            default:
                output = this.contents;
        }
        // update block with formatted text
        this.contents = output;
        return this;
    }

    /**
     * This method creates a block from a Screen object.
     *
     * @param screen
     */
    public static Block screen(Screen screen) {
        return new Block(screen.screen, "screen", screen.xMax);
    }

    /**
     * This method creates a block for the screen's title.
     *
     * @param title string of the title for the block
     * @param width width of the block
     */
    public static Block title(String title, int width) {
        return formatted(title.toUpperCase(), "center", ' ', width);
    }

    /**
     * This method creates a block that shows the player's progress.
     *
     * @param progress
     * @param stat
     * @param width
     */
    public static Block progress(int progress, String stat, int width) {
        // initialize variables
        char[] partial = {' ', '\u258F', '\u258E', '\u258D', '\u258C', '\u258B', '\u258A', '\u2589', '\u2588'};
        String seed;
        // constrain percentage to acceptable range
        if (progress < 0) {
            progress = 0;
        } else if (progress > 100) {
            progress = 100;
        }
        // convert percentage value to bar count
        int bars = progress * (width / 100);
        int part = (int) ((progress * ((double) width / 100) - bars) * 8);
        // create progress bar block
        int size = (width + 1) - stat.length();
        if (size % 2 == 0) {
            seed = " ".repeat(size / 2) + stat.toUpperCase() + " ".repeat(size / 2);
        } else {
            seed = " ".repeat(size / 2) + stat.toUpperCase() + " ".repeat(size / 2 + 1);
        }
        // build and return entire progress bar string
        StringBuilder output = new StringBuilder(seed);
        for (int i = 1; i <= output.length(); i++) {
            if (i <= progress && output.charAt(i) == ' ') {
                output.replace(i, i + 1, "" + '\u2588');
            } else if (i <= progress + 1 && output.charAt(i) == ' ') {
                output.replace(i, i + 1, "" + partial[part]);
            }
        }
        return new Block(output.toString(), "stats", width);
    }

//    /**
//     * This method creates a block that shows the player's health.
//     *
//     * @param health
//     * @param maxHealth
//     * @param split
//     * @param stat
//     */
//    public static Block health(int health, int maxHealth, String split, String stat) {
//        // initialize variables
//        char[] canArray = {'\u25CF', '\u25D5', '\u25D1', '\u25D4', '\u25EF'};
//        int cans, splitVal, size;
//        String full, part;
//        StringBuilder seed;
//        // convert health value to health bar value based on split
//        if (split.equalsIgnoreCase("whole")) {
//            splitVal = 1;
//        } else if (split.equalsIgnoreCase("half")) {
//            splitVal = 2;
//        } else if (split.equalsIgnoreCase("quarter")) {
//            splitVal = 4;
//        } else {
//            splitVal = 1;
//        }
//        // build health bar string seed
//        cans = maxHealth / splitVal;
//        full = (canArray[canArray.length - 1] + "").repeat(health / splitVal);
//        part = "" + canArray[((canArray.length - 1) / splitVal) / (health % splitVal)];
//        seed = new StringBuilder(full);
//        //if health is less than full, add partially full can
//        if (health < maxHealth) {
//            seed.append(part);
//        }
//        // add empty cans if health is below max
//        while (seed.length() < cans) {
//            seed.append(canArray[0]);
//        }
//        seed.insert(0, stat + ": ");
//        // build and return entire health bar string
//        size = (xMax + 1) - seed.length();
//        if (size % 2 == 0) {
//            return "\u2551" + " ".repeat(size / 2) + seed + " ".repeat(size / 2) + "\u2551";
//        } else {
//            return "\u2551" + " ".repeat(size / 2) + seed + " ".repeat(size / 2 + 1) + "\u2551";
//        }
//        StringBuilder output = new StringBuilder(seed);
//        for (int i = 1; i <= output.length(); i++) {
//            if (i <= health && output.charAt(i) == ' ') {
//                output.replace(i, i + 1, "" + '\u2588');
//            } else if (i <= health + 1 && output.charAt(i) == ' ') {
//                output.replace(i, i + 1, " " + seed);
//            }
//        }
//        return new Block(output.toString(), "stats", width);
//    }

    /**
     * This method draws a border around the block and returns the resultant string.
     *
     * @param position string of whether the block is at the top, middle, or bottom of the stack
     * @param quiet if false, does not print the block to the command window.
     */
    public String draw(String position, boolean quiet) {
        // initialize variables
        StringBuilder output = new StringBuilder();
        String border = "\u2551";
        String topBorder = "\u2554" + "\u2550".repeat(this.width + 1) + "\u2557";
        String midBorder = "\u2560" + "\u2550".repeat(this.width + 1) + "\u2563";
        String botBorder = "\u255A" + "\u2550".repeat(this.width + 1) + "\u255D";
        // draw correct top border
        if (position.equalsIgnoreCase("top")) {
            output.append(topBorder).append('\n');
        } else {
            output.append(midBorder).append('\n');
        }
        // print out the lines of the block with borders
        for (String line : this.contents.split("\n")) {
            output.append(border).append(line).append(border).append('\n');
        }
        // draw correct bottom border
        if (position.equalsIgnoreCase("bottom")) {
            output.append(botBorder).append('\n');
        } else {
            output.append(midBorder).append('\n');
        }
        // print out block
        if (!quiet) {
            System.out.println(output);
        }
        return output.toString();
    }

    // overload - 1 input (String)
    public void draw(String position) {
        draw(position, false);
    }

    // overload - 0 inputs
    public void draw() {
        draw("middle", false);
    }
}
