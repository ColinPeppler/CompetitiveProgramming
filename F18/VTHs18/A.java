import java.util.*;
public class A{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int a = in.nextInt();
		int b = in.nextInt();
		int c = in.nextInt();
		int legsLeft = in.nextInt();

		boolean possible = false;
		int aNum = legsLeft/a;
		for(int i = 0; i <= aNum; i++){
			legsLeft -= i * a;
			int bNum = legsLeft/b;
			for(int j = 0; j <= bNum; j++){
				legsLeft -= j * b;
				int cNum = legsLeft/c;
				for(int k = 0; k <= cNum; k++){
					legsLeft -= k * c;
					if(legsLeft == 0){
						System.out.printf("%d %d %d \n", i, j, k);
						possible = true;
					}
					legsLeft += k * c;
				}
				legsLeft += j * b;
			}
			legsLeft += i * a;
		}

		if(!possible)
			System.out.println("impossible");


	/*	List<String> ans = new ArrayList<>();
		helper(ans, "", numLegs, 0, legsLeft);
		
		for(String s : ans){
			System.out.println(ans);
		}*/
	}

	/*public static void helper(List<String> ans, String str, int[] numLegs, int index, int legsLeft){
		if(legsLeft < 0 || index >= 3){
			return;
		}
		if(legsLeft == 0){
			ans.add(str);
			return;
		}

		int d = legsLeft/numLegs[index];
		for(int i = 0; i <= d; i++){
			str += String.valueOf(i);
			legsLeft -= (numLegs[index] * i);
			helper(ans, str, numLegs, index+1, legsLeft);
			legsLeft += (numLegs[index] * i);
			str = str.substring(0, str.length()-1);
		}
	}*/

	
}
