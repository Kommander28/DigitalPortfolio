/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 *    ASSIGNMENT:    "Mountain" - CSCI 0012
 *      DUE DATE:    Sun 10 Nov 24
 *    SELF GRADE:    100 points (100%)
 * JUSTIFICATION:    This program meets all the listed requirements of the assignment and the output matches the given
 * example. I apologize for turning this in late, however I added some extra elements to the game like a health system
 * and a timer. I also made it so you can see the mountain and your progress as you climb up (and fall down). Enjoy! :)
 */

import java.util.Arrays;
import java.util.Random;

public class MountainPeterson {
    // icon glossary
    public static final char iPLAYER = '\u237E';
    public static final char iTARGET = '\u22BB';
    public static final char iPASS = '\u2611';
    public static final char iFAIL = '\u2612';
    // icons with possible future use
//    public static final char iCAUTION = '\u26A0';
//    public static final char iGRAVE = '\u2020';
//    public static final char iSURPRISEFACE = '\u2364';
//    public static final char iCOIN = '\u235F';
//    public static final char iEMPTY = '\u26F6';

    // "global" variables
    public static final int MAXHEALTH = 10;     // maximum possible health
    public static final int FOOT = 100;         // width of the mountain


    public static int[][] MOUNTAIN;             // [0] are the slopes of the mountain, [1] are the elevations
    public static int PROGRESS = 0;             // player's current progress
    public static int HEALTH = 10;              // player's current health
    public static int PEAK = 20;                // highest possible point of the mountain
    public static int TIME = 30;                // player's time remaining
    public static int FOOD = 5;                 // player's current supply of food

    public static final String TITLE = Frame.title("CALAMITY CLIMBER");
    public static Random RNG = new Random();
    public static Screen SCREEN;

    public static void main(String[] args) {
        // opening screen
        Frame.one("""
                Welcome to Calamity Climber 3000
                the best ASCII mountain climbing game!
                
                In this game, your objective is to climb to the top of the mountain.
                You advance by rolling dice, and if your roll is high enough you progress up the mountain.
                However, if you fail, you will fall down part of the mountain - try not to get hurt!
                
                Will you perish, or make it to the top?
                
                To continue, enter a value for the height of the mountain (must be between 20 and 45).
                """, TITLE, true);
        PEAK = GetIn.getPosInt(20, 45, "Enter difficulty (lower is easier) [20,45]:");
        SCREEN = new Screen(FOOT + 3, PEAK + 2);
        // main loop
        do {
            // play game
            playGame();
            // ask the player if they want to play another game
            Frame.one("""
                    Thank you for playing!
                    
                    Would you like to climb again?"
                    """, TITLE, true);
            if (GetIn.getBoolean("Play another game? [yes/no]:")) {
                Frame.one("""
                        Welcome back to Calamity Climber!
                        
                        To continue, enter a value for the height of the mountain (must be between 20 and 45).
                        """, TITLE, true);
                PEAK = GetIn.getPosInt(20, 45, "Enter difficulty (lower is easier) [20,45]:");
            } else {
                break;
            }
        } while (true);
        // exit program with a friendly message
        Frame.draw(new String[]{TITLE, Frame.format("Have a great day!\n:)")});
        GetIn.KB.close();
    }

