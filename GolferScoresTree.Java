import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;

public class GolferScoresTree
{
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
	public static void main(String[] args) throws FileNotFoundException
	{
		File database;
		TreeBag<Golfer> golferDatabase;
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
		while (choice != 6){
			
			//Obtain user's selection
			choice = menuInput(keyboard);
			
			//Menu Switch Board
			switch(choice){
				case 1: //1.	Display all Golfer’s stats ordered by last name
					golferDatabase.display();
					break;
				case 2: //2.	Find and display one individual Golfer's stats
					
					break;
				case 3: //3.	Update an individual Golfer’s stats
					
					break;
				case 4: //4.	Remove a Golfer from the database
					
					break;
				case 5: //5.	Add a new Golfer to the database
					
					break;
				case 6: //6.	Quit and update the file (Golfer Database)
					
					break;
			}//end switch
		}//end while
		
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
		
		//Launch a JFileChooser window to select the file to be used as the database
		JFileChooser fileSelector = new JFileChooser();
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
	private static void FileSaver(TreeBag<Golfer> database)
	{
		//FileSaver Method
		
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
		String delim = " ,;:_\n\t\r\f"; 
		
		while(fileScanner.hasNextLine()){
			readPlayer = fileScanner.nextLine();
			tokenizer = new StringTokenizer(readPlayer, delim);
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
	private static void updateGolferStats()
	{
		//Update Golfer Stats Method
		
	}//End private static void updateGolferStats() Method
	
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
		int input = 0;
			
		//Display Menu
		System.out.println("\n\nAction Menu: ");
		System.out.println("  1. Display all Golfer’s stats ordered by last name");
		System.out.println("  2. Find and display one individual Golfer's stats");
		System.out.println("  3. Update an individual Golfer’s stats");
		System.out.println("  4. Remove a Golfer from the database");
		System.out.println("  5. Add a new Golfer to the database");
		System.out.println("  6. Quit and update the file (Golfer Database)");
		System.out.print("\nPlease enter your selection: ");
		
		//Accept user's input
		try {
			input = keyboard.nextInt();
			if (input < 1 || input > 6){
				System.out.println("\nError! You must enter an integer between 1 and 6.");
			}
		}//end try
		catch (Exception e){
			System.out.println("\nError! You must enter an integer. Decimal numbers, letters, and characters are not allowed.");
			keyboard.next();
		}
		
		//Return the user's integer input
		return input;
		
	}//End acceptInput(Scanner keyboard) method
	
	
	
	
}
