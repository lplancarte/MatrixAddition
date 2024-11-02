/*
This code is provided to give you a
starting place. It should be modified.
No further imports are needed.
To earn full credit, you must also
answer the following question:

Q1: One of the goals of multi-threading
is to minimize the resource usage, such
as memory and processor cycles. In three
sentences, explain how multi-threaded
code accomplishes this goal. Consider
writing about blocking on I/O, multicore
machines, how sluggish humans are,
threads compared to processes, etcetera,
and connect these issues to
multi-threading.

*/
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

import java.nio.file.Path; //used for file verification
import java.nio.file.Paths;
import java.io.FileNotFoundException;

public class Main
{
	//2D Array that will hold result
	private static int [][]purple =
	{{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};

	private static int NUM_ROW  = 0;
	private static int NUM_COL = 0;
	private static String inputFileDefault = "matrix1.txt";


	public static void main(String[] args)
	{
		//2-Dimensional Test Arrays
		int [][] blue =
		{{1,1,1,1,1,1},{2,2,2,2,2,2},{3,3,3,3,3,3},{4,4,4,4,4,4}};

		int [][] red =
		{{4,4,4,4,4,4},{3,3,3,3,3,3},{2,2,2,2,2,2},{1,1,1,1,1,1}};


		System.out.println("Creating 4 ThreadOperation Objects");
		//Instantiate 4 ThreadOperation objects
		ThreadOperation upperRight = new ThreadOperation(blue,red,Quadrant.I);
		ThreadOperation upperLeft = new ThreadOperation(blue,red,Quadrant.II);
		ThreadOperation lowerRight = new ThreadOperation(blue,red,Quadrant.III);
		ThreadOperation lowerLeft = new ThreadOperation(blue,red,Quadrant.IV);
		//Start them
		upperRight.start();
		upperLeft.start();
		lowerRight.start();
		lowerLeft.start();
		//Join them
		try{
			upperRight.join();
			upperLeft.join();
			lowerRight.join();
			lowerLeft.join();

		}catch(InterruptedException e){
			System.out.println("INTERRUPTION HAS OCCURED");
		};

		//Print a test array
		System.out.println("Printing test array: blue");
		print2dArray(blue);

		System.out.println("\nPrinting test array: red");
		print2dArray(red);

		System.out.println("\nPrinting result array: purple");
		print2dArray(purple);

		//File Handling
		File inputFile = checkFile(args);
		if(inputFile == null)
			return;
		else
			processFile(inputFile);



	}//end main()

	public static void print2dArray(int[][] matrix){
		//print array given
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				System.out.printf("%4d",matrix[i][j]);
			}
			System.out.println();
		}
	}//end print2dArray


	/**		checkFile(String[] args)
	*Checks that the file to analyze exists. Default: 'matrix1.txt'
	*Has the capability of accepting user input for different txt files
	*if argument is detected in the terminal.
	*These custom txt files must have the same format as 'matrix1.txt'.
	*Header line with 2 numbers for row and columns
	*Number of lines = 2x rows with as many integers as columns
	@param args - Array of strings from the terminal used to search for file
				  can be empty
	@return inputFile - input file to be processed. Returns null for error.
	*/
	static private File checkFile(String[] args){
		File inputFile = null;
		Path inputFilePath =  null;
		try{
			if(args.length > 0){
				inputFilePath = Paths.get(args[0]);
				inputFile = new File(inputFilePath.toString());
				System.out.println("Checking "+inputFilePath.toString());
			}else{
				inputFile = new File(inputFileDefault); //default file
				System.out.println("No Arguments detected."+
 				"Analyzing default file: " + inputFile.getName());
			}
			//Check if the file exists
			if(!inputFile.canRead()){
				throw new FileNotFoundException("404: File not found."+
				"The file you are looking for, isn't here.\n");
			}
		}catch(Exception e){
			System.out.print("Something went wrong. Error "+
			e.getMessage()+"\nExiting.\n");
			return null;
		}
		return inputFile;
	}//end checkFile()

	/** 	processFile(File inputFile)
	@param inputFile -File to be processed; returned by checkFile()
	*It is assumed that this file has both:
	*A header line with two integers representing rows and columns
	*Dual matrices one on top of the other (2xrow)xcol
	*/
	static private void processFile(File inputFile){
		String line = "";
		try{
			Scanner reader = new Scanner(inputFile);
			line = reader.nextLine(); //get row and col from this line
			while(reader.hasNextLine()){
				line = reader.nextLine();

				//split row into col
				if(line.isEmpty()){continue;};
				String[] lineData = line.split(" ");

				for(int i = 0; i < lineData.length; i++){
					System.out.print(lineData[i]);
				}
				System.out.println();
			}
			//DO SOMETHING POSSIBLY
		}catch(Exception e){};

	}//end processFile()


	//public static int[][] matrixFromFile(int rows,int cols,Scanner 
	//fileReader){
	//	return
	//}

}//end class Main
