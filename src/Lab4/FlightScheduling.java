package Lab4;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Find a route between two cities with minimum number of stops,
 * for graphs of various sizes and densities using BFS and DFS,
 * and output search time.
 * 
 * last updated: 2018-11-04
 * 
 * @author Jason, Stephen
 *
 */
public class FlightScheduling
{
	// Jason's file path: C:\Users\jason\Documents\NTU\Academic\1 SCSE\Year_2_Semester_1\CZ2001 Algorithms\3 Labs\Lab 4\FlightScanner\src\Lab4\World Cities.csv
	// Stephen's file path: C:\Users\steph\Documents\New folder\FlightScanner\src\Lab4\World Cities.csv
	
	public static final String CITIES_FILE_PATH =  ""; // insert your file path for World Cities.csv here
	
//	public static final String SOURCE_CITY = "Kirovohrad";
//	public static final String DESTINATION_CITY = "Kiliya";
	
	public static final int MIN_GRAPH_SIZE = 100;
	public static final int MAX_GRAPH_SIZE = 1000;
	public static final int GRAPH_SIZE_INCREMENT = 100;
	
	public static final double MIN_GRAPH_DENSITY = 0.2;
	public static final double MAX_GRAPH_DENSITY = 1;
	public static final double GRAPH_DENSITY_INCREMENT = 0.2;
	
	public static final int NUM_OF_ITERATIONS = 50;
	/**
	 * main method that applies searching algorithm to flight-scheduling
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception // #YOLO
	{
		GraphGenerator graphGenerator;
		Searcher searcher = new Searcher();
		
		// These 2 Array Lists hold results of searhing using BFS and DFS
		ArrayList<Result> resultsBFS = new ArrayList<Result>();
		ArrayList<Result> resultsDFS = new ArrayList<Result>();
		
		LinkedList<City> inputCities = IOHandler.readCitiesFile(CITIES_FILE_PATH, MAX_GRAPH_SIZE);
		
		for(int graphSize = MIN_GRAPH_SIZE; graphSize <= MAX_GRAPH_SIZE; graphSize += GRAPH_SIZE_INCREMENT)
		{
			graphGenerator = new GraphGenerator(graphSize,inputCities);
			
			for(double graphDensity = MIN_GRAPH_DENSITY; 
					graphDensity <= MAX_GRAPH_DENSITY; graphDensity += GRAPH_DENSITY_INCREMENT)
			{
				System.out.println("For graph of size: " + graphSize + 
						" , density: " + String.format("%.1f", graphDensity) + 
						"\n----------------------------------------------------------------------");
				// start of graph generation
				graphGenerator.setRequiredNumOfNonStopFlights(graphDensity);
				Graph graph = graphGenerator.generateGraph();
//				graph.printGraph();
				
				String graphID = graphSize/GRAPH_SIZE_INCREMENT + "-" + (int)(graphDensity/MIN_GRAPH_DENSITY);
				
				System.out.println("Graph " + graphID + " [number of cities: " + graphSize + 
						", number of non-stop flights: " + graph.numOfNonStopFlights() + "]");
				// end of graph generation
				
				// start of BFS
				System.out.println("\nUsing BFS:");
				Result[] newResultBFS = new Result[NUM_OF_ITERATIONS];
				for(int i=0;i<NUM_OF_ITERATIONS;i++) {
				newResultBFS[i] = new Result(graph.size(),graph.density(),graph.numOfNonStopFlights());
				/*LinkedList<City> shortestRouteBFS = */searcher.bfsShortestRoute(graph.getCity(graphSize-1), 
														graph.getCity(0), newResultBFS[i]);
//				checkAndPrintPath(shortestRouteBFS,newResultBFS[i]);
				graph.resetCitiesVisited();}
				sort(newResultBFS,NUM_OF_ITERATIONS);
				Result F1Result = new Result(graph.size(),graph.density(),graph.numOfNonStopFlights());
				F1Result.setSearchTime(retInterquartile(newResultBFS,NUM_OF_ITERATIONS));
				resultsBFS.add(F1Result);
				
				// end of BFS
				
				// start of DFS
				System.out.println("Using DFS:");
				Result[] newResultDFS = new Result[NUM_OF_ITERATIONS];
				for(int i=0;i<NUM_OF_ITERATIONS;i++) {
				newResultDFS[i] = new Result(graph.size(),graph.density(),graph.numOfNonStopFlights());
				/*LinkedList<City> shortestRouteDFS = */searcher.dfsShortestRoute(graph.getCity(graphSize-1), 
														graph.getCity(0), newResultDFS[i]);
//				checkAndPrintPath(shortestRouteDFS,newResultDFS[i]);
				graph.resetCitiesVisited();}
				sort(newResultDFS,NUM_OF_ITERATIONS);
				Result F2Result = new Result(graph.size(),graph.density(),graph.numOfNonStopFlights());
				F2Result.setSearchTime(retInterquartile(newResultDFS,NUM_OF_ITERATIONS));
				resultsDFS.add(F2Result);
				
				// end of DFS
				
				System.out.println("----------------------------------------------------------------------\n");
			}
		}
		
		IOHandler.outputResult(resultsBFS, "BFS Results.xlsx");
		IOHandler.outputResult(resultsDFS, "DFS Results.xlsx");
	}
	
	/**
	 * This method checks if there is a shortest route, and prints it if it exists.
	 * 
	 * @param shortestRoute
	 * @param newResult
	 */
	public static void checkAndPrintPath(LinkedList<City> shortestRoute, Result newResult)
	{
		if(shortestRoute == null)
		{
			System.out.println("no path found");
		}
		else
		{
			newResult.foundPath();
			
			for(City city: shortestRoute)
			{
				System.out.print("->" + city);
				
			}
		}
		System.out.println("\nSearch time: " + newResult.getSearchTime() + "ns\n");
	}
	
	
	/**
	 * This method sorts the result so that interquartile mean of Search Times can be obtained.
	 * 
	 * @param results
	 * @param noOfIterations
	 */
	public static void sort(Result[] results,int noOfIterations)
	{
		for(int i=0;i<noOfIterations;i++)
			for(int j=i+1;j<noOfIterations;j++)
			if(results[i].getSearchTime()<results[j].getSearchTime()){
				Result temp = results[i];
				results[i]=results[j];
				results[j]= temp;
			}
	}
	
	/**
	 * This method returns the interquartile mean of Search Time for graphs of a specific size and density
	 * 
	 * @param results
	 * @param noOfIterations
	 * @return
	 */
	public static long retInterquartile(Result[] results,int noOfIterations)
	{long sum=0;
	
		for(int i=noOfIterations/4;i<3*noOfIterations/4;i++)
			sum+=results[i].getSearchTime();
		return sum/(noOfIterations/2);
	}
}
