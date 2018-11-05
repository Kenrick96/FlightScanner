package Lab4;

import java.util.LinkedList;

/**
 * This is the class that defines the functions of a graph. The implementation
 * of the graph is as adjacency list, where a LinkedList of City is maintained,
 * and each City maintains its own LinkedList of neighbours (connected
 * vertices).
 * 
 * This is an undirected graph, so when City A is connected to City B, City B is
 * connected to City A. This is an non-weighted graph, so weights of edges are
 * not recorded; instead we simply consider whether 2 Cities are neighbours.
 * 
 * last updated: 2018-11-04
 * 
 * @author annie
 *
 */
public class Graph
{
	private LinkedList<City> cities;
	private int size; // number of cities in graph
	private int numOfNonStopFlights;

	public Graph()
	{
		cities = new LinkedList<City>();
		size = 0;
		numOfNonStopFlights = 0;
	}

	/**
	 * Method to add city into a graph
	 * 
	 * @param newCity
	 */
	public void addCity(City newCity)
	{
		cities.add(newCity);
		size++;
	}

	/**
	 * Method to add numOfNonStopFlights within 2 cities, checks whether there is existing
	 * numOfNonStopFlights between 2 cities before connecting.
	 * 
	 * Undirected graph, so when we connect firstCity to secondCity, we also connect
	 * secondCity to firstCity.
	 * 
	 * @param firstCity
	 * @param secondCity
	 */
	public void connect(City firstCity, City secondCity)
	{
			firstCity.addNeighbor(secondCity);
			secondCity.addNeighbor(firstCity);
			numOfNonStopFlights++;
	}

	/**
	 * Setting all cities within the graph to unvisited
	 */
	public void resetCitiesVisited()
	{
		for (City c : cities)
		{
			c.unVisit();
		}
	}

	public int size()
	{
		return size;
	}

	public int numOfNonStopFlights()
	{
		return numOfNonStopFlights;
	}

	public double density()
	{
		return (double) 2 * numOfNonStopFlights / (size * (size - 1));
	}

	/**
	 * Get city by name
	 * 
	 * @param cityName
	 * @return
	 */
	public City getCity(String cityName)
	{
		for (City c : cities)
		{
			if (c.getCityName().equals(cityName.trim()))
			{
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Get city by ID
	 * 
	 * @param cityID
	 * @return
	 */
	public City getCityByID(int cityID)
	{
		for (City c : cities)
		{
			if (c.getCityID() == cityID)
			{
				return c;
			}
		}
		return null;
	}

	/**
	 * Get city by index
	 * 
	 * @param index
	 * @return the city if inside graph, else return null
	 */
	public City getCity(int index)
	{
		if (index < size)
			return cities.get(index);
		else
			return null;
	}

	/**
	 * Method to delete all cities in the graph
	 */
	public void clear()
	{
		cities.clear();
	}

	@Override
	public String toString()
	{
		String text = "";
		for (City c : cities)
		{
			text = text + c.toString() + '\n';
		}
		return text;
	}
	
	/**
	 * This method prints out the graph as an adjacency matrix.
	 */
	public void printGraph()
	{   System.out.print(String.format("%-30s", "Cities"));
		for(int i=0;i<size;i++)
			System.out.print(String.format("|%-30s|",cities.get(i).getCityName()));
		System.out.println("\n");
		for(int i=0;i<size;i++)
		{
			 System.out.print(String.format("%-30s", cities.get(i).getCityName()));
				for(int j=0;j<size;j++)
					System.out.print(String.format("|%-30s|",cities.get(i).isNeighbor(cities.get(j))));
				System.out.println("\n");
		}
		
	}

}
