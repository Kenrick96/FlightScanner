package Lab4;

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
	
	// constructor does not check if parameters overflow currently
	public City(int cityID, String cityName)
	{
		this.cityID = cityID;
		this.cityName = cityName;
	}
	
	public int getCityID() { return cityID; }
	
	public void visit() { visited = true; }
	public boolean isVisited() { return visited; }
	
	@Override
	public String toString()
	{
		return cityName + "[ID:" + cityID + ", " + (visited? "visited": "not visited") + "]";
	}
}
