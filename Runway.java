// File: Runway.java

// Project #3: Chapter 7, project 9 - Airport Runway Simulation
// Authors: Kevin Soule, Rafael Ferrer, and Carmen Chiu
// Due Date: Monday 4/11/16

/*************************************************************************************************************************
* The Runway class is used in conjunction with the Plane class and the RunwaySimulation class to simulate planes taking off 
* and landing on a runway. Every Runway is assigned a current flight operation, as well as the times required for the
* Runway to process a landing or take-off.
*
* @note
*   (1) A Runway can either be set to idle, or the take-off or landing flight operations.
*   <p>
*   (2) The runway usually receives its current flight operation from the Plane class, otherwise it is idle.
*   <p>
*   (3) The simulation time a runway takes to process a flight operation depends on the time assigned to that operation.
*
* @version
*   April 10, 2016
*************************************************************************************************************************/

class Runway
{
	// Invariant of the Runway class:
	//   1. The timeForLanding variable is the number of minutes it takes for a Plane to land.
	//   2. The timeForTakeoff variable is the number of minutes it takes for a plane to take-off.
	//   3. The runwayTimeLeft variable is the number of minutes left in the current flight operation the runway is processing.
	//   4. The operation variable is the character that signifies the current flight operation the runway is processing.
	//      The available flight operations are 'I' for idle, 'L' for landing, or 'T' for take-off.
	
	
	/// Private Instance Variables ///
	
	private int timeForLanding;
	private int timeForTakeoff;
	private int runwayTimeLeft;
	private char operation;
	
	
	/// Constructor ///
	
	/**
	 * A constructor to create a new Runway object with the times required for processing take-offs and landings.
	 * @param time_takeoff
	 *   The number of minutes it takes for a Plane to take-off.
	 * @param time_landing
	 *   The number of minutes it takes for a Plane to land.
	 * @precondition
	 *   The arguments passed for time_takeoff and time_landing must be integers greater than zero.
	 * @postcondition
	 *   A new Runway object has been created and assigned the times required for processing take-offs and landings.
	 * @exception IllegalArgumentException
	 *   Will occur if the time_takeoff or time_landing arguments passed are not integers greater than zero.
	 **/
	public Runway(int time_takeoff, int time_landing)
	{
		//Verify that the arguments passed are positive integers
		if (time_takeoff < 1 || time_landing < 1){
			throw new IllegalArgumentException("Runway's arguments must be integers greater than zero!");
		}
		
		//Set the time for landing, time for take-off, and set the operation to idle.
		timeForLanding = time_landing;
		timeForTakeoff = time_takeoff;
		operation = 'I';
		
	}//End Runway(int time_takeoff, int time_landing) Constructor
	
	
	/// Accessor Methods ///
	
	/**
	 * An accessor method that tells whether the runway is idle or in use for a flight operation.
	 * @param none  
	 * @return
	 *   A true or false boolean value pertaining to whether the runway is idle or currently in use for a flight operation.
	 **/
	public boolean isBusy()
	{
		return operation != 'I' && runwayTimeLeft > 0;
		
	}//End isBusy() Method
	
	/**
	 * An accessor method that returns the time left in the current flight operation's use of the runway.
	 * @param none 
	 * @return
	 *  Returns an integer value representing the number of minutes left in the use of the runway for a flight operation.
	 * @note
	 *   The time left is decremented by the reduceRemainingTime() method.
	 **/
	public int getTimeLeft()
	{
		return runwayTimeLeft;
		
	}//End getTimeLeft() Method
	
	/**
	 * An accessor method that returns a character representing the flight operation the Runway is processing.
	 * @param none
	 * @return
	 *   Returns a character that represents the flight operation the Runway is currently being used for.
	 *   Returns 'T' for take-off, 'L' for landing or 'I' for idle.
	 **/
	public char kindOfOperation()
	{
		return operation;
		
	}//End kindOfOperation() Method
	
	/**
	 * An accessor method that returns the flight operation the Runway is currently processing as a String value (as opposed to a single character).
	 * @param none
	 * @return
	 *   Returns a String statement that states what flight operation the runway is currently being used for.
	 * @note
	 *   Will return "taking-off" if the plane is taking off, "landing" if the plane is landing, or "Idle" if the runway is not in use.
	 *   "Idle" is the Runway's default operation.
	 **/
	public String getOperationStatement()
	{
		String statement = "Idle";
		
		if (kindOfOperation()=='T'){
			statement = "taking-off";
		}
		else if (kindOfOperation()=='L'){
			statement = "landing";
		}
		
		return statement;
		
	}//End getOperationStatement() Method
	
	
	/// Modifier Methods ///
	
	/**
	 * A modifier method that assigns the Runway a flight operation.
	 * @param typeOfUse
	 *   A character that tells the Runway what flight operation it should start processing.
	 *   Enter 'T' for take-off, 'L' for landing, or 'I' for idle.
	 * @precondition
	 *   The argument passed for typeOfUse must be either 'L' for landing, 'T' for take-off, or 'I' for idle (in which case the Runway is not being used).
	 * @postcondition
	 *   The Runway is now set to the flight operation assigned it and can begin processing the simulation time required to complete the flight operation.
	 * @exception IllegalArgumentException
	 *   Will occur if the typeOfUse argument passed is not 'T', 'L', or 'I'.
	 * @note
	 *   Simulation time for processing flight operations varies depending on the operation.
	 **/
	public void startUsingRunway(char typeOfUse)
	{
		//if typeOfUse is 'L' - then the operation is landing and set the runway time left to the time it takes for landing
		if (typeOfUse == 'L'){
			operation = 'L';
			runwayTimeLeft = timeForLanding;
		}
		//if typeOfUse is 'T' - then the operation is take off and set the runway time left to the time it takes for take-off.
		else if (typeOfUse == 'T'){
			operation = 'T';
			runwayTimeLeft = timeForTakeoff;
		}
		//if typrOfUse is ‘I’ – then the runway is idle, set the runway time left to zero
		else if (typeOfUse == 'I'){
			operation = 'I';
			runwayTimeLeft = 0;
		}
		else {
			throw new IllegalArgumentException("Enter 'T' for take-off, 'L' for landing, or 'I' for idle. No other arguments will be accepted!");
		}
		
	}//End startUsingRunway(char typeOfUse) Method
	
	/**
	 * A modifier method that decrements the amount of time remaining to complete the current flight operation on the runway by one minute.
	 * @param none
	 * @postcondition
	 *   The simulation time left to complete the current flight operation has been decremented by one minute. 
	 **/
	public void reduceRemainingTime()
	{
		--runwayTimeLeft;
		
	}//End reduceRemainingTime() Method
		
}//End Runway Class
