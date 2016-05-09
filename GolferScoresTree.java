// File: GolferScoresTree.java

// Project #4: Golfer Scores Database using a Binary Search Tree
// Authors: Rafael Ferrer and Carmen Chiu
// Due Date: Monday 5/9/16

/**********************************************************************************************************************  
* The GolferScoresTree class is used in conjunction with the Golfer class and the TreeBag class to simulate a database 
* that stores information about golfers. The information stored is the golfer's last name, number of rounds, handicap,
* and average score. Golfers can be added or removed from the database and their information can be updated or changed.
* The golfer "database" is retrieved from a text file and any changes made to it are saved as text within the same file.
* 
* @note
* 	(1) GolferScoresTree allows the user to select a text file to use as the golfer "database" once at the beginning 
* 		of the simulation and then a Golfer TreeBag is constructed with the information in the text file. The file 
* 		selection cannot be changed without restarting the program. 
*   <p>
*   (2) The text file selected must contain a single golfer's information on each line in the following order: 
*   	1. Golfer's Last Name (first letter must be upper case, all other letters must be lower case, no numbers or symbols)
*   	2. Number of Rounds (integer number between 0 and 999)
*   	3. Handicap (integer number between 0 and 20)
*   	4. Average Score (decimal number between 0 and 999)
*   	For each golfer, each piece of information may be separated with any of the following delimiters: " ,;:_\t"
*   	An empty text file may also be loaded to begin a new empty golfer "database".
*   	A text file in any format other than the exact formats specified above will NOT work with the GolferScoresTree class.
*   <p>
*   (3) To ensure a pure alphabetical ordering of golfers, rather than a lexicographical ordering based on each character's Unicode value, 
*   	each golfer's last name should be created in the following format: 
*   	The first letter should be upper case, while all other letters should be lower case, with no numbers or symbols.
*
* @version
*   May 8, 2016
**********************************************************************************************************************/


//Needed for Scanner, JFileChooser, PrintWriter, and FileNotFoundException
import java.io.*;
import java.util.*;
import javax.swing.*;

public class GolferScoresTree
{
	/// Main Method of GolferScoresTree ///
	
