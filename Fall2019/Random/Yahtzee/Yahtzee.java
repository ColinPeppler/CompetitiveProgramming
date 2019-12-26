import java.util.*;
import java.util.stream.Collectors;

public class Yahtzee {
	static final int NUM_HANDS = 13;
	static final int NUM_CATEGORIES = 13;
	static final int NUM_DICE = 5;
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int[][] diceRolls = new int[NUM_HANDS][NUM_DICE];

		for (int i = 0; i < NUM_HANDS; i++){
			String[] roll = in.nextLine().split(" ");
			for (int j = 0; j < NUM_DICE; j++){
				diceRolls[i][j] = Integer.parseInt(roll[j]);
			}
		}

		/*
			Bellman Equation: 
			Best(Category, Hand) = max(score[hand][Category], Best(Category-1, Hand/hand)

			return Best(13, Hand)

			We avoid repeated computation, thus we'll use DP to save time
		*/

		int[][] scores = new int[NUM_HANDS][NUM_CATEGORIES];
		for (int i = 0; i < NUM_HANDS; i++) {
			scores[i][0] = sumOfX(diceRolls[i], 1); 		// sum of all ones
			scores[i][1] = sumOfX(diceRolls[i], 2);			// sum of all twos
			scores[i][2] = sumOfX(diceRolls[i], 3);			// sum of all threes
			scores[i][3] = sumOfX(diceRolls[i], 4);			// sum of all fours
			scores[i][4] = sumOfX(diceRolls[i], 5); 		// sum of all fives
			scores[i][5] = sumOfX(diceRolls[i], 6); 		// sum of all sixes
			scores[i][6] = sum(diceRolls[i]); 				// chance
			scores[i][7] = XOfAKind(diceRolls[i], 3); 		// three of a kind
			scores[i][8] = XOfAKind(diceRolls[i], 4); 		// four of a kind
			scores[i][9] = XOfAKind(diceRolls[i], 5);		// five of a kind
			scores[i][10] = shortStraight(diceRolls[i]); 	// short straight
			scores[i][11] = longStraight(diceRolls[i]); 	// long straight
			scores[i][12] = fullHouse(diceRolls[i]); 		// full house
		}

		final int TOTAL_SETS = 1 << 13;
		final int EMPTY_HAND = 0;
		int[] bestScores = new int[TOTAL_SETS];
		
		// Arrays.stream(scores).forEach(scoreRow -> {System.out.println(Arrays.toString(scoreRow));} );

		best_iterative(bestScores, scores, TOTAL_SETS);
		System.out.println(bestScores[TOTAL_SETS - 1]);

		/*best(NUM_CATEGORIES, EMPTY_HAND, bestScores, scores);
		System.out.println(bestScores[EMPTY_HAND]);*/
	}

	/*
	Bottom-Up approach
	For each category, we need to find the best hand. However, we don't want to
	greedily pick the best hand, so we'll find every subset but reduce our 
	computation thru DP. In this case, for every hand subset we'll calculate
	the next score for our next hand. We'll calculate the current category by 
	the  number of active bits in our current hand, then for each remaining
	hand we'll calculate the score if we were to use that hand for our current
	category. So our answer will be DP[all hands used].
	*/
	public static void best_iterative(int[] bestScores, int[][] scores, final int TOTAL_SETS){
		for (int hand = 0; hand < TOTAL_SETS; hand++){ 	// try every subset
			int category = Integer.bitCount(hand);
			
			for (int i = 0; i < NUM_HANDS; i++){
				boolean currentHandUsed = (hand & (1 << i)) > 0;
				if (currentHandUsed)
					continue;

				int newHand = hand | (1 << i);
				
				/* set the score for our newHand only if it's better 
				than our previous score calculation for our newHand */
				int currentScore = bestScores[hand] + scores[i][category];	
				if (category == 5 && currentScore >= 63)
					currentScore += 35;	
				bestScores[newHand] = Math.max(currentScore, bestScores[newHand]);
			}
		}
	}

	/**
	@param 	category: the category number
	@param		hand: a binary number representing our current hand
					  eg. {0,1,3} = 1011 (binary) = 11 (decimal)
	**/
	public static int best(int category, int hand, int[] bestScores, int[][] scores){
		if (category == 0) 	// finished
			return 0;
		
		if (bestScores[hand] != 0) 	// already computed so return
			return bestScores[hand];	
		
		int maxScore = 0;
		for (int i = 0; i < NUM_HANDS; i++){
			boolean currentHandUsed = (hand & (1 << i)) > 0;
			if (currentHandUsed)
				continue;

			int newHand = hand | (1 << i); 	// newHand = union(H, hands[i])
			
			int currentScore = scores[i][category-1] + 
				best(category-1, newHand, bestScores, scores);	// bellman equation

			maxScore = Math.max(currentScore, maxScore);
		}

		if (category == 6 && maxScore >= 63){
			maxScore += 35;
		}

		return bestScores[hand] = maxScore;
	}
	
	public static int sumOfX(int[] arr, int X){
		return Arrays.stream(arr).filter(elem -> elem == X).sum();
	}

	public static int sum(int[] arr){
		return Arrays.stream(arr).sum();
	}

	public static int XOfAKind(int[] arr, int X){
		for (int elem : Arrays.stream(arr).distinct().toArray())
			if (Arrays.stream(arr).filter(e -> e == elem).count() >= X)
				return (X == 5) ? 50 : sum(arr);
		return 0;
	}

	public static int shortStraight(int[] arr){
		List<Integer> subset1 = Arrays.asList(1, 2, 3, 4);
		List<Integer> subset2 = Arrays.asList(2, 3, 4, 5);
		List<Integer> subset3 = Arrays.asList(3, 4, 5, 6);
		List<Integer> arrList = Arrays.stream(arr).boxed().collect(Collectors.toList());

		if (arrList.containsAll(subset1)
			|| arrList.containsAll(subset2)
			|| arrList.containsAll(subset3))
			return 25;
		return 0;
	}

	public static int longStraight(int[] arr){
		List<Integer> subset1 = Arrays.asList(1, 2, 3, 4, 5);
		List<Integer> subset2 = Arrays.asList(2, 3, 4, 5, 6);
		List<Integer> arrList = Arrays.stream(arr).boxed().collect(Collectors.toList());

		if (arrList.containsAll(subset1)
			|| arrList.containsAll(subset2))
			return 35;
		return 0;
	}

	public static int fullHouse(int[] arr){
		List<Integer> arrList = Arrays.stream(arr).boxed().collect(Collectors.toList());
		int[] freq = Arrays.stream(arr).distinct().map(elem -> 
			Collections.frequency(arrList, elem)).toArray();	
		if (Arrays.stream(freq).max().getAsInt() == 3
			 && Arrays.stream(freq).min().getAsInt() == 2)
			return 40;
		if (Arrays.stream(freq).max().getAsInt() == 5) 	// 1 1 1 1 1 can also be a full house
			return 40;
		return 0;
	}
}
