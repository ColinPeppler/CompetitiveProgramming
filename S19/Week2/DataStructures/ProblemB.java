import java.util.*;

public class ProblemB{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		Deque<Integer> main = new ArrayDeque<Integer>();
		int pairs = in.nextInt();
		int steps = 0;	
		for(int i = 0; i < 2*pairs; i++){
			int sock = in.nextInt();

			if(!main.isEmpty() && sock == main.peek()){
				main.pop();
			} else {
				main.push(sock);
			} 
			steps++;	
		}	
		
		if(main.isEmpty())		
			System.out.println(steps);	
		else
			System.out.println("impossible");
	}
}
