import java.util.*;

public class ProblemA{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		while(in.hasNextLine()) {
			int numTeams = in.nextInt();
			int roomACapacity = in.nextInt();
			int roomBCapacity = in.nextInt();

			if (numTeams == 0 && roomACapacity == 0 && roomBCapacity == 0) 	// end input
				return;

			int[][] teams = new int[numTeams][3];

			for (int i = 0; i < numTeams; i++) {
				teams[i][0] = in.nextInt();    // num balloons team i needs
				teams[i][1] = in.nextInt();    // distance to room A
				teams[i][2] = in.nextInt();    // distance to room B
			}

			EdmondsKarp solver = new EdmondsKarp();
			Node src = solver.addNode();
			Node sink = solver.addNode();

			Node roomA = solver.addNode();
			Node roomB = solver.addNode();
			solver.link(roomA, sink, roomACapacity, 0);
			solver.link(roomB, sink, roomBCapacity, 0);

			Node[] teamNodes = new Node[numTeams];
			for (int i = 0; i < numTeams; i++) {
				int ballonsNeeded = teams[i][0];
				int distanceToA = teams[i][1];
				int distanceToB = teams[i][2];

				teamNodes[i] = solver.addNode();
				solver.link(src, teamNodes[i], ballonsNeeded, 0);
				solver.link(teamNodes[i], roomA, ballonsNeeded, distanceToA);
				solver.link(teamNodes[i], roomB, ballonsNeeded, distanceToB);
			}

			long[] ans = solver.getMinCostMaxFlow(src, sink);
			System.out.println(ans[1]);
		}
	}


	public static class Node {
		// thou shall not create nodes except through addNode()
		private Node() { }

		List<Edge> edges = new ArrayList<Edge>();
		int index;          // index in nodes array

	}

	public static class Edge
	{
		boolean forward; // true: edge is in original graph
		Node from, to;   // nodes connected
		long flow;        // current flow
		final long capacity;
		Edge dual;      // reference to this edge's dual
		long cost;      // only used for MinCost.
		protected Edge(Node s, Node d, long c, boolean f)
		{
			forward = f;
			from = s;
			to = d;
			capacity = c;
		}
		long remaining() { return capacity - flow; }
		void addFlow(long amount) {
			flow += amount;
			dual.flow -= amount;
		}
	}

	public static abstract class MaxFlowSolver {
		List<Node> nodes = new ArrayList<Node>();

		public void link(Node n1, Node n2, long capacity) {
			link(n1, n2, capacity, 1);
		}

		public void link(Node n1, Node n2, long capacity, long cost) {
			Edge e12 = new Edge(n1, n2, capacity, true);
			Edge e21 = new Edge(n2, n1, 0, false);
			e12.dual = e21;
			e21.dual = e12;
			n1.edges.add(e12);
			n2.edges.add(e21);
			e12.cost = cost;
			e21.cost = -cost;
		}
		void link(int n1, int n2, long capacity) {
			link(nodes.get(n1), nodes.get(n2), capacity);
		}
		protected MaxFlowSolver(int n) {
			for (int i = 0; i < n; i++)
				addNode();
		}
		protected MaxFlowSolver() {
			this(0);
		}

		public abstract long getMaxFlow(Node src, Node snk);
		public Node addNode() {
			Node n = new Node();
			n.index = nodes.size();
			nodes.add(n);
			return n;
		}
	}
	static abstract class MinCostMaxFlowSolver extends MaxFlowSolver {
		// returns [maxflow, mincost]
		abstract long [] getMinCostMaxFlow(Node src, Node snk);
		// unavoidable boiler plate
		MinCostMaxFlowSolver ()      { this(0); }
		MinCostMaxFlowSolver (int n) { super(n); }
	}

	static class EdmondsKarp extends MinCostMaxFlowSolver
	{
		EdmondsKarp ()      { this(0); }
		EdmondsKarp (int n) { super(n); }
		long minCost;

		@Override
		public long [] getMinCostMaxFlow(Node src, Node snk) {
			long maxflow = getMaxFlow(src, snk);
			return new long [] { maxflow, minCost };
		}
		static final long INF = Long.MAX_VALUE/4;

		@Override
		public long getMaxFlow(Node src, Node snk) {
			final int n = nodes.size();
			final int source = src.index;
			final int sink = snk.index;
			long flow = 0;
			long cost = 0;
			long[] potential = new long[n]; // allows Dijkstra to work with negative edge weights
			while (true) {
				Edge[] parent = new Edge[n]; // used to store an augmenting path
				long[] dist = new long[n]; // minimal cost to vertex
				Arrays.fill(dist, INF);
				dist[source] = 0;
				PriorityQueue<Item> que = new PriorityQueue<Item>();
				que.add(new Item(0, source));
				while (!que.isEmpty()) {
					Item item = que.poll();
					if (item.dist != dist[item.v])
						continue;

					for (Edge e : nodes.get(item.v).edges) {
						long temp = dist[item.v] + e.cost + potential[item.v] - potential[e.to.index];
						if (e.capacity > e.flow && dist[e.to.index] > temp) {
							dist[e.to.index] = temp;
							parent[e.to.index] = e;
							que.add(new Item(temp, e.to.index));
						}
					}
				}
				if (parent[sink] == null)
					break;
				for (int i = 0; i < n; i++)
					if (parent[i] != null)
						potential[i] += dist[i];
				long augFlow = Long.MAX_VALUE;
				for (int i = sink; i != source; i = parent[i].from.index)
					augFlow = Math.min(augFlow, parent[i].capacity - parent[i].flow);
				for (int i = sink; i != source; i = parent[i].from.index) {
					Edge e = parent[i];
					e.addFlow(augFlow);
					cost += augFlow * e.cost;
				}
				flow += augFlow;
			}

			minCost = cost;
			return flow;
		}

		static class Item implements Comparable<Item> {
			long dist;
			int v;

			public Item(long dist, int v) {
				this.dist = dist;
				this.v = v;
			}

			public int compareTo(Item that) {
				return Long.compare(this.dist, that.dist);
			}
		}
	}
}
