// File: HashTesting.java

// Project #4: Analysis of Hashing versus Double Hashing
// Authors: Rafael Ferrer
// Due Date: Monday 5/22/16

/****************************************************************************************************
* The HashTesting program analyzes the efficiency of adding elements to an open-address hash table 
* versus adding elements to a double hash table. Both tables have a fixed capacity of 241 elements.
* The same elements with the same keys are added to each table in the same order. The elements are 
* read from a text file that contains a single name and integer key on each line. Both tables use 
* Java's hashCode method in their initial identical hash functions, with the double hash table using 
* a modified second hash function (still using hashCode) to perform double hashing. The analysis
* reports information about the number of collisions that occur when adding elements to each hash table.
* 
* @note
* 	(1) The file selected by the user must contain a single (one word) name and a single integer 
*       key on each line of the text file. The name and key may be separated by any of the following
*       delimiters: " "   ","   ";"   ":"   "_"   "\t"
*   <p>
*   (2) Both tables have a fixed capacity of 241 elements. 
*   <p>
*   (3) The tracking of collisions is handled by the Table and TableDoubleHash classes.
*
* @version
*   May 20, 2016
****************************************************************************************************/


import java.io.*; //For File class and FileNotFoundException
import java.util.*; //For Scanner and StringTokenizer classes
import javax.swing.*; // For JFileChooser class

public class HashTesting
{
	/// Main Method of HashTesting ///
	
	/**
	 * This method runs the HashTesting analysis
	 * @precondition
	 *   The file selected by the user must contain a single (one word) name and a single integer key on each line of the text file. 
	 *   The text file selected must contain 241 or fewer elements. 
	 *   The name and key for each element must be separated by one of the following delimiters: " "   ","   ";"   ":"   "_"   "\t" 
	 * @postcondition
	 *   An analysis of the efficiency of adding elements to an open-address hash table versus adding elements to a double hash table 
	 *   has been output to the user.
	 * @throws FileNotFoundException 
	 * @note
	 *   The tracking of collisions is handled by the Table and TableDoubleHash classes.
	 **/
	@SuppressWarnings("unchecked") //hashTable = (Table<Integer, String>) tables[0]; doubleHashTable = (TableDoubleHash<Integer, String>) tables[1];
	public static void main(String[] args) throws FileNotFoundException
	{
		//Instance Variables
		File tableFile; //This is the text file where all the names and keys stored
		Object[] tables; //This is an array that holds the open-address hash table and the double hash table
		Table<Integer, String> hashTable; //The open-address hash table
		TableDoubleHash<Integer, String> doubleHashTable; //The double hash table
		
		//Startup Message
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("<<    Open-Address Hash Table VS Double Hash Table Analysis    >>");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
		
		//Prompt the user to select the text file containing the names and keys to use
		System.out.println("Please select the text file containing the names and keys to be added to the hash tables...");
		tableFile = FileLoader();
		
		//Run the program with the selected text file, or close the program if no file was chosen
		if (tableFile != null){
			
			//Create the hash tables and then retrieve them
			tables = createHashTables(tableFile);
			hashTable = (Table<Integer, String>) tables[0];
			doubleHashTable = (TableDoubleHash<Integer, String>) tables[1];
			
			//Output an analysis of collisions for the open-address hash table
			System.out.println("\n\nOpen-Address Hash Table Analysis:\n");
			System.out.println("Number of elements that experienced a collision when added to the hash table: " + hashTable.getFirstCollisions());
			System.out.println("Percentage of elements that experienced a collision when added to the hash table: " + (hashTable.getFirstCollisions()/hashTable.getElementCount())*100 + "%");
			System.out.println("Average number of collisions experienced by each element added: " + hashTable.getAvgCollisions());
			System.out.println("Maximum number of collisions experienced by a single element: " + hashTable.getMaxCollisions());
			System.out.println("Total number of collisions that occurred: " + hashTable.getTotalCollisions());
			
			//Output an analysis of collisions for the open-address hash table
			System.out.println("\n\nDouble Hash Table Analysis:\n");
			System.out.println("Number of elements that experienced a collision when added to the hash table: " + doubleHashTable.getFirstCollisions());
			System.out.println("Percentage of elements that experienced a collision when added to the hash table: " + (doubleHashTable.getFirstCollisions()/doubleHashTable.getElementCount())*100 + "%");
			System.out.println("Average number of collisions experienced by each element added: " + doubleHashTable.getAvgCollisions());
			System.out.println("Maximum number of collisions experienced by a single element: " + doubleHashTable.getMaxCollisions());
			System.out.println("Total number of collisions that occurred: " + doubleHashTable.getTotalCollisions());
		}//end if
		
		//Exit Message
		System.out.println("\n\n<<<<< Analysis Complete >>>>>");
		
	}//End Main Method
	
	
	/// Additional Methods Used by the Main Method ///
	
