package Lab4;

/**
 * This class holds result obtained from the program.
 * last-updated: 2018-11-05
 * 
 * @author Jason
 *
 */
public class Result
{
	private int graphSize;
	private double density;
	private int numOfNonStopFlights;
	
	private boolean pathFound = false;
	private long searchTime;
	
	public Result() {}
	public Result(int graphSize, double density, int numOfNonStopFlights) 
	{ 
		this.graphSize = graphSize; 
		this.density = density;
		this.numOfNonStopFlights = numOfNonStopFlights; 
	}
	
	// standard getters and setters
	public int getGraphSize() { return graphSize; }
	public void setGraphSize(int graphSize) { this.graphSize = graphSize; }
	
	public double getDensity() { return density; }
	public void setDensity(double density) { this.density = density; }
	
	public int getNumOfNonStopFlights() { return numOfNonStopFlights; }
	public void setNumOfNonStopFlights(int numOfNonStopFlights) { this.numOfNonStopFlights = numOfNonStopFlights; }
	
	public void foundPath() { pathFound = true; }
	public boolean isPathFound() { return pathFound; }
	
	public long getSearchTime() { return searchTime; }
	public void setSearchTime(long searchTime) { this.searchTime = searchTime; }
}