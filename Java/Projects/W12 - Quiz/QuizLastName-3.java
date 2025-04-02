/*

name:
description
self-grade
Testimony: I have written the code by myself with no help from the internet or any other AI sites,
I am aware that getting help from unauthorized sources will result in a zero points.   name:_________
Any submission from previous semesters will be given zero points

Extra feature 35 points: The extra feature deatails are given below. Please read carefully to find out about it. 

DO NOT REMOVE ANY OF THE GIVEN COMMENTS
*/
import java.util.*;
public class QuizLastName
{
  // no code here
}

class Quiz 	 
{
    
   public static void main(String[] args)
   {
      
      makeExam(); 
   }
   /* 
   This method declares 3 arrays to hold questions, answers and the points for each question.
   calls the method populate to fill in the three arrays with math questions, answers and points
   
   */
   public static void makeExam()
   {
      Scanner kb = new Scanner(System.in);
      Random rand = new Random();
      int size = rand.nextInt(10- 5+ 1 ) + 5; //randomly select the number of the questions for the quiz 
      //declare an array of String named questions to hold each test question, with the length of the variable size
      
      String[] questions = new String[size]; 
      //declare an array of int called answers to hold the correct answer for each questions that is in the array questions
      int[] answers = new int[size];
      //declare an array of int called points that holds the pints for each quiz question.
      
      int[] points= new int[size];
      
     // Random  rand = new Random();
      
      boolean repeat = true;
      /*the code in the given while cannot be changed*/ 
      while(repeat)
      {
               
         populate(questions,answers,points);  //initializes the three arrays with questions, answers and points
          
         int[]  score = takeExam(answers,points, questions) ;  // score will hold the points earned for each peoblem
         System.out.println("Here are the scores for each question: "); 
         System.out.println(Arrays.toString(score));      
         int total = total(score);
         System.out.println("Total points earned: " + total);
         System.out.println("\n\nDo you want to take the again!!!!"); 
         String user = yesNo(kb);
         repeat = user.equalsIgnoreCase("yes");
        
      }      
   } 
   /*This method accepts an array of integers and returns the sum of all the numbers stored in the array*/
   public static int total(int[] scores)
   {
      //declare an int called total , set it to zero
      
      //for i = 0 to the length of the array)
      {
        //set total to total + scores[i]
      }
      //return total;
      return 0;   // modifiy this line
   }
   
   /*
   This method gets the user's input yes or no, anything else will not be accepted.
   while loop is needed. as long as the user is not entering yes and not entering no, keep prompting the user
   */
   public static String yesNo(Scanner kb)
   {
   
   
      return "";
   }
   /*This method choose an operation and returns it
   a random number must be generated to use as an index for the string "*%/+-^"
   for example if the random number is 1 then % will be selected. s.charAt(index) should be used
   
   
   Extra feature(15) : must add 5 different operations from the math class: 
   These methods are  ceil, max, min, absolute value, log
   
   These extra operations must be added to the array in the givemn method below
   
   */
   
   public static char getOp(Random rand)
   {
      String[] s = {"%","/","+","-"};
      //generate a random number and store it in a variable called index in the range of 0-4 inclusive
       
      //return the char at the given index in string s
       
      return ' '; // must change this line
   }
   /*
     this method select a random number for out simple expression (num operation num ) and returns it
     this method fills an array of int with the size 100 with random numbers 1-50
     once the array is filled with random numbers, we select a number from the array randonmly;
     
   */
   public static int getNum()  
   {
      Random rand = new Random();
      //declare an array of int called nums with size 100
      
      //initialize the array with 100 randon numbers between 1-50 by using a for loop
      
      //for i = 0 to i less than the lenght of the array you declared: nums.length
      
      {
         //set nums[i] to a random number between 1- 50 inclusive
         
      }
      //declare an int variable called index and set it to a random number 0-100  which will be the index of the array. 100 is not a valid index
      
      //return  the value stored in the array at index 
      
      return 0; // must change this line
   }
   /* This method generates math expressions randomly, the operations used are /+ * % -  plus the extra one you must add
   each math expression will be stored in the array called quiz, the correct answer for each expression will be stored
   in the array answe, and the points for each question will be stored in the array points. 
   max and min are used to generate a random number between min and max for each number.
   Valid operations are stored in the array operation. a random number between 0 and 3 must be generated 
   to pick a random operation
    
    and assign points for each question in the array values
    This method has to be modified based on the aded extra operations.
    some of the added operations are uniary thefore only one opernad is needed
    */
   public static void populate(String[] quiz, int[] answer,  int[] points)
   {
       
      Random rand = new Random();
      for(int i =  0; i < quiz.length; i++)
      {
         //dexlare an int called num1 and set it to getNum()   
          
         //declare an int called num2 and set it to getNum()
         
         //int index = rand.nextInt(5);
         
         //declare a variable of type String and set it to getOp(rand)
         //if operand is log ceil or floor we only need one operand, 
         
         //set quiz[i] to num1 +  op +  " " + num2 , must use conditional statemnet to store the correct expression in the quiz array
         
         //set answer[i] by calling the evaluate method passing num1, num2 and op to it, if the operator is uniary then send 0 for num2
          
         //declare a variable of type int called earned and set it to a random number 1-10
         
         
         //set points[i] to the earned variable
         
       
      }  
      	 
         
   }
   /*
   this method gets two numbers and an operator and returns the result
   do not change anything in this method. The order of the parameters should not be changed
   
   Cannot use switch statments. codes using cse/switch will get no points
   
   Extra feature 15 points: 
   The code in the following method must be modified to include the 5 extra operations you added in the method getOp
   
   */
   public static int evaluate(int num1, int num2, String op) 
   {
      String[] operation = {"*", "/","-","+"};
      if(op.equalsIgnoreCase("*"))
          return num1 * num2; 
       //complete the rest   
      
      
      return 0;//must chnage this line
   }
/* this method gets the three arrays containg the questions, the correct answers and the points for each question 
 this method displays one question at a time for the user to answer, verifies the user's answer
 by comparing it to the proper index in the answers array
 if the person's answer is correct then the points will be stored in an array.
 this method returns an array
 
 Do not change the return or the list of the parameters in this method
 
 */
   public static int[] takeExam(  int[] answers, int[] values, String[] questions)
   
   {
      
      info(); 
      int[]  scores = new int[answers.length]; //this arrays holds the points for each question the user answers it correctly
      Scanner kb = new Scanner(System.in);
      
      for(int  i = 0; i < answers.length; i++)
      {
         //3. display the question in the questions array at the index i
         
          
         //4. declare an int variable called ans to store the user's answer
         
         //call the method getAnswer to get the user's response
          
         //if ans is equal to the answers[i]
          
         {
            //set scores at index i to values at index i
            
           //display the message correct
            
           
         }
         //else
            System.out.println("Incorrect");
                            
      }
      //return the array scores
      return null;  //must change this
                   	 	 
   
   }
   
   /*Extra feature 5 points: This method prompts the user to enter the answer.
   The data type for the asnwer is a double. as long as the  user
   is not entering a double keep propmting the user. hasNextDouble method must be used*/
   public static int getAnswer(Scanner kb)
   {
     return 0; 
      
   }
   /*Must display information about this app. It has to be your own creation  and not a copy of the given info*/
   public static void info()
   {
       
   }	
} 
