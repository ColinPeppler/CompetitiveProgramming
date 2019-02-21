import java.util.*;

public class ProblemA{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.nextLine();
		Map<String, Integer> counts = new TreeMap<String, Integer>();
		for(int i = 0; i < n; i++){
			String costume = in.nextLine();
			counts.put(costume, counts.getOrDefault(costume, 0)+1);
		}

		int min = Integer.MAX_VALUE;
		for(Map.Entry<String, Integer> e : counts.entrySet()){
			min = Math.min(e.getValue(), min);	
		}
		
		for(Map.Entry<String, Integer> e : counts.entrySet()){
			if(e.getValue() == min)
				System.out.println(e.getKey());
		}
	}
}
