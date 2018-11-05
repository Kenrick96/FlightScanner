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
	// Stephen's file path: C:\Users\steph\Documents\New folder\FlightScanner\src\Lab4\World Cities.csv
	
	public static final String CITIES_FILE_PATH =  "C:\\Users\\jason\\Documents\\NTU\\Academic\\1 SCSE\\"
			+ "Year_2_Semester_1\\CZ2001 Algorithms\\3 Labs\\Lab 4\\FlightScanner\\src\\Lab4\\World Cities.csv";
	
//	public static final String SOURCE_CITY = "Kirovohrad";
//	public static final String DESTINATION_CITY = "Kiliya";
	
	public static final int MIN_GRAPH_SIZE = 100;
	public static final int MAX_GRAPH_SIZE = 1000;
	public static final int GRAPH_SIZE_INCREMENT = 100;
	
	public static final double MIN_GRAPH_DENSITY = 0.2;
	public static final double MAX_GRAPH_DENSITY = 1;
	public static final double GRAPH_DENSITY_INCREMENT = 0.2;
	
	public static final int NUM_OF_ITERATIONS = 50;
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
				Result[] newResultBFS = new Result[NUM_OF_ITERATIONS];
				for(int i=0;i<NUM_OF_ITERATIONS;i++) {
				newResultBFS[i] = new Result(graph.size(),graph.density(),graph.numOfNonStopFlights());
				LinkedList<City> shortestRouteBFS = searcher.bfsShortestRoute(graph.getCity(graphSize-1), 
														graph.getCity(0), newResultBFS[i]);
//				checkAndPrintPath(shortestRouteBFS,newResultBFS[i]);
				graph.resetCitiesVisited();}
				sort(newResultBFS,NUM_OF_ITERATIONS);
				Result F1Result = new Result(graph.size(),graph.density(),graph.numOfNonStopFlights());
				F1Result.setSearchTime(retInterquartile(newResultBFS,NUM_OF_ITERATIONS));
				resultsBFS.add(F1Result);
				
				// end of BFS
				
				// start of DFS
				System.out.println("Using DFS:");
				Result[] newResultDFS = new Result[NUM_OF_ITERATIONS];
				for(int i=0;i<NUM_OF_ITERATIONS;i++) {
				newResultDFS[i] = new Result(graph.size(),graph.density(),graph.numOfNonStopFlights());
				LinkedList<City> shortestRouteDFS = searcher.dfsShortestRoute(graph.getCity(graphSize-1), 
														graph.getCity(0), newResultDFS[i]);
//				checkAndPrintPath(shortestRouteDFS,newResultDFS[i]);
				graph.resetCitiesVisited();}
				sort(newResultDFS,NUM_OF_ITERATIONS);
				Result F2Result = new Result(graph.size(),graph.density(),graph.numOfNonStopFlights());
				F2Result.setSearchTime(retInterquartile(newResultDFS,NUM_OF_ITERATIONS));
				resultsDFS.add(F2Result);
				
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
	public static void sort(Result[] results,int noOfIterations)
	{
		for(int i=0;i<noOfIterations;i++)
			for(int j=i+1;j<noOfIterations;j++)
			if(results[i].getSearchTime()<results[j].getSearchTime()){
				Result temp = results[i];
				results[i]=results[j];
				results[j]= temp;
			}
	}
	public static long retInterquartile(Result[] results,int noOfIterations)
	{long sum=0;
	
		for(int i=noOfIterations/4;i<3*noOfIterations/4;i++)
			sum+=results[i].getSearchTime();
		return sum/(noOfIterations/2);
	}
}
