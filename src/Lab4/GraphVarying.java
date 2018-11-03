package Lab4;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This class is used to generate a series of Graphs as specification
 * 
 * @author annie
 *
 */
public class GraphVarying {

	private ArrayList<Graph> graphs;
	private String file;

	/**
	 * Constructor that takes a constant number of density and will then generate
	 * different sizes of graphs with this density specified
	 * 
	 * @param density
	 *            the constant density desired
	 * @param smallestSize
	 *            lower bound of graph size
	 * @param largestSize
	 *            upper bound of graph size
	 * @param totalNum
	 *            total number of graphs to generate
	 * @param srcFile
	 *            source file of cities
	 * @throws FileNotFoundException
	 */
	public GraphVarying(double density, int smallestSize, int largestSize, int totalNum, String srcFile)
			throws FileNotFoundException {
		graphs = new ArrayList<Graph>();
		file = srcFile;
		int space = (largestSize - smallestSize) / totalNum;
		int size = smallestSize;
		int count = 0;
		while (count < totalNum) {
			generate(size, density);
			density += space;
			count++;
		}
	}

	/**
	 * Constructor that takes constant size of graph and generates specified number
	 * of graph from smallestDensity to LargestDensity
	 * 
	 * @param size
	 *            constant size of graphs
	 * @param smallestDensity
	 *            lower bound of density
	 * @param largestDensity
	 *            upper bound of density
	 * @param totalNum
	 *            total number of graphs to be generated
	 * @param srcFile
	 *            source file of cities
	 * @throws FileNotFoundException
	 */
	public GraphVarying(int size, double smallestDensity, double largestDensity, int totalNum, String srcFile)
			throws FileNotFoundException {
		graphs = new ArrayList<Graph>();
		file = srcFile;
		double space = (largestDensity - smallestDensity) / totalNum;
		double density = smallestDensity;
		int count = 0;
		while (count < totalNum) {
			generate(size, density);
			density += space;
			count++;
		}
	}

	/**
	 * Method that generates a graph specified and
	 * 
	 * @param size
	 * @param density
	 * @throws FileNotFoundException
	 */
	private void generate(int size, double density) throws FileNotFoundException {
		GraphGenerator gg = new GraphGenerator(size, density);
		gg.readCities(file);
		graphs.add(gg.getGraph());
	}

	public ArrayList<Graph> getGraphList() {
		return graphs;
	}
}
