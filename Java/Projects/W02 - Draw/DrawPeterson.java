/*
* THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR STATED BELOW (Tim Peterson).
*        AUTHOR:    Tim Peterson
*    ASSIGNMENT:    "Draw" - CSCI 0012
*      DUE DATE:    30 Aug 24
*    SELF GRADE:    100 points (100%)
* JUSTIFICATION:    I have met all the requirements of the assignment. I made my robot look a little more sassy and
* hipster, but the purpose of the assignment is still fully met. If needed, set "show_original" to "true" and the code
* will instead exactly reproduce the original robot from the assignment, however I thought it might be nice to break up
* the monotony of similar robots that you have to grade. :)
*/

/**
 * The DrawPeterson class draws a fun little ASCII art robot.
 * My robot is 16 characters wide and contains an attribute "attitude."
 * The original robot contains no such attribute, and can easily replace the other robot if elevated levels of
 * "attitude" become a health hazard.
 */
public class DrawPeterson {
     public static void main(String[] args) {
          System.out.println();

          // set this to "true" to display the original robot from the assignment instead of my robot
          boolean show_original = false;

          if (show_original) {
               original_robot();
          } else {
               hat();
               head();
               body();
               legs();
          }
     }

     // backwards baseball hat
     public static void hat() {
          System.out.println("   ____--____   ");
          System.out.println("  / \"SPORTS\" \\ ");
          System.out.println(" /____<==>____\\");
     }
     // head and face featuring a look of doubt
     public static void head() {
          System.out.println("   |  _  -  |   ");
          System.out.println("   |  O  O  |   ");
          System.out.println("   |    >   |   ");
          System.out.println("   |  __    |   ");
          System.out.println("   |________|   ");
     }
     // torso and arms and a shirt that alludes to the original robot
     public static void body() {
          System.out.println("  _____||_____  ");
          System.out.println(" /|          |\\ ");
          System.out.println("/ \\   What   / \\");
          System.out.println("\\  | Prize? |  |");
          System.out.println("  @|        |  |");
          System.out.println("   |________|  m");
     }
     // legs and rollers (feet)
     public static void legs() {
          System.out.println("     ||  ||     ");
          System.out.println("     ||  ||     ");
          System.out.println("     ||  ||     ");
          System.out.println("     ||  ||     ");
          System.out.println(" (oooo|  |oooo) ");
     }

     // the original robot
     public static void original_robot() {
          // hat
          System.out.println("     ____\\/____");
          System.out.println("    /          \\");
          System.out.println("   /    NOBEL   \\");
          System.out.println("  /______________\\");
          System.out.println("     |||||||||");
          System.out.println("     |||||||||");
          // head and face
          System.out.println("    | ^      ^ |");
          System.out.println("   /| O  /\\  O |\\");
          System.out.println("  O |    \\/    | O");
          System.out.println("    |   IIII   |");
          System.out.println("    |__________|");
          // neck and torso
          System.out.println("        | |");
          System.out.println("    ___ | | ___");
          System.out.println("   |           |");
          System.out.println("  /|   Prize   |\\");
          System.out.println(" / |___________| \\");
          // legs and ...feet?
          System.out.println("*     ||   ||     *");
          System.out.println("      ||   ||");
          System.out.println("      ||   ||");
          System.out.println("    __||   ||__");
          System.out.println("   |__||   ||__|");
          System.out.println("   |\\\"_\"_\"_\"_\"/|");
          System.out.println("   | \\\"_\"_\"_\"/ |");
          System.out.println("   |  \\\"_\"_\"/  |");
          System.out.println("   |   \\\"_\"/   |");
          System.out.println("   |    \\\"/    |");
          System.out.println("   |___________|");
     }
}