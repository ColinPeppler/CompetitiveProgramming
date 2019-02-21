import java.util.Scanner;
public class ProblemA{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int V = in.nextInt();
		int E = in.nextInt();

		// min: pair off each 2 vertices from V 
		int min = V - (E*2);
		// max: connect a single vert to other vertices
		int max = (V-(E+1));

		min = (min < 0)? 0 : min;
		max = (max < 0)? 0 : max;

		System.out.println(min + " " + max);

	}
}
