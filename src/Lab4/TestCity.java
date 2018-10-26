package Lab4;

public class TestCity
{
	public static void main(String[] args)
	{
		City testCity = new City(1,"Singapore");
		
		System.out.println(testCity);
		
		testCity.visit();
		
		System.out.println(testCity);
	}
}
