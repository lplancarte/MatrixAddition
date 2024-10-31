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
		{{2,3,1,2,5,1},{3,1,2,2,2,4},{1,2,3,2,7,2},{3,6,1,5,1,3}};

		int [][] red =
		{{6,5,4,1,4,3},{3,3,2,2,1,1},{7,5,4,3,2,5},{2,1,8,4,8,4}}

		System.out.println("Creating 4 ThreadOperation Objects");
		//Instantiate 4 ThreadOperation objects
		ThreadOperation upperRight = new ThreadOperation(blue,red,Quadrant.I);
		ThreadOperation upperLeft = new ThreadOperation(blue,red,Quadrant.II);
		ThreadOperation lowerRight = new ThreadOperation(blue,red,Quadrant.III);
		ThreadOperation lowerLeft = new ThreadOperation(blue,red,Quadrant.IV);
		//Start them
		//Join them


	}

}
