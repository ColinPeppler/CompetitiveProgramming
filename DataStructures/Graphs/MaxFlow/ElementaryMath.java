import java.util.*;
import java.awt.Point;
import static java.lang.Math.*;
public class ElementaryMath{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int cases = in.nextInt();
		
		MaxFlowSolver dinic = new Dinic();

		Node source = dinic.addNode();
		Node sink = dinic.addNode();
		Map<Integer,Node> map = new HashMap<Integer,Node>();

		List<Point> pairs = new ArrayList<Point>();
		List<Node> linkWithSource = new ArrayList<Node>();
		for(int i = 0; i < cases; i++){
			int a = in.nextInt();
			int b = in.nextInt();
			pairs.add(new Point(a,b));

			Node current = dinic.addNode();
			linkWithSource.add(current);
			dinic.link(source, current, 1);	

			int subtract = a-b;
			int add = a+b;
			int multiply = a*b;
			if(!map.containsKey(subtract)){
				Node curr = dinic.addNode();
				map.put(subtract,curr);
				dinic.link(curr, sink, 1);
			}
			dinic.link(current, map.get(subtract),1); 
			if(!map.containsKey(add)){
				Node curr = dinic.addNode();
				map.put(add,curr);
				dinic.link(curr, sink, 1);
			}
			dinic.link(current, map.get(add),1); 
			if(!map.containsKey(multiply)){
				Node curr = dinic.addNode();
				map.put(multiply,curr);
				dinic.link(curr, sink, 1);
			}
			dinic.link(current, map.get(multiply),1); 
		}		
		
		long maxFlow = dinic.getMaxFlow(source, sink);
		if(maxFlow != cases)
			System.out.println("impossible");
		else
			System.out.println("possible");

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
        boolean active;     // PushRelabel, FordFulkerson
        long excess;        // PushRelabel
        int mindj;          // AhujaOrlin
        int currentarc;     // AhujaOrlin
        boolean blocked;    // Dinic
        long minCapacity;   // FordFulkerson, AhujaOrlin
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

    /**
     * Implements Ford Fulkerson.
     */
    static class FordFulkerson extends MaxFlowSolver
    {
        /* Create a graph with n nodes. */
        FordFulkerson ()      { this(0); }
        FordFulkerson (int n) { super(n); }

        @Override
        public long getMaxFlow(Node src, Node snk) {
            long total = 0;

            for (;;) {
                // find an augmenting path and record its edges in prev
                Edge [] prev = new Edge[nodes.size()];
                long addedFlow = findAugmentingPath(src, snk, prev);
                if (addedFlow == 0)  break;

                total += addedFlow;

                // Go back along the path, and for each edge, move the
                // added flow from the edge to its dual.
                for (Edge edge = prev[snk.index];
                     edge != null;
                     edge = prev[edge.dual.to.index])
                {
                    edge.addFlow(addedFlow);
                }
            }

            return total;
        }

        /**
         * Find an augmenting path and return its minimum capacity.
         * @return 0 if no augmenting path could be found.
         *
         * Compute minimum capacity as we go to save a second pass
         * along the path.
         */
        long findAugmentingPath(Node src, Node snk, Edge [] from) {
            Deque<Node> queue = new ArrayDeque<Node>();
            queue.offer(src);

            int N = nodes.size();
            src.minCapacity = Long.MAX_VALUE;
            for (Node u : nodes)
                u.active = false;
            src.active = true;

            while (queue.size() > 0) {
                Node node = queue.poll();
                if (node == snk)
                    return snk.minCapacity;

                for (Edge edge : node.edges) {
                    Node dest = edge.to;
                    if (edge.remaining() > 0 && !dest.active) {
                        dest.active = true;
                        from[dest.index] = edge;
                        dest.minCapacity = Math.min(node.minCapacity, edge.remaining());

                        // can bail as soon as we see sink.
                        if (dest == snk)
                            return snk.minCapacity;

                        queue.push(dest);
                    }
                }
            }

            return 0;   // no path.
        }
    }

    /**
     * Implements Ahuja/Orlin.
     *
     * Ahuja/Orlin describe this as an optimized variant of the original Edmonds-Karp
     * shortest augmenting path algorithm.
     *
     * Ahuja, R. K. and Orlin, J. B. (1991), Distance-directed augmenting path algorithms
     * for maximum flow and parametric maximum flow problems.
     * Naval Research Logistics, 38: 413–430.
     * doi:10.1002/1520-6750(199106)38:3<413::AID-NAV3220380310>3.0.CO;2-J
     * and parametric maximum flow problems.
     * This is algorithm DD1 in the paper.
     */
    static class AhujaOrlin extends MaxFlowSolver
    {
        /* Create a graph with n nodes. */
        AhujaOrlin ()      { this(0); }
        AhujaOrlin (int n) { super(n); }

