import java.util.*;
/*

  if r-l is odd then only one odd
  if r-l is even then only even
	

	to optimize we'll have to use some sort of range sum
 */
public class BuildingPalindromes{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int numOfTests = in.nextInt();
		
		for(int t = 0; t < numOfTests; t++){
			int len = in.nextInt();
			int numOfQuestions = in.nextInt();
			in.nextLine();
			String line = in.nextLine();
			int count = 0;
			int[][] letterFreq = new int[26][len+1];

			for(int i = 1; i < len+1; i++){
				char currCh = line.charAt(i-1);
				for(int c = 0; c < 26; c++)
					letterFreq[c][i] = letterFreq[c][i-1];
				letterFreq[currCh-'A'][i]++;
			}

			for(int q = 0; q < numOfQuestions; q++){
				int leftIndex = in.nextInt()-1;
				int rightIndex = in.nextInt();
				int numOfOddFreq = 0;
				boolean isEven = (rightIndex-leftIndex) % 2 == 0;
				boolean isPalindrome = true;

				if(isEven){
					for(int i = 0; i < 26; i++){
						int charFreq = letterFreq[i][rightIndex]-letterFreq[i][leftIndex];
						if(charFreq % 2 == 1){
							isPalindrome = false;
							break;
						}
					}
				} else {
					for(int i = 0; i < 26; i++){
						int charFreq = letterFreq[i][rightIndex]-letterFreq[i][leftIndex];
						if(charFreq % 2 == 1){
							if(++numOfOddFreq == 2){
								isPalindrome = false;
								break;
							}
						}
					}
				}

				if(isPalindrome)
					count++;
			}

			System.out.println("Case #" + (t+1) + ": " + count);
		}
	}
}
