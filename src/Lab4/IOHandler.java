package Lab4;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class is handles IO to/fro external sources.
 * last-updated: 2018-11-05
 * 
 * @author Jason
 *
 */
public class IOHandler
{
	/**
	 * This method reads in the necessary number of cities from a csv file,
	 * where the necessary number of city is graphSize.
	 * 
	 * @param fileName
	 * @param graphSize
	 * @return
	 */
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
	
	/**
	 * This method outputs results to an Excel file.
	 * 
	 * @param results
	 * @param fileName
	 */
	public static void outputResult(ArrayList<Result> results, String fileName)
	{
		XSSFWorkbook outputExcel = new XSSFWorkbook();
		
		XSSFSheet resultSheet = outputExcel.createSheet();
		
		// start of header
		XSSFRow headerRow = resultSheet.createRow(0);

		// header for number of cities
		XSSFCell sizeHeader = headerRow.createCell(0);
		sizeHeader.setCellValue("Graph Size");
		
		// header for graph density
		XSSFCell densityHeader = headerRow.createCell(1);
		densityHeader.setCellValue("Graph Density");
				
		// header for number of non-stop flights
		XSSFCell flightsHeader = headerRow.createCell(2);
		flightsHeader.setCellValue("Num Of Non-Stop Flights");
		
		// header for running time
		XSSFCell searchTimeHeader = headerRow.createCell(3);
		searchTimeHeader.setCellValue("Search Time");
		// end of header
		
		for(int i = 0; i < results.size(); ++i)
		{
			Result outputResult = results.get(i);
			
			XSSFRow newRow = resultSheet.createRow(i+1);
			
			// number of cities
			XSSFCell graphSizeCell = newRow.createCell(0);
			graphSizeCell.setCellValue(outputResult.getGraphSize());
			
			// number of non-stop flights
			XSSFCell graphDensityCell = newRow.createCell(1);
			graphDensityCell.setCellValue(outputResult.getDensity());
			
			// number of non-stop flights
			XSSFCell graphNumOfNonStopFlightsCell = newRow.createCell(2);
			graphNumOfNonStopFlightsCell.setCellValue(outputResult.getNumOfNonStopFlights());
			
			// running time
			XSSFCell searchTimeCell = newRow.createCell(3);
			searchTimeCell.setCellValue(outputResult.getSearchTime());
		}
		
		for(int i = 0; i < 4; ++i)
		{
			resultSheet.autoSizeColumn(i);
		}
		
		try
		{
			File outputFile = new File(fileName);
			FileOutputStream fileOS = new FileOutputStream(outputFile);
			outputExcel.write(fileOS);
			
			fileOS.close();
			outputExcel.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File is opened");
		}
		catch(IOException e)
		{
			e.getMessage();
		}

		System.out.println("Result written to file successfully");
	}
}
