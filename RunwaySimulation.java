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
		int totalMin;
		int takeOffTime;
		int landingTime;
		int takeOffProb;
		int landingProb;
		int crashThreshold;
		
		Scanner keyboard = new Scanner(System.in);
		
		//ask user then run method to simulate planes
		System.out.println("Hello, this is a program to simulate planes taking off and landing on a runway.");
		System.out.println("Please enter the number of minutes you would like to run this simulation for.");
		totalMin = acceptInput(keyboard);
		System.out.println("Please enter the number of minutes a plane will take to take off.");
		takeOffTime = acceptInput(keyboard);
		System.out.println("Please enter the number of minutes a plane will take to land.");
		landingTime = acceptInput(keyboard);
		System.out.println("Please enter the average number of minutes that a plane will arrive for taking off.");
		takeOffProb = acceptInput(keyboard);
		System.out.println("Please enter the average number of minutes that a plane will arrive for landing.");
		landingProb = acceptInput(keyboard);
		System.out.println("Please enter the number of minutes before a plane waiting to land will crash.");
		crashThreshold = acceptInput(keyboard);
		
		simulatePlanes(totalMin,takeOffTime,landingTime,takeOffProb,landingProb,crashThreshold);
		
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
	private static void simulatePlanes (int totalTime, int takeOffTime, int landingTime, double probTakeOff, double probLanding, int minBefCrash)
	{
		LinkedQueue <Plane> takeOffList = new LinkedQueue<Plane>();
		LinkedQueue <Plane> landingList = new LinkedQueue<Plane>();
		LinkedStack <Plane> crashedList = new LinkedStack<Plane>();
		
		Plane currPlane = null;
		
		BooleanSource planeProbT = new BooleanSource((1/probTakeOff));
		BooleanSource planeProbL = new BooleanSource(1/probLanding);
		Runway mainRunway = new Runway(takeOffTime, landingTime);
		mainRunway.startUsingRunway('I');
		
		int planesTakenOff = 0;
		int planesLanded = 0;
		
		double timeWaitedTakeOff = 0;
		double timeWaitedLanding = 0;
		
		for (int i = 1; i < totalTime+1; i++){
			System.out.println ("During minute " + i + ":");
			
			//calc probability of planes and add them
			addPlanes(landingList, takeOffList, planeProbT, planeProbL, i);
			
			//should planes taken off/landed be counted here or when they finish taking off?
			//same with total time
			//check if runway busy if not then process a plane
			if (!mainRunway.isBusy()){
				//get the next plane
				currPlane = getNextPlane(landingList, takeOffList, crashedList, minBefCrash, i);
				//start off an operation if the plane wasn't null
				//if there are no planes, the runway is idle
				if (currPlane!=null){
					mainRunway.startUsingRunway(currPlane.getOperation());
				}
				//idle case
				else {
					System.out.println("Runway: " + mainRunway.getOperationStatement());
				}
			}//end if
			
			//if a plane is currently being processed
			if (currPlane!=null){
				if (mainRunway.getTimeLeft() > 1){
					System.out.println("Plane #" + currPlane.getPlaneNo() + " is " + mainRunway.getOperationStatement());
					mainRunway.reduceRemainingTime();
				}
				//plane is finishing so prepare to set runway back to idle and say that its finishing
				else {
					System.out.println("Plane #" + currPlane.getPlaneNo() + " is finishing " +  mainRunway.getOperationStatement() + ".");
					if (currPlane.getOperation() == 'L'){
						planesLanded++;
						timeWaitedLanding += (i-currPlane.getTime());
					}
					else if (currPlane.getOperation() == 'T'){
						planesTakenOff++;
						timeWaitedTakeOff += (i-currPlane.getTime());
					}
					mainRunway.startUsingRunway('I');
				}
			}
		}//end of for
		
		//run this again to sort through the landing list for any other crashes
		getNextPlane(landingList, takeOffList, crashedList, minBefCrash, totalTime);
		
		//report the info
		reportInfo(planesTakenOff, planesLanded, timeWaitedTakeOff, timeWaitedLanding, crashedList.size());
		
		//report the crashed planes
		reportCrashed(crashedList, minBefCrash);
		
	}//End simulatePlanes (int totalTime, int takeOffTime, int landingTime, double probTakeOff, double probLanding, int minBefCrash) Method
	
	
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
	private static void addPlanes(LinkedQueue landingL, LinkedQueue takeoffL, BooleanSource planeProbT, BooleanSource planeProbL, int minute)
	{
		if (planeProbT.query()){
			Plane nextPlane = new Plane(minute,'T');
			takeoffL.add(nextPlane);
			System.out.println("Arrived for takeoff: Plane No." + nextPlane.getPlaneNo());
		}
		if (planeProbL.query()){
			Plane nextPlane = new Plane(minute,'L');
			landingL.add(nextPlane);
			System.out.println("Arrived for landing: Plane No." + nextPlane.getPlaneNo());
		}
	}//End addPlanes(LinkedQueue landingL, LinkedQueue takeoffL, BooleanSource planeProbT, BooleanSource planeProbL, int minute) Method
	
	
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
	private static Plane getNextPlane(LinkedQueue <Plane> landingList, LinkedQueue <Plane> takeOffList, LinkedStack<Plane> crashedList, int crashThreshold, int minute)
	{
		//first set the plane to the first plane in queue
		Plane validPlane = null;
		boolean isCrashable = true;
		
		if (!landingList.isEmpty()){
			validPlane = landingList.remove();
			
			//break out of loop if validPlane is from takeOffList
			while (validPlane!=null && isCrashable && minute - validPlane.getTime() >= crashThreshold){
				crashedList.push(validPlane);
				//move to next plane if there is no plane set to null
				if (!landingList.isEmpty()){
					validPlane = landingList.remove();
				}
				//if the list was empty after taking the first plane, then move to takeoffList
				else if (!takeOffList.isEmpty()){
					validPlane = takeOffList.remove();
					isCrashable = false;
				}
				else {
					validPlane = null;
				}
			}//end while
		}//end if
		else if (!takeOffList.isEmpty()){
			validPlane = takeOffList.remove();
		}
		
		return validPlane;
		
	}//End getNextPlane(LinkedQueue <Plane> landingList, LinkedQueue <Plane> takeOffList, LinkedStack<Plane> crashedList, int crashThreshold, int minute) Method
	
	
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
	private static void reportInfo(int takeOffPlane, int landedPlane, double takeOffTotal, double landingTotal, int planesCrashed)
	{
		DecimalFormat precision = new DecimalFormat("0.00");
		
		double aveTimeTakeOff = 0;
		double aveTimeLanding = 0;
		
		System.out.println("The number of planes landed is: " + landedPlane);
		System.out.println("The number of planes taken off is: " + takeOffPlane);
		
		if (takeOffPlane > 0){
			aveTimeTakeOff = takeOffTotal/takeOffPlane;
			System.out.println("The average time waited for takeoff is: " + precision.format(aveTimeTakeOff));
		}
		else {
			System.out.println("No planes ever took off so an accurate average cannot be calculated.");
		}
		if (landedPlane > 0)
		{
			aveTimeLanding = landingTotal/landedPlane;
			System.out.println("The average time waited for landing is: " + precision.format(aveTimeLanding));
		}
		else {
			System.out.println("No planes ever landed so an accurate average cannot be calculated.");
		}
		
		System.out.println("The number of planes crashed is: " + planesCrashed);
	}//End private static void reportInfo(int takeOffPlane, int landedPlane, double takeOffTotal, double landingTotal, int planesCrashed) Method
	
	
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
	private static void reportCrashed(LinkedStack <Plane> crashedList, int minBefCrash)
	{  
		Plane currCrash;
		
		while (!crashedList.isEmpty()){
			currCrash = crashedList.pop();
			System.out.print("Plane #" + currCrash.getPlaneNo() + " crashed at minute: ");
			System.out.println((currCrash.getTime()+minBefCrash));
		}
	}//End reportCrashed(LinkedStack <Plane> crashedList, int minBefCrash) Method
	
	
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
		int input = 0;
		boolean notDone = true;
		
		while (notDone){
			try {
				input = keyboard.nextInt();
				if (input < 1){
					throw new IllegalArgumentException("Simulation input values must be whole number integers greater than zero!");
				}
				notDone = false;
				System.out.println("Your input is: " + input);
			}//end try
			catch (Exception e){
				throw new IllegalArgumentException("Simulation input values must be whole number integers greater than zero!");
			}
		}//end while
		return input;
	}//End acceptInput(Scanner keyboard) Method
	
}//End RunwaySimulation Class
