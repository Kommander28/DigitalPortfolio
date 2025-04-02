/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 */

import java.util.Random;

public class games {
    /**
     * This method plays the Letterball game.
     * It is a game where you guess the letter the computer is thinking of.
     *
     * @return returns the number of games won by the player
     */
    public static int[] letterball() {
        // initialize variables
        int[] gameStats = new int[2]; // [games played, games won]
        final int maxGuesses = 6;
        String intro = String.format("""
                [Ken]: Ladies and Gentlemen, welcome to the classic game of Letterball!
                
                [Ken]: In this game, contestants will be asked to guess what letter the computer is thinking
                       of in only %d guesses!
                
                [Ken]: There are two game modes: Easy and Hard. In Easy mode, the game will display the range of
                       possible letters for you to pick from as you narrow it down, but this information is not
                       displayed in Hard mode!
                
                [Ken]: Ready to play?
                
                """, maxGuesses);
        String menu = """
                Please make a selection from the available options below:
                
                [1] Play Letterball! (Easy mode)
                [2] Play Letterball! (Hard mode)
                
                [0] Exit
                """;
        while (true) {
            // print out intro script
            print.head("LETTERBALL");
            if (gameStats[0] == 0) {
                for (String str : intro.split("\n")) {
                    print.er(str);
                }
            } else {
                print.er("[Ken]: Welcome back! Ready to play again?");
            }
            print.brk(' ');
            // print out menu options
            for (String str : menu.split("\n")) {
                print.er(str);
            }
            print.brk();
            print.brk(' ');
            // get player selection, then begin game (or exit) based on player's choice
            int gameChoice = getIn.getPosInt(0, 2, "Game Selection [number]:");
            if (gameChoice == 0) {
                break;
            } else {
                // initialize variables
                int guesses = 0;
                char guess = 'A';
                char rLow = 'A';
                char rHigh = 'Z';
                Random rand = new Random();
                char answer = (char) rand.nextInt(65, 91);
                String range;
                // start game
                while (true) {
                    // print out header if game is starting
                    if (guesses == 0) {
                        print.head("LETTERBALL");
                    } else {
                        print.er("[Ken]: That letter is not correct. Guess again!");
                        print.brk();
                    }
                    // display hard mode indicator if playing on Hard mode
                    if (gameChoice == 2) {
                        print.er("- HARD MODE ENABLED -", "center");
                    }
                    // ask player for their guess
                    print.er(String.format("You have %d guesses remaining.", maxGuesses - guesses), "center");
                    print.er("Guess a letter!", "center");
                    // display possible letter range if playing on Easy mode
                    if (gameChoice == 1) {
                        if ((int) answer < (int) guess) {
                            rHigh = guess;
                        } else {
                            rLow = guess;
                        }
                    }
                    range = "[" + rLow + "-" + rHigh + "]:";
                    guess = Character.toUpperCase(getIn.getChar(range));
                    print.brk(' ');
                    guesses++;
                    // check to see if game should end
                    if (guess == answer || guesses == maxGuesses) {
                        print.brk();
                        if (guess == answer) {
                            // winning sequence
                            gameStats[1]++; // increase wins by one
                            print.er(String.format("[Ken]: Congratulations, you won in %d guesses!", guesses));
                        } else {
                            // loosing sequence
                            print.er("[Ken]: Oh no, better luck next time!");
                        }
                        print.brk(' ');
                        print.er(String.format("[Ken]: The correct letter was: %c!", answer));
                        print.brk(' ');
                        gameStats[0]++; // increase games by one
                        break;
                    }
                }
            }
            // ask if player wants to play again or exit
            print.er("[Ken]: Would you like to play again?");
            print.brk();
            print.brk(' ');
            if (getIn.getBoolean("Play again? [yes/no]:", true)) {
                break;
            }
        }
        return gameStats;
    }

