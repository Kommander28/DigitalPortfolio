/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 */

/**
 * This class runs a screen that is a defined size of characters.
 * The size is defined by xMax and yMax, and the set of allowed points
 * span from (0,0) in the bottom-left corner to (xMax, yMax) in the top-right
 * corner.
 */
public class Screen {
    // initialize variables
    final int xMax;         // size of the screen in the x direction
    final int yMax;         // size of the screen in the y direction
    String screen;          // string representing the screen

    // constructor
    public Screen(int xSize, int ySize) {
        this.xMax = xSize;
        this.yMax = ySize;
        // create a blank screen
        this.screen = (" ".repeat(xMax + 1) + "\n").repeat(yMax + 1);
    }

    // constructor overload - 1 input (int)
    public Screen(int xySize) {
        this(xySize, xySize);
    }

    // toString override
    @Override
    public String toString() {
        return screen;
    }

    /**
     * This method plots/changes a character on the current screen.
     *
     * @param xVal   is the x coordinate for the character to update
     * @param yVal   is the y coordinate for the character to update
     * @param charIn is the character to update the screen with
     */
    public void plot(int xVal, int yVal, char charIn) {
        StringBuilder output = new StringBuilder(screen);
        output.setCharAt((yMax - yVal) * (xMax + 2) + xVal, charIn);
        screen = output.toString();
    }

    // overload - 3 inputs (int, int, str)
    public void plot(int xVal, int yVal, String strIn) {
        plot(xVal, yVal, strIn.charAt(strIn.length() - 1));
    }

    // overload - 3 inputs (int, int, int)
    public void plot(int xVal, int yVal, int intIn) {
        plot(xVal, yVal, Integer.toString(intIn).charAt(Integer.toString(intIn).length() - 1));
    }

    // overload - 3 inputs (int, int, double)
    public void plot(int xVal, int yVal, double dIn) {
        plot(xVal, yVal, Double.toString(dIn).charAt(Double.toString(dIn).indexOf('.') - 1));
    }

    /**
     * This method plots/changes a line of characters on the current screen.
     *
     * @param xyVal    is the coordinate for the character to update
     * @param charIn   is the character to update the screen with
     * @param axisFlip is whether to plot the line in the x direction instead of in the default y direction
     */
    public void line(int xyVal, char charIn, boolean axisFlip) {
        if (axisFlip) {
            for (int x = 0; x <= xMax; x++) {
                plot(x, xyVal, charIn);
            }
        } else {
            for (int y = 0; y <= yMax; y++) {
                plot(xyVal, y, charIn);
            }
        }
    }

    // Overload - two variables (int, char)
    public void line(int xyVal, char charIn) {
        line(xyVal, charIn, false);
    }

    /**
     * This method clears the screen.
     */
    public void clear() {
        for (int y = 0; y <= yMax; y++) {
            for (int x = 0; x <= xMax; x++) {
                plot(x, y, ' ');
            }
        }
    }
}
