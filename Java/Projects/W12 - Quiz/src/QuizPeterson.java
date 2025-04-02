/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 *    ASSIGNMENT:    "Math Quiz" - CSCI 0012
 *      DUE DATE:    Mon 18 Nov 24
 *    SELF GRADE:    100 points (100%)
 * JUSTIFICATION:    This program meets all the listed requirements of the assignment and the output matches the given
 * example. Additionally, I did the entire assignment in a GUI (and I also managed to send outputs to the command
 * window to match the requested output). Enjoy! :)
 */

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.Random;

public class QuizPeterson {
    // "global" variables
    public static final char[] OPS = new char[]{'+', '-', '*', '/', 'v', '^', '%', 'p'};
    public static QuizFrame FRAME = new QuizFrame("MATH MANIA!!");
    public static Random RNG = new Random();
    public static String[] QUESTIONS;
    public static int[] ANSWERS;
    public static int[] POINTS;
    public static int Q_NUM;

    public static void main(String[] args) {
        // main loop
        mainLoop:
        while (true) {
            // setup and show intro
            FRAME.progressBar.setIndeterminate(true);
            print("Welcome!", "Please select an option to begin:", true, true);
            switch (ask(new String[]{"Play Game!", "View Rules", "Exit"})) {
                case "Exit":
                    // exit program
                    break mainLoop;
                case "View Rules":
                    // view rules of the game
                    String[] rules = """
                            In this math quiz, you will be asked a random amount of questions.
                            Each question has a certain point value assigned to it.
                            These point values will be shown to you at the end of the quiz.
                            Answer each question rounded to the nearest integer.
                            The answers may not be exactly correct for some questions.
                            Have fun and enjoy!
                            """.split("\n");
                    int page = 1;
                    while (page >= 1 && page <= rules.length) {
                        print("RULES - Click the arrows to navigate through the pages.", rules[page - 1], true, true);
                        if (ask(new String[]{"<", ">"}).equals("<")) {
                            page--;
                        } else {
                            page++;
                        }
                    }
                    break;
                case "Play Game!":
                    // initialize variables and populate arrays
                    Q_NUM = RNG.nextInt(10) + 10;
                    FRAME.progressBar.setIndeterminate(false);
                    FRAME.progressBar.setMaximum(Q_NUM - 1);
                    FRAME.progressBar.setStringPainted(true);
                    generator(Q_NUM);
                    // loop through and ask questions
                    for (int i = 0; i < Q_NUM; i++) {
                        boolean correct = askQuestion(QUESTIONS[i], ANSWERS[i]);
                        FRAME.progressBar.setValue(i);
                        if (!correct) {
                            POINTS[i] = 0;
                        }
                    }
                    // show scorecard
                    print("Final Score", "Your Scores: " + Arrays.toString(POINTS));
                    ask();
                    int count = Q_NUM;
                    for (int p : POINTS) {
                        if (p == 0) {
                            count--;
                        }
                    }
                    print("Final Score", "Congratulations! You got " + count + "/" + Q_NUM + " and " + IntStream.of(POINTS).sum() + " points!");
                    ask();
                    FRAME.progressBar.setStringPainted(false);
                    break;
            }
        }
        // exit sequence
        FRAME.progressBar.setIndeterminate(false);
        FRAME.progressBar.setValue(100);
        print("Thank you for playing!", "Have a nice day! :)");
        ask(new String[]{"Exit"});
        FRAME.setVisible(false);
        FRAME.dispose();
    }

    /**
     * This method populates the questions, answers, and points arrays with a number of math questions.
     */
    static void generator(int numQuestions) {
        // initialize variables
        QUESTIONS = new String[numQuestions];
        ANSWERS = new int[numQuestions];
        POINTS = new int[numQuestions];
        int[] randArray = new int[100];
        for (int i = 0; i < randArray.length; i++) {
            randArray[i] = RNG.nextInt(0, 50);
        }
        int num1, num2;
        // populate arrays
        for (int i = 0; i < numQuestions; i++) {
            num1 = randArray[RNG.nextInt(randArray.length)];
            do {
                num2 = randArray[RNG.nextInt(randArray.length)];
            } while (num2 == 0 && num2 == num1);
            char operator = OPS[RNG.nextInt(OPS.length)];
            QUESTIONS[i] = evaluate(num1, num2, operator)[0];
            ANSWERS[i] = Integer.parseInt(evaluate(num1, num2, operator)[1]);
            POINTS[i] = RNG.nextInt(5, 15);
        }

    }

