package tests;

import java.io.FileNotFoundException;

import Lab4.Graph;
import Lab4.GraphGenerator;

public class TestGraphGenerator {
	public static void main(String[] args) throws FileNotFoundException
	{
		GraphGenerator graphGen= new GraphGenerator(50,0.5);
//		graphGen.readCities("Cities.csv");
		graphGen.buildEdges();
		Graph g= graphGen.getGraph();
		System.out.println(g.toString());
		System.out.println(g.adjacencyList());
		System.out.println(g.numOfEdges());
	}
}
