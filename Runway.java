
class Runway
{
	
	/// Private Instance Variables ///
	private int timeForLanding;
	private int timeForTakeoff;
	private int runwayTimeLeft;
	private char operation; //operation can be: 'I' – Idle, 'L' - Landing, or 'T' - Takeoff
	
	
	/// Constructor ///
	public Runway(int time_takeoff, int time_landing)
	{
		//set the time for landing, time for takeoff, and the operation to idle.
		timeForLanding = time_landing;
		timeForTakeoff = time_takeoff;
		operation = 'I';
		
	}//end Runway(int time_takeoff, int time_landing) constructor
	
	
	/// Accessor Methods ///
	
	public boolean isBusy()
	{
		return operation != 'I' && runwayTimeLeft > 0;
		
	}//end isBusy() method
	
	public char kindOfOperation()
	{
		//returns the type of operation the runway is currently being used for. 
		return operation;
		
	}//end kindOfOperation() method
	
	
	/// Setter Methods ///

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
		
	}//end startUsingRunway(char typeOfUse) method
	
	public void reduceRemainingTime()
	{
		--runwayTimeLeft;
		
	}//end reduceRemainingTime() method

	
}//End Runway Class
