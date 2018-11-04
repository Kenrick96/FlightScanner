package Lab4;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * task: find a route between two cities with minimum number of stops,
 * for graphs of various sizes and densities
 * 
 * 1. Measure CPU times
 * 
 * to-do: analyse CPU times w.r.t. numOfCities (vertices) and nonStopFlights (edges)
 * 
 * incomplete implementation: source = Tokyo, destination = Shanghai for now
 * 
 * last updated: 2018-11-04
 * 
 * @author Jason
 *
 */
public class FlightScheduling
{
	// Jason's file path: C:\Users\jason\Documents\NTU\Academic\1 SCSE\Year_2_Semester_1\CZ2001 Algorithms\3 Labs\Lab 4\FlightScanner\src\Lab4\World Cities.csv
	// Stephen's file path: C:\\Users\\steph\\Documents\\New folder\\FlightScanner\\src\\Lab4\\World Cities.csv
	
	public static final String CITIES_FILE_PATH =  "C:\\Users\\jason\\Documents\\NTU\\Academic\\1 SCSE\\Year_2_Semester_1\\CZ2001 Algorithms\\3 Labs\\Lab 4\\FlightScanner\\src\\Lab4\\World Cities.csv";
	
//	public static final String SOURCE_CITY = "Kirovohrad";
//	public static final String DESTINATION_CITY = "Kiliya";
	
	public static final int MIN_GRAPH_SIZE = 100;
	public static final int MAX_GRAPH_SIZE = 1000;
	public static final int GRAPH_SIZE_INCREMENT = 100;
	
	public static final double MIN_GRAPH_DENSITY = 0.1;
	public static final double MAX_GRAPH_DENSITY = 1;
	public static final double GRAPH_DENSITY_INCREMENT = 0.1;
	
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
				System.out.println("For graph of size: " + graphSize + " , density: " + graphDensity);
				
				// start of graph generation
				graphGenerator.setGraphDensity(graphDensity);
				Graph graph = graphGenerator.generateGraph();
//				graph.printGraph();
				
				String graphID = graphSize/GRAPH_SIZE_INCREMENT + "-" + (int)(graphDensity/MIN_GRAPH_DENSITY);
				
				System.out.println("Graph " + graphID + " [number of cities: " + graphSize + 
						", number of non-stop flights: " + graph.numOfEdges() + "]");
				// end of graph generation
				
				// start of BFS
				Result newResultBFS = new Result(graph.size(),graph.density());
				
				LinkedList<City> shortestRouteBFS = searcher.bfsShortestRoute(graph.getCity(graphSize-1), 
														graph.getCity(0), newResultBFS);
				
				if(shortestRouteBFS == null)
				{
					System.out.println("no path found using BFS");
				}
				else
				{
					newResultBFS.foundPath();
					
					for(City city: shortestRouteBFS)
					{
						System.out.print("->" + city);
						
					}
				}
				System.out.println("\nSearch time using BFS: " + newResultBFS.getSearchTime() + "ns\n");
				
				resultsBFS.add(newResultBFS);
				graph.resetCitiesVisited();
				// end of BFS
				
				// start of DFS
				Result newResultDFS = new Result(graph.size(),graph.density());
				
				LinkedList<City> shortestRouteDFS = searcher.bfsShortestRoute(graph.getCity(graphSize-1), 
														graph.getCity(0), newResultDFS);
				
				if(shortestRouteDFS == null)
				{
					System.out.println("no path found using DFS");
				}
				else
				{
					newResultDFS.foundPath();
					
					for(City city: shortestRouteDFS)
					{
						System.out.print("->" + city);
						
					}
				}
				System.out.println("\nSearch time using DFS: " + newResultDFS.getSearchTime() + "ns\n");
				
				resultsDFS.add(newResultDFS);
				graph.resetCitiesVisited();
				// end of DFS
			}
		}
		
		
	}
}
