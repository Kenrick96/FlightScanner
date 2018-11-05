package Lab4;

import java.util.LinkedList;
import java.util.Random;

/**
 * This is the generator that randomly creates graphs according to size and
 * density entered last-updated: 2018-11-05
 * 
 * @author annie
 * @author Thomas Stephen Felix
 *
 */
public class GraphGenerator
{
	private int size;
	// private double density;
	boolean[][] bool;
	private int requiredNumOfNonStopFlights;
	private Graph graph;

	/**
	 * Constructor that takes the input of size and density desired to make a graph
	 * 
	 * @param size
	 * @param density
	 */
	public GraphGenerator(int size, LinkedList<City> inputCities)
	{
		this.size = size;
		bool = new boolean[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				bool[i][j] = false;
		graph = new Graph();
		addCities(inputCities);

	}

	public void setRequiredNumOfNonStopFlights(double density)
	{
		requiredNumOfNonStopFlights = (int) Math.round(density * size * (size - 1) / 2);
	}

	public void addCities(LinkedList<City> inputCities)
	{
		int cityCount = 0;
		graph.clear();
		while (cityCount < size && cityCount < inputCities.size())
		{
			// System.out.println("Adding city " + (cityCount + 1));
			graph.addCity(inputCities.get(cityCount));

			++cityCount;
		}
	}

	/**
	 * This method increases the number of edges to the number required.
	 * So, for a graph of the same size, if the density required is increased,
	 * more edges would be added TO THE SAME GRAPH, instead of generating a new graph from scratch.
	 * 
	 * Uses the adjacency matrix representation for the O(1) search time of whether 2 Cities are connected.
	 * 
	 */
	public void buildNonStopFlights()
	{
		Random r = new Random();
		while (graph.numOfNonStopFlights() < requiredNumOfNonStopFlights)
		{
			int x = r.nextInt(size);
			int y = r.nextInt(size);
			
			if (x != y && bool[x][y] != true)
			{
				graph.connect(graph.getCity(x), graph.getCity(y));
				bool[x][y] = true;
			}
		}

	}

	public Graph getGraph()
	{
		return graph;
	}

	public void clearGraph()
	{
		graph.clear();
	}

	public void printGraph()
	{
		graph.printGraph();
	}

	public Graph generateGraph()
	{
		buildNonStopFlights();

		return graph;
	}
}
