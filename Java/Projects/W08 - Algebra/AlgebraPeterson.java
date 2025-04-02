/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 *    ASSIGNMENT:    "Algebra" - CSCI 0012
 *      DUE DATE:    13 Oct 24
 *    SELF GRADE:    130 points (100%)
 * JUSTIFICATION:    This program meets all the listed requirements of the assignment and the output matches the given
 * example. Additionally, I output all results in a neat calculator-like output.
 */

import java.util.*;

public class AlgebraPeterson {
    // global variables
    static final int WIDTH = 80;
    static final String lineBreak = printer("-".repeat(WIDTH - 1));
    static final Scanner kb = new Scanner(System.in);

    public static void main(String[] args) {
        // create and display title
        String[] intro = new String[4];
        Arrays.fill(intro, lineBreak);
        intro[1] = printer("Welcome to the World's Worst Calculator!", "center");
        intro[2] = printer("\"The calculator with no numbers!\"", "center");
        for (String str : intro) {
            System.out.println(str);
        }

        // begin calculation loop
        boolean loop = true;
        while (loop) {
            loop = calculate();
        }

        // exit program with a friendly message
        System.out.println(printer("Have a nice day!", "center"));
        System.out.println(printer(":)", "center"));
        System.out.println(lineBreak);
    }

    /**
     * This method runs the script that performs the calculation. It gathers a user's input for which operation they
     * would like to perform, gets all the required numbers for the calculation, then outputs the result - all as text.
     *
     * @return tells the main control loop whether to continue or not
     */
    public static boolean calculate() {
        // show user valid operation inputs
        menu();

        // get valid user input for operation
        String oprStr = "";
        char oprChar = ' ';
        while (oprStr.isBlank()) {
            System.out.print("| Please enter a valid operation: ");
            oprChar = kb.nextLine().toCharArray()[0];
            oprStr = opr2word(oprChar);
        }
        System.out.println(printer(String.format("               You have entered: %c", oprChar)));
        System.out.println(lineBreak);

        // get a number from user
        String aStr;
        double aDbl;
        while (true) {
            System.out.print("| Please enter a number: ");
            aStr = kb.nextLine().replaceAll("\\D", "");
            if (!aStr.isEmpty()) {
                aDbl = Double.parseDouble(aStr);
                aStr = num2word(aDbl);
                break;
            }
        }
        System.out.println(printer(String.format("     You have entered: %.2f", aDbl)));
        System.out.println(lineBreak);

        // determine if more than one number is needed, then get second number if needed
        String bStr = "";
        double bDbl = 0;
        boolean multiNum = (oprChar + "").matches("[+\\-*/^%]");
        if (multiNum) {
            while (true) {
                System.out.print("| Please enter another number: ");
                bStr = kb.nextLine().replaceAll("\\D", "");
                if (!bStr.isEmpty()) {
                    bDbl = Double.parseDouble(bStr);
                    bStr = num2word(bDbl);
                    break;
                }
            }
            System.out.println(printer(String.format("     You have entered: %.2f", bDbl)));
            System.out.println(lineBreak);
        }

        // "calculate" answer
        String loading = "| Now calculating . . .";
        for (char l : loading.toCharArray()) {
            System.out.print(l);
            try {
                Thread.sleep(120);
            } catch (Exception _) {
            }
        }
        System.out.println(" ".repeat(WIDTH - loading.length()) + "|");
        try {
            Thread.sleep(1200);
        } catch (Exception _) {
        }
        System.out.println(printer(""));

        // now actually calculate the answer, and display both the numerical and word output
        double ans;
        if (multiNum) {
            ans = evaluate(aDbl, bDbl, oprChar);
            System.out.println(printer(String.format(" %.2f %c %.2f = %f", aDbl, oprChar, bDbl, ans)));
            System.out.println(printer(String.format(" %s%s%sequals %s", aStr, oprStr, bStr, num2word(ans))));
        } else {
            ans = mathMethod(aDbl, oprChar);
            System.out.println(printer(String.format(" %.2f %c = %f", aDbl, oprChar, ans)));
            System.out.println(printer(String.format(" %s%sequals %s", aStr, oprStr, num2word(ans))));
        }
        System.out.println(lineBreak);

        // ask if user wants to continue
        boolean loopVar = false;
        String loopChoice;
        while (true) {
            System.out.print("| Would you like to continue? [y/N]: ");
            loopChoice = kb.nextLine();
            if (loopChoice.equalsIgnoreCase("y")) {
                loopVar = true;
                break;
            } else if (loopChoice.equalsIgnoreCase("n") || loopChoice.isEmpty()) {
                break;
            }
        }
        System.out.println(lineBreak);
        return loopVar;
    }

