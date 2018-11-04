package tests;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import Lab4.Graph;
import Lab4.GraphVarying;

public class TestGraphVarying
{
	public static void main(String[] args) throws FileNotFoundException
	{

		ArrayList<Graph> graphs = GraphVarying.generateGraphsVaryingSize(0.5, 20, 100, 8, "Cities.csv");

		for (Graph g : graphs)
		{
			// System.out.println(g);
			System.out.println("Size: " + g.size() + " NumOfEdges: " + g.numOfEdges() + '\n');
		}
	}

}