	/**
	 * This method allows the user to select the text file containing the names and keys to be used. 
	 * @precondition
	 *   The file selected by the user must contain a single (one word) name and a single integer key on each line of the text file. 
	 *   The text file selected must contain 241 or fewer elements. 
	 *   The name and key for each element must be be separated by one of the following delimiters: " "   ","   ";"   ":"   "_"   "\t" 
	 * @return
	 *   Returns the user selected text file containing the names and keys to be used. 
	 **/
	private static File FileLoader()
	{
		//Instance Variables
		File tableFile; //This is the text file where all the names and keys are stored
		JFileChooser fileSelector; //A file selector UI
		
		//Launch a JFileChooser window to select the file to be used
		fileSelector = new JFileChooser();
		int status = fileSelector.showOpenDialog(null);
		
		//Once a file has been selected, return that file
		if (status == JFileChooser.APPROVE_OPTION){
			tableFile = fileSelector.getSelectedFile();
			System.out.println("You have selected the file located at " + tableFile.toString());
			return tableFile;
		}
		//If no file is selected, give the user a second chance to select a file or close the program
		else if (status == JFileChooser.CANCEL_OPTION){
			System.out.println("You must select a file to continue...");
			System.out.println("You may click 'Cancel' again to close the program.");
			//Launch the JFileChooser window to select the file to be used
			status = fileSelector.showOpenDialog(null);
			//Once a file has been selected, return that file
			if (status == JFileChooser.APPROVE_OPTION){
				tableFile = fileSelector.getSelectedFile();
				System.out.println("You have selected the file located at " + tableFile.toString());
				return tableFile;
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
	 * This method reads the text file selected by the FileLoader() method and generates an open-address hash table
	 * and double hash table with the elements in the text file. Elements are added to each table in the same order.
	 * Both of the hash tables are returned in an array with index [0] containing the open-address hash table and
	 * index [1] containing the double hash table.
	 * @param tableFile
	 *   The text file containing the names and keys to be used.
	 * @precondition
	 *   tableFile must contain a single (one word) name and a single integer key on each line of the text file. 
	 *   tableFile must contain 241 or fewer elements. 
	 *   The name and key for each element must be be separated by one of the following delimiters: " "   ","   ";"   ":"   "_"   "\t" 
	 * @return 
	 *   An array is returned with index [0] containing the open-address hash table and index [1] containing the double hash table.
	 * @throws FileNotFoundException
	 * @note
	 *   The tracking of collisions is handled by the Table and TableDoubleHash classes.
	 **/
	private static Object[] createHashTables(File tableFile) throws FileNotFoundException
	{
		//Instance Variables
		Object[] tables = new Object[2]; //This is an array that holds the open-address hash table and the double hash table
		Table<Integer, String> hashTable = new Table<Integer, String>(241); //The open-address hash table
		TableDoubleHash<Integer, String> doubleHashTable = new TableDoubleHash<Integer, String>(241); //The double hash table
		Integer key; //The key for each individual element, obtained from tableFile
		String name; //The name for each individual element, obtained from tableFile
		String readLine; //A cursor for each line of text in tableFile
		
		//Scanner Instantiation
		Scanner fileScanner = new Scanner(tableFile); //Used to read tableFile
		fileScanner.useDelimiter(System.getProperty("line.separator")); //Allows us to read tableFile one line at a time
		
		//String Tokenizer Instantiation
		StringTokenizer tokenizer; 
		String delimeters = " ,;:_\t"; //For each new line, a name and key may be separated with any of these delimiters
		
		//Read tableFile one line at a time, adding each element to both hash tables in the same order
		while (fileScanner.hasNextLine()){
			readLine = fileScanner.nextLine();
			tokenizer = new StringTokenizer(readLine, delimeters);
			if (tokenizer.hasMoreTokens()){
				name = tokenizer.nextToken();
				key = Integer.parseInt(tokenizer.nextToken());
				hashTable.put(key, name);
				doubleHashTable.put(key, name);
			}
		}//end while
		
		fileScanner.close();
		
		//Package and return the hash tables
		tables[0] = hashTable;
		tables[1] = doubleHashTable;
		return tables;
		
	}//End createHashTables(File tableFile) Method
	
}//End HashTesting Class
