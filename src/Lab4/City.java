package Lab4;

import java.util.ArrayList;

/**
 * This class represents each vertex in the graph.
 * cityID is used to identify a City
 * 
 * @author Jason
 *
 */
public class City
{
	private int cityID;
	private String cityName;
	private boolean visited = false;
	private ArrayList<City> neighbors;
	
	// constructor does not check if parameters overflow currently
	public City(int cityID, String cityName)
	{
		this.cityID = cityID;
		this.cityName = cityName;
		neighbors = new ArrayList<City>();
	}
	
	public int getCityID() { return cityID; }
	public String getCityName() { return cityName; }
	public void visit() { visited = true; }
	public void unVisit() { visited=false; }
	public boolean isVisited() { return visited; }
	public void addNeighbor(City theCity) {
		neighbors.add(theCity);
	}
	
	/**
	 * A function that tells whether another city is connected to this city
	 * @param theCity
	 * @return
	 */
	public boolean isNeighbor(City theCity) {
		if (neighbors.contains(theCity))
			return true;
		else 
			return false;
	}
	@Override
	public String toString()
	{
		return cityName + "[ID:" + cityID + ", " + (visited? "visited": "not visited") + "]";
	}
}
