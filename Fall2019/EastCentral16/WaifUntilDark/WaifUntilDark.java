import java.util.*;
import static java.lang.Math.*;

public class WaifUntilDark {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		final int NUM_CHILDREN = in.nextInt();
		final int NUM_TOYS = in.nextInt();
		final int NUM_CATEGORIES = in.nextInt();

		boolean[] visited = new boolean[NUM_TOYS];

		Dinic dinic = new Dinic(NUM_CHILDREN + NUM_TOYS + NUM_CATEGORIES + 2);

		Node src = dinic.addNode();
		Node sink = dinic.addNode();


		Node[] childrenNodes = new Node[NUM_CHILDREN];
		for (int i = 0; i < NUM_CHILDREN; i++) {
			childrenNodes[i] = dinic.addNode();
		}

		Node[] toyNodes = new Node[NUM_TOYS];
		for (int i = 0; i < NUM_TOYS; i++) {
			toyNodes[i] = dinic.addNode();
		}

		Node[] categoryNodes = new Node[NUM_CATEGORIES];
		for (int i = 0; i < NUM_CATEGORIES; i++) {
			categoryNodes[i] = dinic.addNode();
		}


		for (int child = 0; child < NUM_CHILDREN; child++) {
			int numOfPlayableToys = in.nextInt();
			Node currChild = childrenNodes[child];

			for (int i = 0; i < numOfPlayableToys; i++) {
				int toy = in.nextInt() - 1;
				Node currToy = toyNodes[toy];

				dinic.link(currChild, currToy, 1);
			}

			dinic.link(src, currChild, 1);
		}

		for (int category = 0; category < NUM_CATEGORIES; category++) { int numOfToysInCategory = in.nextInt();
			Node currCategory = categoryNodes[category];
			
			for (int i = 0; i < numOfToysInCategory; i++) {
				int toy = in.nextInt() - 1; 
				visited[toy] = true;
				Node currToy = toyNodes[toy];

				dinic.link(currToy, currCategory, 1);
			}

			int toyLimit = in.nextInt();
			dinic.link(currCategory, sink, toyLimit);
		}

		// TODO: all toys in the default category can be used
		//int numOfToys = Arrays.stream(toyCategories).filter(e -> e == -69).count();
		Node defaultCategory = dinic.addNode();
		for (int toy = 0; toy < NUM_TOYS; toy++) {
			if (visited[toy]) {
				continue;
			}

			Node currToy = toyNodes[toy];
			dinic.link(currToy, defaultCategory, 1);
		}
		dinic.link(defaultCategory, sink, 1000);

		long ans = dinic.getMaxFlow(src, sink);