    /**
     * This method plays the game once.
     */
    public static void playGame() {
        // reset stats
        TIME = 30 + RNG.nextInt(25);
        boolean blizzardWarning = false;
        HEALTH = MAXHEALTH;
        PROGRESS = 0;
        FOOD = 5;
        // reset screen
        SCREEN.clear();
        MOUNTAIN = generateMountain();
        int[] nextSpot = move(0);
        // add altimeter to screen
        SCREEN.line(0, '-');
        SCREEN.line(1, '-');
        SCREEN.line(2, '|');
        for (int y = 0; y <= PEAK; y += 5) {
            if ((y + "").length() < 2) {
                SCREEN.plot(0, y, '0');
                SCREEN.plot(1, y, ("" + y).charAt(0));
            } else {
                SCREEN.plot(0, y, ("" + y).charAt(0));
                SCREEN.plot(1, y, ("" + y).charAt(1));
            }
        }
        // print out start screen
        drawGame(Frame.title("How to Play") + "\n" + Frame.format(String.format("""
                - Make a check to see if you (the [%c] icon) advance up the hill by rolling a pair of dice.
                    If you pass the check, you move up the hill to the [%c] icon.
                    If you fail the check, you fall back to the nearest safe location and take some damage.
                - You can choose to eat (if you have food remaining) or rest if you need to regain health.
                - Be careful: the weather is slowly getting worse, so you have to keep moving and summit the mountain before the storm arrives!
                """, iPLAYER, iTARGET), "left"));
        GetIn.getNull("Press the \"Enter\" key to begin!");
        // main game loop
        while (true) {
            // action sequence
            String choice = drawGame();
            if (choice.equalsIgnoreCase("EAT")) {
                // try to eat, but only if there is food
                if (FOOD == 0) {
                    Frame.one("""
                            You search your bag for something to eat, but find nothing.
                            
                            You are out of food.
                            """, TITLE, true);
                    TIME -= 2;
                } else {
                    // TODO add a small chance of food poisoning in the future
                    Frame.one("""
                            You manage to find (and eat) some very, very cold food.
                            
                            You regained a small amount of health.
                            """, TITLE, true);
                    TIME -= 3;
                    FOOD -= 1;
                    HEALTH += 2;
                }
                GetIn.getNull();
            } else if (choice.equalsIgnoreCase("REST")) {
                // try to rest, but only if not at full health or more
                if (HEALTH >= MAXHEALTH) {
                    Frame.one("""
                            You find a soft rock to try and get some sleep, but are unable to successfully do so.
                            
                            You are already at full health and have no need for rest.
                            """, TITLE, true);
                    TIME -= 4;
                } else {
                    Frame.one("""
                            After finding a small hole in a cliff face, you manage to get some sleep
                            safely hidden from the elements.
                            
                            You feel better and have regained full health.
                            """, TITLE, true);
                    TIME -= 5;
                    HEALTH = MAXHEALTH;
                }
                GetIn.getNull();
            } else {
                // make a check and increment time used
                boolean check = roll(nextSpot[1]);
                if (nextSpot[1] >= 6) {
                    TIME -= 1;
                }
                TIME -= 1;
                // determine character movement
                if (check) {
                    nextSpot = move(nextSpot[0]);
                    if (nextSpot[0] >= FOOT - 1) {
                        // player made it to the top of the mountain and won!
                        Frame.one("""
                                Congratulations!
                                You made it to the top of the mountain!
                                \uD83C\uDF82
                                
                                You pause to admire the incredible view from above the clouds, watching as the wicked
                                storm that was once brewing above you now rolls lazily beneath. The evil clouds that
                                you know surely would have caused your demise now pass harmlessly by without so much
                                as a light breeze that nips at your ears.
                                
                                Thankful to have survived the tempest and the treacherous climb, you make your way
                                to the nearby bus stop and have a peaceful, uneventful ride safely home.
                                """, TITLE, true);
                        GetIn.getNull();
                        return;
                    }
                } else {
                    if (PROGRESS == 0) {
                        // player is at the start and cannot fall
                        TIME -= 1;
                        Frame.one("""
                                Instead of climbing, you decided to double check your pack one more time.
                                
                                Just to be sure.
                                
                                Did that help your anxiety?
                                
                                Good - now get moving!
                                """, TITLE, true);
                        GetIn.getNull();
                    } else {
                        nextSpot = fall(nextSpot[0]);
                    }
                }
            }
            // determine if game ends
            if (HEALTH <= 0) {
                // player died
                Frame.one("""
                        Falling one last time, you unfortunately cannot get up after hitting the ground.
                        
                        A search party will have to wait a few weeks for the storm to pass to look for your remains.
                        
                        You have failed to climb the mountain.
                        """, TITLE, true);
                GetIn.getNull();
                return;
            } else if (TIME <= 10 && !blizzardWarning) {
                // player lost in storm
                Frame.one("""
                        You start to now notice a steady snowfall around you, looking almost magical as the evening
                        sun bounces off each tiny crystal as it flutters down from the clouds.
                        
                        As you look around, entranced by the beautiful scene surrounding you, your eyes focus on some
                        darker clouds off in the distance that are headed in your direction.
                        
                        Noting this, you begin to pick up the pace as the snowfall around you continues to fall.
                        """, TITLE, true);
                blizzardWarning = true;
                GetIn.getNull();
            } else if (TIME <= 0) {
                // player lost in storm
                Frame.one("""
                        The light snowfall has manifested into a raging snowstorm that thunders down upon you!
                        
                        With no visibility remaining, you decide to attempt to continue to climb the mountain.
                        
                        Unfortunately, you were unable to find the top of the mountain, and when the storm passed
                        several weeks later, search parties were unable to ever recover your body.
                        
                        You have failed to climb the mountain.
                        """, TITLE, true);
                GetIn.getNull();
                return;
            }
        }
    }

