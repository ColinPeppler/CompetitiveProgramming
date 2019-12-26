import java.util.*; 
public class TimaGoesToXentopia {
	static int NUM_NODES;
	static int NUM_EDGES;
	static int NUM_RED_WANTED;
	static int NUM_BLUE_WANTED;
	static List<List<Edge>> edgeList = new ArrayList();

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		NUM_NODES = in.nextInt();
		NUM_EDGES = in.nextInt();
		NUM_RED_WANTED = in.nextInt();
		NUM_BLUE_WANTED = in.nextInt();

		for (int i = 0; i < NUM_NODES; i++) {
			edgeList.add(new ArrayList<Edge>());
		}

		for (int i = 0; i < NUM_EDGES; i++) {
			int city1 = in.nextInt() - 1;
			int city2 = in.nextInt() - 1;
			long cost = in.nextLong();
			int color = in.nextInt();

			Edge edgeFromCity1 = new Edge(city2, cost, color);
			Edge edgeFromCity2 = new Edge(city1, cost, color);
			edgeList.get(city1).add(edgeFromCity1);
			edgeList.get(city2).add(edgeFromCity2);
		}

		int start = in.nextInt() - 1;
		int goal = in.nextInt() - 1;

		State startState = new State(start, 0, 0, 0);
		State goalState = new State(goal, NUM_RED_WANTED, NUM_BLUE_WANTED, 0);
		
		long ans = dijkstra(startState, goalState);			

		System.out.println(ans);
	}

	public static long dijkstra(State start, State goal) {
		Queue<State> pq = new PriorityQueue(); 
		Map<State, Long> distMap = new HashMap();

		pq.offer(start);
		distMap.put(start, start.dist);

		while (!pq.isEmpty()) {
			State currState = pq.poll();
			if (currState.isGoalState(goal)) {
				return currState.dist;
			}

			if (currState.dist > distMap.get(currState)) {
				continue;
			}

			for (State adj : currState.getAdj()) {
				long rho = adj.dist;

				if (!distMap.containsKey(adj) || distMap.get(adj) > rho) {
					distMap.put(adj, rho);
					pq.offer(adj);
				}
			}
		}

		return -1;
	}

	public static class Edge {
		int to;
		long cost;
		int color;
		
		public Edge (int to, long cost, int color) {
			this.to = to;
			this.cost = cost;
			this.color = color;	
		}
	}

	public static class State implements Comparable {
		int city;
		int reds;
		int blues;
		long dist;

		public State (int city, int reds, int blues, long dist) {
			this.city = city;
			this.reds = reds;
			this.blues = blues;
			this.dist = dist;	
		}

		public List<State> getAdj() {
			List<Edge> edges = edgeList.get(this.city);	
			List<State> adj = new ArrayList();	

			for (Edge e : edges) {
				int newRed = (e.color == 1) ? reds+1 : reds;
				int newBlue = (e.color == 2) ? blues+1 : blues;

				if (newRed > NUM_RED_WANTED || newBlue > NUM_BLUE_WANTED)
					continue;

				State s = new State(e.to, newRed, newBlue, dist + e.cost);			
				adj.add(s);
			}

			return adj;	
		}

		@Override
		public int compareTo(Object o) {
			State other = (State) o;
			return Long.compare(this.dist, other.dist);
		}

		@Override
		public int hashCode() {
			return (int) (city * 89 + reds * 71 + blues * 7);
		}

		public boolean isGoalState(State o) {
			return this.city == o.city && this.reds == o.reds &&
					this.blues == o.blues;
		}

		@Override
		public String toString() {
			return "" + city + " " + reds + " " + blues + " " +dist;
		}

		@Override
		public boolean equals(Object o) {
			State s = (State) o;
			return this.city == s.city && this.reds == s.reds &&
					this.blues == s.blues;
		}
	}
}
