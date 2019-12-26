import java.util.*;

// Colin Peppler
// VTHS Contest Problem
public class Areafill {
	public static void main(String[] args){
		
		// Create an input link from the file with the matrix
		Scanner in = new Scanner(System.in);
		
		// Get the matrix's number of row and columns
		int totalRow = in.nextInt();
		int totalCol = in.nextInt();
		
		in.nextLine(); // bumps the scanner to the next line
		
		char[][] matrix = new char[totalRow][totalCol];
		
		// Get the position of each entry in the matrix
		for(int row = 0; row < totalRow; row++) {
			String line = in.nextLine();
			for(int col = 0; col < totalCol; col++) {
				matrix[row][col] = line.charAt(col);
			}
		}

		int x = in.nextInt();
		int y = in.nextInt();
		
		in.close();

		// fills the area of the matrix at the target row and target column
		fillArea(x, y, matrix);
		
		// prints the updated matrix
		printMatrix(matrix);
	}
	
	public static void printMatrix(char[][] matrix) {
		for(int row = 0; row < matrix.length; row++) {
			for(int col = 0; col < matrix[0].length; col++) {
				System.out.print(matrix[row][col]);
			}
			System.out.println();
		}
	}
	
	public static void fillArea(int x, int y, char[][] matrix) {
		// bounds checking
		if(x == -1 || x == matrix.length || y == -1 || y == matrix[0].length || matrix[x][y] == 'X')
			return;
		
		// change the matrix at the selected row & column to X
		matrix[x][y] = 'X';
		
		// left 
			fillArea(x-1, y, matrix);
		
		// down 
			fillArea(x, y+1, matrix);
		
		// right 
			fillArea(x+1, y, matrix);
		
		// up 
			fillArea(x, y-1, matrix);
	}
}
