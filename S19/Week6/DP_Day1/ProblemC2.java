import java.util.*;

public class ProblemC2{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int numOfSlices = in.nextInt();
		int totalSize = 0;
		int[] pieSizes = new int[numOfSlices];

		for(int i = 0; i < numOfSlices; i++){ 
			int size = in.nextInt();
			totalSize += size;
			pieSizes[i] = size;
	   	}

		int[] remSizes = new int[numOfSlices];		
		final int total = totalSize;

		for(int i = 0; i < numOfSlices; i++){ // calculate total of remaining pies
			totalSize -= pieSizes[i];	
			remSizes[i] = totalSize;
		}

		int[][] memo = new int[2][numOfSlices]; 	// memoization

		/*
			i=0: Bob
		   	i=1: Alice
			memo[i][j] = pie size for Bob given that i:{0,1} has the token for the jth pie			

			2D memo since we can use another dimension for different paths
			take pie or give pie
		 */
		memo[0][numOfSlices-1] = pieSizes[numOfSlices-1]; // bob eats pie
		memo[1][numOfSlices-1] = 0; 					// alice eats last pie

		for(int i = numOfSlices-2; i >= 0; i--){ 
			// Bob wants(max) the best
			memo[0][i] = Math.max(memo[0][i+1], memo[1][i+1]+pieSizes[i]);

			// Alice wants the best(max) or the worst(min) for Bob
			memo[1][i] = Math.min(memo[0][i+1], memo[1][i+1]+pieSizes[i]);

			// give| memo[0][i]: max pie size given Bob decides next
			// take| memo[1][i] + pieSizes[i]: bob eats and max pie size given Alice decides next
		}
	
		int bob = memo[0][0];
		int alice = total - bob;

		System.out.println(alice + " " + bob);
	}
}
