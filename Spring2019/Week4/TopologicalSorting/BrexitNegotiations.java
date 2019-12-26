import java.util.*;

public class BrexitNegotiations{
	public static class Node{
		int index;
		List<Integer> adj;

		public Node(int n){
			this.index = n;
		}
	}
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int topics = in.nextInt();
		int[] indeg = new int[topics];
		List<List<Integer>> graph = new ArrayList<List<Integer>>();
		List<Integer> sorted = new ArrayList<Integer>();

		for(int i = 0; i < topics; i++){
			graph.add(new ArrayList<Integer>());
		}

		for(int i = 0; i < topics; i++){
			int topic = in.nextInt()-1;
			int required = in.nextInt()-1;	
			List<Integer> adj = graph.get(topic);

			for(int j = 0; j < required; j++){
				int reqTopic = in.nextInt()-1;
				adj.add(reqTopic);
			}
		}

		for(int i = 0; i < topics; i++){
			System.out.println(graph.get(i));
		}
	}
}
