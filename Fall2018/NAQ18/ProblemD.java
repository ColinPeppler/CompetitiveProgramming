import java.util.*;

public class ProblemD{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int numOfLanes  = in.nextInt();
		int width = in.nextInt();
		int[][] laneSpecs = new int[numOfLanes][3];
		int[][] carPositions = new int[numOfLanes][width];

		for(int lane = 0; lane < numOfLanes; lane++){
			int offset = in.nextInt();
			int interval = in.nextInt();
			int speed = in.nextInt() * (int) Math.pow(-1, lane);

			if(lane % 2 == 1){
				offset = width-1-offset;
				while(offset - interval >= 0){
					offset -= interval;
				}
			}

			laneSpecs[lane][0] = offset;
			laneSpecs[lane][1] = interval;
			laneSpecs[lane][2] = speed;


			for(int pos = offset; pos < width; pos += interval){
				carPositions[lane][pos] = 1;
			}
		}
		
		// populate our matrix with true being a car in that location

		
		int currLane = numOfLanes;
		int currPos = in.nextInt();
	   	String movement = in.nextLine().trim();	

		boolean isSafe = false;	
		for(int i = 0; i < movement.length(); i++){
			char ch = movement.charAt(i);
			for(int j = 0; j < numOfLanes; j++){
				carPositions[j] = nextTick(laneSpecs[j], carPositions[j]);
			}
			switch(ch){
				case 'U':
					currLane--;
					break;
				case 'D':
					currLane++;
					break;
				case 'L':
					currPos--;
					break;
				case 'R':
					currPos++;
					break;
			}

			if(currLane != -1 && currLane != numOfLanes && carPositions[currLane][currPos] == 1){
				break;
			}
			if(currLane == -1)
			   isSafe = true;	
		}

		if(isSafe)
			System.out.println("safe");
		else
			System.out.println("squish");

	}

	public static int[] nextTick(int[] laneSpecs, int[] carPositions){
		int offset = laneSpecs[0];
		int interval = laneSpecs[1];
		int speed = laneSpecs[2];
		int width = carPositions.length;
		int newOffset = offset+speed;
		int[] newPositions = new int[width];

		while(newOffset < 0){
			newOffset += interval; 
		}
	
		while(newOffset - interval >= 0){
			newOffset -= interval;
		}
		
		for(int pos = newOffset; pos < width; pos += interval){
			newPositions[pos] = 1;
		}	

		/*System.out.println();
		System.out.println("___BEFORE___");
		for(int n : carPositions){
			System.out.print(n + " ");
		}

		laneSpecs[0] = newOffset;
		carPositions = newPositions;

		System.out.println();
		System.out.println("___AFTER___");
		for(int n : carPositions){
			System.out.print(n + " ");
		}
		System.out.println();
		System.out.println();*/

		laneSpecs[0] = newOffset;
		return newPositions;
	}
}
