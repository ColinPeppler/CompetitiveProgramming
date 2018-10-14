import java.util.*;

public class ProblemK {
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		char c = in.next().charAt(0);
		String str = in.nextLine().trim();
		StringBuilder sb = new StringBuilder();

		if(c == 'E'){
			char ch = str.charAt(0);
			int freq = 1;
			for(int i = 1; i < str.length(); i++){
				if(str.charAt(i) != ch){
					sb.append(ch);
					sb.append(String.valueOf(freq));
					ch = str.charAt(i);
					freq = 0;
				} 
				freq++;
			}

			sb.append(ch);
			sb.append(String.valueOf(freq));

		}
		if(c == 'D'){
			for(int i = 0; i < str.length(); i+= 2){
				char ch = str.charAt(i);
				int freq = Integer.valueOf(str.charAt(i+1)-'0');
				for(int j = 0;  j < freq; j++){
					sb.append(ch);
				}

			}
		}

		System.out.println(sb.toString());
	}
}
