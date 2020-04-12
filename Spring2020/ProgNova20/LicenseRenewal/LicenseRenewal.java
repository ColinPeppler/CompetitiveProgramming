import java.util.*;

/*
Insight: You should serve as many customer in the front, which reminds me of
a greedy approach
*/
public class LicenseRenewal {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int NUM_CUSTOMERS = in.nextInt();
		long counterTime1 = in.nextLong();	
		long counterTime2 = in.nextLong();	

		long[] customerTimes = new long[NUM_CUSTOMERS];
		for (int i = 0; i < NUM_CUSTOMERS; i++) {
			customerTimes[i] = in.nextLong();
		}

		int lo = 0;
		int hi = NUM_CUSTOMERS - 1;

		int maxCustomersServed = 0;
		while (lo <= hi) {
			int mid = (lo + hi) >>> 1;
			MaxFlowSolver dinic = new Dinic();

			Node src = dinic.addNode();
			Node sink = dinic.addNode();

			Node counterNode1 = dinic.addNode();
			Node counterNode2 = dinic.addNode();

			dinic.link(counterNode1, sink, counterTime1);
			dinic.link(counterNode2, sink, counterTime2);

			
			long totalCustomerTime = 0;
			for (int i = 0; i <= mid; i++) {
				long customerTime = customerTimes[i];
				Node customerNode = dinic.addNode();

				dinic.link(src, customerNode, customerTime);
				if (customerTime <= counterTime1)
					dinic.link(customerNode, counterNode1, customerTime);
				if (customerTime <= counterTime2)
					dinic.link(customerNode, counterNode2, customerTime);

				totalCustomerTime += customerTime;
			}

			long maxFlow = dinic.getMaxFlow(src, sink);


			if (maxFlow == totalCustomerTime) {
				lo = mid + 1;
				maxCustomersServed = mid + 1;
			} else {
				hi = mid - 1;	
			}
		}

		System.out.println(maxCustomersServed);
	}

	public static class Node {
		// thou shall not create nodes except through addNode()
		private Node() { }

		List<Edge> edges = new ArrayList<Edge>();
		int index;          // index in nodes array

		// The following fields are needed only if the respective solvers
		// are being used. We include them here for improved spatial locality.
		// To avoid unnecessary memory consumption, be sure to remove
		// those fields that aren't used by the solver you are using
		//
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
					long flow = dfs(e.to, snk, Math.min(mincap, e.remaining()));
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
}