		System.out.println(ans);
	}

	public static class Node {
		// thou shall not create nodes except through addNode()
		private Node() { }

		List<Edge> edges = new ArrayList<Edge>();
		int index;          // index in nodes array

		int dist;           // Dinic, HIPR and AhujaOrlin.
		int currentarc;     // AhujaOrlin, Dinic, HIPR
	}

	public static class Edge {
		boolean forward; // true: edge is in original graph
						 // false: edge is a backward edge
		Node from, to;   // nodes connected
		long flow;        // current flow
		final long capacity;
		Edge dual;      // reference to this edge's dual
		double cost;

		// thou shall not create edges except through link()
		protected Edge(Node s, Node d, long c, boolean f)
		{
			forward = f;
			from = s;
			to = d;
			capacity = c;
		}

		// remaining capacity()
		long remaining() { return capacity - flow; }

		// increase flow and adjust dual
		void addFlow(long amount) {
			flow += amount;
			dual.flow -= amount;
		}
	}

	/**
	 * Dinic's algorithm, Shimon Even variant.
	 */
	public static class Dinic extends MaxFlowSolver
	{
		long BlockingFlow(Node src, Node snk) {
			int N = nodes.size();
			for (Node u : nodes) {
				u.dist = -1;
				u.currentarc = 0;
			}
			Node [] Q = new Node[N];

			/* Step 1.  BFS from source to compute levels */
			src.dist = 0;
			int head = 0, tail = 0;
			Q[tail++] = src;
			while (head < tail) {
				Node x = Q[head++];
				List<Edge> succ = x.edges;
				for (Edge e : succ) {
					if (e.to.dist == -1 && e.remaining() > 0) {
						e.to.dist = e.from.dist + 1;
						Q[tail++] = e.to;
					}
				}
			}

			if (snk.dist == -1)     // no flow if sink is not reachable
				return 0;

			/* Step 2. Push flow down until we have a blocking flow */
			long flow, totflow = 0;
			do {
				flow = dfs(src, snk, Long.MAX_VALUE);
				totflow += flow;
			} while (flow > 0);
			return totflow;
		}

		/*
		 * Run DFS on the BFS level graph.
		 */
		long dfs(Node v, Node snk, long mincap) {
			// reached sink
			if (v == snk)
				return mincap;

			// currentarc is an edge to the next child node that is not blocked
			for (; v.currentarc < v.edges.size(); v.currentarc++) {
				Edge e = v.edges.get(v.currentarc);
				if (e.from.dist + 1 == e.to.dist // it's in the BFS level graph
					&& e.remaining() > 0) {  // the edge has remaining capacity
					long flow = dfs(e.to, snk, min(mincap, e.remaining()));
					if (flow > 0) {
						e.addFlow(flow);
						return flow;
					}
				}
			}
			return 0;
		}

		@Override
		public long getMaxFlow(Node src, Node snk) {
			long flow, totflow = 0;
			while ((flow = BlockingFlow(src, snk)) != 0)
				totflow += flow;
			return totflow;
		}

		public Dinic () { this(0); }
		public Dinic (int n) { super(n); }
	}

	/* A Max Flow solver base class. */
	public static abstract class MaxFlowSolver {
		/* List of nodes, indexed. */
		List<Node> nodes = new ArrayList<Node>();

		/* Create an edge between nodes n1 and n2 */
		public void link(Node n1, Node n2, long capacity)
		{
			/*
			 * Only the EdmondsKarp solver takes cost into account
			 * during getMaxFlow().  Setting it to 1 for problems
			 * that do not involve cost means it uses a shortest path
			 * search when finding augmenting paths.  In practice,
			 * this does not seem to make a difference.
			 */
			link(n1, n2, capacity, 1);
		}

		/* Create an edge between nodes n1 and n2 and assign cost */
		public void link(Node n1, Node n2, long capacity, double cost)
		{
			Edge e12 = new Edge(n1, n2, capacity, true);
			Edge e21 = new Edge(n2, n1, 0, false);
			e12.dual = e21;
			e21.dual = e12;
			n1.edges.add(e12);
			n2.edges.add(e21);
			e12.cost = cost;
			e21.cost = -cost;
		}

		/* Create an edge between nodes n1 and n2 */
		void link(int n1, int n2, long capacity)
		{
			link(nodes.get(n1), nodes.get(n2), capacity);
		}

		/* Create an edge between nodes n1 and n2 */
		void link(int n1, int n2, long capacity, double cost)
		{
			link(nodes.get(n1), nodes.get(n2), capacity, cost);
		}

		/* Create a graph with n nodes. */
		protected MaxFlowSolver(int n) {
			for (int i = 0; i < n; i++)
				addNode();
		}

		protected MaxFlowSolver() { this(0); }

		public abstract long getMaxFlow(Node src, Node snk);

		/* Add a new node. */
		public Node addNode() {
			Node n = new Node();
			n.index = nodes.size();
			nodes.add(n);
			return n;
		}

		/* OPTIONAL: Returns the edges associated with the Min Cut.
		 * Must be run immediately after a getMaxFlow() call.
		 * */
		List<Edge> getMinCut(Node src) {
			Queue<Node> bfs = new ArrayDeque<Node>();
			Set<Node> visited = new HashSet<Node>();
			bfs.offer(src);
			visited.add(src);
			// find all nodes reachable from source in the residual network,
			// stopping at fully saturated edges (which are part of the min cut)
			while (!bfs.isEmpty()) {
				Node u = bfs.poll();
				for (Edge e : u.edges) {
					if (e.remaining() > 0 && !visited.contains(e.to)) {
						visited.add(e.to);
						bfs.offer(e.to);
					}
				}
			}
			// NB: visited is equal to the set S in cut ||S, T||, but this code
			// extracts the edges that are cut.
			List<Edge> minCut = new ArrayList<Edge>();
			for (Node s : visited) {
				for (Edge e : s.edges)
					if (e.forward && !visited.contains(e.to))
						minCut.add(e);
			}
			return minCut;
		}

		/* Compute the distance labels (.dist) of all nodes that
		 * can reach the sink in the actual graph (not residual graph)
		 * via backwards BFS.
		 * Assumes that v.dist == -1 for all nodes prior to calling this
		 * function.  Also populates an array 'count' that yields
		 * the number of nodes with a given distance label.
		 */
		void computeDistanceLabelsByReverseBFS(Node snk, int [] count) {
			final int n = nodes.size();
			count[0]++;

			Node [] Q = new Node[n];  // (Q, head, tail) are used as BFS queue
			int head = 0, tail = 0;
			snk.dist = 0;
			Q[tail++] = snk;
			while (head < tail) {
				Node x = Q[head++];
				for (Edge e : x.edges) {
					if (e.to.dist == -1) {  // not yet explored
						e.to.dist = e.from.dist + 1;
						count[e.to.dist]++;
						Q[tail++] = e.to;
					}
				}
			}
		}
	}
}