	/**
	 * This method runs the golfer database simulation
	 * @precondition
	 *  The text file selected must contain a single golfer's information on each line in the following order: 
	 *   1. Golfer's Last Name (first letter must be upper case, all other letters must be lower case, no numbers or symbols)
	 *   2. Number of Rounds (integer number between 0 and 999)
	 *   3. Handicap (integer number between 0 and 20)
	 *   4. Average Score (decimal number between 0 and 999)
	 *  For each golfer, each piece of information may be separated with any of the following delimiters: " ,;:_\t"
	 *  An empty text file may also be loaded to begin a new empty golfer "database".
	 *  A text file in any format other than the exact formats specified above will NOT work with the GolferScoresTree class.
	 * @throws FileNotFoundException 
	 * @note
	 *   To ensure a pure alphabetical ordering of golfers, rather than a lexicographical ordering based on each character's Unicode value, 
	 *   each golfer's last name should be created in the following format: 
	 *   The first letter should be upper case, while all other letters should be lower case, with no numbers or symbols.
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{
		//Instance Variables
		File database; //This is the text file where all the golfer information is stored
		TreeBag<Golfer> golferDatabase; //This is the TreeBag that is created with the information stored in database
		Golfer currentGolfer; //A cursor to select and use individual golfers from the golferDatabase
		String lastName; //The last name of a golfer, used to add a new golfer or search for a golfer in the golferDatabase
		int choice = -1; //The user's menu selection number
		Scanner keyboard = new Scanner (System.in); //Scanner used to obtain user input from the keyboard
		
		//Startup Message
		System.out.println("<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("<<    Golfer Database Management System    >>");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>\n");
		
		//Select the golfer "database" text file to use
		System.out.println("Please select the golfer database file you would like to use...");
		System.out.println("Note: You may select an empty text file to begin a new empty golfer database.\n");
		database = FileLoader();
		
		//Run the program with the selected database file, or close the program if no file was chosen
		if (database != null){
		
			//Read the database text file and construct a TreeBag<Golfer> object to assign to golferDatabase
			golferDatabase = createGolferDatabase(database);
			
			//Display the Main Menu for the user to manage the database
			while (choice != 6 && choice != 7){
				
				//Obtain user's Main Menu selection
				choice = mainMenuInput(keyboard);
				
				//Main Menu Switch Board
				switch(choice){
					case 1: //1. Display all golfer’s information ordered by last name
						if (golferDatabase.size() > 0){
							System.out.println("\nLast Name     \tNumber Of Rounds\tHandicap\tAverage Score");
							golferDatabase.display();
						}
						else {
							System.out.println("\nThis golfer database is currently empty.\nYou may add golfers by selecting option 5 from thr Main Menu.");
						}
						break;
					case 2: //2. Find and display one individual golfer's information
						System.out.print("\nPlease type the last name of the golfer you would like to view: ");
						lastName = keyboard.next();
						currentGolfer = golferDatabase.retrieve(new Golfer(lastName));
						if (currentGolfer != null){
							System.out.println("\nLast Name     \tNumber Of Rounds\tHandicap\tAverage Score");
							System.out.println(currentGolfer.toString());
						}
						else {
							System.out.println("\nNo golfer with the name " + lastName + " could be found in the database!");
						}
						break;
					case 3: //3. Update an individual golfer’s statistics
						System.out.print("\nPlease type the last name of the golfer you would like to update: ");
						lastName = keyboard.next();
						currentGolfer = golferDatabase.retrieve(new Golfer(lastName));
						if (currentGolfer != null){
							//Display the Update Golfer Stats Menu for the user to manage an individual golfer in the golferDatabase
							updateGolferStats(currentGolfer);
						}
						else {
							System.out.println("\nNo golfer with the name " + lastName + " could be found in the database!");
						}
						break;
					case 4: //4. Remove a golfer from the database
						System.out.print("\nPlease type the last name of the golfer you would like to remove: ");
						lastName = keyboard.next();
						currentGolfer = golferDatabase.retrieve(new Golfer(lastName));
						if (currentGolfer != null){
							golferDatabase.remove(currentGolfer);
							System.out.println("\n" + lastName + " has been removed from the database.");
						}
						else {
							System.out.println("\nNo golfer with the name " + lastName + " could be found in the database!");
						}
						break;
					case 5: //5. Add a new golfer to the database
						System.out.println("\nThe first letter of the golfer's last name should be upper case and all letters following it should be lower case.");
						System.out.println("Additionally, no spaces, numbers, or symbols should be used in the golfer's last name.");
						System.out.print("Please enter the last name of the golfer you would like to add: ");
						lastName = keyboard.next();
						currentGolfer = new Golfer(lastName);
						golferDatabase.add(currentGolfer);
						System.out.println("\n" + lastName + " has been added to the database.");
						break;
					case 6: //6. Quit and update the database file
						FileSaver(database, golferDatabase);
						System.out.println("\nGolfer database updated successfully.");
						break;
					case 7: //7. Quit without updating the database file
						System.out.println("\nGolfer database was not updated.");
						break;
				}//end switch
			}//end while
		}//end if
		
		//Exit Message
		System.out.println("\n\n<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("<<    Golfer Database Management System Closed   >>");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
		
	}//End Main Method
	
	
	
	/// Additional Methods Used by the Main Method ///
	
	/**
	 * This method allows the user to select a golfer "database" text file to be used for this program. 
	 * @precondition
	 *   The text file selected must contain a single golfer's information on each line in the following order: 
	 *   1. Golfer's Last Name (first letter must be upper case, all other letters must be lower case, no numbers or symbols)
	 *   2. Number of Rounds (integer number between 0 and 999)
	 *   3. Handicap (integer number between 0 and 20)
	 *   4. Average Score (decimal number between 0 and 999)
	 *   For each golfer, each piece of information may be separated with any of the following delimiters: " ,;:_\t"
	 *   An empty text file may also be loaded to begin a new empty golfer "database".
	 *   A text file in any format other than the exact formats specified above will NOT work with the GolferScoresTree class.
	 * @note
	 *   To ensure a pure alphabetical ordering of golfers, rather than a lexicographical ordering based on each character's Unicode value, 
	 *   each golfer's last name should be created in the following format: 
	 *   The first letter should be upper case, while all other letters should be lower case, with no numbers or symbols.
	 **/
	private static File FileLoader()
	{
		//Instance Variables
		File database; //This is the text file where all the golfer information is stored
		JFileChooser fileSelector; //A file selector UI
		
		//Launch a JFileChooser window to select the file to be used as the database
		fileSelector = new JFileChooser();
		int status = fileSelector.showOpenDialog(null);
		
		//Once a file has been selected, return that file
		if (status == JFileChooser.APPROVE_OPTION){
			database = fileSelector.getSelectedFile();
			System.out.println("You have selected the file located at " + database.toString());
			return database;
		}
		//If no file is selected, give the user a second chance to select a file or close the program
		else if (status == JFileChooser.CANCEL_OPTION){
			System.out.println("You must select a golfer database file to continue...");
			System.out.println("Or you may click 'Cancel' again to close the program.");
			//Launch the JFileChooser window to select the file to be used as the database
			status = fileSelector.showOpenDialog(null);
			//Once a file has been selected, return that file
			if (status == JFileChooser.APPROVE_OPTION){
				database = fileSelector.getSelectedFile();
				System.out.println("You have selected the file located at " + database.toString());
				return database;
			}
			//Close the program if no file is selected
			else {
				//The program will halt after returning null
				return null;
			}
		}//end else if
		
		return null;
		
	}//End FileLoader() Method
	
