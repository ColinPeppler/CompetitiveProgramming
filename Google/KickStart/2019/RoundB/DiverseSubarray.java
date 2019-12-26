import java.util.*;
/*
 max = max(max(S_ij+j+1, max(subset of S_ij)+j)
 */
public class DiverseSubarray{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int numTestCases = in.nextInt();
		for(int t = 0; t < numTestCases; t++){
			int numTrinkets = in.nextInt();
			int typeLimit = in.nextInt();
			int max = 0;
			List<Integer> trinkets = new ArrayList<Integer>();
			Map<Integer,Integer> bucket = new HashMap<Integer,Integer>();

			for(int i = 0; i < numTrinkets; i++)
				trinkets.add(in.nextInt());			

			int left = 0;
			int right = 0;
			int count = 0;
			while(right < numTrinkets){
				int rightTrinket = trinkets.get(right);
				bucket.put(rightTrinket, bucket.getOrDefault(rightTrinket,0)+1);
				count++;

				while(left <= right && bucket.get(rightTrinket) > typeLimit){ // move left pointer
					int leftTrinket = trinkets.get(left);
					bucket.put(leftTrinket, bucket.get(leftTrinket)-1);
					left++;
					count--;
				}
				
				max = Math.max(max, count);
				right++;
			}

			System.out.println("Case #" + (t+1) + ": " + max);
		}

	}
}
