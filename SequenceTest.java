/****
Authors: Carmen Chiu and Rafael Ferrer
File: SequenceTest.java
Description: Test file for the doublearrayseq.java file. Offers a switch to test each of the methods
****/

import java.util.*; //needed for scanner and exceptions

class SequenceTest
{
     //this method returns an integer input
   private static int acceptInput(Scanner keyboard)
   {
      int input = 0;
      boolean notDone = true;
      
      System.out.println("Please enter your option");
      while (notDone)
      {
         try
         {
            input = keyboard.nextInt();
            notDone = false;
            System.out.println("Your input is: " + input);
         }
         catch (Exception e)
         {
            System.out.println("Please enter an integer in the number format. No letters please.");
            keyboard.next();
         }
      }
      return input;
   }
   
   private static double acceptDoubleInput(Scanner keyboard)
   {
      double input = 0;
      boolean notDone = true;
      
      System.out.println("Please enter your option");
      while (notDone)
      {
         try
         {
            input = keyboard.nextDouble();
            notDone = false;
            System.out.println("Your input is: " + input);
         }
         catch (Exception e)
         {
            System.out.println("Please enter a valid number, not a letter.");
            keyboard.next();
         }
      }
      return input;
   }
   
   private static void equivalence(DoubleArraySeq sequenceOne, DoubleArraySeq sequenceTwo)
   {
      if (sequenceOne.equals(sequenceTwo))
      {
         System.out.println("Sequence A and B are equal to each other.");
      }
      else
      {
         System.out.println("Sequence A and B are not equal to each other.");           
      }         
   }

   public static void main (String[] args)
   {
      //create sequences and set sequenceOne as the active sequence
      DoubleArraySeq sequenceA = new DoubleArraySeq();
      DoubleArraySeq sequenceB = new DoubleArraySeq(5);
      DoubleArraySeq activeSequence;
      activeSequence = sequenceA;
      int choice = -1;
      double inputElement;
      int inputIndex = -1;
      
      //create scanner object
      Scanner keyboard = new Scanner (System.in);
      
      //switch for choosing tests
      System.out.println("Hello this is a program that allows you to create a sequence of demical numbers.");
      System.out.println("This program will create two sequences by default.");
      System.out.println("Here are the options: ");
      
      //create sequences
      
      System.out.println("1. Print the sequences of A and B");
      System.out.println("2. Report capacity of A and B");
      System.out.println("3. Is A and B equivalent to each other?");
      System.out.println("4. Change the active sequence to another sequence.(Default is A)");
      System.out.println("5. Add a number to the front of a sequence.");
      System.out.println("6. Add a number before another number.");
      System.out.println("7. Add a number after a number.");
      System.out.println("8. Add a number to the end of a sequence.");
      System.out.println("9. Add sequence B to end of A.");
      System.out.println("10. Replace a number with another number.");
      System.out.println("11. Delete a number at a certain index.");
      System.out.println("12. Delete the first number from the sequence.");
      System.out.println("13. Display a number at a certain index.");
      System.out.println("14. Display the last element in the sequence.");
      System.out.println("15. Trim extra capacity from A and B.");
      System.out.println("16. Create a clone sequence and show it.");
      System.out.println("17. Create a new sequence using concatenate of B and A and show.");
      System.out.println("18. Quit");
      
      while (choice != 18)
      {
         choice = acceptInput(keyboard);
         
         switch(choice)
         {
            case 1:
               System.out.println("Sequence A is: " + sequenceA.toString());
               System.out.println("Seqeunce B is: " + sequenceB.toString());
               break;
            case 2:
               System.out.println("Sequence A capacity is: " + sequenceA.getCapacity());
               System.out.println("Sequence B capacity is: " + sequenceB.getCapacity());
               break;            
            case 3:
               equivalence(sequenceA, sequenceB);
               break;            
            case 4:
               if (activeSequence == sequenceA)
               {
                  System.out.println("Switching the sequence to sequence B.");
                  activeSequence = sequenceB;
               }
               else
               {
                  System.out.println("Switching the sequence to sequence A.");
                  activeSequence = sequenceA;
               }
               break;            
            case 5:
               System.out.print("Please enter the number you would like to add.");
               inputElement = acceptDoubleInput(keyboard);
               activeSequence.addFront(inputElement);
               System.out.println("Number added!");
               break;            
            case 6:
               System.out.print("Please enter the number you would like to add.");
               inputElement = acceptDoubleInput(keyboard);
               activeSequence.addBefore(inputElement);
               System.out.println("Number added!");
               break;            
            case 7:
               System.out.print("Please enter the number you would like to add.");
               inputElement = acceptDoubleInput(keyboard);
               activeSequence.addAfter(inputElement);
               System.out.println("Number added!");
               break;            
            case 8:
               System.out.print("Please enter the number you would like to add.");
               inputElement = acceptDoubleInput(keyboard);
               activeSequence.setCurrentLast();
               activeSequence.addAfter(inputElement);
               System.out.println("Number added!");
               break;            
            case 9:
               System.out.println("Adding sequence B to A: ");
               sequenceA.addAll(sequenceB);
               break;            
            case 10:
               //don't know if we want to make a separate method that flat out replaces for this or
               //if we want to do a remove and addafter thing for this
               break;            
            case 11:
               System.out.print("Please tell me which index you would like to delete.");
               inputIndex = acceptInput(keyboard);
               activeSequence.setCurrent(inputIndex);
               activeSequence.removeCurrent();
               System.out.println("Deleted!");
               break;            
            case 12:
               System.out.println("Deleting the first number in the sequence.");
               activeSequence.removeFront();
               System.out.println("Deleted!");
               break;            
            case 13:
               System.out.print("Please enter the index number you are searching for.");
               inputIndex = acceptInput(keyboard);
               activeSequence.setCurrent(inputIndex);
               System.out.println(activeSequence.getCurrent());
               break;
            case 14:
               System.out.print("The last element in the sequence is: ");
               activeSequence.setCurrentLast();
               System.out.println(activeSequence.getCurrent());
               break;
            case 15:
               System.out.println("Trimming extra memory from sequences A and B.");
               sequenceA.trimToSize();
               sequenceB.trimToSize();
               break;
            case 16:
               System.out.println("Creating a clone of the active sequence.");
               DoubleArraySeq clonedSequence = activeSequence.clone();
               System.out.println("The cloned sequence is: " + clonedSequence.toString());
               break;
            case 17:
               System.out.println("Making a new sequence with A and B put together.");
               DoubleArraySeq combinedSequence = DoubleArraySeq.concatenation(sequenceA, sequenceB);
               System.out.println("The combined sequence is: " + combinedSequence.toString());
               break;    
            case 18:
               System.out.println("Quitting...");
               break;
         }//end of switch
      }//end of while
   }//end of main
}//end of SequenceTest class