	/**
	 * This method reads the golfer "database" text file selected by the FileLoader() method and generates a golfer TreeBag from it to be used in this program.
	 * @param database
	 *   the golfer "database" text file selected by the FileLoader() method
	 * @precondition
	 *   The database text file selected must contain a single golfer's information on each line in the following order: 
	 *   1. Golfer's Last Name (first letter must be upper case, all other letters must be lower case, no numbers or symbols)
	 *   2. Number of Rounds (integer number between 0 and 999)
	 *   3. Handicap (integer number between 0 and 20)
	 *   4. Average Score (decimal number between 0 and 999)
	 *   For each golfer, each piece of information may be separated with any of the following delimiters: " ,;:_\t"
	 *   An empty text file may also be loaded to begin a new empty golfer "database".
	 *   A text file in any format other than the exact formats specified above will NOT work with the GolferScoresTree class.
	 * @throws FileNotFoundException
	 * @note
	 *   To ensure a pure alphabetical ordering of golfers, rather than a lexicographical ordering based on each character's Unicode value, 
	 *   each golfer's last name should be created in the following format: 
	 *   The first letter should be upper case, while all other letters should be lower case, with no numbers or symbols.
	 **/
	private static TreeBag<Golfer> createGolferDatabase(File database) throws FileNotFoundException
	{
		//Instance Variables
		TreeBag<Golfer> golferDatabase = new TreeBag<Golfer>(); //This is the TreeBag that is created with the information stored in the database text file
		String readPlayer; //A cursor for each line of text in the database text file
		Golfer player; //A cursor used to add golfers and their stats to the golferDatabase
		
		//Scanner Instantiation
		Scanner fileScanner = new Scanner(database); //Used to read the database text file
		fileScanner.useDelimiter(System.getProperty("line.separator")); //Allows us to read the database text file one line at a time
		
		//String Tokenizer Instantiation
		StringTokenizer tokenizer; 
		String delimeters = " ,;:_\t"; //For each golfer (new line), each piece of information may be separated with any of these delimiters
		
		//Read the database text file one line at a time, construct each golfer, add each golfer's stats, then add the golfer to golferDatabase
		while(fileScanner.hasNextLine()){
			readPlayer = fileScanner.nextLine();
			tokenizer = new StringTokenizer(readPlayer, delimeters);
			player = new Golfer(tokenizer.nextToken());
			player.setNumberOfRounds(Integer.parseInt(tokenizer.nextToken()));
			player.setHandicap(Integer.parseInt(tokenizer.nextToken()));
			player.setAverageScore(Double.parseDouble(tokenizer.nextToken()));
			golferDatabase.add(player);
		}//end while
		
		fileScanner.close();
		
		return golferDatabase;
		
	}//End createGolferDatabase(File database) Method
	
