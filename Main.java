/**
Programmer: Lucio Plancarte
Modified: 31-OCT-2024
Description: Use threads to split up matrix addition.
			Part 1 of 2: Read in numbers from file and fill in arrays
			Print arrays to terminal
*/

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
/**
A1: 
	I am currently working on a truck. Replacing a clutch and all
	associated parts; seals, gaskets, nuts and bolts. The steps that
	need to be taken include removing systems such as the exhaust and 
	transmission. Each one of these processes takes time and effort.
	As a single mechanic there is only so much that can be accomplished.
	I think of multi-threading as a way of assigning tasks to separate
	threads so that a process, especially a multi-step process, can
	be completed in a more efficient and hopefully faster way. 
	
	Splitting a process into smaller parts can allow for a user to continue
	working with a program without having to wait for different sub processes
	to complete before continuing. With the analogy above, while the 
	transmission is being removed, the starter can be removed concurrently.
	Perhaps a gasket can be replaced while a different process, such
	as the bolt-removal process is happening in the background.

	Working within oracle databases there were times that tables would need to
	be locked to prevent other users from potentially altering or modifying
	data while it was being analyzed. I believe this is an example of 
	potential pitfall of multi-threading where it is important to keep track 
	of potential concurrent processes on different threads within a multi-
	server system. This was handled by the oracle database server with 
	built in locks.These could be at the row, table or database level.

	I think multi-threading is advantageous within databases; scaling
	websites with user iteractions; and game development: where waiting
	on a single thread to finish might makes some games extra laggy.
	-----------------------------------------------------------------
	TLDR: Multi-threading breaks up a process into small manageable chunks
		these threads complete sub-processes and can potentially finish a task
		more efficiently and quickly. Human beings are horrible at 
		multi-tasking, machines are not. Analyzing data can be done quicker
		with multiple threads. Couple hundred rows of data are no big deal;
		a couple hundred thousand or million(s) and breaking up a task is more 
		efficient.
*/
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

import java.nio.file.Path; //used for file verification
import java.nio.file.Paths;
import java.io.FileNotFoundException;

public class Main
{
	//2-Dimensional Test Arrays
	private static int [][] blue = null;
	//	{{1,1,1,1,1,1},{2,2,2,2,2,2},{3,3,3,3,3,3},{4,4,4,4,4,4}};

	private static int [][] red = null;
	//	{{4,4,4,4,4,4},{3,3,3,3,3,3},{2,2,2,2,2,2},{1,1,1,1,1,1}};

	//2-D result array
	private static int [][]purple = null;
	//{{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};

	private static int ROW  = 0;
	private static int COL = 0;
	private static String inputFileDefault = "matrix1.txt";


	public static void main(String[] args)
	{

		//File Handling
		System.out.println("File Handling Start");
		File inputFile = checkFile(args);
		if(inputFile == null)
			return;
		else
			processFile(inputFile);

		System.out.println("File Handling End");

		System.out.printf("ROW: %d\nCOL: %d\n",ROW, COL);
		System.out.println("Printing array: blue");
		print2dArray(blue);

		System.out.println("\nPrinting array: red");
		print2dArray(red);

		//Instantiate 4 ThreadOperation objects
		System.out.println("Creating 4 ThreadOperation Objects");
		ThreadOperation upperRight = new ThreadOperation
									(blue,red,purple,Quadrant.I);
		ThreadOperation upperLeft = new ThreadOperation
									(blue,red,purple,Quadrant.II);
		ThreadOperation lowerRight = new ThreadOperation
									(blue,red,purple,Quadrant.III);
		ThreadOperation lowerLeft = new ThreadOperation
									(blue,red,purple,Quadrant.IV);
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

		//Print purple result array
		System.out.println("\nPrinting result array: purple");
		print2dArray(purple);

		

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
		//String line = "";
		//int count = 0;
		try{
			Scanner reader = new Scanner(inputFile);
			//line = reader.nextLine(); //get row and col from this line
			//String[] rowCol = line.split(" ");
			//ROW = Integer.parseInt(rowCol[0]);
			//COL = Integer.parseInt(rowCol[1]);

			//Feedback implementation
			ROW = reader.nextInt();
			COL = reader.nextInt();

			//Create Arrays red, blue, and purple,fill with 0's
			createArrays(ROW,COL);
			//Fill arrays blue and red with data from file
			fillArray(ROW,COL,blue,reader);
			fillArray(ROW,COL,red,reader);


			/*while(reader.hasNextLine()){
				line = reader.nextLine();

				//split row into col
				if(line.isEmpty()){continue;};
				String[] lineData = line.split(" ");

				for(int i = 0; i < COL; i++){
					if(count < ROW)
						blue[count][i] = Integer.parseInt(lineData[i]);
					else
						red[count -ROW][i] = Integer.parseInt(lineData[i]);
					//System.out.print(lineData[i]);
				}
				count++;
				//System.out.println();
			}*/ //end while
			//DO SOMETHING POSSIBLY
		}catch(Exception e){};

	}//end processFile()

	/**
	*fillArray() fills an array with values from a file using a Scanner.
	*@param - int row - Number of Rows to fill
	*@param - int col - Number of Columns to fill
	*@param - int[][] array - The double integer array to fill
	*@param - Scanner rdr - used to read in data from file
	*@VOID RETURN
	*/
	public static void fillArray(int row,int col,int[][] array,Scanner rdr){
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				array[i][j] = rdr.nextInt();
			}
		}


	}//end fillArray()


	/**		createArrays(int rows, int cols)
	@param int row - number of rows; found in text file
	@param int col - number of cols; found in text file
	*This method is a helper method to processFile. After ROW and COL
	*are read, three arrays are created and filled with 0's with 
	*appropriate number of rows and columns
	*/
	static private void createArrays(int rows, int cols){
		blue = new int[rows][cols];
		red = new int[rows][cols];
		purple = new int[rows][cols];
	}//end createArrays()


}//end class Main
