import java.util.*;

public class ProblemB{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);	

		int p = in.nextInt();
		int q = in.nextInt();
		int s = in.nextInt();
		boolean flag = false;
		Set<Integer> set = new HashSet<Integer>();
		if(p <= s && q <= s){
			if(p%q == 0 || q%p == 0){
				flag = true;
			}
			else if(p * q <= s){
				flag = true;
			}
			else if(s % q == 0 && s % p == 0){
				flag = true;
			}

			for(int i = p; i <= s; i+= p){
				set.add(i);
			}
			for(int i = q; i <= s; i+= q){
				if(set.contains(i)){
					flag = true;
					break;
				}
			}
		}

		if(flag)
			System.out.println("yes");
		else
			System.out.println("no");
	}
}
