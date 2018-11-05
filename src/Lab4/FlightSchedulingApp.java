package Lab4;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class takes source and destination cities as input from user, 
 * and finds the shortest route between source and destination in the graph.
 * 
 * last-updated: 2018-11-05
 * 
 * @author Jason
 *
 */
public class FlightSchedulingApp
{
	// Jason's file path: C:\Users\jason\Documents\NTU\Academic\1 SCSE\Year_2_Semester_1\CZ2001 Algorithms\3 Labs\Lab 4\FlightScanner\src\Lab4\World Cities.csv
	// Stephen's file path: C:\Users\steph\Documents\New folder\FlightScanner\src\Lab4\World Cities.csv
		
	public static final String CITIES_FILE_PATH =  "C:\\Users\\jason\\Documents\\NTU\\Academic\\1 SCSE\\"
			+ "Year_2_Semester_1\\CZ2001 Algorithms\\3 Labs\\Lab 4\\FlightScanner\\src\\Lab4\\World Cities.csv";
	
	public static void main(String[] args) throws Exception // #YOLO
	{
		Scanner sc = new Scanner(System.in);
		
		// get graph size from user
		int graphSize = 0;
		do
		{
			System.out.print("Enter graph size (min 3, max 5000): ");
			if(sc.hasNextInt())
			{
				graphSize = sc.nextInt();
			}
				
		} while(graphSize < 3 || graphSize > 5000);
		
		// instantiate graph generator based on graph size and input cities
		LinkedList<City> inputCities = IOHandler.readCitiesFile(CITIES_FILE_PATH, graphSize);
		GraphGenerator graphGenerator = new GraphGenerator(graphSize,inputCities);
		
		// get graph density from user
		double graphDensity = 0;
		do
		{
			System.out.print("Enter graph density (min 0.1, max 1.0): ");
			if(sc.hasNextDouble())
			{
				graphDensity = sc.nextDouble();
			}
				
		} while(graphDensity < 0.1 || graphDensity > 1);
		
		// generate graph based on graph size, graph density and input cities
		graphGenerator.setRequiredNumOfNonStopFlights(graphDensity);
		Graph graph = graphGenerator.generateGraph();
		System.out.println("Graph consists of: " + graph);
		
		// get source city from user
		City sourceCity = null;
		int sourceID = 0;
		do
		{
			System.out.print("Enter source city ID: ");
			if(sc.hasNextInt())
			{
				sourceID = sc.nextInt();
			}
			
			sourceCity = graph.getCityByID(sourceID);
				
		} while(sourceCity == null);
		
		// get destination city from user
		City destinationCity = null;
		int destinationID = 0;
		do
		{
			System.out.print("Enter destination city ID: ");
			if(sc.hasNextInt())
			{
				destinationID = sc.nextInt();
			}
			
			destinationCity = graph.getCityByID(destinationID);
				
		} while(destinationCity == null);
		
		// search for shortest route between source and destination using BFS
		Searcher searcher = new Searcher();
		Result dummyResult = new Result();
		LinkedList<City> shortestRoute= searcher.bfsShortestRoute(sourceCity, destinationCity, dummyResult);
		
		FlightScheduling.checkAndPrintPath(shortestRoute, dummyResult);
		
		sc.close();
	}

}