	/**
	 * A method which displays a new Update Golfer Stats Menu where an individual golfers stats may be updated.
	 * Stats that can be updated are number of rounds, handicap, and average score.
	 * The user may also enter a new integer score for a single round played. This will increment the golfers
	 * number of rounds by one and a new average score will be calculated for this golfer based on this new score.
	 * @param player
	 *   The golfer object whose information needs to be updated.
	 **/
	private static void updateGolferStats(Golfer player)
	{
		//Instance Variables
		int score; //A golfers score for a single round
		int rounds; //A golfers number of rounds
		int handicap; //A golfers set handicap
		double avgScore; //A golfers average score for all rounds
		int choice = -1; //The user's menu selection number
		Scanner keyboard = new Scanner (System.in); //Scanner used to obtain user input from the keyboard
		
		//Display the Update Golfer Stats Menu for the user to manage an individual golfer in the golferDatabase
		while (choice != 5){
			
			//Display the Update Golfer Stats Menu
			System.out.println("\nUpdate Golfer Stats Menu:");
			System.out.println("  1. Add a score for a new round (updates all stats)");
			System.out.println("  2. Update number of rounds");
			System.out.println("  3. Update handicap");
			System.out.println("  4. Update average score");
			System.out.println("  5. Exit and return to the Main Menu");
			System.out.print("\nPlease enter your selection: ");
			
			//Obtain the user's Update Golfer Stats Menu selection and catch illegal arguments
			try {
				choice = keyboard.nextInt();
				if (choice < 1 || choice > 5){
					System.out.println("\nError! You must enter a choice between 1 and 6.");
				}
			}//end try
			catch (Exception e){
				System.out.println("\nError! You must enter an integer. Decimal numbers, letters, and characters are not allowed.");
				keyboard.next();
			}
			
			//Update Golfer Stats Menu Switchboard
			try{
				switch (choice){
				case 1: //1. Add a score for a new round (updates number of rounds and average score for this golfer)
					System.out.print("\nEnter new score: ");
					score = intInput(keyboard);
					if (score < 0){
						System.out.println("The score cannot be a negative number!\n");
					}
					else if (score < 1){
						System.out.println("\nThe score must be greated than 0!\n");
					}
					else {
						player.addNewScore((double)score);
					}
					break;
				case 2: //2. Update this golfer's number of rounds
					System.out.print("\nEnter number of rounds: ");
					rounds = intInput(keyboard);
					if (rounds < 0){
						System.out.println("The number of rounds cannot be a negative number!\n");
					}
					else {
						player.setNumberOfRounds(rounds);
					}
					break;
				case 3: //3. Update this golfer's handicap
					System.out.print("\nEnter handicap: ");
					handicap = intInput(keyboard);
					if (handicap < 0 || handicap > 20){
						System.out.println("\nError! The handicap must be between 0-20!\n");
					}
					else {
						player.setHandicap(handicap);
					}
					break;
				case 4: //4. Update this golfer's average score
					System.out.print("\nEnter average score: ");
					avgScore = doubleInput(keyboard);
					if (avgScore < 0){
						System.out.println("The average score cannot be a negative number!\n");
					}
					else {
						player.setAverageScore(avgScore);
					}
					break;
				case 5: //5. 5. Exit and return to the Main Menu
					System.out.println("\n" + player.getLastName() + "'s stats updated successfully.");
					break;
				}//end switch
			}//end try
			catch (Exception e) {
				System.out.println("\n");
				keyboard.next();
			}
		}//end while
		
	}//End updateGolferStats(Golfer player) Method
	
	/**
	 * This method overwrites the golfer "database" text file selected in the FileLoader() method with any changes made while running this program, then saves the file.
	 * @param database
	 *   The golfer "database" text file selected in the FileLoader() method.
	 * @param golferDatabase
	 *   The golfer TreeBag created by the createGolferDatabase() method.
	 * @postcondition / return
	 *   The golfer "database" text file selected in the FileLoader() method has been overwritten with any changes made while running this program and saved.
	 * @throws FileNotFoundException
	 * @Note
	 *   This method uses the writeToDatabase() method to actually write over the previous golfer "database" text file. Then this method saves that file.
	 **/
	@SuppressWarnings("unchecked") //cursor = golferDatabase.getRoot();
	private static void FileSaver(File database, TreeBag<Golfer> golferDatabase) throws FileNotFoundException
	{		
		//Instance Variables
		PrintWriter fileWriter; //Writes a line of text to the text file database
		BTNode<Golfer> cursor; //Cursor used to initiate the writeToDatabase method, set to the root of golferDatabase
		
		//Write over the database text file with all changes made to golferDatabase and then save the file
		fileWriter = new PrintWriter(database);
		cursor = golferDatabase.getRoot();
		writeToDatabase(fileWriter, cursor);
		fileWriter.close();

	}//End FileSaver(File database, TreeBag<Golfer> golferDatabase) Method
	
