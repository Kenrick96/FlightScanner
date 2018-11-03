package Lab4;

import java.util.LinkedList;

public class TestBFS
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		City[] testCities = new City[5];
		
		for(int i = 0; i < testCities.length; ++i)
		{
			testCities[i] = new City(i, Character.toString(((char)(i+97))));
		}
		
		Graph testGraph = new Graph();
		for(City city: testCities)
		{
			testGraph.addCity(city);
		}
		
		// connecting cities below
		testGraph.connect(testCities[0], testCities[1]);
		testGraph.connect(testCities[0], testCities[2]);
		testGraph.connect(testCities[0], testCities[3]);
		testGraph.connect(testCities[1], testCities[3]);
		testGraph.connect(testCities[3], testCities[4]);
		
		Searcher testSearcher = new Searcher();
		
		LinkedList<City> testShortest = new LinkedList<City>();
		testShortest = testSearcher.bfsShortestRoute(testCities[0], testCities[4]);
		
		if(testShortest != null)
		{
			System.out.println("no path found");
		}
		else
		{
			for(City city: testShortest)
			{
				System.out.println("->" + city);
			}
		}
		
	}

}
