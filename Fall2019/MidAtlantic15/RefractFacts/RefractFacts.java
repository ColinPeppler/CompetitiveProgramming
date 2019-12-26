import java.util.*;
import static java.lang.Math.*;

public class RefractFacts {
	static int d = 0;
	static int h = 0;
	static double n1 = 0;
	static double n2 = 0;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			d = in.nextInt();
            h = in.nextInt();
            int x = in.nextInt();
            n1 = in.nextDouble();
            n2 = in.nextDouble();	

			if (d == 0)
				return;

			double ans = binarySearch(0, PI/2, x);
			System.out.printf("%.2f%n", toDegrees(PI/2 - ans));
		}
	}

	public static double binarySearch(double lo, double hi, double target) {
		// use ulp when we get within some epsilon to avoid TLE
		while (abs(hi - lo) > 10 * ulp(hi)) {
			double mid = (hi + lo) / 2.0;
			double midVal = calculateAngle(mid); 

			// we need an '=' case or else it times out
			if (midVal < target) {
				lo = mid;
			}
			else {
				hi = mid;
			}
		}
		return (hi + lo) / 2.0;
	}

	// calculates horizontal distance using trig
	public static double calculateAngle(double theta1){
		double x2 = d * tan(theta1);
		double theta2 = asin(n2/n1 * sin(theta1));
		double x1 = h / tan(PI/2 - theta2);
		return (x1 + x2);
	}
}
