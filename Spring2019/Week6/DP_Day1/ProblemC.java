import java.util.*;

// A lot of computation is saved since we memoize the calculations
// This is a version of 0-1 knapsack problem
// The extra part was thinking about how the size score will be calculated
// which is eat: L[0] + total(rem(L)) - score((rem(L))
// don't eat: score(rem(L))
public class ProblemC{
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

		int[] memo = new int[numOfSlices];
		Arrays.fill(memo, Integer.MIN_VALUE);

		int bob = topdown(memo, pieSizes, remSizes, 0);
		int alice = total - bob;

		System.out.println(alice + " " + bob);
	}

	public static int topdown(int[] memo, int[] pieSizes, int[] remSizes, int index){
		if(index == pieSizes.length-1)
			return pieSizes[index];

		// check if previously calculated
		if(memo[index] != Integer.MIN_VALUE) 
			return memo[index];

		// 0-1 knapsack, either take or give
		int take = pieSizes[index] + remSizes[index] - topdown(memo, pieSizes, remSizes, index+1); // eat pie
		int give = topdown(memo, pieSizes, remSizes, index+1); // don't eat pie	
		
		// store calculation
		memo[index] = Math.max(take,give);  // max(take,give) is playing optimally

		return memo[index];
	}
}