    /**
     * This method evaluates a math equation based on the given input string.
     * returns both [0] the String form of the equation and [1] the numerical answer of the equation (as a String)
     */
    static String[] evaluate(int num1, int num2, char op) {
        // initialize variables
        String output;
        int answer;
        // take care of unusual cases
        if (op == 'v') {
            output = String.format("Minimum of %d and %d = ?", num1, num2);
            answer = Math.min(num1, num2);
        } else if (op == '^') {
            output = String.format("Maximum of %d and %d = ?", num1, num2);
            answer = Math.max(num1, num2);
        } else if (op == '%') {
            output = String.format("%d mod %d = ?", num1, num2);
            answer = num1 % num2;
        } else if (op == 'p') {
            output = String.format("%d %% of %d = ?", num1, num2);
            answer = (int) ((double) num1 / 100 * num2);
        } else {
            output = String.format("%d %c %d = ?", num1, op, num2);
            // evaluate common cases
            if (op == '+') {
                answer = num1 + num2;
            } else if (op == '-') {
                answer = num1 - num2;
            } else if (op == '*') {
                answer = num1 * num2;
            } else if (op == '/') {
                answer = (int) ((double) num1 / num2);
            } else {
                answer = 0;
            }
        }
        return new String[]{output, String.valueOf(answer)};
    }

    /**
     * This method generates possible answers and sends a question to the GUI.
     * It also receives the answer that the user entered and returns whether it was right or wrong.
     */
    static boolean askQuestion(String question, int answer) {
        // initialize variables
        boolean[] assigned = new boolean[4];
        int[] answerArray = new int[4];
        Random rng = new Random();
        // assign correct answer to array
        int index = rng.nextInt(4);
        answerArray[index] = answer;
        assigned[index] = true;
        // generate wrong answers and shuffle all answers
        for (int i = 0; i < 4; i++) {
            while (!assigned[i]) {
                int wrongAnswer = answer + rng.nextInt(-17, 29);
                // check if answer is in assigned array answers
                boolean goodAnswer = true;
                for (int j = 0; j < 4; j++) {
                    if (assigned[j] && answerArray[j] == wrongAnswer) {
                        goodAnswer = false;
                        break;
                    }
                }
                if (goodAnswer) {
                    answerArray[i] = wrongAnswer;
                    assigned[i] = true;
                    break;
                }
            }
        }
        // send question to GUI
        print("Choose your answer to the nearest whole number.", question, true, true);
        int userAnswer = ask(answerArray);
        // tell user if they got it correct
        if (userAnswer == answer) {
            print("Correct! : " + question.replaceFirst("\\?", String.valueOf(answer)));
            ask();
            return true;
        } else {
            print("Incorrect : " + question.replaceFirst("\\?", String.valueOf(answer)));
            ask();
            return false;
        }
    }

    /**
     * This method prints a string both to the frame screen and the command line.
     */
    static void print(String heading, String input, boolean newLine, boolean silent) {
        FRAME.header.setText(heading);
        if (!silent) {
            if (newLine) {
                System.out.println(input);
            } else {
                System.out.print(input + "  ->  ? = ");
            }
        }
        FRAME.text.setText(input);
    }

    // overload - 3 inputs (String, String, boolean)
    static void print(String heading, String input, boolean newLine) {
        print(heading, input, newLine, false);
    }

    // overload - 3 inputs (String, boolean, boolean)
    static void print(String input, boolean newLine, boolean silent) {
        print("", input, newLine, silent);
    }

    // overload - 2 inputs (String, String)
    static void print(String heading, String input) {
        print(heading, input, true, false);
    }

    // overload - 2 inputs (String, boolean)
    static void print(String input, boolean newLine) {
        print("", input, newLine, false);
    }

    // overload - 1 input (String)
    static void print(String input) {
        print("", input, true, false);
    }

    /**
     * This method waits for a user selection then returns what the user picked.
     */
    static String ask(String[] choices) {
        // fix incorrect input situations
        if (choices.length < 1) {
            choices = new String[]{"Continue"};
        } else if (choices.length > 4) {
            choices = new String[]{choices[0], choices[1], choices[2], choices[3]};
        }
        // reset frame buttons
        for (int i = 0; i < 4; i++) {
            FRAME.buttonGroup[i].setText("");
            FRAME.buttonGroup[i].setEnabled(false);
            FRAME.isClicked[i] = false;
        }
        // set frame buttons
        for (int i = 0; i < choices.length; i++) {
            FRAME.buttonGroup[i].setText(choices[i]);
            FRAME.buttonGroup[i].setEnabled(true);
        }
        // get user answer
        while (true) {
            FRAME.validate();
            if (FRAME.isClicked[0]) {
                FRAME.isClicked[0] = false;
                return choices[0];
            } else if (choices.length > 1 && FRAME.isClicked[1]) {
                FRAME.isClicked[1] = false;
                return choices[1];
            } else if (choices.length > 2 && FRAME.isClicked[2]) {
                FRAME.isClicked[2] = false;
                return choices[2];
            } else if (choices.length > 3 && FRAME.isClicked[3]) {
                FRAME.isClicked[3] = false;
                return choices[3];
            }
            FRAME.invalidate();
        }
    }

    // overload - 1 input (int[])
    static int ask(int[] choices) {
        String[] convert = new String[choices.length];
        for (int i = 0; i < choices.length; i++) {
            convert[i] = String.valueOf(choices[i]);
        }
        return Integer.parseInt(ask(convert));
    }

    // overload - 0 inputs ()
    static void ask() {
        ask(new String[0]);
    }
}
