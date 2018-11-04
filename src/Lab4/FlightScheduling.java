package Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * task: find a route between two cities with minimum number of stops,
 * for graphs of various sizes and densities
 * 
 * 1. Measure CPU times
 * 
 * to-do: analyse CPU times w.r.t. numOfCities (vertices) and nonStopFlights (edges)
 * 
 * incomplete implementation: source = Tokyo, destination = Shanghai for now
 * 
 * last updated: 2018-11-04
 * 
 * @author Jason
 *
 */
public class FlightScheduling
{
	// Jason's file path: C:\Users\jason\Documents\NTU\Academic\1 SCSE\Year_2_Semester_1\CZ2001 Algorithms\3 Labs\Lab 4\FlightScanner\src\Lab4\World Cities.csv
	// Stephen's file path: C:\\Users\\steph\\Documents\\New folder\\FlightScanner\\src\\Lab4\\World Cities.csv
	
	public static final String CITIES_FILE_PATH =  "C:\\Users\\jason\\Documents\\NTU\\Academic\\1 SCSE\\Year_2_Semester_1\\CZ2001 Algorithms\\3 Labs\\Lab 4\\FlightScanner\\src\\Lab4\\World Cities.csv";
	
//	public static final String SOURCE_CITY = "Kirovohrad";
//	public static final String DESTINATION_CITY = "Kiliya";
	
	public static final int MIN_GRAPH_SIZE = 100;
	public static final int MAX_GRAPH_SIZE = 1000;
	public static final int GRAPH_SIZE_INCREMENT = 100;
	
	public static final double MIN_GRAPH_DENSITY = 0.1;
	public static final double MAX_GRAPH_DENSITY = 1;
	public static final double GRAPH_DENSITY_INCREMENT = 0.1;
	
	/**
	 * main method that applies searching algorithm to flight-scheduling
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception // #YOLO
	{
		GraphGenerator graphGenerator;
		Searcher searcher = new Searcher();
		
		// These 2 Array Lists hold results of searhing using BFS and DFS
		ArrayList<Result> resultsBFS = new ArrayList<Result>();
		ArrayList<Result> resultsDFS = new ArrayList<Result>();
		
		LinkedList<City> inputCities = IOHandler.readCitiesFile(CITIES_FILE_PATH, MAX_GRAPH_SIZE);
		
		for(int graphSize = MIN_GRAPH_SIZE; graphSize <= MAX_GRAPH_SIZE; graphSize += GRAPH_SIZE_INCREMENT)
		{
			graphGenerator = new GraphGenerator(graphSize,inputCities);
			
			for(double graphDensity = MIN_GRAPH_DENSITY; 
					graphDensity <= MAX_GRAPH_DENSITY; graphDensity += GRAPH_DENSITY_INCREMENT)
			{
				System.out.println("For graph of size: " + graphSize + 
						" , density: " + String.format("%.1f", graphDensity) + 
						"\n----------------------------------------------------------------------");
				// start of graph generation
				graphGenerator.setGraphDensity(graphDensity);
				Graph graph = graphGenerator.generateGraph();
//				graph.printGraph();
				
				String graphID = graphSize/GRAPH_SIZE_INCREMENT + "-" + (int)(graphDensity/MIN_GRAPH_DENSITY);
				
				System.out.println("Graph " + graphID + " [number of cities: " + graphSize + 
						", number of non-stop flights: " + graph.numOfNonStopFlights() + "]");
				// end of graph generation
				
				// start of BFS
				System.out.println("\nUsing BFS:");
				Result newResultBFS = new Result(graph.size(),graph.numOfNonStopFlights());
				
				LinkedList<City> shortestRouteBFS = searcher.bfsShortestRoute(graph.getCity(graphSize-1), 
														graph.getCity(0), newResultBFS);
				checkAndPrintPath(shortestRouteBFS,newResultBFS);
				
				resultsBFS.add(newResultBFS);
				graph.resetCitiesVisited();
				// end of BFS
				
				// start of DFS
				System.out.println("Using DFS:");
				Result newResultDFS = new Result(graph.size(),graph.numOfNonStopFlights());
				
				LinkedList<City> shortestRouteDFS = searcher.bfsShortestRoute(graph.getCity(graphSize-1), 
														graph.getCity(0), newResultDFS);
				checkAndPrintPath(shortestRouteDFS,newResultDFS);
				
				resultsDFS.add(newResultDFS);
				graph.resetCitiesVisited();
				// end of DFS
				
				System.out.println("----------------------------------------------------------------------\n");
			}
		}
		
		outputResult(resultsBFS, "BFS Results.xlsx");
		outputResult(resultsDFS, "DFS Results.xlsx");
	}
	
	public static void checkAndPrintPath(LinkedList<City> shortestRoute, Result newResult)
	{
		if(shortestRoute == null)
		{
			System.out.println("no path found");
		}
		else
		{
			newResult.foundPath();
			
			for(City city: shortestRoute)
			{
				System.out.print("->" + city);
				
			}
		}
		System.out.println("\nSearch time: " + newResult.getSearchTime() + "ns\n");
	}
	
	/**
	 * This method output results to an Excel file.
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
		XSSFCell graphSizeHeaderCell = headerRow.createCell(0);
		graphSizeHeaderCell.setCellValue("Graph Size");
		
		// header for number of non-stop flights
		XSSFCell graphNumOfNonStopFlightsHeaderCell = headerRow.createCell(1);
		graphNumOfNonStopFlightsHeaderCell.setCellValue("Num Of Non-Stop Flights");
		
		// header for running time
		XSSFCell searchTimeHeaderCell = headerRow.createCell(2);
		searchTimeHeaderCell.setCellValue("Search Time");
		// end of header
		
		for(int i = 0; i < results.size(); ++i)
		{
			Result outputResult = results.get(i);
			
			XSSFRow newRow = resultSheet.createRow(i+1);
			
			// number of cities
			XSSFCell graphSizeCell = newRow.createCell(0);
			graphSizeCell.setCellValue(outputResult.getGraphSize());
			
			// number of non-stop flights
			XSSFCell graphNumOfNonStopFlightsCell = newRow.createCell(1);
			graphNumOfNonStopFlightsCell.setCellValue(outputResult.getNumOfNonStopFlights());
			
			// running time
			XSSFCell searchTimeCell = newRow.createCell(2);
			searchTimeCell.setCellValue(outputResult.getSearchTime());
		}
		
		for(int i = 0; i < 3; ++i)
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
