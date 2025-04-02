/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 *    ASSIGNMENT:    "Encode Decode" - CSCI 0012
 *      DUE DATE:    27 Oct 24
 *    SELF GRADE:    100 points (100%)
 * JUSTIFICATION:    This program meets all the listed requirements of the assignment and the output matches the given
 * example. Additionally, this program has the ability to create multiple kinds of ciphers in addition to the required
 * polyalphabetic substitution cipher. To match the example output, please select option "3" ("Custom Reverse
 * Substitution Cipher") from the menu.
 */

import java.util.*;

public class EncodeDecodePeterson {
    // constant variables
    static final int WIDTH = 80;
    static final String LB = print.er("=".repeat(WIDTH - 1));

    public static void main(String[] args) {
        boolean loop = true;
        // main loop
        while (loop) {
            header();
            loop = menu();
        }
        // exit program with a friendly message
        header();
        System.out.println(print.er("Have a nice day!", "center"));
        System.out.println(print.er(":)", "center"));
        System.out.println(LB);
    }

    /**
     * This method displays the title/header block.
     */
    public static void header() {
        System.out.println("\n".repeat(40));
        String[] intro = new String[3];
        Arrays.fill(intro, LB);
        intro[1] = print.er("Substitution Cipher Generator", "center");
        for (String str : intro) {
            System.out.println(str);
        }
    }

    /**
     * This method displays all valid options for ciphers available for the user to select.
     * It also triggers the relevant cipher code, then returns if the user wishes to continue.
     *
     * @return is the boolean value of if the user wants to continue
     */
    public static boolean menu() {
        // create main menu
        String[] mMenu = new String[10];
        Arrays.fill(mMenu, print.er(""));
        mMenu[0] = print.er("Below is a list of available options.", "center");
        mMenu[1] = print.er("Please enter the number that corresponds to your choice:", "center");
        mMenu[3] = print.er(" 1- Vigenère Cipher");
        mMenu[4] = print.er(" 2- Caesar Cipher");
        mMenu[5] = print.er(" 3- Custom Reverse Substitution Cipher");
        mMenu[6] = print.er(" 4- Basic Substitution Cipher");
        mMenu[8] = print.er(" 0- Quit");
        // create encode/decode menu
        String[] edMenu = new String[8];
        Arrays.fill(edMenu, print.er(""));
        edMenu[0] = print.er("Below is a list of available options.", "center");
        edMenu[1] = print.er("Please enter the letter that corresponds to your choice:", "center");
        edMenu[3] = print.er(" D- Decode a Message");
        edMenu[4] = print.er(" E- Encode a Message");
        edMenu[6] = print.er(" Q- Quit");
        // initialize other variables
        boolean userQuit = false;
        int mChoice = mMenu.length;
        String cipherType = "";
        char edChoice = ' ';
        String codeOut;
        boolean loopVar = false;

        // print out main menu
        for (String str : mMenu) {
            System.out.println(str);
        }
        System.out.println(LB);
        // get input from user for cipher selection
        while (mChoice < 0 || mChoice > (mMenu.length - 5)) {
            mChoice = getIn.getInt(1);
        }
        System.out.println(print.er(" You have entered: " + mChoice));
        System.out.println(LB);
        // convert user choice to cipher type
        if (mChoice == 4) {
            cipherType = "basic";
        } else if (mChoice == 3) {
            cipherType = "custom";
        } else if (mChoice == 2) {
            cipherType = "caesar";
        } else if (mChoice == 1) {
            cipherType = "vigenere";
        } else {
            userQuit = true;
        }
        if (!userQuit) {
            // print out encode/decode menu
            for (String str : edMenu) {
                System.out.println(str);
            }
            System.out.println(LB);
            // get input from user for encode/decode selection
            while (!(edChoice == 'D' || edChoice == 'E' || edChoice == 'Q')) {
                edChoice = getIn.getChar();
            }
            System.out.println(print.er(" You have entered: " + edChoice));
            System.out.println(LB);
        }
        // check if user wants to quit
        if (edChoice == 'Q') {
            userQuit = true;
        }
        if (!userQuit) {
            // get message to translate
            String message = getIn.getString("| Please enter a message: ", true, false);
            // compute and output results
            codeOut = codex(message, cipherType, edChoice);
            header();
            if (cipherType.equals("vigenere")) {
                System.out.println(print.er("Vigenère Cipher", "center"));
                message = message.replaceAll(" ", "");
            } else if (cipherType.equals("caesar")) {
                System.out.println(print.er("Caesar Cipher", "center"));
                message = message.replaceAll(" ", "");
            } else if (cipherType.equals("custom")) {
                System.out.println(print.er("Custom Reverse Substitution Cipher", "center"));
            } else {
                System.out.println(print.er("Basic Substitution Cipher", "center"));
                message = message.replaceAll(" ", "");
            }
            System.out.println(print.er(""));
            System.out.println(print.er(" Message Input:  " + message));
            System.out.println(print.er(" Message Output: " + codeOut));
            System.out.println(LB);
            // ask user if they wish to continue
            char continueLoop = getIn.getChar("| Would you like to continue? [y/N]: ");
            if (continueLoop == 'y' || continueLoop == 'Y') {
                loopVar = true;
            }
        }
        System.out.println(LB);
        return loopVar;
    }

    /**
     * This method encodes or decodes a message using a chosen cipher.
     *
     * @param inMessage determines what message to translate
     * @param cipher    determines what kind of cipher to encode or decode the message string with
     * @param code      determines whether to encode or decode the message string
     * @return is the encoded or decoded string of the input message
     */
    public static String codex(String inMessage, String cipher, char code) {
        // code or encode message from user
        StringBuilder outMessage = new StringBuilder();
        int shift;
        switch (cipher) {
            case "vigenere":
                // get key
                inMessage = inMessage.replaceAll(" ", "");
                String vKey = getIn.getString("| Please enter a key phrase: ", true, true);
                // translate message using key
                for (int i = 0; i < inMessage.length(); i++) {
                    int inShift = inMessage.toCharArray()[i] - 65;
                    int keyShift = vKey.toCharArray()[i % vKey.length()] - 65;
                    if (code == 'D') {
                        shift = 65 + inShift - keyShift;
                        while (shift < 65) {
                            shift += 26;
                        }
                    } else {
                        shift = 65 + keyShift + inShift;
                        while (shift > 90) {
                            shift -= 26;
                        }
                    }
                    outMessage.append((char) (shift));
                }
                // return result
                System.out.println(LB);
                return outMessage.toString();
            case "caesar":
                // get key
                inMessage = inMessage.replaceAll(" ", "");
                int cKey = getIn.getInt();
                // translate message using key
                for (int i = 0; i < inMessage.length(); i++) {
                    shift = inMessage.toCharArray()[i];
                    if (code == 'D') {
                        shift -= cKey;
                        while (shift < 65) {
                            shift += 26;
                        }
                    } else {
                        shift += cKey;
                        while (shift > 90) {
                            shift -= 26;
                        }
                    }
                    outMessage.append((char) (shift));
                }
                // return result
                System.out.println(LB);
                return outMessage.toString();
            case "custom":
                // get key
                int xKey = 2 * inMessage.length();
                if (xKey > 26) {
                    xKey = inMessage.length() * 2 % 10;
                }
                // translate message using key
                for (int i = 0; i < inMessage.length(); i++) {
                    if (inMessage.toCharArray()[i] == ' ') {
                        outMessage.append(':');
                    } else if(inMessage.toCharArray()[i] == ':') {
                        outMessage.append(' ');
                    } else {
                        shift = inMessage.toCharArray()[i];
                        if (code == 'D') {
                            shift -= xKey;
                            while (shift < 65) {
                                shift += 26;
                            }
                        } else {
                            shift += xKey;
                            while (shift > 90) {
                                shift -= 26;
                            }
                        }
                        outMessage.append((char) (shift));
                    }
                }
                // return result
                System.out.println(LB);
                return outMessage.reverse().toString();
            default:
                // get key
                int bKey = inMessage.length();
                // translate message using key
                for (int i = 0; i < inMessage.length(); i++) {
                    shift = inMessage.toCharArray()[i];
                    if (code == 'D') {
                        shift -= bKey;
                        while (shift < 65) {
                            shift += 26;
                        }
                    } else {
                        shift += bKey;
                        while (shift > 90) {
                            shift -= 26;
                        }
                    }
                    outMessage.append((char) (shift));
                }
                // return result
                System.out.println(LB);
                return outMessage.toString();
        }
    }
}