import java.util.*;
public class RADutyScheduler {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);	

		final int NUM_RAS = in.nextInt();
		final int NUM_DAYS = in.nextInt();
		Map<String, List<Integer>> map = new HashMap<>();

		in.nextLine();

		for (int i = 0; i < NUM_RAS; i++) {
			String line = in.nextLine();
			String[] arr = line.split(" ");
			String name = arr[0];

			List<Integer> workingDays = new ArrayList<Integer>();
			for (int j = 2; j < arr.length; j++) {
				workingDays.add(Integer.parseInt(arr[j]));
			}

			map.put(name, workingDays);
		}

		for (int currDay = 1; currDay <= NUM_DAYS; currDay++) {
			MaxFlowSolver dinic = new Dinic();

			Node src = dinic.addNode();
			Node sink = dinic.addNode();

			Node[] dayNodes = new Node[NUM_DAYS+1];
			Map<Node, String> nodeToNameMap = new HashMap<>();
			// might need to map node to RA

			for (int i = 1; i <= NUM_DAYS; i++) {
				dayNodes[i] = dinic.addNode();

				dinic.link(dayNodes[i], sink, 2);	
			}

			for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
				String name = entry.getKey();
				Node raNode = dinic.addNode();
				
				dinic.link(src, raNode, currDay);
				nodeToNameMap.put(raNode, name);

				List<Integer> workDays = entry.getValue();
				for (int day : workDays) {
					Node dayNode = dayNodes[day];
					dinic.link(raNode, dayNode, 1);
				}
			}

			long maxFlow = dinic.getMaxFlow(src, sink);

			// Each day is assigned 2 RA's
			if (maxFlow == NUM_DAYS * 2) {
				System.out.println(currDay);
				
				for (int i = 1; i <= NUM_DAYS; i++) {
					Node dayNode = dayNodes[i];

					List<String> assigned = new ArrayList<>();
					for (Edge e : dayNode.edges) {
						if (!e.forward && e.flow == -1) {
							Node raNode = e.to;
							String name = nodeToNameMap.get(raNode);
							assigned.add(name);
						}
					}
					System.out.printf("Day %d: %s %s\n", i, assigned.get(0), assigned.get(1));
				}

				System.exit(0);
			}
		}
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
