import java.util.Scanner;
public class ProblemB{
	/*
		# of R - # of B is maximized 
		BBRB | [1:4]
		1212
            _________
		1 2 3 4 5 6 7 8 9 10
		B B R R B R R B R B | [3:7]
		1 2 1 0 1-1-2-1 0 1

		_________
		1 2 3 4 5 6 7 8
		B B R B B R R B | [1:5]
		1 2 1 2 3 2 1 2

	 */
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		char[] line = in.nextLine().toCharArray();
		Tree BIT = new Tree(line.length);
		for(int i = 1; i < line.length; i++){
			if(line[i-1] != line[i]){
				BIT.add(i, 1);		
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

		int getSingle(int i){
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
