import java.util.*;

public class FlowFree {
						//up, down, east, west
	static int[] dirX = {-1, 1, 0, 0};
	static int[] dirY = {0, 0, -1, 1};
	static int NUM_COLORS;
	static List<List<int[]>> origins = new ArrayList(6);

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int[][] board = new int[4][4];

		for (int i = 0; i < 6; i++) {
			origins.add(new ArrayList());
		}

		for (int i = 0; i < 4; i++) {
			String line = in.nextLine();
			for (int j = 0; j < 4; j++) {
				char ch = line.charAt(j);
				int color = getNumFromChar(ch);
				board[i][j] = color;

				int[] pos = new int[2];
				pos[0] = i;
				pos[1] = j;
				origins.get(color).add(pos);
			}
		}

		NUM_COLORS = getNumColors(board);

		origins.get(NUM_COLORS + 1).add(new int[2]);
		//Arrays.stream(board).forEach(row -> {System.out.println(Arrays.toString(row));});

		int startingColor = 1;
		int startingColorX = origins.get(startingColor).get(0)[0];
		int startingColorY = origins.get(startingColor).get(0)[1];
		boolean solveable = backtrack(board, startingColor, startingColorX, startingColorY);

		System.out.println(solveable ? "solvable" : "not solvable");
	}

	public static boolean backtrack(int[][] board, int colorCount, int i, int j) {
		if (colorCount > NUM_COLORS) {
			return boardIsFilled(board);
		}

		for (int k = 0; k < 4; k++) {
			int x = i + dirX[k];
			int y = j + dirY[k];


			if (x < 0 || x > 3 || y < 0 || y > 3) {
				continue;
			}

			// we've reached the end point for this color
			if (origins.get(colorCount).get(1)[0] == x && origins.get(colorCount).get(1)[1] == y) {
				int nextColorX = origins.get(colorCount+1).get(0)[0];
				int nextColorY = origins.get(colorCount+1).get(0)[1];

				// let's try the next color
				if (backtrack(board, colorCount+1, nextColorX, nextColorY)) {
					return true;
				}
			} 
			// keep trying until we reached the end point for this color	
			else if (board[x][y] == 0) {
				board[x][y] = colorCount;

				if (backtrack(board, colorCount, x, y)) {
					return true;
				}

				board[x][y] = 0;
			}
		}

		return false;
	}

	public static int getNumFromChar(char ch) {
		switch (ch) {
			case 'W': return 0;
			case 'R': return 1;
			case 'G': return 2;
			case 'B': return 3;
			case 'Y': return 4;
		}

		return -1;
	}

	public static boolean boardIsFilled(int[][] board) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == 0) {
					return false;	
				}
			}
		}

		return true;
	}

	public static int getNumColors(int[][] board) {
		Set<Integer> set = new HashSet();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (!set.contains(board[i][j])) {
					set.add(board[i][j]);
				}
			}
		}

		return set.size() - 1;
	}
}
