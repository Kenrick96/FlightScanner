package Lab4;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TestGraphVarying {
	public static void main(String[] args) throws FileNotFoundException {
		GraphVarying gv = new GraphVarying(0.5, 20, 100, 2, "Cities.csv");
		ArrayList<Graph> graphs = gv.getGraphList();
		for (Graph g : graphs) {
			System.out.println("Size: " + g.size() + "Density: " + g.density() + '\n');
		}
	}

}