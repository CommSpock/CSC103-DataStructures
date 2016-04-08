// File: Runway.java

// Project #3: Chapter 7, project 9 - Airport Runway Simulation
// Authors: Kevin Soule, Rafael Ferrer, and Carmen Chiu
// Due Date: Monday 4/11/16

/*****************************************************************************************************************  
* An UnboundedInt is a positive whole number, base 10 integer, of arbitrary length. The UnboundedInt class allows 
* for the creation of unbounded integers and provides methods for performing basic arithmetic operations with
* UnboundedInt's (addition and multiplication).
*
* @note
* 	(1) An UnboundedInt can be increased indefinitely after it's created, but the maximum size is limited 
*   by the amount of free memory on the machine. The UnboundedInt(int... elements), addFront(int element), 
*   addEnd(int element), add(UnboundedInt addendInt), multiply(UnboundedInt multiplierInt), and clone() 
*   methods will result in an OutOfMemoryError when free memory is exhausted.
*   <p>
*   (2) The elements of an UnboundedInt are stored in a sequence in increasing order from the element containing 
*   the ones place to the element containing the highest multiple of ten.
*   <p>
*   (3) Each element of an UnboundedInt contains a positive one, two, or three digit integer that represents
*   three placeholders of UnboundedInt.
*
* @version
*   March 20, 2016
*****************************************************************************************************************/

class Runway
{
	// Invariant of the Runway class:
		//   1. The number of three integer elements in the UnboundedInt is in the instance variable nodeCount.
		//   2. The hundreds place in the UnboundedInt is in the instance variable head, which is also the first 
		//      element of the linked list holding the elements of the UnboundedInt.
		//   3. The highest power of ten in the UnboundedInt is in the instance variable tail, which is also the
		//      last element of the linked list holding the elements of the UnboundedInt.
		//   4. The instance variable current points to the currently selected element of the linked list. If there 
		//      is no currently selected element, the cursor will be null and at the end of the linked list.
		//   5. We care what is stored in every element that the head links to (directly or indirectly). We do not 
		//      care about any elements that the head has no link to (directly or indirectly).
	
	
	/// Private Instance Variables ///
	
	private int timeForLanding;
	private int timeForTakeoff;
	private int runwayTimeLeft;
	private char operation; //operation can be: 'I' – Idle, 'L' - Landing, or 'T' - Takeoff
	
	
	/// Constructor ///
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
	public Runway(int time_takeoff, int time_landing)
	{
		//set the time for landing, time for takeoff, and the operation to idle.
		timeForLanding = time_landing;
		timeForTakeoff = time_takeoff;
		operation = 'I';
		
	}//End Runway(int time_takeoff, int time_landing) Constructor
	
	
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
	public boolean isBusy()
	{
		return operation != 'I' && runwayTimeLeft > 0;
		
	}//End isBusy() Method
	
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
	public int getTimeLeft()
	{
		return runwayTimeLeft;
	}
	
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
	public char kindOfOperation()
	{
		//returns the type of operation the runway is currently being used for. 
		return operation;
		
	}//End kindOfOperation() Method
	
	/**
	 * Description
	 * this states what operation the runway is currently doing
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
	public String getOperationStatement()
	{
		String statement = "Idle";
		if (kindOfOperation()=='T'){
			statement = "taking off";
		}
		else if (kindOfOperation()=='L'){
			statement = "landing";
		}
		return statement;
	}//End getOperationStatement() Method
	
	
	/// Setter Methods ///
	
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
	public void startUsingRunway(char typeOfUse)
	{
		//if typeOfUse is 'L' - then the operation is landing and set the runway time left to the time it takes for landing
		if (typeOfUse == 'L'){
			operation = 'L';
			runwayTimeLeft = timeForLanding;
		}
		//if typeOfUse is 'T' - then the operation is take off and set the runway time left to the time it takes for takeoff.
		else if (typeOfUse == 'T'){
			operation = 'T';
			runwayTimeLeft = timeForTakeoff;
		}
		//if typrOfUse is ‘I’ – then the runway is idle, set the runway time left to zero
		else if (typeOfUse == 'I'){
			operation = 'I';
			runwayTimeLeft = 0;
		}
		
	}//End startUsingRunway(char typeOfUse) Method
	
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
	public void reduceRemainingTime()
	{
		--runwayTimeLeft;
		
	}//End reduceRemainingTime() Method
		
}//End Runway Class
