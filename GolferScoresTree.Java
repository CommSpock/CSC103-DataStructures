
import java.io.*;
import java.util.*;
import javax.swing.*;

public class GolferScoresTree
{
	/**
	 * Description
	 * @param
	 * @throws FileNotFoundException 
	 * @throws  
	 * @precondition
	 *   
	 * @postcondition / return
	 *   
	 * @exception
	 *   
	 * @note
	 *   
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{
		File database;
		TreeBag<Golfer> golferDatabase;
		Golfer currentGolfer;
		String lastName;
		int choice = -1;
		Scanner keyboard = new Scanner (System.in);
		
		//User Greeting and Golfer database file selection
		System.out.println("<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("<<    Golfer Database Management System    >>");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>\n");
		System.out.println("Please select the golfer database file you would like to use...");
		database = FileLoader();
		System.out.println("You have selected the file located at " + database.toString());
		
		//Parse the database file and compile a TreeBag<Golfer> object to assign to golferDatabase
		golferDatabase = createGolferDatabase(database);
		
		//Display the menu options for the user to manage the database
		while (choice != 6 && choice != 7){
			
			//Obtain user's selection
			choice = menuInput(keyboard);
			
			//Main Menu Switch Board
			switch(choice){
				case 1: //1. Display all golfer’s stats ordered by last name
					System.out.println("\nLast Name     \tNumber Of Rounds\tHandicap\tAverage Score");
					golferDatabase.display();
					break;
				case 2: //2. Find and display one individual golfer's stats
					System.out.print("\nPlease type the last name of the golfer you would like to view: ");
					lastName = keyboard.next();
					currentGolfer = getGolfer(golferDatabase, lastName);
					if (currentGolfer != null){
						System.out.println("\nLast Name     \tNumber Of Rounds\tHandicap\tAverage Score");
						System.out.println(currentGolfer.toString());
					}
					else {
						System.out.println("\nNo golfer with the name " + lastName + " could be found in the database!");
					}
					break;
				case 3: //3. Update an individual golfer’s stats
					System.out.print("\nPlease type the last name of the golfer you would like to update: ");
					lastName = keyboard.next();
					currentGolfer = getGolfer(golferDatabase, lastName);
					if (currentGolfer != null){
						updateGolferStats(currentGolfer);
					}
					else {
						System.out.println("\nNo golfer with the name " + lastName + " could be found in the database!");
					}
					break;
				case 4: //4. Remove a golfer from the database
					System.out.print("\nPlease type the last name of the golfer you would like to remove: ");
					lastName = keyboard.next();
					currentGolfer = getGolfer(golferDatabase, lastName);
					if (currentGolfer != null){
						golferDatabase.remove(currentGolfer);
						System.out.println("\n" + lastName + " has been removed from the database.");
					}
					else {
						System.out.println("\nNo golfer with the name " + lastName + " could be found in the database!");
					}
					break;
				case 5: //5. Add a new golfer to the database
					System.out.print("Please enter the last name of the golfer you would like to add: ");
					lastName = keyboard.next();
					currentGolfer = new Golfer(lastName);
					golferDatabase.add(currentGolfer);
					System.out.println("\n" + lastName + " has been added to the database.");
					break;
				case 6: //6. Quit and update the database
					FileSaver(database, golferDatabase);
					System.out.println("\nGolfer database updated successfully.");
					break;
				case 7: //7. Quit without updating the database
					System.out.println("\nGolfer database was not updated.");
					break;
			}//end switch
		}//end while
		
		//Exit Message
		System.out.println("\n\n<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("<<    Golfer Database Management System Closed   >>");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
		
	}//End Main Method
	
	
	/// Additional Methods Used by the Main Method ///
	
	/**
	 * Description
	 * @param
	 *   
	 * @precondition
	 *   
	 * @postcondition / return
	 *   
	 * @exception
	 *   
	 * @note
	 *   
	 **/
	private static File FileLoader()
	{
		File database;
		JFileChooser fileSelector;
		
		//Launch a JFileChooser window to select the file to be used as the database
		fileSelector = new JFileChooser();
		int status = fileSelector.showOpenDialog(null);
		
		//If a file has been selected return that file
		if (status == JFileChooser.APPROVE_OPTION){
			database = fileSelector.getSelectedFile();
			return database;
		}
		
		return null;
		
	}//End private static File FileLoader() Method
	
	/**
	 * Description
	 * @param
	 * @throws  
	 * @throws
	 *   
	 * @precondition
	 *   
	 * @postcondition / return
	 *   
	 * @exception
	 *   
	 * @note
	 *   
	 **/
	private static void FileSaver(File database, TreeBag<Golfer> golferDatabase)
	{		
		//Still needs a working implementation

	}//End private static void FileSaver(TreeBag<Golfer> database) Method
	
	/**
	 * Description
	 * @param
	 * @throws FileNotFoundException
	 *   
	 * @precondition
	 *   
	 * @postcondition / return
	 *   
	 * @exception
	 *   
	 * @note
	 *   
	 **/
	private static TreeBag<Golfer> createGolferDatabase(File database) throws FileNotFoundException
	{
		TreeBag<Golfer> golferDatabase = new TreeBag<Golfer>();
		Golfer player;
		String readPlayer;
		
		//Scanner Instantiation
		Scanner fileScanner = new Scanner(database);
		fileScanner.useDelimiter(System.getProperty("line.separator"));
		
		//String Tokenizer 
		StringTokenizer tokenizer; 
		String delimeters = " ,;:_\n\t\r\f"; 
		
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
		
	}//End private static TreeBag<Golfer> createGolferDatabase(File database) Method
	
