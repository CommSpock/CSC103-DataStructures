// File: Golfer.java

// Project #4: Golfer Scores Database using a Binary Search Tree
// Authors: Carmen Chiu and Rafael Ferrer
// Due Date: Monday 5/9/16

/******************************************************************************************
* This class is a homework assignment;
* An <CODE>TreeBag</CODE> is a collection of int numbers.
*
* <dl><dt><b>Limitations:</b> <dd>
*   Beyond <CODE>Integer.MAX_VALUE</CODE> elements, <CODE>countOccurrences</CODE>,
*   and <CODE>size</CODE> are wrong. 
*
* <dt><b>Note:</b><dd>
*   This file contains only blank implementations ("stubs")
*   because this is a Programming Project for my students.
*
* @version
*   April 23, 2016
******************************************************************************************/

public class Golfer implements Comparable<Golfer>
{
	// Invariant of the TreeBag class:
	//   1. 
	//   2. 
	//   3. 
	//   4. 
	
	/// Instance Variables ///
	private String lastName;
	private int numberOfRounds;
	private double averageScore;
	private int handicap;
	
	
	/// Constructors ///
	
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
	public Golfer(String inputName, int inputRounds, double inputAveScore, int inputHandicap)
	{
		lastName = inputName;
		numberOfRounds = inputRounds;
		averageScore = inputAveScore;
		handicap = inputHandicap;
		
	}//End Golfer(String inputName, int inputRounds, double inputAveScore, int inputHandicap) Constructor
	
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
	public Golfer(String name)
	{
		this(name,0,0,0);
		
	}//End Golfer(String name) Constructor
	
	
	/// Accessor Methods ///
	
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
	public String getLastName()
	{
		return lastName;
		
	}//End getLastName() Method
	
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
	public int getNumberOfRounds()
	{
		return numberOfRounds;
		
	}//End getNumberOfRounds() Method
	
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
	public double getAverageScore()
	{
		return averageScore;
		
	}//End getAverageScore() Method
	
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
	public int getHandicap()
	{
		return handicap;
		
	}//End getHandicap() Method
	
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
	public String toString()
	{
		//may have to import decimal formats for the score formatting
		return "Golfer: " + lastName + "\n        Number of Rounds: " + numberOfRounds + "\n        Average Score: " + averageScore + "\n        Handicap: " + handicap;
	}//End toString() Method
	
	
	/// Modifier Methods ///
	
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
	public void setLastName(String inputName)
	{
		lastName = inputName;
		
	}//End setLastName(String inputName) Method
	
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
	public void setNumberOfRounds(int inputRounds)
	{
		if (inputRounds > -1){
			numberOfRounds = inputRounds;
		}
		else {
			throw new IllegalArgumentException("The number of rounds has to be 0 or more!");
		}
		
	}//End setNumberOfRounds(int inputRounds) Method
	
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
	public void setAverageScore(double inputAveScore)
	{
		if (inputAveScore > -1){
			averageScore = inputAveScore;
		}
		else {
			throw new IllegalArgumentException("The score must be 0 or more!");
		}
		
	}//End setAverageScore(double inputAveScore)
	
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
	public void setHandicap(int inputHandicap)
	{
		if (inputHandicap >=0 && inputHandicap <= 20){
			handicap = inputHandicap;
		}
		else {
			throw new IllegalArgumentException("The handicap has to be between 0-20!");
		}
		
	}//End setHandicap(int inputHandicap) Method
	
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
	public void addNewScore(double inputScore)
	{
		if (inputScore >= 1){
			numberOfRounds++;
			averageScore = ((averageScore + inputScore)/2);
		}
		else {
			throw new IllegalArgumentException("The score must be more than 0!");
		}
		
	}//End addNewScore(double inputScore) Method
	
	
	/// Comparable Interface ///
	
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
	public int compareTo(Golfer inputGolfer)
	{
		int comparison;
		
		//Returns a negative integer if inputGolfer's last name precedes this Golfer's lastName alphabetically
		//Returns a positive integer if inputGolfer's last name follows this Golfer's lastName alphabetically
		//Returns zero if inputGolfer's last name is the same as this Golfer's lastName
		comparison = lastName.compareTo(inputGolfer.getLastName());
		
		return comparison;
		
	}//End compareTo(String inputName) Method
	
	
}//End Golfer Class

