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
	public static final String CITIES_FILE_PATH =  "C:\\Users\\steph\\Documents\\New folder\\FlightScanner\\src\\Lab4\\World Cities.csv";
	
//	public static final String SOURCE_CITY = "Kirovohrad";
//	public static final String DESTINATION_CITY = "Kiliya";
	
	public static final int MIN_GRAPH_SIZE = 10;
	public static final int MAX_GRAPH_SIZE = 20;
	public static final int GRAPH_SIZE_INCREMENT = 10;
	
	public static final double MIN_GRAPH_DENSITY = 0.1;
	public static final double MAX_GRAPH_DENSITY = 1;
	public static final double GRAPH_DENSITY_INCREMENT = 0.1;
	
	public static void main(String args[]) throws Exception // #YOLO
	{
		GraphGenerator graphGenerator;
		Searcher searcher = new Searcher();
		ArrayList<Result> results = new ArrayList<Result>();
		
		LinkedList<City> inputCities = IOHandler.readCitiesFile(CITIES_FILE_PATH, MAX_GRAPH_SIZE);
		
		for(int graphSize = MIN_GRAPH_SIZE; graphSize <= MAX_GRAPH_SIZE; graphSize += GRAPH_SIZE_INCREMENT)
		{graphGenerator = new GraphGenerator(graphSize,inputCities);
			for(double graphDensity = MIN_GRAPH_DENSITY; 
					graphDensity <= MAX_GRAPH_DENSITY; graphDensity += GRAPH_DENSITY_INCREMENT)
			{
				System.out.println("For graph of size: " + graphSize + " , density: " + graphDensity);
				
				graphGenerator.setGraphDensity(graphDensity);
				Graph graph = graphGenerator.generateGraph();
				graph.printGraph();
				
				String graphID = graphSize/GRAPH_SIZE_INCREMENT + "-" + (int)(graphDensity/MIN_GRAPH_DENSITY);
				
				System.out.println("Graph " + graphID + " [number of cities: " + graphSize + 
						", number of non-stop flights: " + graph.numOfEdges() + "]");
				
				Result newResult = new Result(graph.size(),graph.density());
				
				LinkedList<City> shortestRoute = searcher.bfsShortestRoute(graph.getCity(graphSize-1), 
														graph.getCity(0), newResult);
				
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
				
				results.add(newResult);
				graph.resetCitiesVisited();
			}
		}
	}
}
