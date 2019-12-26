import java.util.*;

public class Bumped {
	static final long INF = Long.MAX_VALUE;
	static Map<Integer, List<Edge>> edgeMap = new HashMap<>();

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		final int NUM_CITIES = in.nextInt();
		final int NUM_ROADS = in.nextInt();
		final int NUM_FLIGHTS = in.nextInt();
		final int START = in.nextInt();
		final int FINISH = in.nextInt();
		
		
		for(int i = 0; i < NUM_CITIES; i++){
			edgeMap.put(i, new ArrayList<Edge>());
		}

		for (int i = 0; i < NUM_ROADS; i++) {
			int cityA = in.nextInt();
			int cityB = in.nextInt();
			int cost = in.nextInt();

			// Add an undirected edge between A & B 
			edgeMap.get(cityA).add(new Edge(cityB, cost));
			edgeMap.get(cityB).add(new Edge(cityA, cost));
		}

		for (int i = 0; i < NUM_FLIGHTS; i++) {
			int fromCity = in.nextInt();
			int toCity = in.nextInt();
			int cost = 0;

			edgeMap.get(fromCity).add(new Edge(toCity, cost));
		}

		State startState = new State(START, false, 0);
		State goalState = new State(FINISH, true, 0);
		long ans = dijkstras(startState, goalState);

		System.out.println(ans);
	}


	/**
	 * Lazy implementation of Dijkstra.
	 *
	 * The frontier queue contains Entry objects which
	 * represent a pair (cost, node).
	 */
	static class Edge {
		public int to;
		public long cost;
		public Edge(int i, long d) { cost = d; to = i; }
	}

	static class State /* DO NOT implement Comparable */ {
		// Note: the code below assumes that the cost of a path can be
		// expressed using a single long.  If the problem instead
		// asks for some kind of tiebreaker among paths of equal length,
		// a separate, comparable DistanceType would need to be used.
		final long dist;     // cost of path
		boolean ticketUsed;
		int city;

		// any other information about this state.  Usually, this is
		// information such as where it ends, e.g. location.
		public State(int city, boolean ticketUsed, long dist) {
			this.city = city;
			this.ticketUsed = ticketUsed;
			this.dist = dist;
		}

		@Override
		public int hashCode() {
			return (101 * city + 2 * (ticketUsed ? 1 : 0));
		}
		@Override
		public boolean equals(Object o) {
			State s = (State) o;
			return this.city == s.city && this.ticketUsed == s.ticketUsed;
		}
		
		/**
		 * Produce this state's successors. */
		List<State> adj() {
			List<State> adj = new ArrayList<State>();
			List<Edge> edgeList = edgeMap.get(city);

			for (Edge e : edgeList) {
				boolean isPlane = e.cost == 0;
				if (ticketUsed == true && isPlane) {
					continue;
				}

				int toCity = e.to;
				State s = new State(toCity, isPlane, dist + e.cost);

				adj.add(s);
			}

			return adj;
		}

		boolean isDestination(State goalState) {
			return this.city == goalState.city;
		}
	}	

	static long dijkstras(State start, State goal) {
		// a custom comparator is required because we cannot make State 
		// Comparable based on the distance - doing so would create an object
		// whose comparator is inconsistent with equals, which break its use 
		// in HashMap in subtle ways, leading to unpredictable and wrong
		// behavior.
		PriorityQueue<State> pq = new PriorityQueue<State>(
			new Comparator<State>() {
				@Override
				public int compare(State a, State b) {
					return Long.compare(a.dist, b.dist);
				}
			});

		HashMap<State, Long> dist = new HashMap<>(); 

		pq.offer(start);
		dist.put(start, start.dist);

		while (!pq.isEmpty()) {
			State cur = pq.poll();

			if (cur.isDestination(goal))  // abort if target is reached
				return cur.dist;

			// avoid relaxation if a shorter path to 'cur' is pending in queue
			if (Long.compare(dist.get(cur), cur.dist) < 0)
				continue;

			for (State adj : cur.adj()) {
				Long bestSoFar = dist.get(adj);
				if (bestSoFar == null || Long.compare(adj.dist, bestSoFar) < 0) {
					pq.offer(adj);
					dist.put(adj, adj.dist);
				}
			}
		}
		// no path to target
		return -1;
	}
}
