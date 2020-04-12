import java.util.*;

public class AlexAndBarb {
	static final int ALEX_WINS = -1; 
	static final int BARB_WINS = 1; 
	static final int ALEX_TURN = 0; 
	static final int BARB_TURN = 1; 
	static int M;
	static int N;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int NCARDS = in.nextInt();
		M = in.nextInt();
		N = in.nextInt();

		int[][] table = new int[2][NCARDS+1];
		int finalRes = rec(NCARDS, ALEX_TURN, table);
		if (finalRes == ALEX_WINS)
			System.out.println("Alex");
		else
			System.out.println("Barb");
	}

	public static int rec(int curr, int turn, int[][] table) {
		if (table[turn][curr] != 0)
			return table[turn][curr];

		if (curr < M) {
			if (turn == BARB_TURN)
				table[turn][curr] = ALEX_WINS;
			else
				table[turn][curr] = BARB_WINS;
			return table[turn][curr];
		}

		int winner = 0;
		int nextTurn = ALEX_TURN;
		if (turn == ALEX_TURN)
			nextTurn = BARB_TURN;

		for (int d = M; d < N+1; d++) {
			if ((curr-d) < 0)
				break;
			int res = rec(curr-d, nextTurn, table);
			
			if (turn == ALEX_TURN && res == ALEX_WINS)
				winner = ALEX_WINS;
			else if (turn == BARB_TURN && res == BARB_WINS)
				winner = BARB_WINS;
		}

		return table[turn][curr] = winner;
	}
}
