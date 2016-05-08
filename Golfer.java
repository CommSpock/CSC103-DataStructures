// File: Golfer.java
 
// Project #4: Golfer Scores Database using a Binary Search Tree
// Authors: Carmen Chiu and Rafael Ferrer
// Due Date: Monday 5/9/16
 
/************************************************************************************************
* The Golfer class is used in conjunction with the GolferScoresTree, BTNode and TreeBag classes 
* to simulate a database of golfers. Each golfer has a last name, average score, handicap, and 
* number of rounds played.
*   
* @version
*   May 8, 2016
************************************************************************************************/


public class Golfer implements Comparable<Golfer>
{
	// Invariant of the Golfer class:
    //   1. The lastName is a string representing the last name of a golfer.
    //   2. The numberOfRounds is an integer representing how many rounds a golfer has played.
    //   3. The averageScore is a double representing the golfer's average score.
    //   4. The handicap is an integer representing the golfer's handicap.
	
	/// Instance Variables ///
	private String lastName;
	private int numberOfRounds;
	private double averageScore;
	private int handicap;
	
	
	/// Constructors ///
	
	/**
	 * A constructor to create a new golfer object with a last name, rounds played, handicap, and average score.
	 * @param inputName
	 *   The last name of this golfer.
	 * @param inputRounds
	 *   The number of rounds this golfer has played.
	 * @param inputHandicap
	 *   The average score of this golfer.
	 * @param inputAvgScore
	 *   The handicap for this golfer.
	 * @precondition
	 *   The argument passed for inputRounds is an integer value greater then zero and less than Int.MAX_VALUE.
	 *   The argument passed for inputHandicap is an integer value between 0 and 20.
	 *   The argument passed for inputAvgScore is a double value between 0 and Double.MAX_VALUE.
	 * @postcondition
	 *   A new golfer has been created with the attributes input.
	 * @exception IllegalArgumentException
	 *   Will occur if inputRounds is less than 0 or not an integer value.
	 *   Will occur if inputHandicap is not between 0 and 20 or not an integer value.
	 *   Will occur if inputAvgScore is less than 0 or not a double value.
	 * @note
	 *   The golfer's last name should be entered in the following format if the String's compareTo method will be used to sort golfers alphabetically: 
	 *   The first letter should be upper case, while all other letters should be lower case, with no numbers or symbols.
	 **/
	public Golfer(String inputName, int inputRounds, int inputHandicap , double inputAvgScore)
	{
		lastName = inputName;
		
		//Verify arguments are legal and set golfer attributes if so.
		if (inputRounds > -1){
			numberOfRounds = inputRounds;
		}
		else {
			throw new IllegalArgumentException("The number of rounds must be 0 or greater!");
		}
		if (inputHandicap > -1 && inputHandicap < 21){
			handicap = inputHandicap;
		}
		else {
			throw new IllegalArgumentException("The handicap must be between 0-20!");
		}
		if (inputAvgScore > -1){
			averageScore = inputAvgScore;
		}
		else {
			throw new IllegalArgumentException("The average score must be 0 or greater!");
		}
		
	}//End Golfer(String inputName, int inputRounds, double inputAvgScore, int inputHandicap) Constructor
	
	/**
	 * A constructor to create a new golfer object with a last name.
	 * @param inputName
	 *   The last name of this golfer.
	 * @postcondition
	 *   A new golfer has been created with the last name inputName. All other golfer attributes will be set to zero.
	 * @note
	 *   The golfer's last name should be entered in the following format if the String's compareTo method will be used to sort golfers alphabetically: 
	 *   The first letter should be upper case, while all other letters should be lower case, with no numbers or symbols.
	 **/
	public Golfer(String inputName)
	{
		this(inputName,0,0,0);
		
	}//End Golfer(String inputName) Constructor
	
	
	/// Accessor Methods ///
	
	/**
	 * An accessor method that returns the golfer's last name.
	 * @return
	 *   A string value containing the golfer's last name.
	 **/
	public String getLastName()
	{
		return lastName;
		
	}//End getLastName() Method
	
	/**
	 * An accessor method that returns the number of rounds a golfer has played.
	 * @return
	 *   An integer value signifying the number of rounds a golfer played. 
	 **/
	public int getNumberOfRounds()
	{
		return numberOfRounds;
		
	}//End getNumberOfRounds() Method
	
	/**
	 * An accessor method that returns the handicap.
	 * @return
	 *   An integer value representing the handicap of the golfer.
	 **/
	public int getHandicap()
	{
		return handicap;
		
	}//End getHandicap() Method
	
	/**
	 * An accessor method that returns the average score.
	 * @return
	 *   A double value signifying the average score of the golfer.
	 **/
	public double getAverageScore()
	{
		return averageScore;
		
	}//End getAverageScore() Method
	
	/**
	 * A method that returns the golfer's information in a string.
	 * @return
	 *   A string containing the golfer's last name, number of rounds, handicap, and average score. 
	 **/
	public String toString()
	{
		return lastName + "     \t" + numberOfRounds + "\t\t\t" + handicap + "\t\t" + averageScore;
	}//End toString() Method
	
	
	/// Modifier Methods ///
	
