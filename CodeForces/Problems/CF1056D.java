import java.util.*;
import java.io.*;

public class CF1056D{
    public static void main(String[] args){
        FastScanner in = new FastScanner();
        
        int numOfVertices = in.nextInt();
        List<List<Integer>> children = new ArrayList<List<Integer>>(); 
        
        // Populate children list with empty arraylists
        for(int i = 0; i < numOfVertices; i++){
            children.add(new ArrayList<Integer>());
        }

        // Populate children for each vertex
        for(int i = 1; i < numOfVertices; i++){
            int root = in.nextInt()-1; 
            children.get(root).add(i);  // edge(i,root)
        }

        int[] node = new int[numOfVertices]; 

        DFS(0, children, node);

        int[] numOfLeaves = new int[numOfVertices+1];

        // populate numOfLeaves w/ number of nodes that have nLeaves <= number of colors
        for(int node_nLeaves : node){
            numOfLeaves[node_nLeaves]++;
        }

        // For optimization, we'll use DP
        // this is better than a for loop incrementing numOfLeaves[i]
        for(int i = 1; i < numOfLeaves.length; i++){
            numOfLeaves[i] += numOfLeaves[i-1];
        }

        // Since the numOfLeaves[i] is already in increasing order we'll just
        // walk through numOfLeaves searching for values >= k
        // this works because the number of happy junctions
        // is the number of junctions with a number of leaves <=
        // the number of colors
        int k = 1;
        int i = 1;
        StringBuilder builder = new StringBuilder();
        while(i < numOfLeaves.length){
            if(numOfLeaves[i] >= k){
                builder.append(i + " ");
                k++;
            } else {
                i++;
            }
        }

        System.out.println(builder.toString());
        
    }

    public static int DFS(int vertex, List<List<Integer>> children, int[] node){
        for(int child : children.get(vertex)){
            node[vertex] += DFS(child, children, node);             
        }

        if(node[vertex] == 0)    // curently at a leaf
            return 1;
        return node[vertex];
    }

    public static class FastScanner{
		BufferedReader br;
		StringTokenizer st;

		public FastScanner(Reader in){
			br = new BufferedReader(in);
		}

		public FastScanner() { this(new InputStreamReader(System.in)); }

		String next(){
			while(st == null || !st.hasMoreElements()){
				try{
					st = new StringTokenizer(br.readLine());
				} catch (IOException e){
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() { return Integer.parseInt(next()); }
	}

}
