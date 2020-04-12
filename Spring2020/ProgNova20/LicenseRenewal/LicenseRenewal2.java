import java.util.*;

public class LicenseRenewal2 {
	public static int max = -1;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int NUM_CUSTOMERS = in.nextInt();
		long counterTime1 = in.nextLong();	
		long counterTime2 = in.nextLong();	

		long[] customerTimes = new long[NUM_CUSTOMERS];
		for (int i = 0; i < NUM_CUSTOMERS; i++) {
			customerTimes[i] = in.nextLong();
		}

		rec(counterTime1, counterTime2, 0, customerTimes);

		System.out.println(max+1);
	}

	public static void rec(long counterTime1, long counterTime2, int index, 
								long[] customerTimes) {
		if (counterTime1 < 0 || counterTime2 < 0 || index == customerTimes.length) {
			return;
		}

		long currCustomerTime = customerTimes[index];
		
		if (counterTime1 < currCustomerTime && counterTime2 < currCustomerTime) {
			return;
		}

		rec(counterTime1 - currCustomerTime, counterTime2, index+1, customerTimes);
		rec(counterTime1, counterTime2 - currCustomerTime, index+1, customerTimes);

		// Putting goal case here fixed the problem where it would automatically
		// set max = index = 0 without any decremt from counterTimes
		if (counterTime1 >= 0 && counterTime2 >= 0) {
			max = Math.max(max, index);	
		}
	}
}
