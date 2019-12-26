import java.util.*;

/*
DP with 3 States
1. index of current position
2. current subarray number
3. whether we use nums[i] in our current subarray or not
*/
public class MaximumSubarrays {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();

		long[] nums = new long[n];
		for (int i = 0; i < n; i++){
			nums[i] = in.nextLong();
		}

		long[][][] best = new long[n][k+1][2];

		for (int i = 0; i < n; i++){
			for (int j = 0; j <= k+1; j++){
				Arrays.fill(best[i][j], -Long.MAX_VALUE);
			}
		}

	}
}
