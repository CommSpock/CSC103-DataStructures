/*********************************************************
* Authors: 
*   Carmen Chiu and Rafael Ferrer
* File: 
*   LargeNumberTest.java
* Description: 
*   Test file for the UnboundedInt.java file. 
*   Offers a switch to test each of the methods.
*********************************************************/

import java.util.*; //needed for scanner and exceptions

class LargeNumberTest
{
	//this method returns an integer input
	private static int acceptInput(Scanner keyboard)
	{
		int input = 0;
		boolean notDone = true;
		
		System.out.println("Please enter your option");
		while (notDone){
			try {
				input = keyboard.nextInt();
				if (input < 0 || input > 999){
					throw new IllegalArgumentException("Please enter a number between 0 and 999!");
				}//end if
				notDone = false;
				System.out.println("Your input is: " + input);
			}//end try
			catch (Exception e){
				System.out.println("Please enter an integer in the number format. No letters please.");
				keyboard.next();
			}
		}//end while
		return input;
		
	}//End acceptInput(Scanner keyboard) method

	public static void main (String[] args)
	{
		//create the ints
		UnboundedInt intOne = new UnboundedInt();
		UnboundedInt intTwo = new UnboundedInt();
		int choice = -1;
		int inputElement;
		int inputIndex = -1;
		
		//create scanner object
		Scanner keyboard = new Scanner (System.in);
		
		//ask user
		System.out.println("Hello this is a program that allows you to work with large numbers.");
		
		//ask for and create integers
		
		//display choices
		System.out.println("1. Display both numbers");
		System.out.println("2. Input two new numbers");
		System.out.println("3. Check if numbers are equal");
		System.out.println("4. Report the sum of the two numbers");
		System.out.println("5. Report the multiplication of the two numbers");
		System.out.println("6. Create and output clone of the numbers");
		System.out.println("7. Quit");
		
		//start switch loop
		while (choice != 7){
			choice = acceptInput(keyboard);
			
			if (choice > 7 || choice < 1){
				System.out.println("Please input a number between 1 and 7!");
			}
			
			switch(choice)
			{
			case 1:
				System.out.println("Number A is: " + intOne.toString());
				System.out.println("Number B is: " + intTwo.toString());
				break;
			case 2:
				break;            
			case 3:
				break;            
			case 4:
				break;            
			case 5:
				break;            
			case 6:
				break;            
			case 7:
				System.out.println("Quitting...");
				break;            
			}//end witch
		}//end while
		
	}//End main() method
	
}//End LargeNumberTest class
