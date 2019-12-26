import java.util.*;

public class Training{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int numOfTestCases = in.nextInt();
		for(int t = 0; t < numOfTestCases; t++){
			int numOfStudents = in.nextInt();
			int p = in.nextInt();
			int[] skills = new int[numOfStudents];

			for(int i = 0; i < numOfStudents; i++){
				skills[i] = in.nextInt();
			}

			Arrays.sort(skills);
			int n = skills.length;

			PrefixTree tree = new PrefixTree(n);
			for(int i = 0; i < n; i++){
				tree.add(i, skills[i]);
			}

			long min = Integer.MAX_VALUE;
			for(int b = p-1; b < n; b++){
				int a = b-p+1;
				long e = (p-1) * skills[b];
				long rangeSum = tree.getRangeSum(a,b-1);	
				//System.out.println(skills[b+p-1] + " | " + rangeSum + "("+b+","+(b+p)+")");
				min = Math.min(min, e - rangeSum);
			}

			System.out.println("Case #"+(t+1)+": " + min);
		}
	}

	static class PrefixTree{
		private long[] tree;

		PrefixTree(int size){
			this.tree = new long[size];
		}

		long getPartialSum(int i){
			long res = 0;
			while(i >= 0){
				res += tree[i];
				i = (i & (i +1)) - 1;
			}
			return res;
		}

		void add(int i, int val){
			while(i < tree.length){
				tree[i] += val;
				i |= (i+1);
			}
		}

		long getRangeSum(int from, int to){
			return getPartialSum(to) - getPartialSum(from-1);
		}
	}
}
