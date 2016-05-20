// File: HashTesting.java

// Project #4: Analysis of Hashing versus Double Hashing
// Authors: Rafael Ferrer
// Due Date: Monday 5/22/16

/****************************************************************************************************
* A Table is an open-address hash table with a fixed capacity. The purpose is to show students how 
* an open-address hash table is implemented. Programs should generally use java.util.Hashtable
* rather than this hash table.
*
* @version
*   May 12, 2016
****************************************************************************************************/


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;

public class HashTesting
{
	/// Main Method of HashTesting ///
	
	@SuppressWarnings("unchecked") //hashTable = (Table<Integer, String>) tables[0];
	public static void main(String[] args) throws FileNotFoundException
	{
		//Instance Variables
				File tableFile; //This is the text file where all the names and keys stored
				Object[] tables;
				Table<Integer, String> hashTable;
				TableDoubleHash<Integer, String> doubleHashTable;
				/*
				Integer key;
				String name;
				int choice = -1; //The user's menu selection number
				Scanner scanKB = new Scanner (System.in); //Scanner used to obtain user input from the keyboard
				*/
				
				//Startup Message
				System.out.println("<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>");
				System.out.println("<<    Hash Table Analysis    >>");
				System.out.println("<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>\n");
				
				//Select the golfer "database" text file to use
				System.out.println("Please select the hash table text file you would like to use...");
				tableFile = FileLoader();
				
				//Run the program with the selected database file, or close the program if no file was chosen
				if (tableFile != null){
					
					tables = createHashTables(tableFile);
					hashTable = (Table<Integer, String>) tables[0];
					doubleHashTable = (TableDoubleHash<Integer, String>) tables[1];
					
					//For Testing
					System.out.println("\n\nSINGLE HASH TABLE:\n\n");
					System.out.println("Average Collisions: " + hashTable.getAvgCollisions());
					System.out.println("Maximum Collisions: " + hashTable.getMaxCollisions());
					System.out.println("First Collisions: " + hashTable.getFirstCollisions());
					System.out.println("Total Collisions: " + hashTable.getTotalCollisions());
					//hashTable.printTable();
					System.out.println("\n\n\n\n\n\nDOUBLE HASH TABLE:\n\n");
					System.out.println("Average Collisions: " + doubleHashTable.getAvgCollisions());
					System.out.println("Maximum Collisions: " + doubleHashTable.getMaxCollisions());
					System.out.println("First Collisions: " + doubleHashTable.getFirstCollisions());
					System.out.println("Total Collisions: " + doubleHashTable.getTotalCollisions());
					//doubleHashTable.printTable();
					
				}//end if
		
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
	 * This method reads the golfer "database" text file selected by the FileLoader() method and generates a golfer TreeBag from it to be used in this program.
	 * @param tableFile
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
	private static Object[] createHashTables(File tableFile) throws FileNotFoundException
	{
		//Instance Variables
		Object[] tables = new Object[2];
		Table<Integer, String> hashTable = new Table<Integer, String>(241);
		TableDoubleHash<Integer, String> doubleHashTable = new TableDoubleHash<Integer, String>(241);
		Integer key;
		String name;
		String readLine; //A cursor for each line of text in the database text file
		
		//Scanner Instantiation
		Scanner fileScanner = new Scanner(tableFile); //Used to read the database text file
		fileScanner.useDelimiter(System.getProperty("line.separator")); //Allows us to read the database text file one line at a time
		
		//String Tokenizer Instantiation
		StringTokenizer tokenizer; 
		String delimeters = " ,;:_\t"; //For each new line, a name and key may be separated with any of these delimiters
		
		//Read the database text file one line at a time, construct each golfer, add each golfer's stats, then add the golfer to golferDatabase
		while(fileScanner.hasNextLine()){
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
		
		tables[0] = hashTable; //(Table<Integer, String>)
		tables[1] = doubleHashTable; //(TableDoubleHash<Integer, String>)
		return tables;
		
	}//End createHashTables(File tableFile) Method
	
	
}//End HashTesting Class
