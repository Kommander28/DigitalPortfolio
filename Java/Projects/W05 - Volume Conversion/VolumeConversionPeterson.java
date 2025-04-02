/*
 * THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR STATED BELOW (Tim Peterson).
 *        AUTHOR:    Tim Peterson
 *    ASSIGNMENT:    "Gallon Conversion" - CSCI 0012
 *      DUE DATE:    22 Sep 24
 *    SELF GRADE:    100 points (100%)
 * JUSTIFICATION:    This program meets all the listed requirements of the assignment and the output matches the given
 * example. Additionally, I completed all extra feature requirements.
 */

/**
 * This class produces a document that displays helpful conversions
 */
public class VolumeConversionPeterson {
    // define class constants
    public static final double LITRES_IN_GALLON = 3.785411784;    // L
    public static final double OUNCES_IN_GALLON = 128;            // oz
    public static final double POUNDS_IN_GALLON = 8.345404452021; // lb

    // main
    public static void main(String[] args) {
        int outputWidth = 70;
        printDescription(outputWidth);
        convertTable(outputWidth / 4);
    }

    /**
     * This method prints out the details of a particular ride.
     */
    public static void printDescription(int width) {
        // provide details
        String description = String.format("""
                Welcome!
                
                This page converts gallons into different
                units, including: Litres, Fluid Ounces, and Pounds*!
                (*assuming the density of the volume to be the same as water)
                
                Below are the conversion rates used:
                Litres:Gallons - %.2f
                Ounces:Gallons - %.2f
                Pounds:Gallons - %.2f
                """, LITRES_IN_GALLON, OUNCES_IN_GALLON, POUNDS_IN_GALLON);
        String[] lines = description.split("\n");
        // output text
        lineBreak(width);
        for (String line : lines) {
            PrettyPrinter.lp(line, width, "center");
        }
        lineBreak(width);
    }

    /**
     * This method creates a table for converting units of volume
     */
    public static void convertTable(int padding) {
        if (padding < 15) {
            padding = 15;
        }
        // print title
        String[] title = "Gallons (gal.)|Litres (L.)|Ounces (fl.oz.)|Pounds (lb.)".split("\\|");
        System.out.print(" ");
        for (String name : title) {
            System.out.print(name + " ".repeat(padding - name.length()));
        }
        System.out.println("\n" + "-".repeat(padding * 4));
        for (double i = 99; i >= 12; i -= 3) {
            // calculate values
            double g2L = i * LITRES_IN_GALLON;
            double g2oz = i * OUNCES_IN_GALLON;
            double g2lb = i * POUNDS_IN_GALLON;
            // print result
            double[] values = {i, g2L, g2oz, g2lb};
            System.out.print("|");
            for (double v : values) {
                String vStr = String.format("%.2f", v);
                System.out.print(vStr + " ".repeat(padding - vStr.length() - 1) + "|");
            }
            System.out.println();
        }
    }

    /**
     * This method creates a long line
     */
    public static void lineBreak(int width) {
        for (int i = 0; i < width; i++) {
            System.out.print('~');
        }
        System.out.println();
    }
}