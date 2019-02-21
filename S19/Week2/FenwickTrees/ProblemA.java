import java.util.Scanner;
public class ProblemA{

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int length = in.nextInt();
		int queries = in.nextInt();
		Tree bit = new Tree(length);
		in.nextLine();

		for(int i = 0; i < queries; i++){
			String line = in.nextLine();
			String[] split = line.split(" ");
			char c = split[0].charAt(0);
			int a = Integer.parseInt(split[1]);

			if(c == '+'){
				int b = Integer.parseInt(split[2]);
				bit.add(a,b);		
			} 
			if(c == '?'){
				if(a == 0)
					System.out.println(0);
				else
					System.out.println(bit.getPartialSum(a-1));
			}
		}
	}
	
	static class Tree{
		private int[] tree;

		// initialize all elements to 0
		Tree(int size){
			this.tree = new int[size];		
		}

		// logn get sum of tree [0...i], inclusive
		int getPartialSum(int i){
			int res = 0;
			while(i >= 0 ){
				res += tree[i];
				i = (i & (i + 1)) - 1;
			}	

			return res;
		}

		// logn update tree[i] += val
		void add(int i, int val){
			while(i < tree.length){
				tree[i] += val;
				i |= (i+1);
			}
		}

		int getSingle(int i ){
			int sum = tree[i];
			if(i > 0){
				int z = (i & (i+1))-1;
				i--;
				while(i != z){
					sum -= tree[i];
					i = (i & (i+1)) -1;
				}
			}
			return sum;
		}

		int getRangeSum(int from, int to){
			return getPartialSum(to) - getPartialSum(from -1);
		}
	}	

}
