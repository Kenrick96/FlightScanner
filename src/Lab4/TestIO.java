package Lab4;

import java.util.LinkedList;

public class TestIO
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		LinkedList<City> testCities = IOHandler.readCitiesFile("C:\\Users\\jason\\Documents\\NTU\\Academic\\1 SCSE\\"
				+ "Year_2_Semester_1\\CZ2001 Algorithms\\3 Labs\\Lab 4\\FlightScanner\\src\\Lab4\\World Cities.csv", 11000);
		
		for(City city: testCities)
		{
			System.out.println(city);
		}
	}

}
