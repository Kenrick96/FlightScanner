package Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * This is the generator that randomly creates graphs according to size and
 * density entered
 * 
 * @author annie
 *
 */
public class GraphGenerator {
	private int size;
	private double density;
	private int edges;
	private Graph graph;

	/**
	 * Constuctor that takes the input of size and density desired to make a graph
	 * 
	 * @param size
	 * @param density
	 */
	public GraphGenerator(int size, double density) {
		this.size = size;
		this.density = density;
		edges = (int) Math.round(size / density);
		graph = new Graph();
	}

	/**
	 * Add cities to graph from a external file, this functions reads line by line
	 * until reaching the graph size
	 * 
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public void readCities(String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		Scanner s = new Scanner(f);
		int count = 0;
		while (count < size) {
			if (s.hasNextLine()) {
				Scanner lineScan = new Scanner(s.nextLine());
				int cityID;
				String cityName;
				if (s.hasNextInt()) {
					cityID = s.nextInt();
					if (s.hasNext()) {
						cityName = s.next();
						graph.addCity(new City(cityID, cityName));
						count++;
					}
				}
			}
		}
	}

	public void buildEdges() {
		Random r = new Random();
		while (graph.edges() < edges) {

			// Get 2 random cities from graph
			City a = graph.getCity(r.nextInt(size));
			City b = graph.getCity(r.nextInt(size));

			// Check that a, b are not null, and not already neighbors before connecting
			if (a != null && b != null && a != b)
				graph.connect(a, b);

		}
	}

}
