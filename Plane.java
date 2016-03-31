
class Plane
{
	
	/// Private Instance Variables ///
	static private int planeCount = 0; //the plane number arrived to the queue should be in incrementing order
	private int time; //the time the plane arrived in queue
	private char flightOperation; //the kind of operation the plane is doing. 'L" is  for landing and 'T' is for taking off. 
	private int planeNo; // plane number
	
	
	/// Constructor ///
	public Plane(int currentTime, char landingOrTakeOff)
	{	
		time = currentTime;
		flightOperation =  landingOrTakeOff;
		planeNo = ++planeCount;
		
	}//end Plane(int aTime, char landingOrTakeOff) constructor
	
	
	/// Accessor Methods ///
	
	public int getTime()
	{
		return time;
		
	}//end getTime() method
	
	public char getOperation()
	{
		return flightOperation;
		
	}//end getOperation() method
	
	public int getPlaneNo()
	{
		return planeNo;
		
	}//end getPlaneNo() method
	
	
	/// Overridden Java Methods ///
	
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
	
	private static int getPlaneCount()
	{
		return planeCount;
		
	}//end getPlaneCount() method
	
	
}//End Plane Class
