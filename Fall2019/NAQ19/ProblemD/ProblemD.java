import java.util.*;
public class ProblemD{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int x = in.nextInt();
		List<Integer> num = new ArrayList<Integer>();

		while(in.hasNextInt()){
			num.add(in.nextInt());
		}

		num.sort((a, b) -> a-b);

		int max = num.get(num.size()-1);

		boolean[] arr = new boolean[max+1];

		for(int n : num)
			arr[n] = true;

		boolean f = false;
		for (int i = 1; i < max+1; i++){
			if(!arr[i]){
				f = true;
				System.out.println(i);
			}
		}

		if(!f)
			System.out.println("good job");
	}
}