    /**
     * This method draws all the frames that the game repeatedly calls.
     *
     * @param finalFrame is the contents of the last frame
     */
    public static void drawGame(String finalFrame) {
        // add frames to frame array
        String[] frameList = new String[6];
        frameList[0] = TITLE;
        frameList[1] = SCREEN.toString();
        frameList[2] = Frame.progress(PROGRESS, "Mountain Progress");
        frameList[3] = Frame.item(HEALTH, MAXHEALTH, new String[]{"\u2591 ", "\u2592 ", "\u2593 "}, "Player Health");
        frameList[4] = Frame.item(FOOD, 5, new String[]{"  ", "\u25C9 "}, "Food");
        frameList[5] = finalFrame;
        // print updated frame array
        Frame.draw(frameList);
    }

    // overload - 0 inputs
    public static String drawGame() {
        drawGame(Frame.format("""
                ACTION MENU
                
                - CLIMB:  attempt to climb higher
                -   EAT:  regenerate some health   (consumes 1 food)
                -  REST:  regenerate all health    (consumes 2 food)
                """, "left"));
        return GetIn.getString(new String[]{"CLIMB", "EAT", "REST"}, true, true);
    }

    /**
     * This method moves the player and the target icon.
     *
     * @param moveSpot the spot on the mountain to move the player to
     * @return is an int array where:
     * [0] is the target spot for the player to move to next
     * [1] is the difficulty of how hard it is to get to that spot
     */
    public static int[] move(int moveSpot) {
        // move character
        SCREEN.plot(PROGRESS + 3, MOUNTAIN[1][PROGRESS] + 1, ' ');
        SCREEN.plot(moveSpot + 3, MOUNTAIN[1][moveSpot] + 1, iPLAYER);
        PROGRESS = moveSpot;
        // find next target, then move target
        int nextTarget = PROGRESS, difficulty = 0;
        for (int i = PROGRESS + 1; i <= FOOT; i++) {
            // check if next climbing point is at the end
            if (i >= FOOT - 1) {
                nextTarget = FOOT - 1;
                break;
            }
            // if slope changes direction, stop looking immediately
            if ((difficulty > 0 && MOUNTAIN[0][i] < 0) || (difficulty < 0 && MOUNTAIN[0][i] > 0)) {
                nextTarget = i;
                break;
            }
            // stop if there is a flat area
            if (MOUNTAIN[0][i] == 0 && MOUNTAIN[0][i + 1] == 0) {
                do {
                    i++;
                } while (MOUNTAIN[0][i] == 0);
                nextTarget = i;
                break;
            }
            // add current slope to difficulty and loop
            difficulty += MOUNTAIN[0][i];
        }
        // update screen and return
        SCREEN.plot(nextTarget + 3, MOUNTAIN[1][nextTarget] + 1, iTARGET);
        return new int[]{nextTarget, Math.abs(difficulty)};
    }

