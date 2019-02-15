import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparable;

/*
BFS w/ a PriorityQueue
Single source shortest path for weighted graph
*/
public class Dijkstra{
    
    static class Node extends Comparable{
        static class Edge{
            Node to;
            Node from;
            int cost;

            public Edge(Node to, Node from, int cost){
                this.to = to;
                this.from = from;
                this.cost = cost;
            }

            int compareTo(Edge other){
                return this.cost-other.cost;
            }
        }

        int label;
        ArrayList<Edge> edges;

        public Node(int y){
            this.label label;
            edges = new ArrayList<Edge>();
        }

        void addEdge(Edge e){
            return this.dist-other.dist;
        }
    }

    static int dijkstra(int[][] graph, int[] start, int[] end){
        PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>();
    }

    static void makeGraph(int[][] graph){
            
    }
}
