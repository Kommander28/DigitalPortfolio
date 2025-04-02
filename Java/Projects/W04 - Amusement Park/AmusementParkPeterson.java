/*
* THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR STATED BELOW (Tim Peterson).
*        AUTHOR:    Tim Peterson
*    ASSIGNMENT:    "Park Ride" - CSCI 0012
*      DUE DATE:    15 Sep 24
*    SELF GRADE:    100 points (100%)
* JUSTIFICATION:    I have met all the requirements of this assignment because I met all the assignment requirements
* and additionally made it easily expandable to include any number of custom rides by adding two simple lines of code
* for any new ride added to the park.
*/

/**
 ************************************************************
 Welcome to Beech Bend Amusement Park.
 Height and weight requirements for our rides are shown below.
 please choose a ride that matches your height and weight

 ****************Blue Grass Breeze Ride**********************
 To ride blueGrassBreeze, the minimum height is :
 In inches: 38 inches
 In feet and inches: 3 feet  and 2 inches
 In centimeter: 96.52 centimeter

 ****************Cyclone Saucer Ride*************************
 Requirements to ride Cyclone SaucerRide:
 Weight: 105.0 pounds or 47.25 kilograms
 Height: 42.0 inches or 106.68 centimeter
 */
public class AmusementParkPeterson {
     public static void main(String[] args) {
          // print out and display park info
          String buffer = "~".repeat(10);
          String parkTitle = buffer + " Welcome to: BEECH BEND Amusement Park! " + buffer;
          int lineSize = parkTitle.length();
          System.out.println("~".repeat(lineSize));
          linePrint(parkTitle, '*', lineSize);
          System.out.println("~".repeat(lineSize));
          linePrint(" Park Hours: ", ' ', lineSize);
          linePrint(" Open from 10a - 10p daily! ", ' ', lineSize);
          System.out.println("~".repeat(lineSize));
          linePrint(" Height and weight restrictions for ", ' ', lineSize);
          linePrint(" our rides are shown below: ", ' ', lineSize);
          System.out.println();

          // create newRide classes for available park rides and print ride info
          newRide BlueGrassBreeze = new newRide("Blue Grass Breeze Ride", 38, 380.5);
          printDescription(BlueGrassBreeze, lineSize);
          newRide CycloneSaucer = new newRide("Cyclone Saucer", 42, 105.0);
          printDescription(CycloneSaucer, lineSize);
          newRide Twistinator = new newRide("Twistinator", 56, 330.0);
          printDescription(Twistinator, lineSize);
          newRide BigTopDrop = new newRide("Big Top Drop", 48, 290.5);
          printDescription(BigTopDrop, lineSize);

          // to crate your own new ride, add these two lines of code:
          // newRide <ride name, no spaces> = new newRide("<ride name>", <min height>, <max weight>);
          // printDescription(<ride name, no spaces>, lineSize);

          linePrint(" Enjoy your visit! ", '~', lineSize);
     }

     /** This class contains details about a particular ride. */
     public static class newRide {
          String name; // name of ride
          int minHeight; // minimum ride height
          double maxWeight; // maximum ride weight

          public newRide(String RideName, int minRideHeight, double maxRideWeight) {
               name = RideName;
               minHeight = minRideHeight;
               maxWeight = maxRideWeight;
          }
     }

     /** This method prints out the details of a particular ride.  */
     public static void printDescription(newRide rideIn, int lineLength) {
          // calculate alternate values
          String heightString = "Minimum Height Required: " + rideIn.minHeight + "in (" +
                  String.format("%dft %din", rideIn.minHeight / 12, rideIn.minHeight % 12) + ", " +
                  String.format("%.2f", convert(rideIn.minHeight, "length")) + "cm)";
          String weightString = "Maximum Weight Allowed: " + rideIn.maxWeight + "lb (" +
                  String.format("%.2f", convert(rideIn.maxWeight, "weight")) + "kg)";

          // print results
          linePrint(" " + rideIn.name.toUpperCase() + " ", '*', lineLength);
          linePrint(heightString, ' ', lineLength);
          linePrint(weightString, ' ', lineLength);
          System.out.println("*".repeat(lineLength) + "\n");
     }

     /** This method converts a length or a weight from imperial to metric units. */
     public static double convert(double num, String dim) {
          if (dim.equals("length")) {
               return num * 2.54; // in -> cm
          } else if (dim.equals("weight")) {
               return num * 0.45359237; // lbs -> kg
          } else {
               return num; // return input if invalid dimension
          }
     }

     /** This method prints text to a given length. */
     public static void linePrint(String text, char spacer, int size) {
          // error catch if text string is too long to print within the character count of the line
          if (text.length() > size) {
               System.out.println("Error! Line length is smaller than text input.");
               return;
          }
          int halfsize = (size - text.length()) / 2;
          if (halfsize % 2 == 1) {
               System.out.println((spacer + "").repeat(halfsize) + text + (spacer + "").repeat(halfsize + 1));
          } else {
               System.out.println((spacer + "").repeat(halfsize) + text + (spacer + "").repeat(halfsize));
          }
     }
}