	/**
	 * A modifier method that changes the name of a golfer.
	 * @param inputName
	 *   The new last name of this golfer.
	 * @postcondition
	 *   This golfers last name has been changed to the inputName.
	 * @note
	 *   The golfer's last name should be entered in the following format if the String's compareTo method will be used to sort golfers alphabetically:
	 *   The first letter should be upper case, while all other letters should be lower case, with no numbers or symbols.
	 **/
	public void setLastName(String inputName)
	{
		lastName = inputName;
		
	}//End setLastName(String inputName) Method
	
	/**
	 * A modifier method that changes the number of rounds a golfer has played.
	 * @param inputRounds
	 *   An integer value that the number of rounds should be change to.
	 * @precondition
	 *   The argument passed for inputRounds is an integer value greater then zero and less than Int.MAX_VALUE.
	 * @postcondition
	 *   The golfer's number of rounds has been changed to inputRounds.
	 * @exception IllegalArgumentException
	 *   Will occur if inputRounds is less than 0 or not an integer value.
	 * @note
	 *   The number of rounds is automatically updated when the addNewScore(double inputScore) method is called.
	 **/
	public void setNumberOfRounds(int inputRounds)
	{
		if (inputRounds > -1){
			numberOfRounds = inputRounds;
		}
		else {
			throw new IllegalArgumentException("The number of rounds must be 0 or greater!");
		}
		
	}//End setNumberOfRounds(int inputRounds) Method
	
	/**
	 * A modifier method that changes the handicap for a golfer.
	 * @param inputHandicap
	 *   An integer value  that the handicap should be changed to.
	 * @precondition
	 *   The argument passed for inputHandicap is an integer value between 0 and 20.
	 * @postcondition
	 *   The golfer's handicap has been changed to inputHandicap.
	 * @exception IllegalArgumentException
	 *   Will occur if inputHandicap is not between 0 and 20 or not an integer value.
	 **/
	public void setHandicap(int inputHandicap)
	{
		if (inputHandicap > -1 && inputHandicap < 21){
			handicap = inputHandicap;
		}
		else {
			throw new IllegalArgumentException("The handicap must be between 0-20!");
		}
		
	}//End setHandicap(int inputHandicap) Method
	
	/**
	 * A modifier method that changes the average score of a golfer.
	 * @param inputAvgScore
	 *   A double value that the average score should be change to.
	 * @precondition
	 *   The argument passed for inputAvgScore is a double value between 0 and Double.MAX_VALUE.
	 * @postcondition
	 *   The golfer's average score has been changed to inputAvgScore.
	 * @exception IllegalArgumentException
	 *   Will occur if inputAvgScore is less than 0 or not a double value.
	 * @note
	 *   The average score is automatically updated when the addNewScore(double inputScore) method is called.
	 **/
	public void setAverageScore(double inputAvgScore)
	{
		if (inputAvgScore > -1){
			averageScore = inputAvgScore;
		}
		else {
			throw new IllegalArgumentException("The average score must be 0 or greater!");
		}
		
	}//End setAverageScore(double inputAvgScore)
	
	/**
	 * A modifier method that increments a golfer's number of rounds by one and calculates a new average score based on the inputScore.
	 * @param inputScore
	 *   An integer score for a single round played.
	 * @precondition
	 *   The argument passed for inputScore is an integer value greater than one and less than Int.MAX_VALUE.
	 * @postcondition
	 *   The golfer's number of rounds has been incremented by one and a new average score has been calculated for this golfer.
	 * @exception IllegalArgumentException
	 *   Will occur if inputScore is less than one or not an integer value.
	 * @note
	 *   This method accurately calculates the golfer's new average score with their previous average score and a single new score.
	 *   No record of previous individual scores for each round played is required to calculate the correct average score.
	 **/
	public void addNewScore(double inputScore)
	{			
		if (inputScore >= 1){
			//Increment this golfer's numberOfRounds by one and calculate their new averageScore
			numberOfRounds++;
			averageScore = (((((double) numberOfRounds) - 1)*averageScore)+inputScore)/((double) numberOfRounds);
		}
		else {
			throw new IllegalArgumentException("The new score must be greater than 0!");
		}
		
	}//End addNewScore(double inputScore) Method
	
	
	/// Comparable Interface ///
	
	/**
	 * A method that returns an integer comparison based on the lexicographical order of the golfers last name's.
	 * The comparison is based on the Unicode value of each character in a golfer's last name. 
	 * @param inputGolfer
	 *   The Golfer object to compare to other Golfer objects.
	 * @return
	 *   Returns a negative integer if inputGolfer's last name precedes this Golfer's lastName lexicographically.
	 *   Returns a positive integer if inputGolfer's last name follows this Golfer's lastName lexicographically.
	 *   Returns zero if inputGolfer's last name is the same as this Golfer's lastName.
	 * @note
	 *   To ensure a pure alphabetical ordering, rather than a lexicographical ordering based on each characters Unicode value, 
	 *   the golfer's last name should be entered in the following format: 
	 *   The first letter should be upper case, while all other letters should be lower case, with no numbers or symbols.
	 **/
	public int compareTo(Golfer inputGolfer)
	{
		int comparison;
		
		comparison = lastName.compareTo(inputGolfer.getLastName());
		
		return comparison;
		
	}//End compareTo(String inputName) Method
	
	
}//End Golfer Class

