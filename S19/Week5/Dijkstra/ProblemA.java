import java.util.*;

public class ProblemA{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		
		int n = in.nextInt(); // number of vertices
		int m = in.nextInt(); // number of edges	
		
		int[][] graph = new int[n][n]; // matrix representation of graph 
		boolean[] visited = new boolean[n]; // set

		for(int i = 0; i < n; i++){
			Arrays.fill(graph[i], Integer.MAX_VALUE); 	// assign as infinity
											// to avoid wrong mins
		}
	
		// populating graph	
		for(int i = 0; i < m; i++){
			int u = in.nextInt()-1;
			int v = in.nextInt()-1;
			int cost = in.nextInt();

			// undirected
			graph[u][v] = cost;
			graph[v][u] = cost;
		}	

		ArrayList<Integer> sol = new ArrayList<Integer>();
		// start at vertex 1
		int curr = 0;
		sol.add(1);	

		while(curr != n-1){
			visited[curr] = true; // visited
			int vertIndex = findArgmin(graph, visited, curr); // extract-min
			
			if(vertIndex == -1){
				System.out.println(vertIndex);
				break;
			}

			sol.add(vertIndex+1);
			curr = vertIndex; // onto next node
		}

		if(sol.size() > 1)
			for(int vertex : sol)
				System.out.print(vertex + " ");
	}

	public static int findArgmin(int[][] graph, boolean[] visited, int idx){
		int min = Integer.MAX_VALUE;
		int minIndex = -1;
		for(int i = 0; i < graph[idx].length; i++){
			if(graph[idx][i] < min && !visited[i]){ 	// if smaller than current min and hasn't been visited
				min = graph[idx][i];
				minIndex = i;
			}
		}

		return minIndex;
	}
}
