import java.util.*;

public class ProblemA{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int testcases = in.nextInt();	

		for(int i = 0; i < testcases; i++){
			double d = in.nextInt();	
			double a = 0;
			double b = d;		
			// final double ERR = Math.pow(10, -6);
			final double ERR = .000001;
			int count = 0;

			while(a <= b && count != 60){
				double mid = (a+b)/2;

				if(Math.abs((mid+(d/mid)-d)) < ERR){
					a = mid;	
					break;				
				}
				if(mid+(d/mid) < d){ // a -> mid
					a = mid;
				}
				if(mid+(d/mid) > d){ // b -> mid
					b = mid;	
				}

				count++;
			}
			
			if(d < 4 && d > 0){
				System.out.println("N");
			} else {
				System.out.println("Y " + a + " " + (d/a));
			}

			/*
			 a + b = d
			 a * b = d

			 a = d-b | b = d-a
			 a = d/b | b = d/a

			 a + b = a + (d/a) = d
			 
			 (1) a = d - b	
			 (2) b * a = d
			 (3) b * (d - b) = d
			 (4) bd - b^2 = d
			 (5) -b^2 + bd - d = 0 
			 quadratic forumula:
			 (-b +- sqrt(b^2 - 4ac))/2a
			 sqrt(d^2 - 4d) d < 4 is not possible
			 */
		}
	}
}