    /**
     * This method moves the player back after failing a roll.
     * In case of a tie, this method prioritizes falling backwards.
     *
     * @param targetSpot the spot on the mountain where the target used to be located
     * @return is an int array where:
     * [0] is the target spot for the player to move to next
     * [1] is the difficulty of how hard it is to get to that spot
     */
    public static int[] fall(int targetSpot) {
        // calculate new movement spot
        int fallSpot, fallDamage = MOUNTAIN[1][PROGRESS], leftSpot = PROGRESS, rightSpot = PROGRESS;
        boolean fallLeft = false, fallRight = false;
        String description = "";
        while (true) {
            // check endpoints
            if (leftSpot - 1 != 0) {
                leftSpot--;
            }
            if (rightSpot + 1 != FOOT) {
                rightSpot++;
            }
            // see which way downhill is
            if (MOUNTAIN[0][leftSpot] > 0 && MOUNTAIN[0][rightSpot] == 1) {
                // fall backwards
                description = "You tumble down the mountain, losing progress, health, and sanity.";
                fallLeft = true;
            } else if (MOUNTAIN[0][leftSpot] == -1 && MOUNTAIN[0][rightSpot] < 0) {
                // fall forwards
                description = "You manage to fall the correct direction, gaining progress at a cost.";
                fallRight = true;
            } else if (MOUNTAIN[0][leftSpot] == -1 && MOUNTAIN[0][rightSpot] == 1) {
                // don't move, player is in a valley or flat spot
                fallSpot = PROGRESS;
                description = "You thankfully did not get hurt as you do not have far to fall.";
                break;
            }
            // check to see if at the beginning
            if (leftSpot - 1 <= 0 && fallLeft) {
                fallSpot = 0;
                break;
            } else if (fallLeft) {
                do {
                    leftSpot--;
                    if (leftSpot <= 0) {
                        break;
                    }
                } while (MOUNTAIN[0][leftSpot] > 0);
                fallSpot = leftSpot;
                break;
            }
            // check to see if at the end
            if (rightSpot + 1 >= (FOOT - 1) && fallRight) {
                fallSpot = FOOT;
                break;
            } else if (fallRight) {
                do {
                    rightSpot++;
                    if (rightSpot >= FOOT - 1) {
                        break;
                    }
                } while (MOUNTAIN[0][rightSpot] <= 0);
                fallSpot = rightSpot;
                break;
            }
        }
        // calculate damage
        fallDamage -= MOUNTAIN[1][fallSpot];
        if (fallDamage > 8) {
            HEALTH -= 5;
        } else if (fallDamage > 5) {
            HEALTH -= 3;
        } else {
            HEALTH -= Math.min(fallDamage, 1);
        }
        // update values and return
        Frame.one(description, TITLE, true);
        GetIn.getNull();
        SCREEN.plot(PROGRESS + 3, MOUNTAIN[1][PROGRESS] + 1, ' ');
        SCREEN.plot(targetSpot + 3, MOUNTAIN[1][targetSpot] + 1, ' ');
        PROGRESS = fallSpot;
        return move(fallSpot);
    }

