package Lab4;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This static class is used to generate a series of Graphs as per specification.
 * last-updated: 2018-11-04
 * 
 * @author annie
 * @subauthor Jason (refactor)
 *
 */
public class GraphVarying
{
	/**
	 * Static method that takes a constant number of density and generate
	 * different sizes of graphs of this specified density 
	 * 
	 * @param density
	 *            the constant density desired
	 * @param smallestSize
	 *            lower bound of graph size
	 * @param largestSize
	 *            upper bound of graph size
	 * @param numOfGraphs
	 *            total number of graphs to generate
	 * @param srcFile
	 *            source file of cities
	 * @throws FileNotFoundException
	 */
	public static ArrayList<Graph> generateGraphsVaryingSize(double density, int smallestSize, 
			int largestSize, int numOfGraphs, LinkedList<City> inputCities) throws FileNotFoundException
	{
		ArrayList<Graph> graphs = new ArrayList<Graph>();
		
		int sizeIncrement = (largestSize - smallestSize) / numOfGraphs;
		
		int size = smallestSize;
		
		for (int count = 0; count < numOfGraphs; ++count)
		{
			graphs.add(generateGraph(size, density, inputCities));
			size += sizeIncrement;
		}
		
		return graphs;
	}

	/**
	 * Static method that takes constant size of graph and generates specified number
	 * of graphs from smallestDensity to LargestDensity
	 * 
	 * @param size
	 *            constant size of graphs
	 * @param smallestDensity
	 *            lower bound of density
	 * @param largestDensity
	 *            upper bound of density
	 * @param numOfGraphs
	 *            total number of graphs to be generated
	 * @param srcFile
	 *            source file of cities
	 * @throws FileNotFoundException
	 */
	public static ArrayList<Graph> generateGraphsVaryingDensity(int size, double smallestDensity, 
			double largestDensity, int numOfGraphs, LinkedList<City> inputCities) throws FileNotFoundException
	{
		ArrayList<Graph> graphs = new ArrayList<Graph>();
		
		double densityIncrement = (largestDensity - smallestDensity) / numOfGraphs;
		double density = smallestDensity;
		
		for (int count = 0; count < numOfGraphs; ++count)
		{
			graphs.add(generateGraph(size, density, inputCities));
			density += densityIncrement;
		}
		
		return graphs;
	}

	/**
	 * Method that generates a graph specified and add it to list of graphs
	 * 
	 * @param size
	 * @param density
	 * @throws FileNotFoundException
	 */
	private static Graph generateGraph(int size, double density, LinkedList<City> inputCities) 
			throws FileNotFoundException
	{
		GraphGenerator gg = new GraphGenerator(size, density);
		
		gg.addCities(inputCities);
		gg.buildEdges();
		return gg.getGraph();
	}

}
