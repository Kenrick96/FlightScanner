package Lab4;

import java.util.LinkedList;

/**
 * This class searches for the shortest path between a source and a destination
 * city. last-updated: 2018-11-04
 * 
 * @author Jason
 *
 */
public class Searcher
{
	private LinkedList<City> L; // used as queue in BFS, stack in DFS

	public Searcher()
	{
		L = new LinkedList<City>();
	}

	/**
	 * This method constructs the shortest path between a source and destination,
	 * by: getting the city that visits the destination, the city that visits the
	 * city that visits the destination, ... till the source is reached, each time
	 * storing the Cities in a LinkedList from the front.
	 * 
	 * eg source -> city 1 -> city 2 -> ... -> city i -> destination
	 * 
	 * @param source
	 * @param destination
	 * @return shortestRoute between source and destination
	 */
	private LinkedList<City> constructRoute(City source, City destination)
	{
		LinkedList<City> route = new LinkedList<City>();
		City temp = destination;

		while (!temp.equals(source))
		{
			route.addFirst(temp);

			temp = temp.getCityVisitedFrom();

		}
		route.addFirst(temp);

		return route;
	}

	/**
	 * This method returns the shortest route between a source and destination in a
	 * graph using Breadth-First Search. Linked-List L is used as a queue for the
	 * order of visiting, as well as to prevent revisits. This method makes use of
	 * constructRoute() to return a route in the right order.
	 * 
	 * Assumptions: 1. Graph is undirected 2. Graph is un-weighted i.e. all flights
	 * (edges) between Cities are of equal distance (weight)
	 * 
	 * @param source
	 * @param destination
	 * @return shortestRoute between source and destination
	 */

	public LinkedList<City> bfsShortestRoute(City source, City destination, Result result)
	{
		// System.out.println("Searching...");

		long startTime = System.nanoTime();

		// start from the source
		source.visit();
		L.addLast(source);

		while (!L.isEmpty())
		{
			City visitedCity = L.removeFirst(); // dequeue

			for (City neighbour : visitedCity.getNeighbors()) // BFS considers the neighbours of each node at each
																// level
			{
				if (!neighbour.isVisited()) // prevent revisits
				{
					// each city keep track of visiting city for construction of shortest route
					neighbour.setCityVisitedFrom(visitedCity);

					if (neighbour.equals(destination))
					{
						long endTime = System.nanoTime();
						result.setSearchTime(endTime - startTime);
						L.clear();
						return constructRoute(source, destination);

					}

					neighbour.visit(); // prevent revisits
					L.addLast(neighbour); // enqueue this City in queue to visit its neighbours later
				}

				neighbour.visit(); // prevent revisits
				L.addLast(neighbour); // enqueue this neighbouring City in queue to visit its neighbours later
			}
		}

		long endTime = System.nanoTime();
		result.setSearchTime(endTime - startTime);

		L.clear();
		return null; // no path found
	}

	/**
	 * Does not work correct, as cityVisitedFrom is overwritten each time a new route is found.
	 * 
	 * This method returns the shortest route between a source and destination in a
	 * graph using Depth-First Search. Linked-List L is used as a stack for the
	 * order of visiting, as well as to prevent revisits. This method makes use of
	 * constructRoute() to return a route in the right order. This implementation of
	 * DFS is the iterative version, where an actual stack L is used, unlike the
	 * recursive version that uses system stack.
	 * 
	 * Assumptions: 1. Graph is undirected 2. Graph is un-weighted i.e. all flights
	 * (edges) between Cities are of equal distance (weight)
	 * 
	 * @param source
	 * @param destination
	 * @return shortestRoute between source and destination
	 */
	public LinkedList<City> dfsShortestRoute(City source, City destination, Result result)
	{
		// System.out.println("Searching...");
		long startTime = System.nanoTime();
		LinkedList<City> shortestRoute = null;

		// start from the source
		source.visit();
		L.addLast(source);

		while (!L.isEmpty())
		{
			City visitedCity = L.removeFirst(); // pop from stack

			for (City neighbour : visitedCity.getNeighbors())
			{
				if (!neighbour.isVisited()) // prevent revisits
				{
					// each city keep track of visiting city for construction of shortest route
					neighbour.setCityVisitedFrom(visitedCity);

					if (neighbour.equals(destination))
					{
						LinkedList<City> possibleShortest = constructRoute(source, destination);

						if (shortestRoute == null || (possibleShortest.size() < shortestRoute.size()))
						{
							shortestRoute = possibleShortest;
						}

					}

					neighbour.visit(); // prevent revisits

					// if this neighbouring City is already in Stack, remove it
					if (L.contains(neighbour))
						L.remove(neighbour);
					L.addFirst(neighbour); // push this neighbouring City onto stack to visit its neighbours later
				}
			}
		}

		long endTime = System.nanoTime();
		result.setSearchTime(endTime - startTime);

		L.clear();
		return shortestRoute; // if no path found, null will be returned just like in bfs
	}
}