    /**
     * This method displays all valid options for operations available for the user to select.
     */
    public static void menu() {
        // display all operations
        System.out.println(printer("Below is a list of available operations:", "center"));
        String menu = """
                "Below is a list of available operations:"
                
                  - TWO INPUTS -
                Addition:        +
                Subtraction:     -
                Multiplication:  *
                Division:        /
                Exponential:     ^
                Modulus:         %
                
                  - ONE INPUT -
                Factorial:       !
                Absolute Value:  |
                Round:           ~
                Floor:           f
                Ceiling:         c
                """;
        for (String str : menu.split("\n")) {
            System.out.println(printer(str, "center"));
        }
        System.out.println(lineBreak);
    }

    /**
     * This method converts a number into the words representing the number.
     *
     * @param nIn is a double representing the number to be converted
     * @return is a string representing the word form of double nIn
     */
    public static String num2word(double nIn) {
        // create the word string from the numerical input
        String nStr = String.valueOf(nIn);
        StringBuilder nOut = new StringBuilder();
        for (int i = 0; i < nStr.length(); i++) {
            if (nStr.charAt(i) == '0') {
                nOut.append("zero ");
            } else if (nStr.charAt(i) == '1') {
                nOut.append("one ");
            } else if (nStr.charAt(i) == '2') {
                nOut.append("two ");
            } else if (nStr.charAt(i) == '3') {
                nOut.append("three ");
            } else if (nStr.charAt(i) == '4') {
                nOut.append("four ");
            } else if (nStr.charAt(i) == '5') {
                nOut.append("five ");
            } else if (nStr.charAt(i) == '6') {
                nOut.append("six ");
            } else if (nStr.charAt(i) == '7') {
                nOut.append("seven ");
            } else if (nStr.charAt(i) == '8') {
                nOut.append("eight ");
            } else if (nStr.charAt(i) == '9') {
                nOut.append("nine ");
            } else if (nStr.charAt(i) == '.') {
                nOut.append("point ");
            } else {
                nOut.append(" ");
            }
        }
        return nOut.toString();
    }

    /**
     * This method converts a selected operator into its word equivalent.
     *
     * @param opr is a char representing the operator to be converted
     * @return is a string representing the word form of char opr
     */
    public static String opr2word(char opr) {
        // return word equivalent of operator
        return switch (opr) {
            case '+' -> "plus ";
            case '-' -> "minus ";
            case '*' -> "times ";
            case '/' -> "divided by ";
            case '^' -> "to the power of ";
            case '%' -> "modulo ";
            case '!' -> "factorial ";
            case '|' -> "absolute ";
            case '~' -> "rounded ";
            case 'f' -> "floor ";
            case 'c' -> "ceiling ";
            default -> "";
        };
    }

    /**
     * This method evaluates the selected operator on the two numbers that the user has selected.
     *
     * @param aIn is the user's first inputted number
     * @param bIn is the user's second inputted number
     * @param opr is the user's chosen operator
     * @return the value of: double aIn char opr double bIn
     */
    public static double evaluate(double aIn, double bIn, char opr) {
        // return the calculated result
        return switch (opr) {
            case '+' -> aIn + bIn;
            case '-' -> aIn - bIn;
            case '*' -> aIn * bIn;
            case '/' -> aIn / bIn;
            case '^' -> Math.pow(aIn, bIn);
            case '%' -> aIn % bIn;
            default -> 0;
        };
    }

    /**
     * This method evaluates the selected operator on the number that the user has selected.
     *
     * @param aIn is the user's inputted number
     * @param opr is the user's chosen operator
     * @return the value of: double aIn char opr
     */
    public static double mathMethod(double aIn, char opr) {
        // return the calculated result
        return switch (opr) {
            case '!' -> factorial(aIn);
            case '|' -> Math.abs(aIn);
            case '~' -> Math.round(aIn);
            case 'f' -> Math.floor(aIn);
            case 'c' -> Math.ceil(aIn);
            default -> 0;
        };
    }

    /**
     * This method calculates the factorial of a given number.
     *
     * @param aIn is a double that is converted to an int to take the factorial of
     * @return is the value of double aIn !
     */
    public static double factorial(double aIn) {
        // calculate and return the factorial of the input
        int out = (int) aIn;
        for (int i = out - 1; i > 0; i--) {
            out = out + i;
        }
        return out;
    }

    /**
     * This method aligns a screen with the chosen direction.
     *
     * @param input     is the string input to be aligned
     * @param alignment is the alignment of the output text
     * @return is the complete string of aligned text
     */
    public static String printer(String input, String alignment) {
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
    public static String printer(String input) {
        return printer(input, "left");
    }
}
