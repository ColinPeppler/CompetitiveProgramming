import java.io.*;
import java.util.*;
public class LongSwap{
	public static void main(String[] args){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		try{
			line = br.readLine();
		} catch (Exception e){
			System.out.println(e);
		}

		String str = line.substring(0, line.indexOf(" "));
		int k = Integer.parseInt(line.substring(line.indexOf(" ")+1));

		int[][] table = new int[str.length()][str.length()];
		for(int i = 0; i < str.length(); i++){
			for(int j = 0; j < str.length(); j++){
				if(Math.abs(i-j) < k)
					continue;
				table[i][j] = 1;
			}
		}

		ArrayList<Integer> list = new ArrayList<Integer>();

		for(int i = 0; i < str.length(); i++){
			boolean allZero = true;
			for(int j = 0; j < str.length(); j++){
				if(table[i][j] == 1)
					allZero = false;
			}
			if(allZero){
				list.add(i);
			}
		}
		char[] strArray = str.toCharArray();
		char[] sortedStr = str.toCharArray();
		Arrays.sort(sortedStr);

		boolean flag = true;

		for(int n : list){
			if(strArray[n] != sortedStr[n])
				flag = false;
		}

		if(flag)
			System.out.println("Yes");
		else
			System.out.println("No");

	}
}
