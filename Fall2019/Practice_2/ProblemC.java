import java.util.*;
import java.awt.Point;

public class ProblemC {
    public static void main(String[] args) {

    }
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        Point[] points = new Point[n];
        List<Edge> edges = new ArrayList<Edge>();

        for (int i = 0; i < n; i++){
            int x = in.nextInt();
            int y = in.nextInt();
            points[i] = new Point(x, y);
        }

        for (int i = 0; i < n; i++){
            for (int j = i+1; j < n; j++){
                // calculate distance between 2 coordinates
                int y1 = points[i].y;
                int y2 = points[j].y;
                int x1 = points[i].x;
                int x2 = points[j].x;
                double distance = Math.sqrt(Math.pow(y2-y1, 2) + Math.pow(x2-x1, 2));

                edges.add(new Edge(i, j, distance));    // Create an edge between each node
            }
        }

        // Run Kruskal algorithm on graph to find Minimal Spanning Tree
        double dist = solve(n, edges.toArray(new Edge[edges.size()]));
        System.out.printf("%.2f \n", dist);     // used .2f to round to 2 decimals
    }

    static class Edge {
        int to, from;
        double cost;
        Edge(int to, int from, double cost) {
            this.to = to;
            this.from = from;
            this.cost = cost;
        }
    }

    // Kruskal Algorithm
    static double solve(int V, Edge[] edges) {
        Arrays.sort(edges,
                    (Edge e1, Edge e2) -> {         // lambda expression returns 1 if e1.cost > e2.cost
                        return (int) Math.signum(e1.cost - e2.cost);
                    });

        int set[] = new int[V];   // look up disjoint sets
        for (int i = 0; i < set.length; i++)
            set[i] = i;

        double costSum = 0;
        for (int i = 0; i < edges.length; i++){
            Edge edge = edges[i];
            if (set[edge.to] != set[edge.from]) {   // find(to) != find(from) (make sure to node and from node are not in the same set)
                costSum += edge.cost;               // add edge to MSP and add cost

                // union(to,from)   (merges the set of the FROM node & TO node)
                int toSet = set[edge.to];
                int fromSet = set[edge.from];
                for (int j = 0; j < set.length; j++){
                    if(set[j] == fromSet)   // find any node in the FROM set
                        set[j] = toSet;         // put the nodes into the TO set; adding part of union(to,from)
                }
            }
        }

        return costSum;
    }
}
