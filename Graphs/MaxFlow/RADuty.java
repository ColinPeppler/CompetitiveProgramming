import java.util.*;
public class RADuty{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int RAs = in.nextInt();
		int days = in.nextInt();
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		// <RA, the days the RA can work>
		in.nextLine();

		// Parse each line
		for(int i = 0; i < RAs; i++){
			String line = in.nextLine();
			String[] array = line.split(" ");
			String name = array[0];

			List<Integer> workingDays = new ArrayList<Integer>();
			for(int j = 1; j < array.length; j++){
				workingDays.add(Integer.parseInt(array[j]));	
			}

			map.put(name, workingDays);
		}	

		in.close();

		// minimize the max num of days each RA work
		for(int minNumOfDays = 1; minNumOfDays <= days; minNumOfDays++){
			MaxFlowSolver dinic = new Dinic();

			Node S = dinic.addNode(); 	// source
			Node T = dinic.addNode(); 	// sink

			Map<Integer, Node> dayMap = new HashMap<Integer, Node>();
			Map<Node, String> raMap = new HashMap<Node, String>();
			for(Map.Entry<String,List<Integer>> entry : map.entrySet()){
				String name = entry.getKey();
				Node curr = dinic.addNode();

				raMap.put(curr, name);
				List<Integer> workingDays = entry.getValue();
				int numOfAvailableDays = workingDays.size();
				
				dinic.link(S, curr, minNumOfDays); 	// link sink with RA (capacity is the minimum number of days each ra can work)

				for(int i = 0; i < numOfAvailableDays; i++){
					int day = workingDays.get(i);
					if(!dayMap.containsKey(day)){
						Node dayNode = dinic.addNode();
						dayMap.put(day, dayNode);
						dinic.link(dayNode, T, 2); 			// link day to source (capacity 2)
					}
					dinic.link(curr, dayMap.get(day), 1); 	// link ra with day (capacity 1)
				}

			}

			long maxFlow = dinic.getMaxFlow(S, T);
		
			// each day has 2 RAs working	
			if(maxFlow == days*2){
				System.out.println(minNumOfDays);
				
				for(int day = 0; day < days; day++){
					System.out.printf("Day %d: ", day+1);
					Node dayNode = dayMap.get(day+1);
					
					for(Edge e : dayNode.edges){
						// edge(v,u) where u is the RA and v is the day node
						// as long as flow(v,u) == -1 (backward edge) then we know RA (u) is assigned to this day (v)
						if(e.from == dayNode && !e.forward && e.flow == -1){
							System.out.print(raMap.get(e.to) + " "); 		// e.to is the RA
						}
					}

					System.out.println();
				}		
				break;
			}
		}
	}
	    // NB: we added public to allow use for testing from outside maxflow.*
// --------------- CUT HERE -------------------
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
        int dist;           // PushRelabel, Dinic, and AhujaOrlin.
        boolean blocked;    // Dinic
    }

    public static class Edge
    {
        boolean forward; // true: edge is in original graph
        // false: edge is a backward edge
        Node from, to;   // nodes connected
        long flow;        // current flow
        final long capacity;
        Edge dual;      // reference to this edge's dual
        long cost;      // only used for MinCost.

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
        public void link(Node n1, Node n2, long capacity, long cost)
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
         * Must be run immediately after a getMaxFlow() call.  */
        List<Edge> getMinCut(Node src) {
            Queue<Node> bfs = new ArrayDeque<Node>();
            Set<Node> visited = new HashSet<Node>();
            bfs.offer(src);
            visited.add(src);
            while (!bfs.isEmpty()) {
                Node u = bfs.poll();
                for (Edge e : u.edges) {
                    if (e.remaining() > 0 && !visited.contains(e.to)) {
                        visited.add(e.to);
                        bfs.offer(e.to);
                    }
                }
            }
            List<Edge> minCut = new ArrayList<Edge>();
            for (Node s : visited) {
                for (Edge e : s.edges)
                    if (e.forward && !visited.contains(e.to))
                        minCut.add(e);
            }
            return minCut;
        }
    }

    public static class Dinic extends MaxFlowSolver
    {
        long BlockingFlow(Node src, Node snk) {
            int N = nodes.size();
            for (Node u : nodes) {
                u.dist = -1;
                u.blocked = false;
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

            for (Edge e : v.edges) {
                // standard DFS, but consider an edge only if
                if (!e.to.blocked    // the path to the sink is not already blocked
                        && e.from.dist + 1 == e.to.dist // it's in the BFS level graph
                        && e.remaining() > 0) {  // the edge has remaining capacity
                    long flow = dfs(e.to, snk, Math.min(mincap, e.remaining()));
                    if (flow > 0) {
                        e.addFlow(flow);
                        return flow;
                    }
                }
            }
            // if we couldn't add any flow then there is no point in ever going
            // past this node again.  Mark it blocked.
            v.blocked = true;
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
