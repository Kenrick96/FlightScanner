package Lab4;

import java.util.LinkedList;

/**
 * This class searches for a path between a source and a destination city
 * 
 * @author Jason
 *
 */
public class Searcher
{
	private LinkedList<City> L; // used as queue in BFS, stack in DFS
	
	public Searcher() { L = new LinkedList<City>(); }
	
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
	
	public LinkedList<City> bfsShortestRoute(City source, City destination)
	{
		L.addLast(source);
		
		while(!L.isEmpty())
		{
			City visitedCity = L.removeFirst();
			visitedCity.visit();
			
			LinkedList<City> neighbours = visitedCity.getNeighbors();
			
			for(City neighbour: neighbours)
			{
				
				if(!neighbour.isVisited())
				{
					neighbour.setCityVisitedFrom(visitedCity);
					
					if(neighbour.equals(destination))
					{
						return shortestRoute(source, destination);
					}
					
					L.addLast(neighbour);
				}
			}
		}
		
		return null;
	}
	
	
}
