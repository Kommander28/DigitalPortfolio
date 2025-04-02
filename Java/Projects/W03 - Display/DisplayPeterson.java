/*
* THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR STATED BELOW (Tim Peterson).
*        AUTHOR:    Tim Peterson
*    ASSIGNMENT:    "Display TEENS TENSE" - CSCI 0012
*      DUE DATE:    08 Sep 24
*    SELF GRADE:    100 points (100%)
* JUSTIFICATION:    I have met all the requirements of this assignment.
*/

/**
 * This code exactly matches the output of the file provided.
 * It does so by doing multiple calls to the same methods to increase code efficiency, reduce the possibility of
 * mistakes, and increase overall cleanliness and readability of the code.
 */
public class DisplayPeterson {
     public static void main(String[] args) {
          System.out.println("Keep true of the dreams of your  \n");
          // print the word TEEN in large letters
          printTeen();
          System.out.println("\n\n****************************************************\n\n");
          System.out.println("Winners live in the present  \n\n");
          // print the word TENSE in large letters
          printTense();
     }

     /** This method spells TEEN in large letters. */
     public static void printTeen() {
          printT();
          printE();
          printE();
          printN();
          printS();
     }

     /** This method spells TENSE in large letters. */
     public static void printTense() {
          printT();
          printE();
          printN();
          printS();
          printE();
     }

     /** This method displays two large "E"s */
     public static void printE() {
          String e = """
                  EEEEEEEEEE                            EEEEEEEEEE
                  E                                     E
                  EEEEEEEEEE                            EEEEEEEEEE
                  E                                     E
                  EEEEEEEEEE                            EEEEEEEEEE
                  """;
          System.out.println(e);
     }

     /** This method displays two large "N"s */
     public static void printN() {
          String n = """
                  N N     N                             N N     N
                  N   N   N                             N   N   N
                  N     N N                             N     N N
                  N       N                             N       N
                  """;
          System.out.println(n);
     }

     /** This method displays two large "S"s */
     public static void printS() {
          String s = """
                   SSSSSSSS                            SSSSSSSS
                  S        S                          S        S
                  S                                   S
                  S                                   S
                   SSSSSSSS                            SSSSSSSS
                          S                                   S
                          S                                   S
                   S      S                            S     S
                   SSSSSSSS                            SSSSSSSS
                  """;
          System.out.println(s);
     }

     /** This method displays two large "T"s */
     public static void printT() {
          String t = """
                  TTTTTTTTTT                            TTTTTTTTTT
                      TT                                    TT
                      TT                                    TT
                      TT                                    TT
                      TT                                    TT
                      TT                                    TT
                  """;
          System.out.println(t);
     }
}
