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

public class Main
{
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

		


	}//end main()

	public static void print2dArray(int[][] matrix){
		//print array given
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				System.out.printf("%4d",matrix[i][j]);
			}
			System.out.println();
		}
	}

}//end class Main
