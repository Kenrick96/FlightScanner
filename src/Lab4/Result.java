package Lab4;

/**
 * This class holds result obtained from the program.
 * last-updated: 2018-11-04
 * 
 * @author Jason
 *
 */
public class Result
{
	private int graphSize;
	private int numOfNonStopFlights;
	
	private boolean isPathFound = false;
	private long searchTime;
	
	public Result(int graphSize, int numOfNonStopFlights) { this.graphSize = graphSize; this.numOfNonStopFlights = numOfNonStopFlights; }
	
	public int getGraphSize() { return graphSize; }
	public void setGraphSize(int graphSize) { this.graphSize = graphSize; }
	
	public int getNumOfNonStopFlights() { return numOfNonStopFlights; }
	public void setNumOfNonStopFlights(int numOfNonStopFlights) { this.numOfNonStopFlights = numOfNonStopFlights; }
	
	public void foundPath() { isPathFound = true; }
	
	public long getSearchTime() { return searchTime; }
	public void setSearchTime(long searchTime) { this.searchTime = searchTime; }
}