/*
 we know A must be the lowest prime
 Z must be the highest prime
 *Try to find the highest prime first to get upperbound

 */
import java.util.*;
public class Cryptopangrams{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int numOfTest= in.nextInt();

		for(int t = 0; t < numOfTest; t++){
			long n = in.nextLong();
			int l = in.nextInt();
			long[] arr = new long[l];

			for(int i = 0; i < l; i++)
				arr[i] = in.nextLong();
		}
	}

	
}
