import java.util.*;

// Adjacency Matrix representation
public class SimpleDijkstra2{
	static int V;
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		V = in.nextInt();
		int E = in.nextInt();
		int S = in.nextInt();
		int T = in.nextInt();

		long[][] matrix = new long[V][V];
		long[] dist = new long[V];
		Arrays.fill(dist, Long.MAX_VALUE);
		boolean[] visited = new boolean[V];

		for(int i = 0; i < E; i++){
			int u = in.nextInt();
			int v = in.nextInt();
			long weight = in.nextLong();

			matrix[u][v] = weight;	
			matrix[v][u] = weight;	
		}

		dist[S] = 0;

		for(int i = 0; i < V; i++){
			int u = minDistance(dist, visited);
			visited[u] = true;
			
			for(int v = 0; v < V; v++){
				/* 1.) if v is not visited yet
				 * 2.) if there is an edge from u to v
				 * 3.) total weight of path from src to v is smaller than current value of dist[v]
				 */
				if(!visited[v] && matrix[u][v] != 0 && dist[u] != Long.MAX_VALUE && dist[u]+matrix[u][v] < dist[v])
					dist[v] = dist[u] + matrix[u][v];
			}
		}

		System.out.println(dist[T]);
	}

	// finds the index with the distance within the dist array
	static int minDistance(long dist[], boolean visited[]){
		long min = Long.MAX_VALUE;
		int minIndex = -1;

		for(int v = 0; v < V; v++){
			if(visited[v] == false && dist[v] <= min){
				min = dist[v];
				minIndex = v;
			}
		}

		return minIndex;
	}
}
