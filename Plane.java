// File: Plane.java

// Project #3: Chapter 7, project 9 - Airport Runway Simulation
// Authors: Kevin Soule, Rafael Ferrer and Carmen Chiu
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

class Plane
{
	// Invariant of the Plane class:
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
	
	static private int planeCount = 0; //the plane number arrived to the queue should be in incrementing order
	private int time; //the time the plane arrived in queue
	private char flightOperation; //the kind of operation the plane is doing. 'L" is  for landing and 'T' is for taking off. 
	private int planeNo; // plane number
	
	
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
	public Plane(int currentTime, char landingOrTakeOff)
	{	
		time = currentTime;
		flightOperation =  landingOrTakeOff;
		planeNo = ++planeCount;
		
	}//end Plane(int aTime, char landingOrTakeOff) constructor
	
	
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
	public int getTime()
	{
		return time;
		
	}//end getTime() method
	
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
	public char getOperation()
	{
		return flightOperation;
		
	}//end getOperation() method
	
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
	public int getPlaneNo()
	{
		return planeNo;
		
	}//end getPlaneNo() method
	
	
	/// Overridden Java Methods ///
	
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
	public Plane clone()
	{
		//Create a new Plane that will be returned as the clone of the invoked Plane. 
		Plane answer;
		
		try{
			//Clone the instance variables of the invoked Plane and assign them to the answer Plane.
			answer = (Plane) super.clone( );
		}
		catch (CloneNotSupportedException e){
			// This exception should not occur. But if it does, it would probably indicate a programming error that made super.clone unavailable.
			// The most common error would be forgetting the "Implements Cloneable" clause at the start of this class.
			throw new RuntimeException ("This class does not implement Cloneable");
		}
		catch (OutOfMemoryError e){
			throw new OutOfMemoryError ("There is not enough memory available to clone this Plane!");
		}
		
		return answer;
		
	}//end Plane clone() method
	
	
	/// Static Methods ///
	
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
	private static int getPlaneCount()
	{
		return planeCount;
		
	}//end getPlaneCount() method
	
}//End Plane Class
