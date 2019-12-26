import java.util.*;
public class ProblemC{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int precints = in.nextInt();
		int districts = in.nextInt();
		int[][] votes = new int[districts][2];	

		for(int i = 0; i < precints; i++){
			int district = in.nextInt()-1;
			int voteA = in.nextInt();
			int voteB = in.nextInt();

			votes[district][0] += voteA;
			votes[district][1] += voteB;
		}

		int wastedATotal = 0;
		int wastedBTotal = 0;
		double voteTotal = 0;
		for(int i = 0; i < districts; i++){
			int voteA = votes[i][0];
			int voteB = votes[i][1];
			voteTotal += voteA + voteB;
			int totalVotes = voteA + voteB;
			int winningVotes = Math.max(voteA, voteB);
			int losingVotes = Math.min(voteA, voteB);
			char winner = (voteA > voteB) ? 'A' : 'B';

			int majority = (totalVotes + 2) / 2;
			int wastedWinning = winningVotes - majority;
			int wasted = losingVotes + wastedWinning;

			int wastedA = (voteA < voteB) ? voteA : wastedWinning;
			int wastedB = (voteB < voteA) ? voteB : wastedWinning;
			wastedATotal += wastedA;
			wastedBTotal+= wastedB;

			System.out.println(winner + " " + wastedA + " " + wastedB);
		}
		
		double z = (double) Math.abs(wastedATotal - wastedBTotal) / voteTotal;
		System.out.println(z);
	}
}
