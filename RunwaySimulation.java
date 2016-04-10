// File: RunwaySimulation.java

// Project #3: Chapter 7, project 9 - Airport Runway Simulation
// Authors: Carmen Chiu and Rafael Ferrer
// Due Date: Monday 4/11/16

/********************************************************************************************************************  
* The RunwaySimulation class is used in conjunction with the Runway class and the Plane class to simulate planes 
* taking off and landing on a runway. The RunwaySimulation class performs the airport runway simulation by creating 
* a number of pseudorandomly generated Planes and processing them through a single Runway.
*
* @note
*   (1) RunwaySimulation accepts simulation parameters from the user once at the beginning of the simulation and then
* 	runs the simulation based on those parameters.
*   <p>
*   (2) RunwaySimulation gives priority runway use to PLanes requesting landing in order to prevent Planes from
*   	running out of fuel and crashing while waiting to land. 
*   <p>
*   (3) RunwaySimulation Outputs a minute-by-minute playback of the simulation's status, as well as a summary of the 
*   	simulation's events.
*
* @version
*   April 10, 2016
********************************************************************************************************************/

import java.util.*;//need for user input and exceptions
import java.text.DecimalFormat;//need for formatting average
 
class RunwaySimulation
{
	/**
	 * The main method that obtains the simulation's starting parameters from the user and then initiates the runway simulation.
	 * @postcondition
	 *   The runway simulation has been initiated. 
	 * @note
	 *   Starting Parameters for a Runway Simulation:
	 *    <p>
	 *    All times must be entered in integer minutes greater than zero. 
	 *    <p>
	 *    1) The simulations length in minutes
	 *    <p>
	 *    2) The amount of runway time required for a plane to take-off
	 *    <p>
	 *    3) The amount of runway time required for a plane land
	 *    <p>
	 *    4) The amount of time a plane can wait to begin landing before crashing
	 *    <p>
	 *    5) The average number of minutes between new departing planes requesting take-off
	 *    <p>
	 *    6) The average number of minutes between new arriving planes requesting landing
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
		System.out.print("   Please enter the amount of runway time required for a plane to take-off: ");
			takeOffTime = acceptInput(keyboard);
		System.out.print("   Please enter the amount of runway time required for a plane to land: ");
			landingTime = acceptInput(keyboard);
		System.out.print("   Please enter the maximum amount of time a plane can wait to begin landing before crashing: ");
			crashThreshold = acceptInput(keyboard);	
		System.out.print("   Please enter the average number of minutes between new departing planes requesting take-off: ");
			avgDepartureTime = acceptInput(keyboard);
		System.out.print("   Please enter the average number of minutes between new arriving planes requesting landing: ");
			avgArrivalTime = acceptInput(keyboard);
		System.out.println("\n*********************************************************");
		System.out.println("<<<<<<<<<<<<<<   BEGIN RUNWAY SIMULATION   >>>>>>>>>>>>>>");
		System.out.println("*********************************************************");
		
		//Run the simulation with the values input by the user
		simulatePlanes(simulationLength, takeOffTime, landingTime, crashThreshold, avgDepartureTime, avgArrivalTime);
		
	}//End Main Method
	
	
	/**
	 * This method runs the runway simulation one minute at a time. Each minute a new Plane may be pseudorandomly generated, the runway's current use 
	 * is managed, queues of Planes waiting to use the Runway are managed, the simulation metrics are tracked, and a minute-by-minute report of the 
	 * simulation's status is output. A simulation summary is also output at the end.
	 * @param simulationLength
	 *   The total amount of time to run the simulation (in minutes).
	 * @param takeOffTime
	 *   The amount of runway time required for each Plane to take-off (in minutes).
	 * @param landingTime
	 *   The amount of runway time required for each Plane to land (in minutes).
	 * @param crashThreshold
	 *   The maximum amount of time a Plane can wait to land before crashing (in minutes).
	 * @param avgDepartureTime
	 *   The average number of minutes between new departing Planes requesting take-off.
	 * @param avgArrivalTime
	 *   The average number of minutes between new arriving Planes requesting landing.
	 * @precondition
	 *   The arguments passed for simulationLength, takeOffTime, landingTime, and crashThreshold must all be integer values greater than zero.
	 *   The arguments passed for avgDepartureTime and avgArrivalTime must both be double values greater than zero.
	 * @postcondition
	 *   The runway simulation has completed and a simulation summary and minute-by-minute playback have been output.
	 * @note
	 *   Priority runway use is given to Planes requesting landing in order to prevent Planes from running out of fuel and crashing while waiting to land.
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
		double timeWaitedTakeOff = 0; //The total number of minutes that Planes which completed take-off during the simulation waited in the take-off queue
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
	 * This method pseudorandomly generates planes that are either requesting take-off or landing.
	 * @param takeOffQueue
	 *   Queue of Planes waiting to use the Runway for take-off.
	 * @param landingQueue
	 *   Queue of Planes waiting to use the Runway for landing.
	 * @param takeOffProb
	 *   The probability that a new Plane will request take-off at any given minute.
	 * @param landingProb
	 *   The probability that a new Plane will request landing at any given minute.
	 * @param minute
	 *   The current minute of the simulation. 
	 * @precondition
	 *   The arguments passed for takeOffProb and landingProb must be between zero and one.
	 *   The argument passed for minute must be an integer greater than zero.
	 * @postcondition
	 *   A new Plane may have been added to the take-off or landing queues.
	 * @note
	 *   The probability of new planes requesting landing or take-off is calculated by the simulatePlanes() method, using the average times provided by the main method.
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
	 * This method manages the landing queue, take-off queue, and any Planes that may have crashed waiting to land. 
	 * It tracks and clears any crashed planes from the landing queue and returns the next Plane (uncrashed) in 
	 * either queue when it is their turn to use the Runway.
	 * @param takeOffQueue
	 *   Queue of Planes waiting to use the Runway for take-off.
	 * @param landingQueue
	 *   Queue of Planes waiting to use the Runway for landing.
	 * @param crashStack
	 *   Stack that will store any Planes that crashed waiting to land.
	 * @param crashThreshold
	 *   The maximum amount of time a Plane can wait to land before crashing (in minutes).
	 * @param minute
	 *   The current minute of the simulation.
	 * @precondition
	 *   The arguments passed for crashThreshold and minute must be integers greater than zero. 
	 * @return
	 *   A Plane that will use the Runway to either land or take-off.
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
	 * This method calculates and reports a summary of the simulation based on metrics recorded by the simulatePlanes() method.
	 * @param planesTakenOff
	 *   The total number of Planes that completed take-off during the simulation.
	 * @param planesLanded
	 *   The total number of Planes that completed landing during the simulation.
	 * @param planesCrashed
	 *   The total number of Planes that crashed waiting to land.
	 * @param timeWaitedTakeOff
	 *   The total number of minutes that Planes which completed take-off during the simulation waited in the take-off queue.
	 * @param timeWaitedLanding
	 *   The total number of minutes that Planes which completed landing during the simulation waited in the landing queue.
	 * @precondition
	 *   The arguments passed for planesTakenOff, planesLanded, and planesCrashed must be integers greater than zero.
	 *   The arguments passed for timeWaitedTakeOff and timeWaitedLanding must be doubles greater than zero.
	 * @postcondition
	 *   A summary of the runway simulation has been output.
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
			System.out.println("   Average wait time (in minutes) to begin taking-off: No planes completed take-off during this simulation");
		}
		if (planesLanded > 0)
		{
			aveTimeLanding = timeWaitedLanding/planesLanded;
			System.out.println("   Average wait time (in minutes) to begin landing: " + precision.format(aveTimeLanding));
		}
		else {
			System.out.println("   Average wait time (in minutes) to begin landing: No planes completed landing during this simulation");
		}
		System.out.println("   Number of planes that crashed waiting to land: " + planesCrashed);
		
	}//End reportInfo(int planesTakenOff, int planesLanded, int planesCrashed, double timeWaitedTakeOff, double timeWaitedLanding) Method
	
	
	/**
	 * This method reports the details of each plane crash that occurred during the simulation.
	 * @param crashStack
	 *   Stack that stores any Planes that crashed waiting to land.
	 * @param crashThreshold
	 *   The maximum amount of time a Plane can wait to land before crashing (in minutes).
	 * @precondition
	 *   The argument passed for crashThreshold must be an integer greater than zero.
	 * @postcondition
	 *   A list of Planes that crashed have been output, along with the simulation minute of each crash.
	 * @note
	 *   The list of Planes that crashed will be output in order of most recent crash.
	 **/
	private static void reportCrashed(LinkedStack<Plane> crashStack, int crashThreshold)
	{  
		//Initialize a Plane cursor
		Plane currentCrash;
		
		//Output a list of crashed Planes
		while (!crashStack.isEmpty()){
			currentCrash = crashStack.pop();
			System.out.println("    > Plane #" + currentCrash.getPlaneNo() + " crashed at minute: " + (currentCrash.getTime()+crashThreshold));
		}
		
	}//End reportCrashed(LinkedStack<Plane> crashedList, int crashThreshold) Method
	
	
	/**
	 * This method accepts user input in integers and catches any illegal arguments.
	 * @param keyboard
	 *   The Scanner being used to read user input.
	 * @precondition
	 *   The user input being read by keyboard must be an integer greater than zero.
	 * @return
	 *   The integer read by keyboard and input by the user.
	 * @exception IllegalArgumentException
	 *   Will occur if the user input being read is not an integer greater than zero.
	 * @note
	 *   This method is used in the main() method to ensure that all simulation parameters entered by the user are legal.
	 **/
	private static int acceptInput(Scanner keyboard)
	{
		//Initialize an input variable
		int input = 0;
		
		//Throw an exception if the user enters a non-integer value, a number less than 1, or a letter
		try {
			input = keyboard.nextInt();
			if (input < 1){
				throw new IllegalArgumentException("Simulation input values must be integers greater than zero!");
			}	
		}//end try
		catch (Exception e){
				throw new IllegalArgumentException("Simulation input values must be integers greater than zero!");
		}
		
		//Return the user's input
		return input;
		
	}//End acceptInput(Scanner keyboard) Method
	
}//End RunwaySimulation Class
