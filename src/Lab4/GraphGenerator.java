package Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * This is the generator that randomly creates graphs according to size and
 * density entered last-updated: 2018-11-04
 * 
 * @author annie
 *
 */
public class GraphGenerator {
	private int size;
	// private double density;
	private int requiredNumOfEdges;
	private Graph graph;

	/**
	 * Constructor that takes the input of size and density desired to make a graph
	 * 
	 * @param size
	 * @param density
	 */
	public GraphGenerator(int size, double density) {
		this.size = size;
		// this.density = density;
		requiredNumOfEdges = (int) Math.round(density * size * (size - 1) / 2);
		// System.out.println("Required num of edges:" + requiredNumOfEdges);
		graph = new Graph();
	}

	public void addCities(LinkedList<City> inputCities) {
		int cityCount = 0;
		while (cityCount < size && cityCount < inputCities.size()) {
//			System.out.println("Adding city " + (cityCount + 1));
			graph.addCity(inputCities.get(cityCount));

			++cityCount;
		}
	}

//	/**
//	 * Add cities to graph from a external file, this functions reads line by line
//	 * until reaching the graph size
//	 * 
//	 * @param fileName
//	 * @throws FileNotFoundException
//	 */
//	public void readCities(String fileName) throws FileNotFoundException
//	{
//		File file = new File(fileName);
//		Scanner fileScanner = new Scanner(file);
//		
//		int cityCount = 0;
//		while (cityCount < size)
//		{
//			if (fileScanner.hasNextLine())
//			{
//				String line = fileScanner.nextLine();
//				
//				Scanner lineScanner = new Scanner(line);
//				
//				int cityID;
//				String cityName;
//				
//				if (lineScanner.hasNextInt())
//				{
//					cityID = lineScanner.nextInt();
//					
//					if (lineScanner.hasNext())
//					{
//						cityName = lineScanner.next();
//						
//						if (lineScanner.hasNext())
//							cityName = cityName + " " + lineScanner.next();
//						
//						City newCity = new City(cityID, cityName);
//						if (newCity != null)
//						{
//							graph.addCity(newCity);
//							++cityCount;
//						}
//					}
//				}
//			}
//		}
//	}

	public void buildEdges() {
		Random r = new Random();
		boolean[][] bool = new boolean [size][size];
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				bool[i][j]=false;
		while (graph.numOfEdges() < requiredNumOfEdges) {
			int x = r.nextInt(size);
			int y = r.nextInt(size);
			if(x!=y && bool[x][y]!=true)
					{graph.connect(graph.getCity(x), graph.getCity(y));
					bool[x][y]=true;
					}
		}

	}

	public Graph getGraph() {
		return graph;
	}
	
	public void clearGraph()
	{
		graph.clear();
	}

	public void printGraph() {
		graph.printGraph();
	}

	public Graph generateGraph(LinkedList<City> inputCities)
	{
		addCities(inputCities);
		buildEdges();
		
		return graph;
	}
}