    /**
     * This method plays the Number Bash game.
     * It is a game where you guess the number the computer is thinking of
     *
     * @return returns the number of games won by the player
     */
    public static int[] numberbash() {
        // initialize variables
        int[] gameStats = new int[2]; // [games played, games won]
        final int maxGuesses = 8;
        String intro = String.format("""
                [Ken]: Welcome all to the fantastic game of Number Bash!
                
                [Ken]: In this game, contestants will have to guess the correct number chosen by the computer, but
                       they only have %d guesses to do so!
                
                [Ken]: There are a few game modes available for selection, each one ramping up in difficulty. Easy
                       mode has a range of 30 possible numbers, Medium with 50, and Hard has 80 possibilities for
                       correct numbers!
                
                [Ken]: Ready to play?
                
                """, maxGuesses);
        String menu = """
                Please make a selection from the available options below:
                
                [1] Play Number Bash! (Easy mode)
                [2] Play Number Bash! (Medium mode)
                [3] Play Number Bash! (Hard mode)
                
                [4] Play a Custom Number Bash! Game
                
                [0] Exit
                """;
        while (true) {
            // print out intro script
            print.head("NUMBER BASH");
            if (gameStats[0] == 0) {
                for (String str : intro.split("\n")) {
                    print.er(str);
                }
            } else {
                print.er("[Ken]: Welcome back! Ready to play again?");
            }
            print.brk(' ');
            // print out menu options
            for (String str : menu.split("\n")) {
                print.er(str);
            }
            print.brk();
            print.brk(' ');
            // get player selection, then begin game (or exit) based on player's choice
            int gameChoice = getIn.getPosInt(0, 4, "Game Selection [number]:");
            print.brk(' ');
            if (gameChoice == 0) {
                break;
            } else {
                // initialize variables
                int guesses = 0;
                int guess = 0;
                int rLow = 1;
                int rHigh = 1;
                switch (gameChoice) {
                    case 1:
                        rHigh = 30;
                        break;
                    case 2:
                        rHigh = 50;
                        break;
                    case 3:
                        rHigh = 80;
                        break;
                    default:
                        // define parameters for custom game
                        print.er("Please enter the size of the range of numbers you want the computer to pick within.");
                        print.brk(' ');
                        rHigh = getIn.getPosInt(10, 99, "Enter a range between 10 and 99:");
                }
                Random rand = new Random();
                int answer = rand.nextInt(rLow, rHigh);
                String range;
                // start game
                while (true) {
                    // print out header if game is starting
                    if (guesses == 0) {
                        print.head("NUMBER BASH");
                    } else {
                        print.er("[Ken]: That is not the correct number. Try again!");
                        print.brk();
                    }
                    // ask player for their guess
                    print.er(String.format("You have %d guesses remaining.", maxGuesses - guesses), "center");
                    print.er("Guess a letter!", "center");
                    // display possible number range
                    if (answer < guess) {
                        rHigh = guess;
                    } else {
                        rLow = guess;
                    }
                    range = "[" + rLow + "-" + rHigh + "]:";
                    guess = getIn.getPosInt(range, 2);
                    print.brk(' ');
                    guesses++;
                    // check to see if game should end
                    if (guess == answer || guesses == maxGuesses) {
                        print.brk();
                        if (guess == answer) {
                            // winning sequence
                            gameStats[1]++; // increase wins by one
                            print.er(String.format("[Ken]: You won in %d guesses! Congratulations!", guesses));
                        } else {
                            // losing sequence
                            print.er("[Ken]: Uh oh, maybe you should try again!");
                        }
                        print.brk(' ');
                        print.er(String.format("[Ken]: The correct number was: %d", answer));
                        print.brk(' ');
                        gameStats[0]++; // increase games by one
                        break;
                    }
                }
            }
            // ask if player wants to play again or exit
            print.er("[Ken]: Would you like to play again?");
            print.brk();
            print.brk(' ');
            if (getIn.getBoolean("Play again? [yes/no]:", true)) {
                break;
            }
        }
        return gameStats;
    }

