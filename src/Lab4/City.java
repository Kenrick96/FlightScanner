package Lab4;

import java.util.LinkedList;

/**
 * This class represents each vertex in the graph. cityID is used to identify a City.
 * last-updated: 2018-11-03
 * 
 * @author annie, Jason
 *
 */
public class City
{
	private int cityID;
	private String cityName;
	
	private boolean visited = false;
	private City cityVisitedFrom; // keep track of the visiting City for printing of path found later
	
	private LinkedList<City> neighbors; // Linked List of Cities this City is connected to (adjacency list)

	public City(int cityID, String cityName)
	{
		this.cityID = cityID;
		this.cityName = cityName;
		neighbors = new LinkedList<City>();
	}

	public int getCityID() { return cityID; }
	public String getCityName() { return cityName; }

	public void visit() { visited = true; }
	public void unVisit() { visited = false; }
	public boolean isVisited() { return visited; }
	
	public City getCityVisitedFrom() { return cityVisitedFrom; }
	public void setCityVisitedFrom(City cityVisitedFrom) {this.cityVisitedFrom = cityVisitedFrom; }

	public void addNeighbor(City theCity)
	{
		neighbors.add(theCity);
	}

	/**
	 * A function that tells whether another city is connected to this city
	 * 
	 * @param theCity
	 * @return
	 */
	public boolean isNeighbor(City theCity)
	{
		return neighbors.contains(theCity);
	}

	public LinkedList<City> getNeighbors()
	{
		return neighbors;
	}

	public String printNeighbors()
	{
		String list = this.cityName + "'s neighbours: [";
		for (City c : neighbors)
		{
			list = list + c.getCityName() + ',';
		}
		list = list + ']';
		return list;
	}

	@Override
	public String toString()
	{
		return cityName + "[ID:" + cityID + "]";
	}
}
