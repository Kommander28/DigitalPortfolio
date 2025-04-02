/*
Name:
Date:
Description
Self-grade:
Testimony: I have written all the code by myself. Did not copy and paste the code from any resources: name: _______________

The output must match the following output. Any deviation will result in losing points
Sample output:

*******************************************************
Welcome to Beech Bend Amusment Park.
Height and weight requirmnets for our rides are shown below.
please choose a ride that matches your height and weight
*******************************************************
 
****************Blue Grass Breeze Ride***************************
To ride blueGrassBreeze, the minimum heigh is : 
In inches: 38 inches 
In feet and inches: 3 feet  and 2 inches
In centimeter: 96.52 centimeter
  
 ****************Cyclone Saucer Ride******************************
Requirments to ride Cyclone SaucerRide:
Weight: 105.0 pounds or 47.25 kilograms
Height: 42.0 inches or 106.68 centimeter


*/
public class AmusmentParkLastname
{
  //no code here
}
class AmusmentPark
{
   public static void main(String[] args)
   {
     //call the method description
      description();
      System.out.println("\n\n****************Blue Grass Breeze Ride***************************");
      //call the method blueGrassBreezeRide
      
      System.out.println("\n\n****************Cyclone Saucer Ride******************************");
      //call the method cycloneSaucersRide
      
   }
   
   /*method of your choice which represents a ride at the amusment park
   You must change the "name" to a name that represents the ride.
   This method has 20 points.*/
   public static void name()
   {
   }
   
   /*
   This method converts the minimum required height in inches to feet and inches, and centimeter:
   1 feet is 12 inches, 
   one inch is 2.54 centimeter
   
   For example 45 inches is equal to 45/12 = 3 feet and 45%12 = 9  inches
   45 inches is equal to 45 * 2.54 = 114.3  centimeters
       
   */
   public static void blueGrassBreezeRide()
   {
      int  mindHeightInches = 38; //heigh in inches
      //converting inches to feet and inches
      
      //declare a variable of type int to hold the number of feets: midHeightInches/12 
      
      //declare a variable of type int to hold the number of inches. To calculate this : midheightInches % 12
      
      
      //converting the height to centimeter
      
     // declare a variable of type double to hold the amount of centimeter: minHeightInches * 2.54
       
      
      //display the calculated info excatly the same as the given output. The format and the calculation must be exact same
      System.out.println("To ride blueGrassBreeze, the minimum heigh is : ");
      
      //display the mindHeightInches. Refer to the out for formatting
       
      //display feet and inches: refer to the output for formatting
      
      //display the height in centimeter. refer to the formatting
       
      
   }
   /*
   This method converts the max required weight in pounds to kilogram
   and maximum required height to feet inches and centimeter. 
   1 pound is 0.45 kilograms
   1 feet = 12 inches
   i inch is 2.54 centimeter
   
   
   */
   public static void cycloneSaucersRide()
   {
      double maxdWeightPounds = 105;   
      double minHeightInches = 42;
      
     //conversion
      //declare a variable of type double, calculate the given weigh in kilogram and store it in this variable
       
      
      //declare a variable of type double, calculate the given heigth in centimeters and store it in this variable
       
      
      System.out.println("Requirments to ride Cyclone SaucerRide:");
      
      //display the weight in pounds and kilograms. refer to the output for formatting
       
      //display the height in inches and centimeters. refer to the output for formatting
      
           
   }
   
   /*
   This method display a description of what the program does. Please provide your own description.
   Your description must be different than the given description.
   */
   public static void description()
   {
      //your code
          }
}