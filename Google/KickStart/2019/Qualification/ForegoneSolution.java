/*
 	if n is even
		start a,b = n/2
	if n is odd
		start a,b = ceil(n/2), floor(n/2)

	if a/b or has a 4 in 10^i space -> a += 10^i, b -= 10^i
	? How to check if 4 in 10^i
		- string
	? Problem if a gets set to 4 with a carry
 */
import java.util.*;
public class ForegoneSolution{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int numTestCases = in.nextInt();

		for(int t = 0; t < numTestCases; t++){
			int n = in.nextInt();

			Random r = new Random();
			int a = n-1;
			int b = 1;

			// a or b has a 4 in it
			while((hasFour(a) || hasFour(b))){
				a -= 1;
				b += 1;
				// MISTAKE: changing a then calculating diff, calculate diff beforehand
			}

			System.out.println("Case #" + (t+1) + ": " + a + " " + b);
		}
	}

	static boolean hasFour(int x){
		String str = Integer.toString(x);
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) == '4'){
				return true;
			}
		}
		return false;
	}
}
