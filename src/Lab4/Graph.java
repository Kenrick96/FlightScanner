package Lab4;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This is the class that defines the functions of a graph
 * 
 * @author annie
 *
 */
public class Graph
{
	private LinkedList<City> cities;
	private int size;
	private int numOfEdges;

	public Graph()
	{
		cities = new LinkedList<City>();
		size = 0;
		numOfEdges = 0;
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
	 * Method to add numOfEdges within 2 cities, checks whether there is existing numOfEdges
	 * between 2 cities before connecting
	 * 
	 * @param firstCity
	 * @param secondCity
	 */
	public void connect(City firstCity, City secondCity)
	{
		if (!firstCity.isNeighbor(secondCity))
		{
			firstCity.addNeighbor(secondCity);
			secondCity.addNeighbor(firstCity);
			numOfEdges++;
		}
	}

	/**
	 * Setting all cities within the graph to unvisited
	 */
	public void resetCity()
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

	public int numOfEdges()
	{
		return numOfEdges;
	}

	public double density() {
		return (double) 2 * numOfEdges / (size * (size - 1));
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

	public String adjacencyList() {
		String text = "";
		for (City c : cities)
		{
			text = text + c.printNeighbors() + '\n';
		}
		return text;
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

}
