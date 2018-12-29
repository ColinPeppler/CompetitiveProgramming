import java.util.*;

/*
 * We represent the graph with an adjacency lists because
 * it's easier to traverse through all the adjacent vertices.
 * While an adjacency matrix will require us to traverse through
 * all vertices making it O(|V|) instead of O(|E|)
 */

public class SimpleDijkstra{
	// Represents a Node class
	static class Entry implements Comparable {
		public int index;
		public long distance;

		public Entry(int i, long d){
			index = i;
			distance = d;
		}

		// Required to be inserted into a PriorityQueue
		public int compareTo(Object o){
			Entry e = (Entry)o;
			if(distance != e.distance)
				return (int) (distance - e.distance);
			return index - e.index;
		}
	}

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int E = in.nextInt();
		int S = in.nextInt();
		int T = in.nextInt();

		// Initializing the graph (Adj list hence ArrayList) 
		ArrayList<ArrayList<Entry>> graph = new ArrayList<>();
		for(int i = 0; i < N; i++){
			graph.add(new ArrayList<Entry>());
		}
		// Keeps track of the shortest distance to each node from S
		long[] distance = new long[N];
		Arrays.fill(distance, Long.MAX_VALUE);

		// Parsing
		for(int i = 0; i < E; i++){
			int from = in.nextInt();
			int to = in.nextInt();
			int cost = in.nextInt();

			graph.get(from).add(new Entry(to, cost));
			graph.get(to).add(new Entry(from, cost));
		}

		// Initialize PriorityQueue and poll start node
		PriorityQueue<Entry> queue = new PriorityQueue<Entry>();
		queue.offer(new Entry(S, 0));

		while(!queue.isEmpty()){
			Entry curr = queue.poll();
			// if target is found then we stop
			if(curr.index == T)
				break;
			// if distance isn't smaller than the shortest distance
			if(curr.distance > distance[curr.index])
				continue;
			for(Entry adj : graph.get(curr.index)){
				long rho = curr.distance + adj.distance;
				// if the shortest path from curr to adj is smaller than the shortest path from curr to adj
				if(rho < distance[adj.index]){
					queue.offer(new Entry(adj.index, rho));
					distance[adj.index] = rho;
				}
			}
		}		

		System.out.println(distance[T]);
	}
}
