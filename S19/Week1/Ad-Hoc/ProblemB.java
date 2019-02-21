import java.util.Scanner;
public class ProblemB{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int a = in.nextInt();
		int b = in.nextInt();
		int c = in.nextInt();
		int d = (a+b)/c;
		int e = (a+b)%c;
		/*
		 	a - numbr of bottles at start of day
			b = numbr of bottles found during the day
			c - cost of new soda

			d = a+b/c number of sodas he can buy
			e = d/c	
		 */
			
		int sum = 0;
		while(d > 0){
			sum += d;
			int newd = (d+e)/c;
			e = (d+e)%c;
			d = newd;
		}
		
		System.out.println(sum);	
	}
}