        @Override
        public long getMaxFlow(Node src, Node snk) {
            /**
             * INITIALIZE. Perform a (reverse) breadth-first search of the residual network
             * starting from the sink node to compute distance labels d(i).
             */
            final int n = nodes.size();
            long totalFlow = 0;
            for (Node u : nodes) {
                u.dist = -1;
                u.mindj = n; // tracks min dist of children for relabeling
                u.currentarc = 0;   // current active arc
            }

            int [] count = new int[n+1]; // count[i] number of nodes u with u.dist == i
            count[0]++;                  // count is used to bail as soon as maxflow is found

            Node [] Q = new Node[n];  // (Q, head, tail) are used as BFS queue
            int head = 0, tail = 0;
            snk.dist = 0;
            Q[tail++] = snk;
            while (head < tail) {
                Node x = Q[head++];
                for (Edge e : x.edges) {
                    if (e.to.dist == -1) {
                        e.to.dist = e.from.dist + 1;
                        count[e.to.dist]++;
                        Q[tail++] = e.to;
                    }
                }
            }

            if (src.dist == -1)     // no flow if source is not reachable
                return 0;

            src.minCapacity = Long.MAX_VALUE;
            Edge [] pred = new Edge[n];         // current augmenting path
            Node i = src;       // i is the 'tip' of the augmenting path as in paper

            advance:
            while (src.dist < n) {  // If d(s) >= n, then STOP.
            /* If the residual network contains an admissible
             * arc (i, j), then set pred(j) := i
             * If j = t then go to AUGMENT; otherwise, replace i by j
             * and repeat ADVANCE(i). */
                boolean augment = false;

                for (; i.currentarc < i.edges.size(); i.currentarc++) {
                    Edge e = i.edges.get(i.currentarc);
                    if (e.remaining() == 0) {
                        continue;
                    }

                    Node j = e.to;
                    i.mindj = Math.min(i.mindj, j.dist + 1);

                /* an admissible edge (i, j) is one for which d(i) = d(j) + 1 */
                    if (i.dist == j.dist + 1) {
                        pred[j.index] = e;
                        j.minCapacity = Math.min(i.minCapacity, e.remaining());
                        if (j == snk) {
                            augment = true;
                            break;
                        } else {
                            i = j;
                            continue advance;
                        }
                    }
                }
                // either ran out of admissible edges, or reached snk and thus are ready to augment

                if (!augment) {
                /* RETREAT: Update d(i): = min{d(j) + 1: rij > 0 and (i, j) \in A(i)}.
                 * If d(s) >= n, then STOP.  Otherwise, if i = s then go to
                 * ADVANCE(i); else replace i by pred(i) and go to ADVANCE(i).
                 */

                    // check if maximum flow was already reached.  This is indicated
                    // if a distance level is missing (a 'gap')
                    if (--count[i.dist] == 0 && i.dist < src.dist)
                        break;
                    // TBD: i.dist describeds a min cut

                    i.dist = i.mindj;    // relabel
                    count[i.dist]++;

                    i.currentarc = 0;    // reset current arc on node (i)
                    i.mindj = n;

                    if (i != src)
                        i = pred[i.index].from;
                } else {
                /* AUGMENT. Let sigma: = min{ri: (i, j) \in P}. Augment sigma units
                 * of flow along P. Set i = s and go to ADVANCE(i).  */
                    long addedFlow = snk.minCapacity;
                    for (Edge edge = pred[snk.index]; edge != null; edge = pred[edge.dual.to.index])
                    {
                        edge.addFlow(addedFlow);
                    }
                    totalFlow += addedFlow;
                    i = src;            // start over at source
                }
            }
            return totalFlow;
        }
    }

    static abstract class MinCostMaxFlowSolver extends MaxFlowSolver {
        // returns [maxflow, mincost]
        abstract long [] getMinCostMaxFlow(Node src, Node snk);
        // unavoidable boiler plate
        MinCostMaxFlowSolver ()      { this(0); }
        MinCostMaxFlowSolver (int n) { super(n); }
    }

    /**
     * Implements Edmonds/Karp min-cost max-flow.
     *
     * This algorithm uses costs to find an mincost augmenting path.
     *
     * See Theoretical Improvements in Algorithmic Efficiency for
     * Network Flow Problems by Edmonds and Karp,
     * Journal of the Association for Computing Machinery,
     * Vol. 19, No. 2, Apri; 1972. pp. 248-264.
     */
    static class EdmondsKarp extends MinCostMaxFlowSolver
    {
        EdmondsKarp ()      { this(0); }
        /* Create a graph with n nodes. */
        EdmondsKarp (int n) { super(n); }

        long minCost;

        @Override
        public long [] getMinCostMaxFlow(Node src, Node snk) {
            long maxflow = getMaxFlow(src, snk);
            return new long [] { maxflow, minCost };
        }
        static final long INF = Long.MAX_VALUE/4;

        /**
         * Maximize the flow, and simultaneously minimize its cost.
         * Code taken from judge solution to Chicago 2013/Job Postings
         * http://serjudging.vanb.org/wp-content/uploads/jobpostings_artur.java
         */
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

                // Dijkstra on cost
                PriorityQueue<Item> que = new PriorityQueue<Item>();
                que.add(new Item(0, source));
                while (!que.isEmpty()) {
                    Item item = que.poll();
                    if (item.dist != dist[item.v])
                        continue;

                    for (Edge e : nodes.get(item.v).edges) {
                        long temp = dist[item.v] + e.cost + potential[item.v] - potential[e.to.index];
                        // if can push some flow, and new cost is cheaper than push
                        if (e.capacity > e.flow && dist[e.to.index] > temp) {
                            dist[e.to.index] = temp;
                            parent[e.to.index] = e;
                            que.add(new Item(temp, e.to.index));
                        }
                    }
                }

                // couldn't reach sink
                if (parent[sink] == null)
                    break;
                // update potentials for Dijkstra
                for (int i = 0; i < n; i++)
                    if (parent[i] != null)
                        potential[i] += dist[i];

                // maximum flow that can be pushed
                long augFlow = Long.MAX_VALUE;
                for (int i = sink; i != source; i = parent[i].from.index)
                    augFlow = Math.min(augFlow, parent[i].capacity - parent[i].flow);

                // push the flow
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

    /**
     * Dinic's algorithm, Shimon Even variant.
     */
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

