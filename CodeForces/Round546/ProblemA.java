import java.util.*;

public class ProblemA{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		List<Integer> endingPages = new ArrayList<Integer>();

		int chapters = in.nextInt();
		
		for(int i = 0; i < chapters; i++){
			int start = in.nextInt();
			int end = in.nextInt();

			endingPages.add(end);
		}

		int markedPage = in.nextInt();
		
		for(int i = 0; i < chapters; i++){
			if(markedPage <= endingPages.get(i)){
				System.out.println(endingPages.size()-i);	
				break;
			}
		}
	}
}
