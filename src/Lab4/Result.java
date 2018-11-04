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
	private double graphDensity;
	
	private long searchTime;
	
	public Result() {}
	
	public int getGraphSize() { return graphSize; }
	public void setGraphSize(int graphSize) { this.graphSize = graphSize; }
	
	public double getGraphDensity() { return graphDensity; }
	public void setGraphDensity(double graphDensity) { this.graphDensity = graphDensity; }
	
	public long getSearchTime() { return searchTime; }
	public void setSearchTime(long searchTime) { this.searchTime = searchTime; }
}