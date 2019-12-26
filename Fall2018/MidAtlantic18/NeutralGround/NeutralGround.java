import java.util.*;

/*
	Min-Cut algorithm to find the smallest total capacity to cut the kings
	into separate sets. A shares the same set as souce, where B shares the same
	set as the sink.

	LEARNINGS
		1.) node split for node constraints
		2.) set capacity = infinity if we shouldn't "cut" that node
		3.) if we want to create our edges, just create out->in links 
			instead of creating both in->out and out->in links for each node
		4.) we should just retrieve a Node using a Node[][] instead of
			a <Point,Node> since it's much easier to access an array.
*/
public class NeutralGround{
	static long INF = Long.MAX_VALUE;

	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		int width = input.nextInt();
		int height = input.nextInt();

		char[][] graph = new char[height][width];

		/* Parse Graph */
		input.nextLine();
		for (int i = 0; i < height; i++){
			String row = input.nextLine();
			for (int j = 0; j < width; j++){
				graph[i][j] = row.charAt(j);
			}
		}

		MaxFlowSolver maxFlowSolver = new Dinic(); 	// using Dinic's variation
		Node source = maxFlowSolver.addNode();	
		Node sink = maxFlowSolver.addNode();	

		/*
		INSIGHT:
		We are using node splitting since we have a node constraint in this
		case, our constraint is the number of soldiers at a position. Which is
		why we're storing the in and out nodes in a matrix. Each node will have
		an in node and an out node.
		*/
		Node[][] in = new Node[height][width];
		Node[][] out = new Node[height][width];

		/* Populate matrix with nodes */
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				in[i][j] = maxFlowSolver.addNode();
				out[i][j] = maxFlowSolver.addNode();
			}
		}

		/* North (-1,0), South (1,0), East (0,1), West (0,-1) */
		int[] verticalDirection = {-1, 1, 0, 0};
		int[] horizontalDirection = {0, 0, 1, -1};

		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				char currChar = graph[i][j];		
				
				/* Node split and union(A, source) or union(B, sink) */
				switch (currChar){
					case 'A': 	// link source to A nodes and node split
						maxFlowSolver.link(source, in[i][j], INF);
						maxFlowSolver.link(in[i][j], out[i][j], INF);
						// we use INF since our A nodes should share the same
						// set as the source nodes
						break;
					case 'B': 	// link B nodes to sink and node split
						maxFlowSolver.link(out[i][j], sink, INF);
						maxFlowSolver.link(in[i][j], out[i][j], INF);
						break;
					case '0': // impassible terrain 
						break; 
					default:
						int numSoldiers = Character.getNumericValue(currChar);
						maxFlowSolver.link(in[i][j], out[i][j], numSoldiers);	
				}

				/* create an out->in link for each adjacent node */
				for (int direction = 0; direction < 4; direction++){
					int vertical = i + verticalDirection[direction];
					int horizontal = j + horizontalDirection[direction];

					/* Bounds Check */
					if (vertical < 0 || horizontal < 0 || 
						vertical >= height || horizontal >= width)
						continue;

					// each node will create an edge outwards
					maxFlowSolver.link(out[i][j], in[vertical][horizontal], INF);	
					// again we use INF because we don't want to cut an edge
					// instead we want to be cutting the node which is why
					// we only use a non-infinite capacity on the node split
				}
			}
		}

		long minCost = maxFlowSolver.getMaxFlow(source, sink);
		System.out.println(minCost);
	}

	public static class Node {
        // thou shall not create nodes except through addNode()
        private Node() {
        }
        List<Edge> edges = new ArrayList<Edge>();
        int index;          // index in nodes array
        int dist;           // Dinic, HIPR and AhujaOrlin.
        int currentarc;     // AhujaOrlin, Dinic, HIPR
    }

    public static class Edge {
        boolean forward; // true: edge is in original graph
        Node from, to;   // nodes connected
        long flow;        // current flow
        final long capacity;
        Edge dual;      // reference to this edge's dual
        long cost;      // only used for MinCost.
        // thou shall not create edges except through link()
        protected Edge(Node s, Node d, long c, boolean f) {
            forward = f;
            from = s;
            to = d;
            capacity = c;
        }
        // remaining capacity()
        long remaining() {
            return capacity - flow;
        }
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
        public void link(Node n1, Node n2, long capacity) {
            link(n1, n2, capacity, 1);
        }

        /* Create an edge between nodes n1 and n2 and assign cost */
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

        /* Create a graph with n nodes. */
        protected MaxFlowSolver(int n) {
            for (int i = 0; i < n; i++)
                addNode();
        }

        public abstract long getMaxFlow(Node src, Node snk);

        /* Add a new node. */
        public Node addNode() {
            Node n = new Node();
            n.index = nodes.size();
            nodes.add(n);
            return n;
        }
    }

    public static class Dinic extends MaxFlowSolver {

        long BlockingFlow(Node src, Node snk) {
            int N = nodes.size();
            for (Node u : nodes) {
                u.dist = -1;
                u.currentarc = 0;
            }
            Node[] Q = new Node[N];
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

        public Dinic() {
            this(0);
        }

        public Dinic(int n) {
            super(n);
        }
    }
}
