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
 * @author Jason
 *
 */
public class FlightScheduling
{
	public static final String SOURCE_CITY = "Tokyo";
	public static final String DESTINATION_CITY = "Shanghai";
	
	public static final int MIN_GRAPH_SIZE = 20;
	public static final int MAX_GRAPH_SIZE = 100;
	public static final int GRAPH_SIZE_INCREMENT = 10;
	
	public static final double MIN_GRAPH_DENSITY = 0.1;
	public static final double MAX_GRAPH_DENSITY = 1;
	public static final double GRAPH_DENSITY_INCREMENT = 0.1;
	
	public static void main(String args[]) throws Exception // #YOLO
	{
		Searcher searcher = new Searcher();
		ArrayList<Graph> graphs;
		ArrayList<Result> results = new ArrayList<Result>();
		
		for(int graphSize = MIN_GRAPH_SIZE; graphSize <= MAX_GRAPH_SIZE; graphSize += GRAPH_SIZE_INCREMENT)
		{
			graphs = GraphVarying.generateGraphsVaryingDensity(graphSize, MIN_GRAPH_DENSITY, 
					MAX_GRAPH_DENSITY, (int) Math.round(MAX_GRAPH_DENSITY/GRAPH_DENSITY_INCREMENT), "Cities.csv");
			
			for(int graphIndex = 0; graphIndex <graphs.size(); ++graphIndex)
			{
				Graph graph = graphs.get(graphIndex);
				
				System.out.println("Graph " + (graphIndex + 1) + " [number of cities: " + graph.size() + 
						", number of non-stop flights: " + graph.numOfEdges() + "]");
				Result newResult = new Result(graph.size(),graph.density());
				
				LinkedList<City> shortestRoute = searcher.bfsShortestRoute(graph.getCity(SOURCE_CITY), 
														graph.getCity(DESTINATION_CITY), newResult);
				
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
				System.out.println("\nSearch time: " + newResult.getSearchTime() + "\n");
				
				results.add(newResult);
			}
		}
	}
}
