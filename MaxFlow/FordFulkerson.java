import java.util.*;
import java.lang.*;
import java.io.*;
public class FordFulkerson{
	static int numOfVertices = 0;
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		numOfVertices = in.nextInt();
		int numOfEdges = in.nextInt(); 
		int[][] graph = new int[numOfVertices][numOfVertices];
		for(int i = 0; i < numOfEdges; i++){
			int u = in.nextInt()-1;
			int v = in.nextInt()-1;
			if(u == v)
				continue;
			int capacity = in.nextInt();
			graph[u][v] = capacity;
			graph[v][u] = capacity;
		}
		int maxFlow = FF(graph, 0, numOfVertices-1);
		System.out.println(maxFlow);
	}

	static boolean BFS(int[][] residualGraph, int S, int T, int parent[]){
		boolean[] visited = new boolean[numOfVertices];
		Deque<Integer> queue = new ArrayDeque<Integer>();
	
		for(int i = 0; i < numOfVertices; i++){
			visited[i] = false;
		}	

		queue.offer(S);
		visited[S] = true;
		parent[S] = -1;

		while(!queue.isEmpty()){
			int u = queue.poll();
			if(u == T)
				return true;
			for(int v = 0; v < numOfVertices; v++){
				// as long as we haven't visited adj and
				// edge(u,v) is not full
				if(!visited[v] && residualGraph[u][v] > 0){
					queue.offer(v);
					visited[v] = true;
					parent[v] = u;
				}
			}
		}

		return visited[T];
	}

	static int FF(int[][] graph, int S, int T){
		int u, v;
	
		int[][] residualGraph = new int[numOfVertices][numOfVertices];

		// fill the residualGraph with original flows
		for(u = 0; u < numOfVertices; u++){
			for(v = 0; v < numOfVertices; v++){
				residualGraph[u][v] = graph[u][v];
			}
		}
		// parent[v] : the parent of v 	
		int[] parent = new int[numOfVertices];

		int maxFlow = 0;

		// while there is a path from S to T
		while(BFS(residualGraph, S, T, parent)){
			int bottleneck  = Integer.MAX_VALUE;			
		
			// Find bottleneck flow
			for(v = T; v != S; v = parent[v]){
				u = parent[v];
				bottleneck = Math.min(bottleneck, residualGraph[u][v]);
			}
			// Augment the path from S to T
			// We start at T because we're building the residual graph in reverse 
			for(v = T; v != S; v = parent[v]){
				u = parent[v];
				residualGraph[u][v] -= bottleneck; // back edge
				residualGraph[v][u] += bottleneck; // forward edge
			}

			maxFlow += bottleneck;
		}	

		return maxFlow;
	}


}
