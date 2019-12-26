/*
 Rearrange order of lydia's
 */
import java.util.Scanner;
public class Labyrinth{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int numTestCases = in.nextInt();

		for(int t = 0; t < numTestCases; t++){
				int n = in.nextInt();
				in.nextLine();
				char[] lydia = in.nextLine().toCharArray();
				StringBuilder path = new StringBuilder();

				for(int i = 0; i < lydia.length; i++){
					char ch = (lydia[i] == 'E') ? 'S' : 'E';
					path.append(ch);
				}

				System.out.println("Case #" + (t+1) + ": " + path.toString());
		}
	}
}
