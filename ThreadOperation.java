/**
Programmer: Lucio Plancarte
Created: 31-OCT-2024
Description: extends Thread and performs submatrix addition
*/

/**
ThreadOperation constructor takes in two matrices and a string to indicate
quadrant. The result is stored in 2-D array C.
*/

enum Quadrant {
		I,
		II,
		III,
		IV
	};

public class ThreadOperation extends Thread{

	//Class Variables
	private int[][] A;
	private int[][] B;
	private int[][] C;

	private Quadrant q;
	//https://www.w3schools.com/java/java_enums.asp

	//Constructor
	public ThreadOperation(int[][] A, int[][] B, Quadrant q){
		this.A = A;
		this.B = B;
		this.q = q;
	}

	@Override
	public void run(){
		//TODO: Add quadrants of matrices A and B set to C
		this.C = new int[0][0];
	}

	public int[][] getResult(){
		return this.C;
	}
}
