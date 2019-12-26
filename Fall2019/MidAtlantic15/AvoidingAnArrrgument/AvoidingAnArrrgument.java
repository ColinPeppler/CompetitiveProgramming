import java.util.*;

/*
Looks hard at first, but there is an insight which makes this problem easier.

Insight: We are assuming the pirate are choosing the best gem they can. This is
the greedy assumption. Therefore, we'll only care to look at the first value
of the first gem we pick, and the last value of the second gem we pick. We can
do this because the last value for any gem can never be picked by the remaining
pirates since there is N+1 values and only N pirates. Although the pirates can
choose twice, they are forbidden from choosing the same gem. Additionally, we're
looking at the last value for all gems because the output only wants the first
gem, and let's say we picked a gem where the last value isn't optimal.

eg. let's have two gems A and B and 2 other gems with last values, x1 and x2
A + x1 < B + x1 is going to be equivalent to A + x2 < B + x2

Which leads to the question "Why not just look at the 1st gem and pick the 
greatest?" Well, there may be a situation where choosing the greatest gem value 
for you 1st gem is not the most optimal solution.

gemValA1 - greatest gem value
gemValB1 - 2nd greatest gem value
gemValA2 - last value of gem type A
gemValB2 - last value of gem type B
gemValB1 + gemValA2 > gemValA1 + gemValB2
*/
public class AvoidingAnArrrgument {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		while (true) {
			final int NUM_GEMSTONES = in.nextInt();
			final int NUM_CREWMEN = in.nextInt();

			if (NUM_GEMSTONES == 0) break;
			Map<Character, int[]> gemValue = new HashMap(NUM_GEMSTONES);
			
			for (int i = 0; i < NUM_GEMSTONES; i++) {
				char gem = in.next().charAt(0);
				int[] arr = new int[NUM_CREWMEN + 1];

				for (int j = 0; j < NUM_CREWMEN + 1; j++) {
					arr[j] = in.nextInt();
				}

				gemValue.put(gem, arr);
			}

			int max = Integer.MIN_VALUE;
			char maxGem = 'A';

			for (Map.Entry<Character, int[]> e1 : gemValue.entrySet()) {
				char gem1 = e1.getKey();
				int gemValue1 = e1.getValue()[0];

				for (Map.Entry<Character, int[]> e2 : gemValue.entrySet()) {
					char gem2 = e2.getKey();
					int gemValue2 = e2.getValue()[NUM_CREWMEN];
					int combinedGemValue = gemValue1 + gemValue2;					

					if (gem1 == gem2) {
						continue;
					}

					if (combinedGemValue > max) {
						max = combinedGemValue; 	
						maxGem = gem1;
					}
				 	else if (combinedGemValue == max && gem1 < maxGem) {
						maxGem = gem1;
					}
				}
			}

			System.out.println(maxGem);
		}
	}
}
