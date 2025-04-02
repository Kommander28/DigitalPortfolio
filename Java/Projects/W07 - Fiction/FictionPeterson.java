/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 *    ASSIGNMENT:    "Travel" - CSCI 0012
 *      DUE DATE:    06 Oct 24
 *    SELF GRADE:    100 points (100%)
 * JUSTIFICATION:    This program meets all the listed requirements of the assignment and the output matches the given
 * example. Though each individual story may not have 10 sentences each, there are plenty of total stories (randomly
 * selected each time) and each story has a variable amount of inputs. Additionally, I decided to take my own spin on
 * this assignment and create a "MadLibs" style game where the user is given the part of speech for the required
 * words in the story, but is then fed the story after providing the words. I made sure to also include the reverse
 * string method, implemented where I "load" the story. Enjoy! :)
 *
 * All the MadLibs were gathered from: https://www.thewordfinder.com/wordlibs/
 */

import java.io.*;
import java.util.*;
import java.nio.file.*;

public class FictionPeterson {
    static final int WIDTH = 60;

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner kb = new Scanner(System.in);
        // intro
        head();
        slowPrint("Welcome to MADLIBS!\n");
        slowPrint("Loading", false);
        slowPrint("...  ", 800);
        head();
        slowPrint("How many stories would you like to create today?");
        System.out.print("[number of stories]:  ");
        int loopCount = kb.nextInt();
        kb.nextLine();
        for (int loop = 1; loop <= loopCount; loop++) {
            head(loop);
            //get MadLib data
            slowPrint("Please fill in the blanks for the given words:\n");
            String[] madlib = getStory();
            String output = fillStory(kb, madlib[0], madlib[1]);
            // "process results"
            slowPrint("\nGenerating Story...");
            String reverseStory = flipper(output.replace("\n", ""));
            for (int revLetter = 0; revLetter < reverseStory.length(); revLetter++) {
                if (revLetter % WIDTH == 0) {
                    slowPrint("" + reverseStory.charAt(revLetter), 5);
                } else {
                    slowPrint("" + reverseStory.charAt(revLetter), 5, false);
                }
            }
            slowPrint("Loading", false);
            slowPrint("...  ", 800);
            // output results
            head(loop);
            slowPrint(output, 10);
            slowPrint("Press the [Enter] key to continue...");
            kb.nextLine();
        }
        // exit
        System.out.println("\nThank you for playing, have a nice day!\n:)\n");
    }

    /**
     * This method returns the reversed version of a string
     */
    public static String flipper(String input) {
        StringBuilder tupni = new StringBuilder();
        for (int nLetter = input.length() - 1; nLetter >= 0; nLetter--) {
            tupni.append(input.charAt(nLetter));
        }
        return tupni.toString();
    }

    /**
     * This method gets all the MadLibs from the "./madlibs/" folder, then selects one at random to be the chosen
     * story for the user to fill in.
     */
    public static String[] getStory() throws IOException {
        // get list of all MadLibs files and indices
        File mlFolder = new File("./madlibs/");
        File[] mlList = mlFolder.listFiles();
        assert mlList != null;
        // pick a random MadLib story
        Random generator = new Random();
        File mlFile = mlList[generator.nextInt(mlList.length)];
        // get the title of the MadLib
        String[] madlib = new String[2];
        madlib[0] = mlFile.getName().substring(mlFile.getName().indexOf("-") + 1, mlFile.getName().length() - 4);
        // get the story from the contents of the file
        madlib[1] = Files.readString(mlFile.getAbsoluteFile().toPath()).replace("\n", "");
        // return string array containing the MadLib title and body text
        return madlib;
    }

    /**
     * This method gathers inputs from the user that correspond to the blanks in the MadLib chosen from the getStory()
     * method.
     */
    public static String fillStory(Scanner user, String title, String body) {
        // gather all the keywords from the story
        int keyCount = body.length() - body.replace("{", "").length();
        int[] keysStart = new int[keyCount], keysEnd = new int[keyCount];
        int keyIndex = 0;
        for (int iLet = 0; iLet < body.length(); iLet++) {
            if (body.charAt(iLet) == '{') {
                keysStart[keyIndex] = iLet;
            } else if (body.charAt(iLet) == '}') {
                keysEnd[keyIndex] = iLet + 1;
                keyIndex++;
            }
        }
        // get user inputs based on keywords in story
        String[] userInputs = new String[keyCount];
        for (int iKey = 0; iKey < keyCount; iKey++) {
            String keyWord = body.substring(keysStart[iKey] + 1, keysEnd[iKey] - 1);
            // prompt
            System.out.printf("[" + (iKey + 1) + "/" + (keyCount) + "] " + keyWord + ":  ");
            if (keyWord.equals("NUMBER")) {
                double userInputDouble = user.nextDouble();
                user.nextLine();
                userInputs[iKey] = userInputDouble + "";
            } else if (keyWord.equals("WHOLE NUMBER")) {
                int userInputInt = user.nextInt();
                user.nextLine();
                userInputs[iKey] = userInputInt + "";
            } else {
                String userInputString = user.nextLine();
                userInputs[iKey] = userInputString.toUpperCase();
            }
        }
        // assemble the string with user inputs
        StringBuilder storyString = new StringBuilder();
        int inputIndex = 0;
        boolean toggleKey = false;
        for (int iStr = 0; iStr < body.length(); iStr++) {
            for (int j : keysStart) {
                if (iStr == j) {
                    storyString.append(userInputs[inputIndex]);
                    inputIndex++;
                    toggleKey = true;
                    break;
                }
            }
            for (int j : keysEnd) {
                if (iStr == j) {
                    toggleKey = false;
                    break;
                }
            }
            if (!toggleKey) {
                storyString.append(body.charAt(iStr));
            }
        }
        // format story text to fit within WIDTH
        String newBody = storyString.toString();
        int nextSpace, lineChar = 0;
        for (int iChar = 0; iChar < newBody.length(); iChar++) {
            lineChar++;
            if (newBody.charAt(iChar) == ' ') {
                nextSpace = newBody.substring(iChar + 1).indexOf(' ');
                if ((lineChar + nextSpace) / WIDTH > 0) {
                    storyString.setCharAt(iChar, '\n');
                    lineChar = 0;
                }
            }
        }
        // add title to story string
        String reverseBody = flipper(storyString.append("\n").toString());
        String buffer = " ".repeat((WIDTH - title.length()) / 2);
        String reverseTitle = flipper("\n" + buffer + title.toUpperCase() + buffer + "\n\n");
        storyString.setLength(0);
        // return string filled with user inputs
        return flipper(storyString.append(reverseBody).append(reverseTitle).toString());
    }

    /**
     * This method prints a string letter by letter, pausing by "delay" milliseconds (1/1000 s).
     */
    public static void slowPrint(String output, long delay, boolean newLine) throws InterruptedException {
        for (char letter : output.toCharArray()) {
            System.out.print(letter);
            Thread.sleep(delay);
        }
        if (newLine) {
            System.out.println();
        }
    }

    public static void slowPrint(String output, long delay) throws InterruptedException {
        slowPrint(output, delay, true);
    }

    public static void slowPrint(String output, boolean newLine) throws InterruptedException {
        slowPrint(output, 10, newLine);
    }

    public static void slowPrint(String output) throws InterruptedException {
        slowPrint(output, 10, true);
    }

    /**
     * This method "clears" the screen and shows the header
     */
    public static void head(int storyNumber) {
        System.out.println("\n".repeat(99));
        System.out.print("MADLIBS");
        if (storyNumber > 0) {
            System.out.println(" - Story Number " + storyNumber);
        } else {
            System.out.println();
        }
        System.out.println("~".repeat(WIDTH) + "\n");
    }

    public static void head() {
        head(0);
    }
}
