package Lab4;

import java.util.ArrayList;

/**
 * This is the class that defines the functions of a graph
 * 
 * @author annie
 *
 */
public class Graph {
	private ArrayList<City> cities;
	private int size;
	private int edges;

	public Graph() {
		cities = new ArrayList<City>();
		size = 0;
		edges = 0;
	}

	/**
	 * Method to add city into a graph
	 * 
	 * @param newCity
	 */
	public void addCity(City newCity) {
		cities.add(newCity);
		size++;
	}

	/**
	 * Method to add edges within 2 cities
	 * 
	 * @param firstCity
	 * @param secondCity
	 */
	public void connect(City firstCity, City secondCity) {
		firstCity.addNeighbor(secondCity);
		secondCity.addNeighbor(firstCity);
		edges++;
	}

	/**
	 * Setting all cities within the graph to unvisited
	 */
	public void resetCity() {
		for (City c : cities) {
			c.unVisit();
		}
	}

	public int size() {
		return size;
	}

	public int edges() {
		return edges;
	}

	public double density() {
		return (double) edges / size;
	}
	
	public City getCity(String cityName) {
		for(City c:cities) {
			if(c.getCityName().equals(cityName.trim())) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Method to delete all cities in the graph
	 */
	public void clear() {
		cities.clear();
	}
	

}