	/**
	 * Description
	 * @param
	 *   
	 * @precondition
	 *   
	 * @postcondition / return
	 *   
	 * @exception
	 *   
	 * @note
	 *   
	 **/
	@SuppressWarnings("unchecked") //BTNode<Golfer> cursor = golferDatabase.getRoot();
	private static Golfer getGolfer(TreeBag<Golfer> golferDatabase, String lastName)
	{
		Golfer currentGolfer = null;
		
		BTNode<Golfer> cursor = golferDatabase.getRoot();
		boolean found = false;
		
		while (cursor != null && !found){
			if (lastName.compareTo(cursor.getData().getLastName()) < 0){
				cursor = cursor.getLeft();
			}
			else if (lastName.compareTo(cursor.getData().getLastName()) > 0){
				cursor = cursor.getRight();
			}
			else if (lastName.compareTo(cursor.getData().getLastName()) == 0){
				found = true;
				currentGolfer = (Golfer) cursor.getData();
			}
		}//end while
		
		return currentGolfer;
		
	}//End getGolfer(TreeBag<Golfer> golferDatabase, String lastName) Method
	
	/**
	 * Description
	 * @param
	 *   
	 * @precondition
	 *   
	 * @postcondition / return
	 *   
	 * @exception
	 *   
	 * @note
	 *   
	 **/
	private static void updateGolferStats(Golfer player)
	{
		int score;
		int rounds;
		int handicap;
		double avgScore;
		int choice = -1;
		Scanner keyboard = new Scanner (System.in);
		
		while (choice != 5){
			//Display stats update menu
			System.out.println("\nUpdate Golfer Stats Menu:");
			System.out.println("  1. Add a score for a new round (updates all stats)");
			System.out.println("  2. Update number of rounds");
			System.out.println("  3. Update handicap");
			System.out.println("  4. Update average score");
			System.out.println("  5. Exit and return to the Main Menu");
			System.out.print("\nPlease enter your selection: ");
			
			//Obtain user's selection
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
			
			//Update Golfer Stats
			try{
			switch (choice){
			case 1: //1. Add a score for a new round (updates all stats)
				System.out.print("\nEnter new score: ");
				score = intInput(keyboard);
				player.addNewScore((double)score);
				break;
			case 2: //2. Update number of rounds
				System.out.print("\nEnter number of rounds: ");
				rounds = intInput(keyboard);
				player.setNumberOfRounds(rounds);
				break;
			case 3: //3. Update handicap
				System.out.print("\nEnter handicap: ");
				handicap = intInput(keyboard);
				player.setHandicap(handicap);
				break;
			case 4: //4. Update average score
				System.out.print("\nEnter average score: ");
				avgScore = doubleInput(keyboard);
				player.setAverageScore(avgScore);
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
		
	}//End private static void updateGolferStats() Methods
	
	
	/// User Input Methods ///
	
	/**
	 * Description
	 * @param
	 *   
	 * @precondition
	 *   
	 * @postcondition / return
	 *   
	 * @exception
	 *   
	 * @note
	 *   
	 **/
	private static int menuInput(Scanner keyboard)
	{
		int input = -1;
		
		//Display Menu
		System.out.println("\n\nMain Menu: ");
		System.out.println("  1. Display all golfer’s stats ordered by last name");
		System.out.println("  2. Find and display one individual golfer's stats");
		System.out.println("  3. Update an individual golfer’s stats");
		System.out.println("  4. Remove a golfer from the database");
		System.out.println("  5. Add a new golfer to the database");
		System.out.println("  6. Quit and update the database");
		System.out.println("  7. Quit without updating the database");
		System.out.print("\nPlease enter your selection: ");
		
		//Accept user's input
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
		
		//Return the user's integer input
		return input;
		
	}//End acceptInput(Scanner keyboard) method
	
	/**
	 * Description
	 * @param
	 *   
	 * @precondition
	 *   
	 * @postcondition / return
	 *   
	 * @exception
	 *   
	 * @note
	 *   
	 **/
	private static int intInput(Scanner keyboard)
	{
		//Instance Variables
		int input = 0;
		boolean notDone = true;
		
		//Accept User Input
		while (notDone){
			try {
				input = keyboard.nextInt();
				if (input < 0 || input > 999){
					System.out.println("\nError! You must enter a number between 0 and 999.\n");
				}
				if (input < 0){
					System.out.print("Negative number detected. Please enter zero and hit return.");
				}
				notDone = false;
			}//end try
			catch (Exception e){
				System.out.print("\nError! You must enter an integer number.\n");
				keyboard.nextInt();
			}
		}//end while
		
		//Return the user's integer input to be used
		return input;
		
	}//End intInput(Scanner keyboard) Method
	
	/**
	 * Description
	 * @param
	 *   
	 * @precondition
	 *   
	 * @postcondition / return
	 *   
	 * @exception
	 *   
	 * @note
	 *   
	 **/
	private static double doubleInput(Scanner keyboard)
	{
		//Instance Variables
		double input = 0;
		boolean notDone = true;
		
		//Accept User Input
		while (notDone){
			try {
				input = keyboard.nextDouble();
				if (input < 1 || input > 999){
					System.out.println("\nError! You must enter a number between 1 and 999.\n");
				}
				if (input < 0){
					System.out.print("Negative number detected. Please enter zero and hit return.");
				}
				notDone = false;
			}//end try
			catch (Exception e){
				System.out.print("\nError! You must enter a decimal number.\n");
				keyboard.nextDouble();
			}
		}//end while
		
		//Return the user's integer input to be used
		return input;
		
	}//End doubleInput(Scanner keyboard) method
	
}//End GolferScoresTree Class
