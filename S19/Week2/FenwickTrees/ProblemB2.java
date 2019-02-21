import java.util.Scanner;
/*
	PLAN:
	Window
		+1 if B
		-1 if R
	
	keep moving right
		if smaller than smallest window
		move right but stop until window is @ min

	Doesn't work in cases like
	RBRRBB
 */
public class ProblemB2{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		char[] line = in.nextLine().toCharArray();

		int left = 0;
		int right = 0;	
		int max = Integer.MIN_VALUE;
		int[] pos = new int[2];
		int count = 0;
		while(right < line.length){	// move right pointer
			if(line[right] == 'B')
				count++;
			else
				count--;	
			if(Math.abs(count) > max){ // update
				max = Math.max(max, Math.abs(count));
				pos[0] = left+1;
				pos[1] = right+1;
				
				while(left < right){ // move left pointer
					// MISTAKE:
					// way too greedy eg. RBRR
					// left at 0 and right at 1
					// left moves to 1 and we lose that 1st R
					if(line[left] == 'B')
						count++;
					else
						count--;	
					if(Math.abs(count) < max){
						count = max;
						break;
					}
					max = Math.max(max, Math.abs(count));
					left++;
					pos[0] = left+1;
					pos[1] = right+1;
				}
			}
			right++;
		}
		System.out.println(pos[0] + " | " + pos[1]);
	}
}
