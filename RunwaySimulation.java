// File: RunwaySimulation.java

// Project #3: Chapter 7, project 9 - Airport Runway Simulation
// Authors: Carmen Chiu and Rafael Ferrer
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

import java.util.*;//need for user input and exceptions
import java.text.DecimalFormat;//need for formatting average
 
class RunwaySimulation
{
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
	public static void main(String[] args)
	{
		//Declare the time variables required for the simulatePlanes() method's arguments (all times in minutes)
		int simulationLength; //The total amount of time to run the simulation
		int takeOffTime; //The amount of runway time required for each Plane to take-off
		int landingTime; //The amount of runway time required for each Plane to land
		int crashThreshold; //The maximum amount of time a Plane can wait in the landing queue before crashing
		int avgDepartureTime; //The average number of minutes between new departing Planes requesting take-off
		int avgArrivalTime; //The average number of minutes between new arriving Planes requesting landing
		
		//Initialize the Scanner for user input
		Scanner keyboard = new Scanner(System.in);
		
		//Obtain variable values from the user and initialize the variables
		System.out.println("\n********************************************************");
		System.out.println("<<<<<<<<<<<<<<   RUNWAY SIMULATOR SETUP   >>>>>>>>>>>>>>");
		System.out.println("********************************************************");
		System.out.println("\nRunway Simulation is ready for starting parameters.");
		System.out.println("Please enter all times in minutes.\n");
		System.out.print("   Please enter the total time to run this simulation: ");
			simulationLength = acceptInput(keyboard);
		System.out.print("   Please enter the amount of runway time required for a plane to takeoff: ");
			takeOffTime = acceptInput(keyboard);
		System.out.print("   Please enter the amount of runway time required for a plane to land: ");
			landingTime = acceptInput(keyboard);
		System.out.print("   Please enter the maximum amount of time a plane can wait to begin landing before crashing: ");
			crashThreshold = acceptInput(keyboard);	
		System.out.print("   Please enter the average number of minutes between new departing planes waiting for takeoff: ");
			avgDepartureTime = acceptInput(keyboard);
		System.out.print("   Please enter the average number of minutes between new arriving planes waiting for landing: ");
			avgArrivalTime = acceptInput(keyboard);
		System.out.println("\n*********************************************************");
		System.out.println("<<<<<<<<<<<<<<   BEGIN RUNWAY SIMULATION   >>>>>>>>>>>>>>");
		System.out.println("*********************************************************");
		
		//Run the simulation with the values input by the user
		simulatePlanes(simulationLength, takeOffTime, landingTime, crashThreshold, avgDepartureTime, avgArrivalTime);
		
	}//End Main Method
	
	
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
	private static void simulatePlanes(int simulationLength, int takeOffTime, int landingTime, int crashThreshold, double avgDepartureTime, double avgArrivalTime)
	{
		//Initialize the Runway and Plane cursor
		Runway mainRunway = new Runway(takeOffTime, landingTime);
			mainRunway.startUsingRunway('I');
		Plane currentPlane = null;
		
		//Initialize the Landing and Take-Off queues, and the Crashed Planes stack
		LinkedQueue <Plane> takeOffQueue = new LinkedQueue<Plane>();
		LinkedQueue <Plane> landingQueue = new LinkedQueue<Plane>();
		LinkedStack <Plane> crashStack = new LinkedStack<Plane>();
		
		//Initialize the pseudorandom factors for Plane generation
		BooleanSource takeOffProb = new BooleanSource((1/avgDepartureTime));
		BooleanSource landingProb = new BooleanSource(1/avgArrivalTime);
		
		//Initialize trackers for simulation metrics
		int simulationMinute; //The current minute in the simulation
		int planesTakenOff = 0; //The total number of Planes to complete take-off during the simulation
		int planesLanded = 0; //The total number of Planes to complete landing during the simulation
		double timeWaitedTakeOff = 0; //The total number of minutes that Planes which complete take-off during the simulation waited in the take-off queue
		double timeWaitedLanding = 0; //The total number of minutes that Planes which complete landing during the simulation waited in the landing queue
		
		//Run through the simulation one minute at a time and output a minute-by-minute playback
		for (simulationMinute = 1; simulationMinute <= simulationLength; simulationMinute++){
			
			//Begin minute-by-minute playback output
			System.out.println ("\nSimulation Minute " + simulationMinute + ":");
			
			//Pseudorandomly generate new Planes and output new landing or take-off requests
			addPlanes(takeOffQueue, landingQueue, takeOffProb, landingProb, simulationMinute);
			
			//Check if the Runway is busy and process a Plane if it is not busy
			if (!mainRunway.isBusy()){
				//Get the next Plane
				currentPlane = getNextPlane(takeOffQueue, landingQueue, crashStack, crashThreshold, simulationMinute);
				//Begin a Runway operation if there is a next Plane
				if (currentPlane!=null){
					mainRunway.startUsingRunway(currentPlane.getOperation());
				}
				//If there is no next Plane, then the Runway is idle, output the Runway status
				else {
					System.out.println("   Runway Status: " + mainRunway.getOperationStatement());
				}
			}//end if
			
			//If a plane is currently performing an operation with the Runway, then output the Runway status
			if (currentPlane!=null){
				if (mainRunway.getTimeLeft() > 1){
					System.out.println("   Runway Status: Plane #" + currentPlane.getPlaneNo() + " is " + mainRunway.getOperationStatement());
					mainRunway.reduceRemainingTime();
				}
				//If a plane is currently finishing an operation with the Runway, then output the Runway status, update simulation metrics
				else {
					System.out.println("   Runway Status: Plane #" + currentPlane.getPlaneNo() + " is finishing " +  mainRunway.getOperationStatement());
					if (currentPlane.getOperation() == 'L'){
						planesLanded++;
						timeWaitedLanding += (simulationMinute-(landingTime-1)-currentPlane.getTime());
					}
					else if (currentPlane.getOperation() == 'T'){
						planesTakenOff++;
						timeWaitedTakeOff += (simulationMinute-(takeOffTime-1)-currentPlane.getTime());
					}
					//Prepare the Runway to be idle if there are no Planes to use it in the next simulation minute
					mainRunway.startUsingRunway('I');
				}//end else
			}//end if
		}//end of for
		
		//Run this again to sort through the landing list for any additional crashes during the simulation
		getNextPlane(takeOffQueue, landingQueue, crashStack, crashThreshold, simulationLength);
		
		//Report the simulation summary metrics to the user
		reportInfo(planesTakenOff, planesLanded, crashStack.size(), timeWaitedTakeOff, timeWaitedLanding);
		
		//List the crashed Planes to the user
		reportCrashed(crashStack, crashThreshold);
		
	}//End simulatePlanes(int simulationLength, int takeOffTime, int landingTime, int crashThreshold, double avgDepartureTime, double avgArrivalTime) Method
	
	
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
	private static void addPlanes(LinkedQueue<Plane> takeOffQueue, LinkedQueue<Plane> landingQueue, BooleanSource takeOffProb, BooleanSource landingProb, int minute)
	{
		//Pseudorandomly generate new Planes and output new landing or take-off requests
		if (takeOffProb.query()){
			Plane newPlane = new Plane(minute,'T');
			takeOffQueue.add(newPlane);
			System.out.println("   New Take-off Request: Plane #" + newPlane.getPlaneNo());
		}
		if (landingProb.query()){
			Plane newPlane = new Plane(minute,'L');
			landingQueue.add(newPlane);
			System.out.println("   New Landing Request: Plane #" + newPlane.getPlaneNo());
		}
	}//End addPlanes(LinkedQueue<Plane> takeOffQueue, LinkedQueue<Plane> landingQueue, BooleanSource takeOffProb, BooleanSource landingProb, int minute) Method
	
	
	/**
	 * Description
	 * clears any crashed planes and gives the next valid plane in queue
	 * since we assume only one plane appears per minute and this method would be run at least
	 * every few minutes that 
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
	private static Plane getNextPlane(LinkedQueue<Plane> takeOffQueue, LinkedQueue<Plane> landingQueue, LinkedStack<Plane> crashStack, int crashThreshold, int minute)
	{
		//Initialize a Plane cursor and crash test variable
		Plane nextPlane = null;
		boolean hasCrashed = true;
		
		//Check the landing queue for Planes waiting to land
		if (!landingQueue.isEmpty()){
			nextPlane = landingQueue.remove();
			//Test if the next Plane in the landing queue has crashed, repeat until a Plane can land or the landing queue is empty
			while (nextPlane!=null && hasCrashed && (minute - nextPlane.getTime()) >= crashThreshold){
				crashStack.push(nextPlane);
				//If the previous plane waiting to land crashed, then check the landing queue for the next Plane waiting to land
				if (!landingQueue.isEmpty()){
					nextPlane = landingQueue.remove();
				}
				//If the landing queue is now empty, then check the take-off queue for Planes waiting to take-off
				else if (!takeOffQueue.isEmpty()){
					nextPlane = takeOffQueue.remove();
					hasCrashed = false;
				}
				else {
					nextPlane = null;
				}
			}//end while
		}//end if
		//If the landing queue was empty, then check the take-off queue for Planes waiting to take-off
		else if (!takeOffQueue.isEmpty()){
			nextPlane = takeOffQueue.remove();
		}
		
		//Return the next Plane to use the Runway
		return nextPlane;
		
	}//End getNextPlane(LinkedQueue <Plane> takeOffQueue, LinkedQueue <Plane> landingQueue, LinkedStack<Plane> crashStack, int crashThreshold, int minute) Method
	
	
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
	private static void reportInfo(int planesTakenOff, int planesLanded, int planesCrashed, double timeWaitedTakeOff, double timeWaitedLanding)
	{
		//Initialize the formatting tool for double numbers
		DecimalFormat precision = new DecimalFormat("0.00");
		
		//Initialize the average landing time and take-off time metrics
		double aveTimeTakeOff = 0;
		double aveTimeLanding = 0;
		
		//Output Runway simulation metrics
		System.out.println("\n*******************************************************");
		System.out.println("<<<<<<<<<<<<   RUNWAY SIMULATION SUMMARY   >>>>>>>>>>>>");
		System.out.println("*******************************************************\n");
		System.out.println("   Number of departing planes that completed takeoff: " + planesTakenOff);
		System.out.println("   Number of arriving planes that completed landing: " + planesLanded);
		if (planesTakenOff > 0){
			aveTimeTakeOff = timeWaitedTakeOff/planesTakenOff;
			System.out.println("   Average wait time (in minutes) to begin taking-off: " + precision.format(aveTimeTakeOff));
		}
		else {
			System.out.println("   Average wait time (in minutes) to begin taking-off: No planes took-off during this simulation");
		}
		if (planesLanded > 0)
		{
			aveTimeLanding = timeWaitedLanding/planesLanded;
			System.out.println("   Average wait time (in minutes) to begin landing: " + precision.format(aveTimeLanding));
		}
		else {
			System.out.println("   Average wait time (in minutes) to begin landing: No planes landed during this simulation");
		}
		System.out.println("   Number of planes that crashed waiting to land: " + planesCrashed);
		
	}//End reportInfo(int planesTakenOff, int planesLanded, int planesCrashed, double timeWaitedTakeOff, double timeWaitedLanding) Method
	
	
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
	private static void reportCrashed(LinkedStack<Plane> crashedList, int crashThreshold)
	{  
		//Initialize a Plane cursor
		Plane currentCrash;
		
		//Output a list of crashed Planes
		while (!crashedList.isEmpty()){
			currentCrash = crashedList.pop();
			System.out.println("    > Plane #" + currentCrash.getPlaneNo() + " crashed at minute: " + (currentCrash.getTime()+crashThreshold));
		}
		
	}//End reportCrashed(LinkedStack<Plane> crashedList, int crashThreshold) Method
	
	
	/**
	 * Description
	 * this method returns an integer input
	 * need to check this method to make sure it works properly
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
	private static int acceptInput(Scanner keyboard)
	{
		//Initialize an input variable
		int input = 0;
		
		//Throw an exception if the user enters a non-integer value, a number less than 1, or a letter
		try {
			input = keyboard.nextInt();
			if (input < 1){
				throw new IllegalArgumentException("Simulation input values must be whole number integers greater than zero!");
			}	
		}//end try
		catch (Exception e){
				throw new IllegalArgumentException("Simulation input values must be whole number integers greater than zero!");
		}
		
		//Return the user's input
		return input;
		
	}//End acceptInput(Scanner keyboard) Method
	
}//End RunwaySimulation Class