    /**
     * This method plays the Monty Hall game.
     * It is a game where you guess the correct door that the prize is behind.
     *
     * @return returns the number of games won by the player
     */
    public static int[] montyhall() {
        // initialize variables
        int[] gameStats = new int[2]; // [games played, games won]
        String intro = """
                [Ken]: Welcome to a truly classic game: Monty Hall!
                
                [Ken]: Contestants who wish to play this game will have the opportunity to try and guess which door
                       is hiding the (metaphorical) car! Guess wrong however, and your door reveals a goat and
                       you lose!
                
                [Ken]: There is a twist however: after picking a door, the contestants will have the option to
                       switch which door they have chosen. Will this alter the course of history in your
                       favor? There's only one way to find out!
                
                [Ken]: Ready to play?
                
                """;
        String menu = """
                Please make a selection from the available options below:
                
                [1] Play Classic Monty Hall
                [2] Play Monty Hall Remix
                
                [0] Exit
                """;
        while (true) {
            // print out intro script
            print.head("MONTY HALL");
            if (gameStats[0] == 0) {
                for (String str : intro.split("\n")) {
                    print.er(str);
                }
            } else {
                print.er("[Ken]: Welcome back! Ready to play again?");
            }
            print.brk(' ');
            // print out menu options
            for (String str : menu.split("\n")) {
                print.er(str);
            }
            print.brk();
            print.brk(' ');
            // get player selection, then begin game (or exit) based on player's choice
            int gameChoice = getIn.getPosInt(0, 2, "Game Selection [number]:");
            if (gameChoice == 0) {
                break;
            } else {
                // initialize variables
                int doorCount = 3;
                if (gameChoice == 2) {
                    doorCount = 5;
                }
                String[] doors = new String[doorCount];
                for (int i = 0; i < doorCount; i++) {
                    doors[i] = getDoor(i + 1);
                }
                Random rand = new Random();
                int carDoor = rand.nextInt(0, doorCount);
                int badDoor = rand.nextInt(0, doorCount);
                int otherDoor = rand.nextInt(0, doorCount);
                // start game by printing out header and initial doors, then have the player pick a door
                printDoors(-1, doors);
                print.er("[Ken]: Please select the door you think is the winning door!");
                print.brk();
                print.brk(' ');
                int playerDoor = getIn.getPosInt(1, doorCount, "Door Selection [number]:") - 1;
                // select a door to show contents of
                while (badDoor == playerDoor || badDoor == carDoor) {
                    badDoor = rand.nextInt(0, doorCount);
                }
                doors[badDoor] = getDoor("open");
                // select a door to offer to switch to
                while (otherDoor == playerDoor || otherDoor == badDoor) {
                    otherDoor = rand.nextInt(0, doorCount);
                }
                // show updated doors to player
                printDoors(playerDoor, doors);
                print.er("[Ken]: That's an excellent choice!");
                print.brk(' ');
                print.er(String.format("[Ken]: Let's open door number %d and see what's inside!", badDoor + 1));
                print.brk();
                print.brk(' ');
                getIn.getNull();
                // open a bad door and ask if the player wants to trade for the other door
                doors[badDoor] = getDoor("goat");
                doors[otherDoor] = getDoor("open");
                printDoors(playerDoor, doors);
                print.er("[Ken]: Oh zonk! Looks like that door wasn't a winner! But you may still have a chance.");
                print.brk(' ');
                print.er("[Ken]: How confident are you feeling in your choice now? Would you like to switch doors?");
                print.brk();
                print.brk(' ');
                boolean swap = getIn.getBoolean(String.format("Switch to door #%d? [yes/no]:", otherDoor + 1),
                        false, false, true);
                if (swap) {
                    int temp = otherDoor;
                    otherDoor = playerDoor;
                    playerDoor = temp;
                }
                // display final state of doors before opening them
                doors[playerDoor] = getDoor("open");
                printDoors(playerDoor, doors);
                if (swap) {
                    print.er("[Ken]: Changing it up are we? Okay, let's see if that was the right decision!");
                } else {
                    print.er("[Ken]: Ooh I guess we are feeling confident! Let's see if you've won!");
                }
                print.brk();
                print.brk(' ');
                getIn.getNull("Press the \"Enter\" key to open your door!");
                // open all the doors and determine if the player won
                for (int i = 0; i < doorCount; i++) {
                    if (i == carDoor) {
                        doors[i] = getDoor("car");
                    } else {
                        doors[i] = getDoor("goat");
                    }
                }
                printDoors(playerDoor, doors);
                if (playerDoor == carDoor) {
                    // winning sequence
                    gameStats[1]++; // increase wins by one
                    if (swap) {
                        print.er("[Ken]: Congratulations! Good thing you switched to the winning door!");
                    } else {
                        print.er("[Ken]: You won, and didn't have to switch! Congratulations!");
                    }
                } else {
                    // losing sequence
                    if (swap && carDoor == otherDoor) {
                        print.er("[Ken]: Whoops! Looks like you should have gone with your gut feeling!");
                    } else {
                        print.er("[Ken]: Oh no! You got a goat, and that's not a good thing in this case.");
                    }
                }
                print.brk(' ');
                gameStats[0]++; // increase games by one
                // ask if player wants to play again or exit
                print.er("[Ken]: Would you like to play again?");
                print.brk();
                print.brk(' ');
                if (getIn.getBoolean("Play again? [yes/no]:", true)) {
                    break;
                }
            }
        }
        return gameStats;
    }

