package Lab4;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class IOHandler
{
	public static LinkedList<City> readCitiesFile(String fileName, int graphSize)
	{
		String line = "";
		String separator = ",";

		LinkedList<City> cities = new LinkedList<City>();

		File citiesFile = null;
		FileInputStream citiesFileIS = null;
		Scanner fileScanner = null;
		
		try
		{
			citiesFile = new File(fileName);
			citiesFileIS = new FileInputStream(citiesFile);
			fileScanner = new Scanner(citiesFileIS);
			
			System.out.println("Reading file...");
			for(int i = 0; i < graphSize; ++i)
			{
				if(fileScanner.hasNextLine())
				{
					line = fileScanner.nextLine();
					
					String[] cityInfo = line.split(separator);
					City newCity = new City(Integer.parseInt(cityInfo[3]),cityInfo[0]);
					cities.add(newCity);
				}
			}
		} 
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return cities;
	}
}