    /**
     * This method returns a 6-sided dice roll
     *
     * @param challenge is the number you are trying to beat to pass the check
     * @return is a string array that is the die face [0] and the value [1] of the roll
     */
    public static boolean roll(int challenge) {
        // initialize variables
        char[] die = new char[]{'\u2680', '\u2681', '\u2682', '\u2683', '\u2684', '\u2685'};
        StringBuilder out = new StringBuilder();
        Random dieRoll = new Random();
        int dice = 2, sum = dice;
        // normalize challenge
        if (challenge > (dice * die.length) - 2) {
            challenge = (dice * die.length) - 2;
        } else if (challenge < (die.length / dice) + 2) {
            challenge = (die.length / dice) + 2;
        }
        // roll dice
        for (int i = 0; i < dice; i++) {
            int roll = dieRoll.nextInt(die.length);
            sum += roll;
            out.append(String.format("%c  ", die[roll]));
        }
        out.append(String.format("\nYour roll:  %d\n", sum));
        if (sum > challenge) {
            out.append(iPASS).append("\nYou passed the check!");
        } else {
            out.append(iFAIL).append("\nYou failed the check!");
        }
        // display dice roll and return output
        Frame.one(out.toString(), TITLE, true);
        GetIn.getNull();
        return sum > challenge;
    }

    /**
     * This method generates an int array consisting of the slope values of the mountain the player needs to climb.
     *
     * @return returns an int array of arrays containing the slope values for the mountain and the elevations
     */
    public static int[][] generateMountain() {
        // initialize variables
        int[] mountain = new int[FOOT];
        int elevation = 0;
        // generate the new mountain
        for (int i = 0; i < FOOT; i++) {
            // slope of beginning and end should be 0
            if (i == 0 || i == FOOT - 1) {
                mountain[i] = 0;
            } else {
                // determine whether slope goes up or down
                int s = (int) Math.round(RNG.nextDouble(
                        1.5 * ((2.0 * Math.abs((double) i / FOOT - 0.5) - 1.0) - (double) PEAK / FOOT),
                        2 * ((double) i / FOOT + 1.5) * (2 * (double) PEAK / FOOT)
                ));
                if (s >= 1) {
                    s = 1;
                } else if (s <= -1) {
                    s = -1;
                } else {
                    s = 0;
                }
                // keep height within limits
                if (elevation + s <= 0) {
                    elevation += 1;
                    mountain[i] = 1;
                } else if (elevation + s > PEAK) {
                    elevation -= 2;
                    mountain[i] = -1;
                    mountain[i + 1] = -1;
                    i++;
                } else {
                    elevation += s;
                    mountain[i] = s;
                }
            }
        }
        return drawMountain(mountain);
    }

    /**
     * This method prints out a string representing the mountain the player needs to climb.
     *
     * @param mountain is the int array of slopes of the mountain to draw
     */
    public static int[][] drawMountain(int[] mountain) {
        // initialize variables
        char defaultChar = '\u2500';
        StringBuilder map = new StringBuilder();
        char[] topography = new char[FOOT];
        int[] elevation = new int[FOOT];
        int height = 0;
        // map out mountain elevation lines
        for (int i = 0; i < FOOT; i++) {
            elevation[i] = height;
            height += mountain[i];
            if (i == 0 || i == FOOT - 1) {
                // beginning and end values
                topography[i] = defaultChar;
            } else {
                // calculate terrain visually
                topography[i] = switch (Arrays.toString(new int[]{mountain[i - 1], mountain[i]})) {
                    // upward -> upward
                    case "[1, 1]" -> '\u250C';
                    // upward -> flat
                    case "[1, 0]" -> '\u250C';
                    // upward -> downward (peak)
                    case "[1, -1]" -> '\u2581';
                    // flat -> upward
                    case "[0, 1]" -> '\u2518';
                    // flat -> downward
                    case "[0, -1]" -> '\u2510';
                    // downward -> upward (valley)
                    case "[-1, 1]" -> '\u2594';
                    // downward -> flat
                    case "[-1, 0]" -> '\u2514';
                    // downward -> downward
                    case "[-1, -1]" -> '\u2510';
                    // all else
                    default -> defaultChar;
                };
            }
        }
        // plot mountain onto screen
        for (int i = 0; i < FOOT; i++) {
            SCREEN.plot(i + 3, elevation[i], topography[i]);
        }
        return new int[][]{mountain, elevation};
    }
}
