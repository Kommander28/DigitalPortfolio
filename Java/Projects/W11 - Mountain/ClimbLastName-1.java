/*
Name:
Description:
self-grade
Testimony: I have written the code all by myself __________________

*/

import java.util.*;
public class ClimbLastName  //change this name to include your last name
{
   //no code here
}
class RockClimb
{
  
   public static final int MAX_HEIGHT = 30;  //max height of a mountain
   public static void main(String [] args)
   {
      description();   
      execute();
   }
   public static void execute()
   {
      boolean b = true;
      Scanner kb = new Scanner (System.in);
      while(b)
      {
         climbUp(); 
         String user = yesNoAnswer(kb);
         kb.nextLine();
         if(user.equalsIgnoreCase("no"))
            b = false;
      }     
   }
   /*
   This method asks the user to enter yes/no answer.
   as long as the user is not entering a valid answer,
   the program must loop
   
   */
   public static String yesNoAnswer(Scanner kb)
   {
      
      String user = "";
      //while the content of the user variable is not equal to yes and is not equal to no(use equals method)  
      {
          //prompt the user to see if they want to climb upanother mountain
      
          //set the user to kb.next()
          
      }
      return user;
      
   }
  
  /* this method asks the height of the rock that a person will climb
  Data validation must be done to ensure that the highet is a positive integer
  while loop and do- while loop must be used.
  
  the height of the mountain cannot be negative and cannot be greater than MAX_HEIGHT 
   */
   public static int getHeight()
   {
      //create a scnner object
      
      //declare a variable of type int to hold the height of the mountain
      int h = 0;
         
      do
      {
         //prompt the user to enter the heigh
          
         //while the user has not entered an int , use hasNext()
        // while(!kb.hasNextInt())
         {
            //flush the buffer
            
            //prompt the user to enter the height of the mountain
             
         } 
         //read the suer's input and store it in h
         
         //flush the buffer
         
          
         //if h is less than 0
         { 
            //display a message that height cannot be negative
         }   
            
            
         //if h is greater than MAX_LENGTH
         {
             //disply a message that height cannot be more than MAX_LENGTH
         }   
          
      }while(h < 0 || h > 30);  
      System.out.println("You are about to climb a mountain with the height " + h + ". Get set, ready go!"); 
      return h;
   
   }
   public static void climbUp()
   {
      int distance = 0;  // distance that the user has climbed so far
      int slip = 0;  //number of the times the user sliped down
      
      //call the method getHeight and store the result in a variable name h 
      int h = getHeight();
      
      int left = h;  //the height that is left to climb
      
      //create a Random object
      Random rand = new Random();  
      do 
      {
         int r = 0;
         int steps = 0;
         //generate a random number between 0-1 inclusive and store it in a variable called r. Either 0 must be generated or 1 must be generated
          
         //if r is equal to 0
         {
            // steps  = 0;
            //generate a random number between 1-5 inclusive store it in a variable called steps. this how many steps the user is goin up
             
             //if h - distance is less than steps
         
                //set steps to h - distance
              
             //set distance to distance + steps
             
             
                        
            System.out.println("You climbed up " + steps + " steps");
            //set left to h - distance
            
            if (left > 0)
               System.out.println("You are " + left + " steps away to reach the top of the mountain");
            else
            {
               System.out.println("*************CONGRATULATIONS****************");
               System.out.println("You reached the tp of the mountain!");
               System.out.println("While clinmbing you sliped " + slip + " times.");
               System.out.println("*********************************************\n");
            }     
            
         } 
         //sliping down
         
        // else if r is equal to 1
         
          
         { 
            if (distance > 0)  //making sure that the user has climb up some steps before slipping down
            { 
               
               int down = 0; 
               //declare a random number between 1-5 and store it into down
               //if down is greater than h - distance
                   //set down to h - distance
               
               System.out.println("Sorry you slipped down " + down +" step(s)"); 
               if(distance != 0 && distance > down) // if the distance is greater than the number of the slips
               {        
                  distance = distance - down;  //decremrnting the steps the user slipped
                  slip = slip + down;   //holds the total number the person slipped while climbing up
               }
               else
               {
                  distance = 0;
               }
                               
               left = left + down;   //adding the slip to the height that is left to climb
               if(left > h)  //if the amount left is greater than the height of the mountain
                  left = h;
               System.out.println("You are " + left + " steps away to reach the top of the mountain"); 
            } 
         } 
         //System.out.println("The distance moved up so far : " + distance);  
           
      }while(distance < h);
        
   }
   /*
   This method displays a description for the program.
   create your own description. Do not use the provided one
   
   */
   public static void description()
   {
   
   
   
   }
}