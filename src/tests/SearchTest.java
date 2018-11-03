package tests;

import java.util.LinkedList;

import Lab4.CPUTime;
import Lab4.City;
import Lab4.Graph;
import Lab4.Searcher;

/**
 * This class tests the Searcher and CPUTime classes to make sure they work correctly.
 * last-updated: 2018-11-03
 * 
 * @author Jason
 *
 */
public class SearchTest
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
		testGraph.connect(testCities[1], testCities[3]);
		testGraph.connect(testCities[1], testCities[4]);
		testGraph.connect(testCities[2], testCities[4]);
		
		Searcher testSearcher = new Searcher();
		
		LinkedList<City> testShortest = new LinkedList<City>();
		
		CPUTime bfsTimeTaken = new CPUTime();
		testShortest = testSearcher.bfsShortestRoute(testCities[0], testCities[4], bfsTimeTaken);
		
		if(testShortest == null)
		{
			System.out.println("no path found");
		}
		else
		{
			for(City city: testShortest)
			{
				System.out.print("->" + city);
			}
		}
		System.out.println("\nBFS took:" + bfsTimeTaken.getCPUTimeTaken() + "ns");
		System.out.println();
		
		CPUTime dfsTimeTaken = new CPUTime();
		testShortest = testSearcher.dfsShortestRoute(testCities[0], testCities[4], dfsTimeTaken);
		
		if(testShortest == null)
		{
			System.out.println("no path found");
		}
		else
		{
			for(City city: testShortest)
			{
				System.out.print("->" + city);
			}
		}
		System.out.println("\nDFS took:" + dfsTimeTaken.getCPUTimeTaken() + "ns");
	}

}