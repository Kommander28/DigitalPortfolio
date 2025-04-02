/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 *    ASSIGNMENT:    "Letter Game" - CSCI 0012
 *      DUE DATE:    Sun 03 Nov 24
 *    SELF GRADE:    100 points (100%)
 * JUSTIFICATION:    This program meets all the listed requirements of the assignment and the output matches the given
 * example. Additionally, I created and included an additional game based on a real life game show and statistics
 * experiment: The Monty Hall Problem from the show "Let's Make a Deal"
 *
 * Have fun and enjoy playing - hopefully this breaks up the monotony of grading the same assignment over and over! :)
 */

public class LetterGamePeterson {
    // "global" variables
    static int gamesPlayed = 0;
    static int gamesWon = 0;
    static boolean mainLoop = true;

    public static void main(String[] args) {
        // main loop
        while (mainLoop) {
            // show a unique message on the first run
            // show a different message for playing another game past the first
            introduction(gamesPlayed != 0);
            if (mainLoop) {
                // ask the player if they want to play another game
                print.head("THE EXTRA FANTASTIC WONDER ARCADE!");
                print.er("[Ken]: Would you like to play another game? Otherwise you can exit.");
                print.brk();
                print.brk(' ');
                if (getIn.getBoolean("Play another game? [yes/no]:", true)) {
                    mainLoop = false;
                    break;
                }
            }
        }
        // exit program with a friendly message
        String outro = String.format("""
                Out of the %d total games you played today,
                you won %d of them. Congratulations!
                
                Have an extra-fantastic wonderful day!
                :)
                """, gamesPlayed, gamesWon);
        print.head("THE EXTRA FANTASTIC WONDER ARCADE!");
        for (String str : outro.split("\n")) {
            print.er(str, "center");
        }
        print.brk();
    }

    /**
     * This method displays all valid options for games available for the user to select.
     */
    public static void introduction(boolean returningPlayer) {
        // create intro string
        String intro = """
                [Ken]: Hello and welcome to The Extra Fantastic Wonder Arcade!
                
                [Ken]: I am your host Ken Jennings (no relation to the Jeopardy host) and today it looks
                       like we have a truly spectacular selection of games for you to choose from!
                
                [Ken]: Have fun!
                
                """;
        // print out intro script
        print.head("THE EXTRA FANTASTIC WONDER ARCADE!");
        if (!returningPlayer) {
            for (String str : intro.split("\n")) {
                print.er(str);
            }
        } else {
            print.er("[Ken]: Welcome back! Ready for more fun?");
        }
        print.brk();
        mainMenu();
    }

    /**
     * This method displays all valid options for games available for the user to select, then triggers the playGame
     * method for that game.
     */
    public static void mainMenu() {
        // create options string
        String options = """
                Please make a selection from the available games below:
                
                [1] Letterball
                [2] Number Bash
                [3] Monty Hall
                
                [0] Quit
                """;
        // show game options
        for (String str : options.split("\n")) {
            print.er(str, "left");
        }
        print.brk();
        print.brk(' ');
        // get user selection and play that game
        playGame(getIn.getPosInt(0, options.split("\n").length - 4,
                "Game Selection [number]:"));
    }

    /**
     * This method takes an input from mainMenu method and triggers a game to play.
     */
    public static void playGame(int gameChoice) {
        int[] score = new int[2]; // [games played, games won]
        // choose game to play based on player selection
        switch (gameChoice) {
            case 1:
                score = games.letterball();
                break;
            case 2:
                score = games.numberbash();
                break;
            case 3:
                score = games.montyhall();
                break;
            default:
                mainLoop = false;
        }
        // update number of games played and number of games won
        gamesPlayed += score[0];
        gamesWon += score[1];
    }
}