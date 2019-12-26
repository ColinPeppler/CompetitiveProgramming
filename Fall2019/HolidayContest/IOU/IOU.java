import java.util.*;

public class IOU {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();

		for (int i = 0; i < m; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			int c = in.nextInt();


		}
	}

	public class Edge {
		int u;
		int v;
		int c;

		public Edge(int u, int v, int c) {
			this.u = u;
			this.v = v;
			this.c = c;
		}
	}

public class TarjanCycleFinder
{
    List<Edge> [] adj;
    int V;
    boolean [] mark;
    Stack<Integer> marked;
    Stack<Integer> point;
    int ncircuits;

    TarjanCycleFinder(List<Integer> [] adj) {
        this.adj = adj;
        this.V = adj.length;
    }

    boolean backtrack(int v, int s) {
        boolean found = false;
        point.push(v);
        mark[v] = true;
        marked.push(v);
        Iterator<Edge> it = adj[v].iterator();
        while (it.hasNext()) {
            int w = it.next().u;
            if (w < s) {
                it.remove();
            } else
            if (w == s) {
                // System.out.println("Found cycle: " + point);
                ncircuits++;
                found = true;
            } else
            if (!mark[w]) {
                found = backtrack(w, s) || found;
            }
        }

        if (found) {
			// here the found the cycle
            while (marked.peek() != v) {
                mark[marked.pop()] = false;
            }
            mark[marked.pop()] = false;
        }
        point.pop();
        return found;
    }

    int findcircuits() {
        ncircuits = 0;
        mark = new boolean[V];
        marked = new Stack<Integer>();
        point = new Stack<Integer>();
        for (int s = 0; s < V; s++) {
            backtrack(s, s);
            while (!marked.isEmpty())
                mark[marked.pop()] = false;
        }
        return ncircuits;
    }
}
}
