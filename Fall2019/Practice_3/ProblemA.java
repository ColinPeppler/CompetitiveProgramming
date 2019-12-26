import java.util.*;

public class ProblemA{
	static int capacity = 0;
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int times = in.nextInt();

		for(int t = 0; t < times; t++){
			int num = in.nextInt();
			capacity = in.nextInt();
			int[] arr = new int[10];
			for(int i = 0; i <10; i++){
				arr[i] = in.nextInt();
			}
					
			/*String ans = DFS(arr, 0, 0) ? "YES" : "NO";
			System.out.printf("%d %s\n", num, ans);*/
			
			int[][] DP = new int[capacity][10];
			Arrays.stream(DP).forEach(row -> {
				Arrays.fill(row, -1); }
			);

			String ans = DP_DFS(arr, 0, 0, DP) > 0 ? "YES" : "NO";
			System.out.printf("%d %s\n", num, ans);
		}
	}

	public static boolean DFS(int[] arr, int index, int weight){
		if(weight > capacity)
			return false;
		if(weight == capacity)
			return true;	
		if(index == arr.length)
			return false;
		return DFS(arr, index+1, weight+arr[index]) || DFS(arr, index+1, weight);
	}

	public static int DP_DFS(int[] arr, int index, int weight, int[][] DP){
		if(weight > capacity)
			return 0;
		if(weight == capacity)
			return 1;
		if(index == arr.length)
			return 0;
		if(DP[weight][index] != -1)
			return DP[weight][index];

		return DP[weight][index] = DP_DFS(arr, index+1, weight+arr[index], DP) +
			DP_DFS(arr, index+1, weight, DP);
	}
}
