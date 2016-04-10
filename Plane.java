// File: Plane.java

// Project #3: Chapter 7, project 9 - Airport Runway Simulation
// Authors: Kevin Soule, Rafael Ferrer, and Carmen Chiu
// Due Date: Monday 4/11/16

/*****************************************************************************************************************
* The Plane class is used in conjunction with the Runway class and the RunwaySimulation class to simulate planes 
* taking off and landing on a runway. Every Plane is assigned a unique plane number ID, a starting time, and a
* flight operation it will perform.
* 
* @note
*   (1) A Plane can either be set to take-off or landing as its flight operation.
*   <p>
*   (2) A Plane's number ID is dependent on the number of Planes that were created before it.
*   <p>
*   (3) A Plane's starting time is dependent on the simulation minute when it was created. 
*
* @version
*   April 10, 2016
*****************************************************************************************************************/

class Plane
{
	// Invariant of the Plane class:
	//   1. The planeCount is the number of planes that have been created so far.
	//   2. The time is the minute a plane arrived at the runway in the simulation.
	//   3. The flightOperation is the operation the plane will perform. This can either be landing or taking off.
	//   4. The planeNo is the Plane's unique ID number. This is based on the number of planes that have been created 
	//      so far, signified by the static planeCount variable.
	
	
	/// Private Instance Variables ///
	
	static private int planeCount = 0;
	private int time;
	private char flightOperation;
	private int planeNo;
	
	
	/// Constructor ///
	
	/**
	 * A constructor to create a new Plane object with a creation time, flight operation and unique plane number ID.
	 * @param currentTime
	 *   currentTime is the minute when the plane arrives in the queue.
	 * @param landingOrTakeOff
	 *   The character that represents the current flight operation the plane will perform.
	 *   A Plane can land or take-off. 'L' for landing, 'T' for take-off.
	 * @precondition
	 *   The argument passed for currentTime must be a positive integer. 
	 *   The argument passed for landingOrTakeOff may not be any characters other than 'L' or 'T'.
	 * @postcondition
	 *   A new Plane object has been created and assigned a flight operation.
	 * @exception IllegalArgumentException
	 *   Will occur if the currentTime argument passed is not an integer greater than zero.
	 *   Will occur if the landingOrTakeOff argument passed is not 'L' or 'T'.
	 * @note
	 *   The plane number ID is based on the static planeCount variable, so each Plane created will have a unique id.
	 **/
	public Plane(int currentTime, char landingOrTakeOff)
	{	
		//Verify that the currentTime argument passed is a positive integer
		if (currentTime < 1){
			throw new IllegalArgumentException("The argument passed for currentTime must be an integer greater than zero!");
		}
		
		//Verify that the landingOrTakeOff argument passed is legal
		if (landingOrTakeOff == 'L' || landingOrTakeOff == 'T'){
			time = currentTime;
			flightOperation =  landingOrTakeOff;
			planeNo = ++planeCount;
		}
		else {
			throw new IllegalArgumentException("Enter 'T' for take-off or 'L' for landing. No other arguments will be accepted for landingOrTakeOff!");
		}
		
	}//End Plane(int currentTime, char landingOrTakeOff) constructor
	
	
	/// Accessor Methods ///
	
	/**
	 * An accessor method that returns the time at which the Plane arrived at the Runway.
	 * @param none  
	 * @return
	 *    An integer value signifying the simulation minute when the Plane arrived at the Runway.
	 **/
	public int getTime()
	{
		return time;
		
	}//End getTime() method
	
	/**
	 * An accessor method that returns the type of flight operation that a Plane needs to use the Runway for.
	 * @param none
	 * @return
	 *   Returns a character that signifies the type of flight operation a Plane needs to use the Runway for. 
	 *   Returns 'T' for take-off or 'L' for landing.
	 **/
	public char getOperation()
	{
		return flightOperation;
		
	}//End getOperation() method
	
	/**
	 * An accessor method that returns the unique plane number ID of the invoked Plane.
	 * @param none
	 * @return
	 *   Returns an integer value that contains the unique plane number ID of the invoked Plane.
	 * @note
	 *   The plane number ID is based on the static planeCount variable, so each Plane created will have a unique id.
	 **/
	public int getPlaneNo()
	{
		return planeNo;
		
	}//End getPlaneNo() method
	
	
	/// Static Methods ///
	
	/**
	 * A static accessor method that returns the number of Planes that have been created so far.
	 * @param none
	 * @return
	 *   Returns an integer value signifying the number of Planes that have been created since beginning the simulation.  
	 **/
	private static int getPlaneCount()
	{
		return planeCount;
		
	}//End getPlaneCount() method
	
}//End Plane Class