	/**
	 * This method overwrites the golfer "database" text file selected in the FileSaver() method with any changes made while running this program.
	 * @param fileWriter
	 *   The PrintWriter object that contains the required file to be overwritten.
	 * @param cursor
	 *   The Golfer TreeBag root node selected in the FileSaver() method.
	 * @postcondition
	 *   The golfer "database" text file selected in the FileSaver() method has been overwritten with any changes made while running this program, but has not been saved.
	 * @note
	 *   This method does not save any changes made to the golfer "database" text file. The changes are saved by the FileSaver() method.
	 *   This method is only to be used by the FileSaver() method for its recursive pre-order TreeBag traversal algorithm.
	 **/
	private static void writeToDatabase(PrintWriter fileWriter, BTNode<Golfer> cursor)
	{
		//Instance Variables
		BTNode<Golfer> leftCursor, rightCursor; //Cursors used to traverse the entire golferDatabase TreeBag
		
		//Write over the database text file with all changes made to golferDatabase
		fileWriter.println(cursor.getData().toString());
		if (cursor.getLeft() != null){
			leftCursor = cursor.getLeft();
			writeToDatabase(fileWriter, leftCursor);
		}
		if (cursor.getRight() != null){
			rightCursor = cursor.getRight();
			writeToDatabase(fileWriter, rightCursor);;
		}
		
	}//End writeToDatabase(PrintWriter fileWriter, BTNode<Golfer> cursor) Method
	
	
	
	/// User Input Methods Used by Main and Additional Methods ///
	
	/**
	 * This method is used to run the Main Menu for this program and catch illegal input.
	 * @param keyboard
	 *   The scanner object being used to take keyboard input from the user.
	 **/
	private static int mainMenuInput(Scanner keyboard)
	{
		//Instance Variables
		int input = -1; //The user's menu selection number
		
		//Display the Main Menu
		System.out.println("\n\nMain Menu: ");
		System.out.println("  1. Display all golfer’s stats ordered by last name");
		System.out.println("  2. Find and display one individual golfer's stats");
		System.out.println("  3. Update an individual golfer’s stats");
		System.out.println("  4. Remove a golfer from the database");
		System.out.println("  5. Add a new golfer to the database");
		System.out.println("  6. Quit and update the database");
		System.out.println("  7. Quit without updating the database");
		System.out.print("\nPlease enter your selection: ");
		
		//Obtain the user's Main Menu selection and catch illegal arguments
		try {
			input = keyboard.nextInt();
			if (input < 1 || input > 7){
				System.out.println("\nError! You must enter a choice between 1 and 6.");
			}
		}//end try
		catch (Exception e){
			System.out.println("\nError! You must enter an integer. Decimal numbers, letters, and characters are not allowed.");
			keyboard.next();
		}
		
		return input;
		
	}//End mainMenuInput(Scanner keyboard) Method
	
	/**
	 * This method is used to accept integer input by the updateGolferStats() method when adding new scores, rounds, and handicaps.
	 * It is also used to catch illegal input.
	 * @param keyboard
	 *   The scanner object being used to take keyboard input from the user.
	 **/
	private static int intInput(Scanner keyboard)
	{
		//Instance Variables
		int input = 0; //The user's menu selection number
		boolean notDone = true; //while loop stopping case
		
		//Accept the user's input and catch illegal arguments
		while (notDone){
			try {
				input = keyboard.nextInt();
				if (input < 0 || input > 999){
					System.out.println("\nError! You must enter a number between 0 and 999.\n");
				}
				notDone = false;
			}//end try
			catch (Exception e){
				System.out.print("\nError! You must enter an integer number. Decimal numbers, letters, and characters are not allowed.\n");
				keyboard.nextInt();
			}
		}//end while
		
		//Return the user's integer input to be used
		return input;
		
	}//End intInput(Scanner keyboard) Method
	
	/**
	 * This method is used to accept double input by the updateGolferStats() method when adding new average scores.
	 * It is also used to catch illegal input.
	 * @param keyboard
	 *   The scanner object being used to take keyboard input from the user.
	 **/
	private static double doubleInput(Scanner keyboard)
	{
		//Instance Variables
		double input = 0; //The user's menu selection number
		boolean notDone = true; //while loop stopping case
		
		//Accept the user's input and catch illegal arguments
		while (notDone){
			try {
				input = keyboard.nextDouble();
				if (input < 0 || input > 999){
					System.out.println("\nError! You must enter a number between 0 and 999.\n");
				}
				notDone = false;
			}//end try
			catch (Exception e){
				System.out.print("\nError! You must enter a decimal number. Letters and characters are not allowed.\n");
				keyboard.nextDouble();
			}
		}//end while
		
		//Return the user's double input to be used
		return input;
		
	}//End doubleInput(Scanner keyboard) method
	
}//End GolferScoresTree Class
