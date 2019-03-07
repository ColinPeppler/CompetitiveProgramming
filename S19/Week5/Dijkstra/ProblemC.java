import java.util.*;

public class ProblemC{
	static class Node implements Comparable<Node>{
		int row;
		int col;
		int dist;

		public Node(int r, int c, int d){
			row = r;
			col = c;
			dist = d;	
		}

		public int compareTo(Node other){ // for prique
			return this.dist-other.dist;
		}
	}
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int r = in.nextInt();
		int c = in.nextInt();
		in.nextLine();

		int[][] matrix = new int[r][c];
		List<Integer> list = new ArrayList<Integer>();

		for(int row = 0; row < r; row++){
			String line = in.nextLine();
			for(int col = 0; col < c; col++){
				char spot = line.charAt(col);
				int n = 1;
				if(spot == '#')
					n = -1;
				if(spot == 'D')
					n = 0;	
				
				matrix[row][col] = n;
			}
		}

		int y = in.nextInt()-1;
		int x = in.nextInt()-1;

		System.out.println(dijkstra(y, x, matrix));
	}

	// how dijkstra is used here
	// we need to remember the shortest path from any given car to src
	// to minimize computing we check if the current smallest distance to v, dist[v] < dist[u] + edge(u,v) 
	// in this case, our edge(u,v) = 1 v is car, 0 if v is a door
	// another problem specific condition is if we hit a wall we must continue
	// 
	// what I learned:
	// Initially, I thought dijkstra arrived to a solution by taking one shortest path
	// However, it takes the shortest path, but it also updates the distance of the adj nodes
	// This is done thru the use of a priorityqueue, which is a BFS
	//
	// Dijkstra is a BFS for a weight graph
	// DFS does preorder, inorder, postorder traversals
	//
	// improvement:
	// I should have used a char array, it's easier to check if something is a wall, door.
	// since I started by updating the parsed matrix, I ran into an issue to deciding
	// what value was a car, door, or wall
	//
	// what I liked:
	// using int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}} {(up),(down),(right),(left)}
	// writing psuedocode for dijkstra first
	//
	// Dijkstra(Graph,Weights,Source)
	// S <- 0
	// Q <- V[G]
	// d[s] = 0
	// while Q != 0
	// u <- extract-min(Q)
	// S <- union(S,u)
	// for each vertex v that exists in adj[u]
	// 	RELAX(u,v,w)
	
	public static int dijkstra(int y, int x, int[][] matrix) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

		// d[v] = infinity for all v that exists in G
        int[][] distances = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
        }

        distances[y][x] = 1;
        PriorityQueue<Node> queue = new PriorityQueue<>();
	
		// union(Q,src)	
		Node src = new Node(y, x, 1);
        queue.offer(src);

        while (!queue.isEmpty()) { 	// Q != {} : Q isn't an empty set
            Node curr = queue.poll(); // extract-min
            int row = curr.row;
            int col = curr.col;
            int dist = curr.dist;

            // goal
            if (row == 0 || col == 0 || row == matrix.length-1 || col == matrix[0].length- 1) {
				return dist;
			}

            // we've already found a shorter path to this node,
            // so we don't need to update it nor it's neighbors
            if (distances[row][col] < dist) 
				continue;

            for (int i = 0; i < 4; i++) { // go up, down, left, right
                int adjRow = row + directions[i][0];
                int adjCol = col + directions[i][1];

                if (adjRow >= 0 && adjCol >= 0 && adjRow < matrix.length && adjCol < matrix[0].length) { // bounds check
                    int n = matrix[adjRow][adjCol];
			
                    if (n == -1)  // hit a wall
						continue;

                    if ((dist+n) < distances[adjRow][adjCol]) { // relax(u,adj,matrix)
                        distances[adjRow][adjCol] = dist + n; 	// rho = d[curr] + w[curr,adj]
						Node adj = new Node(adjRow, adjCol, distances[adjRow][adjCol]);
						queue.offer(adj);
                    }
                }
            }
        }

        return -1;
    }
}
