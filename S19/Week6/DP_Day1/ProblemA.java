import java.util.*;

public class ProblemA{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int numOfIntegers = in.nextInt();

		int[] arr = new int[numOfIntegers];

		for(int i = 0; i < numOfIntegers; i++){
			arr[i] = in.nextInt();
		}

		int[] maxOddSum = new int[numOfIntegers];

		int maxOddHolder = Integer.MIN_VALUE;
		maxOddSum[0] = arr[0];
		for(int i = 1; i < numOfIntegers; i++){
			if(maxOddSum[i-1]%2 == 0){ // dp is even
				if(arr[i] % 2 == 1){ // curr is odd
					maxOddSum[i] = maxOddSum[i-1] + arr[i];
				} else { // curr is even
					if(maxOddSum[i] < 0)
						maxOddSum[i] = (arr[i] > maxOddSum[i-1]) ? arr[i] : maxOddSum[i];
					else
						maxOddSum[i] = (maxOddSum[i-1] + arr[i] > maxOddSum[i]) ? (maxOddSum[i-1] + arr[i]) : maxOddSum[i-1];
				}
			} else { // dp is odd 
				if(arr[i-1] < 0){ // curr is even
					maxOddSum[i] = maxOddSum[i-1] - arr[i-1] + arr[i];
				}
				else{ // curr is odd
					maxOddSum[i] = maxOddSum[i-1] + arr[i];
				}
			}
			System.out.println(i + " | " + arr[i] + " | " + maxOddSum[i]);
			maxOddHolder = setMaxOddHolder(maxOddHolder,maxOddSum[i]);
		}

		System.out.println(maxOddHolder);
	}

	public static int setMaxOddHolder(int maxOddHolder, int x){
		if(x%2 == 1 && x > maxOddHolder)
			return x;
		else
			return maxOddHolder;
	}
}
