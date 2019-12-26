import java.util.*;
public class ProblemK{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		String str = in.nextLine();
		char[] arr = str.toCharArray();
		int len = str.length();
		Map<Character, Integer> map = new HashMap<>();
		int count = 0;

		label: for(int i = 0; i < len; i++){
			char a = arr[i];
			map.put(a, 1);
			for(int j = i+1; j < len; j++){
				char b = arr[j];
				map.put(b, map.getOrDefault(b, 0)+1);
				if(map.get(a) > 1){
					map.clear();
					continue label;
				}
				if(map.get(b) > 1)
					continue;
				count++;
			}
			map.clear();
		}

		System.out.println(count);
	}

	private static boolean isValid(Map<Character, Integer> map, char a, char b){
		return a != b && map.get(a) == 1 && map.get(b) == 1;
	}
}
