import java.util.*;

public class GraphRepresentation{
	static class Node{
		public int index;
		public int distance;

		public Node(int index, int distance){
			this.index = index;
			this.distance = distance;
		}
	}
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int V = in.nextInt();
		int E = in.nextInt();

		// Adjacency Matrix
		int[][] adjacencyMatrix = new int[V][V];
		
		// Adjacency List
		List<List<Node>> adjacencyList = new ArrayList<List<Node>>();
		for(int i = 0; i < V; i++){
			adjacencyList.add(new ArrayList<Node>());
		}

		
		for(int i = 0; i < E; i++){
			int from = in.nextInt();
			int to = in.nextInt();
			int cost = in.nextInt();

			adjacencyMatrix[from][to] = cost;
			adjacencyMatrix[to][from] = cost;

			adjacencyList.get(from).add(new Node(to, cost));
			adjacencyList.get(to).add(new Node(from, cost));
		}	

		System.out.println("Adjacency Matrix");
		for(int i = 0; i < V; i++){
			for(int j = 0; j < V; j++){
				int val = adjacencyMatrix[i][j];
				if(val == 0)
					continue;
				System.out.printf("%d == %2d ==> %d \n", i, val, j);
			}
		}

		System.out.println("\n Adjacency List");
		for(int i = 0; i < V; i++){
			for(Node adj : adjacencyList.get(i)){
				int j = adj.index;
				int val = adj.distance;
				System.out.printf("%d == %2d ==> %d \n", i, val, j);
			}
		}
	}
}
