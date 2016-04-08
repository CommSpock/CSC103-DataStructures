
import java.util.*; //Required for Scanner class

public class RunwaySim
{

	public static void main(String[] args)
	{
		/// Instance Variables ///
		
		//Plane and Runway Variables
		Runway runway51; //The only runway available for use in this simulation
		Plane currentPlane; //Cursor to keep track of each plane's instance variables
		Plane landingPlane; //Cursor to keep track of each plane's instance variables
		Plane takeoffPlane; //Cursor to keep track of each plane's instance variables
		
		//Time Tracking Variables
		int simulationLength; //The total amount of time (in minutes) to run the simulation
		int runningTime; //The current amount of time (in minutes) the simulation has been running
		
		//Queue and Stack Variables
		LinkedQueue<Plane> takeoffQueue; //Queue of planes waiting for takeoff
		LinkedQueue<Plane> landingQueue; //Queue of planes waiting for landing
		LinkedStack<Plane> crashStack;   //Stack of planes that have crashed
		
		//Time Requirement Variables
		int timeForTakeoff; //The amount of runway time (in minutes) required for each plane to takeoff
		int timeForLanding; //The amount of runway time (in minutes) required for each plane to land
		int maxLandingWait; //The maximum waiting time (in minutes) before a plane in landingQueue will crash
		
		//Plane Generation Variables
		double avgDepartureTime; //The average number of minutes between departing planes for takeoff
		double avgArrivalTime;   //The average number of minutes between arriving planes for landing
		BooleanSource departure; //Pseudorandomly generates a boolean decision to create a new departing plane, based on the probability a new plane would be ready for takeoff that minute
		BooleanSource arrival;   //Pseudorandomly generates a boolean decision to create a new arriving plane, based on the probability a new plane would be ready for landing that minute
		
		//Simulation Summary Variables
		double avgTakeoffWait; //The average number of minutes each plane waited to takeoff
		double avgLandingWait; //The average number of minutes each plane waited to land
		int takeoffWaitCount;  //The total number of departing planes that waited to takeoff
		int landingWaitCount;  //The total number of arriving planes that waited to land
		int totalTakeoffs; //The total number of successful takeoffs
		int totalLandings; //The total number of successful landings
		int totalCrashes; //The total number of planes that crashed waiting to land
		
		//Scanner for User Input
		Scanner scan = new Scanner(System.in);
		
		
		/// Simulation Startup ///
		
		//Obtain initialization variables from the user
		System.out.println("Starting up Runway Simulation Program... \n");
		System.out.println("********************************************************");
		System.out.println("<<<<<<<<<<<<<<   RUNWAY SIMULATOR SETUP   >>>>>>>>>>>>>>");
		System.out.println("********************************************************");
		System.out.println("\nRunway Simulation Ready for Starting Parameters.");
		System.out.println("Please enter all times in minutes.\n");
		System.out.print("   Please enter the total time to run this simulation: ");
			simulationLength = scan.nextInt();
		System.out.print("   Please enter the amount of runway time required for a plane to takeoff: ");
			timeForTakeoff = scan.nextInt();
		System.out.print("   Please enter the amount of runway time required for a plane to land: ");
			timeForLanding = scan.nextInt();
		System.out.print("   Please enter the maximum amount of time a plane can wait to land before crashing: ");
			maxLandingWait = scan.nextInt();
		System.out.print("   Please enter the average number of minutes between new departing planes waiting for takeoff: ");
			avgDepartureTime = scan.nextDouble();
			departure = new BooleanSource(1/avgDepartureTime);
		System.out.print("   Please enter the average number of minutes between new arriving planes waiting for landing: ");
			avgArrivalTime = scan.nextDouble();
			arrival = new BooleanSource(1/avgArrivalTime);
		
		//Initialize the runway, queues, stack, timer and summary trackers
		runway51 = new Runway(timeForTakeoff, timeForLanding);
		landingPlane = new Plane(0, 'T'); //Dummy initialization required to satisfy minute-by-minute output compilation, must subtract 2 from all plane numbers
		takeoffPlane = new Plane(0, 'T'); //Dummy initialization required to satisfy minute-by-minute output compilation, must subtract 2 from all plane numbers
		landingQueue = new LinkedQueue<Plane>();
		takeoffQueue = new LinkedQueue<Plane>();
		crashStack = new LinkedStack<Plane>();
		runningTime = 1;
		takeoffWaitCount = 0;
		landingWaitCount = 0;
		totalTakeoffs = 0;
		totalLandings = 0;
		totalCrashes = 0;
		avgTakeoffWait = 0;
		avgLandingWait = 0;
		
		//Close scanner, no longer used
		scan.close();
		
		
		/// Run the Simulation ///
		
		while (runningTime <= simulationLength){
			
			//Output for minute-by-minute playback
			System.out.println("\nSimulation Minute " + runningTime + ":");
			
			//Create a Plane to wait for landing if one arrives
			if (arrival.query()){
				currentPlane = new Plane(runningTime, 'L');
				landingQueue.add(currentPlane);
				landingWaitCount++;
				//Output for minute-by-minute playback
				System.out.println("   Requesting Landing: Plane #" + (currentPlane.getPlaneNo()-2));
			}
			
			//Create a Plane to wait for takeoff if one is ready to depart
			if (departure.query()){
				currentPlane = new Plane(runningTime, 'T');
				takeoffQueue.add(currentPlane);
				takeoffWaitCount++;
				//Output for minute-by-minute playback
				System.out.println("   Requesting Takeoff: Plane #" + (currentPlane.getPlaneNo()-2));
			}
			
			//Begin using runway for landing or takeoff if available, or wait if not available
			if (runway51.isBusy()){
				runway51.reduceRemainingTime();
			}
			else {
				if (!landingQueue.isEmpty()){ //Always land planes waiting first
					currentPlane = landingQueue.remove();
					try {
						while ((runningTime - currentPlane.getTime()) > maxLandingWait){
							System.out.println("   EMERGENCY NOTICE: Plane #" + (currentPlane.getPlaneNo()-2) + " crashed waiting to land!"); //Output for minute-by-minute playback
							crashStack.push(currentPlane);
							totalCrashes++;
							avgLandingWait += (runningTime - currentPlane.getTime());
							currentPlane = landingQueue.remove();
						}
						runway51.startUsingRunway('L');
						totalLandings++;
						avgLandingWait += (runningTime - currentPlane.getTime());
						landingPlane = currentPlane;
						runway51.reduceRemainingTime();
					}//end try
					catch (NoSuchElementException e){}
				}//end if
				else if (!takeoffQueue.isEmpty()){ //Planes may takeoff if none are waiting to land
					currentPlane = takeoffQueue.remove();
					runway51.startUsingRunway('T');
					totalTakeoffs++;
					avgTakeoffWait += (runningTime - currentPlane.getTime());
					takeoffPlane = currentPlane;
					runway51.reduceRemainingTime();
				}
				else { //Runway is not in use, set to idle
					runway51.startUsingRunway('I');
				}
			}//end else
			
			//Output for minute-by-minute playback
			System.out.print("   Runway Status: ");
				if (runway51.kindOfOperation() == 'L'){
					System.out.println("Plane #" + (landingPlane.getPlaneNo()-2) + " is landing");
				}
				else if (runway51.kindOfOperation() == 'T'){
					System.out.println("Plane #" + (takeoffPlane.getPlaneNo()-2) + " is taking off");
				}
				else {
					System.out.println("Idle");
				}
			
			//Increase running time of simulation for next loop
			runningTime++;
			
		}//end while
		
		
		/// Output Simulation Summary ///
		
		//Finish Computing Averages and Crashed Planes
		while (!takeoffQueue.isEmpty()){
			currentPlane = takeoffQueue.remove();
			avgTakeoffWait += (runningTime - currentPlane.getTime());
		}
		while (!landingQueue.isEmpty()){
			currentPlane = landingQueue.remove();
			if ((runningTime - currentPlane.getTime()) > maxLandingWait){
				crashStack.push(currentPlane);
				totalCrashes++;
				avgLandingWait += (runningTime - currentPlane.getTime());
			}
			else {
				avgLandingWait += (runningTime - currentPlane.getTime());
			}
		}//end while
		
		//Output Summary
		System.out.println("\n*******************************************************");
		System.out.println("<<<<<<<<<<<<   RUNWAY SIMULATION SUMMARY   >>>>>>>>>>>>");
		System.out.println("*******************************************************\n");
		System.out.println("   Number of departing planes that waited for takeoff: " + takeoffWaitCount);
		System.out.println("   Number of arriving planes that waited for landing: " + landingWaitCount);
		System.out.println("   Number of departing planes that completed takeoff: " + totalTakeoffs);
		System.out.println("   Number of arriving planes that completed landing: " + totalLandings);
		System.out.println("   Number of arriving planes that crashed waiting for landing: " + totalCrashes);
		System.out.println("   Average time (in minutes) waiting in takeoff queue: " + avgTakeoffWait/takeoffWaitCount);
		System.out.println("   Average time (in minutes) waiting in landing queue: " + avgLandingWait/landingWaitCount);
		System.out.println("\nShutting down Runway Simulation Program...");
		System.out.println("Thank you for using the Runway Simulation Program!");

	}//End Main Method
	
}//End RunwaySimulation Class
