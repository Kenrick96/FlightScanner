package Lab4;

import java.util.LinkedList;

/**
 * This class searches for the shortest path between a source and a destination city
 * 
 * @author Jason
 *
 */
public class Searcher
{
	private LinkedList<City> L; // used as queue in BFS, stack in DFS
	
	public Searcher() { L = new LinkedList<City>(); }
	
	/**
	 * This method constructs the shortest path between a source and destination, by:
	 * getting the city that visits the destination, 
	 * the city that visits the city that visits the destination,
	 * ...
	 * till the source is reached,
	 * each time storing the Cities in a LinkedList from the front.
	 * 
	 * eg source -> city 1 -> city 2 -> ... -> city i -> destination
	 * 
	 * @param source
	 * @param destination
	 * @return shortestRoute between source and destination
	 */
	private LinkedList<City> shortestRoute(City source, City destination)
	{
		LinkedList<City> shortestRoute = new LinkedList<City>();
		City temp = destination;
		
		while(!temp.equals(source))
		{
			shortestRoute.addFirst(temp);
			
			temp = temp.getCityVisitedFrom();
			
		}
		shortestRoute.addFirst(temp);
		
		return shortestRoute;
	}
	
	/**
	 * This method returns the shortest route between a source and destination in a graph using Breadth-First Search.
	 * Linked-List L is used as a queue for the order of visiting, as well as to prevent revisits.
	 * This method makes use of shortestRoute() to return the shortest route in the right order
	 * 
	 * Assumptions:
	 * 1. Graph is undirected
	 * 2. Graph is un-weighted i.e. all flights (edges) between Cities are of equal distance (weight)
	 * 
	 * @param source
	 * @param destination
	 * @return shortestRoute between source and destination
	 */
	public LinkedList<City> bfsShortestRoute(City source, City destination)
	{
		// start from the source
		source.visit();
		L.addLast(source);
		
		while(!L.isEmpty())
		{
			City visitedCity = L.removeFirst();
			
			for(City neighbour: visitedCity.getNeighbors()) // BFS considers the neighbours of each node at each level
			{
				if(!neighbour.isVisited()) // prevent revisits
				{
					// each city keep track of visiting city for construction of shortest route
					neighbour.setCityVisitedFrom(visitedCity); 
					
					if(neighbour.equals(destination))
					{
						return shortestRoute(source, destination);
					}
					
					neighbour.visit(); // prevent revisits
					L.addLast(neighbour); // place this City in queue to visit its neighbours later
				}
			}
		}
		
		return null; // no path found
	}
	
	public LinkedList<City> dfsShortestRoute(City source, City destination)
	{
		// start from the source
		source.visit();
		L.addLast(source);
		
		while(!L.isEmpty())
		{
			City visitedCity = L.removeFirst();
			
			for(City neighbour: visitedCity.getNeighbors()) // BFS considers the neighbours of each node at each level
			{
				if(!neighbour.isVisited()) // prevent revisits
				{
					// each city keep track of visiting city for construction of shortest route
					neighbour.setCityVisitedFrom(visitedCity); 
					
					if(neighbour.equals(destination))
					{
						return shortestRoute(source, destination);
					}
					
					neighbour.visit(); // prevent revisits
					
					L.addFirst(neighbour); // place this City in stack to visit its neighbours later
				}
			}
		}
		
		return null; // no path found
	}
}
