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
	//This method displays the menu, prompts the user for an integer input, and then returns the user's input
	private static int acceptInput(Scanner keyboard)
	{
		//Instance Variables
		int input = 0;
		boolean notDone = true;
		
		//Display Menu
		System.out.println("\n    Menu:");
		System.out.println("    1. Display both numbers");
		System.out.println("    2. Input two new numbers");
		System.out.println("    3. Check if the two numbers are equal");
		System.out.println("    4. Report the sum of the two numbers");
		System.out.println("    5. Report the product of the two numbers");
		System.out.println("    6. Create a clone of a number");
		System.out.println("    7. Quit");
		System.out.print("\nPlease enter your selection: ");
		
		//Accept User Input
		while (notDone){
			try {
				input = keyboard.nextInt();
				if (input < 0 || input > 999){
					System.out.print("\nError! You must enter a number between 0 and 999.\n\n");
				}
				notDone = false;
				System.out.println();
			}//end try
			catch (Exception e){
				System.out.print("\nError! You must enter an integer in the number format.\n\nPlease enter your selection: ");
				keyboard.next();
			}
		}//end while
		
		//Return the user's integer input to be used
		return input;
		
	}//End acceptInput(Scanner keyboard) method
	
	
	public static void main (String[] args)
	{
		//Instance Variables
		int choice = -1;
		int inputElement = 0;
		boolean quit = false;
		
		//Create the UnboundedIntegers to be used
		UnboundedInt intOne = new UnboundedInt();
		UnboundedInt intTwo = new UnboundedInt();
		
		//Create the Scanner object
		Scanner keyboard = new Scanner (System.in);
		
		//Greet User
		System.out.println("~~~ Welcome to the Unbounded Integer Calculator ~~~\n~~~ Please select an option from the menu below ~~~");
		
		//Begin Program Loop
		while (choice != 7){
			 
			//User's Input
			choice = acceptInput(keyboard);
			
			//Verify legal input
			if (choice > 7 || choice < 1){
				System.out.print("Error! You must input an option between 1 and 7.\n\n");
			}
			
			//Input Operations
			switch(choice){
			
			case 1: //Display both numbers
				try {
					System.out.println("Number A is: " + intOne.toString());
					System.out.println("Number B is: " + intTwo.toString() + "\n");
				}
				catch (IllegalStateException e){
					System.out.println("Error! No numbers have been created yet.\n");
				}
				break;
			
			case 2: //Input two new numbers
				System.out.println("You must create two positive integer numbers of arbitrary length.\n"
								 + "Each number should be entered from the highest multiple of ten to the ones place, "
								 + "in three digit elements at a time.\n"
								 + "LEADING ZEROS SHOULD NOT BE INCLUDED IN ANY ELEMENT!\n"
								 + "Enter any negative number to stop entering digits.\n");
				try{
					System.out.println("Please enter the digits of Number A below:\n");
					intOne = new UnboundedInt();
					while (!quit){
						System.out.print("Next Digits: ");
						inputElement = keyboard.nextInt();
						if (inputElement < 0){
							quit = true;
						}
						else{
							intOne.addFront(inputElement);
						}
					}//end while
					quit = false;
					System.out.println("\nPlease enter the digits of Number B below:\n");
					intTwo = new UnboundedInt();
					while (!quit){
						System.out.print("Next Digits: ");
						inputElement = keyboard.nextInt();
						if (inputElement < 0){
							quit = true;
						}
						else{
							intTwo.addFront(inputElement);
						}
					}//end while
					quit = false;
				}//end try
				catch (IllegalStateException e){
					System.out.print("\nError! You cannot enter more than three digits.\n\n");
				}
				System.out.println("\nNumbers A and B have been successfully input!\n");
				break;
			
			case 3: //Check if the two numbers are equal
				if(intOne.equals(intTwo)){
					System.out.println("Numbers A and B are equal.\n");
				}
				else {
					System.out.println("Numbers A and B are not equal.\n");
				}
				break;            
			
			case 4: //Report the sum of the two numbers
					System.out.println("Sum of Number A and B: " + intOne.add(intTwo) + "\n");
				break;            
			
			case 5: //Report the product of the two numbers
					System.out.println("Product of Number A and B: " + intOne.multiply(intTwo) + "\n");
				break;            
			
			case 6: //Create a clone of a number
					System.out.println("To clone Number A and replace Number B with it, enter 1.");
					System.out.println("To clone Number B and replace Number A with it, enter 2.");
					System.out.println("Enter 0 to leave this option without making a clone.");
					System.out.print("\nEnter Selection: ");
					inputElement = keyboard.nextInt();
					if (inputElement == 1){
						intTwo = (UnboundedInt) intOne.clone();
						System.out.println("\nNumber A has been successfully cloned!");
					}
					else if (inputElement == 2) {
						intOne = (UnboundedInt) intTwo.clone();
						System.out.println("\nNumber B has been successfully cloned!");
					}
					else {
						System.out.println();
					}
				break;            
			
			case 7: //Quit
				System.out.println("\nClosing the Unbounded Integer Calculator...\nCalculator Closed. Have a nice day!");
				break;            
			}//end switch
		}//end while
		
	}//End main() method
	
}//End LargeNumberTest class