    /**
     * This method is a support to the Monty Hall game; it generates door ASCII art.
     *
     * @param doorNum  is an int that puts that number on the door
     * @param doorType is the kind of door to return
     * @return returns the string of the ASCII art door
     */
    public static String getDoor(int doorNum, String doorType) {
        return switch (doorType) {
            case "open" -> """
                       _________
                      /|       |
                     / |   ?   |
                    |  |       |
                    |  |       |
                    |  |_______|
                    | /
                    |/
                    """;
            case "goat" -> String.format("""
                       _________
                      /|       |
                     / |       |
                    |  |       |
                    |  |  %s   |
                    |  |_______|
                    | /
                    |/
                    """, Character.toString(0x1F410));
            case "car" -> String.format("""
                       _________
                      /|       |
                     / |       |
                    |  |       |
                    |  |  %s   |
                    |  |_______|
                    | /
                    |/
                    """, Character.toString(0x1F697));
            default -> String.format("""
                       _________
                       |       |
                       |   %d   |
                       |       |
                       |       |
                       |_______|
                    %s
                    %s
                    """, doorNum, " ", " ");
        };
    }

    // Overload - one input (int)
    public static String getDoor(int doorNum) {
        return getDoor(doorNum, "closed");
    }

    // Overload - one input (String)
    public static String getDoor(String doorType) {
        return getDoor(0, doorType);
    }

    /**
     * This method is a support to the Monty Hall game; it prints the ASCII doors to the command line.
     *
     * @param playerDoor is the door that the player currently has chosen
     * @param doors      is a String array that contains all the doors to be printed
     */
    public static void printDoors(int playerDoor, String[] doors) {
        // print header
        print.head("MONTY HALL");
        // initialize variables
        int pad = 5;
        StringBuilder doorString = new StringBuilder();
        // print out the icon that indicates which door the player has chosen
        if (playerDoor < 0 || playerDoor > doors.length) {
            print.brk(' ');
        } else {
            // reset StringBuilder
            doorString.setLength(0);
            for (int i = 0; i < doors.length; i++) {
                doorString.append(" ".repeat(pad));
                if (i == playerDoor) {
                    doorString.append(" ".repeat(7));
                    doorString.append("V");
                    doorString.append(" ".repeat(4));
                } else {
                    doorString.append(" ".repeat(12));
                }
                doorString.append(" ".repeat(pad));
            }
            // output resultant string
            print.er(doorString.toString(), "center");
        }
        // split all the doors up by line
        for (int line = 0; line < 8; line++) {
            // reset StringBuilder
            doorString.setLength(0);
            // combine the same line from each door into one string
            for (String door : doors) {
                doorString.append(" ".repeat(pad));
                doorString.append(door.split("\n")[line]);
                doorString.append(" ".repeat(12 - door.split("\n")[line].length()));
                doorString.append(" ".repeat(pad));
            }
            // output resultant string
            print.er(doorString.toString(), "center");
        }
        print.brk();
    }
}