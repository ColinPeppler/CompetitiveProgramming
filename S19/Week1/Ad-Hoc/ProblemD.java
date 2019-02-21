import java.util.Scanner;
public class ProblemD{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int row = in.nextInt();
		int col = in.nextInt();

		char[] matrix = new char[row][col];

		for(int i = 0;i < row; i++){
			String line = in.nextLine();
			matrix[i] = line.toCharArray();
		}

			
	}

	public void backtrack(int[][] matrix, int x, int y){
						
	}
}
