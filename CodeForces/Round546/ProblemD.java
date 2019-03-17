import java.util.*;

/*
 Node
 	pupil#
	place

build graph with edge(p1,p2)
find nodes that connect with Natysa
after finish BFS
sort array with nodes that connect with natysa by place
 */
public class ProblemD{
	static class Node implements Comparable<Node>{
		int pupil;
		int place;
		List<Node> adj;

		public Node(int pupil, int place){
			this.pupil = pupil;
			this.place = place;
			adj = new ArrayList<Node>();
		}

		public int compareTo(Node other){
			return this.place-other.place;
		}	
	}	

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		int m = in.nextInt();
		int nastya = n-1;

		Set<Node> set = new HashSet<Node>();
		Map<Integer,Node> map = new HashMap<Integer,Node>(); // <pupil,node>


		for(int i = 0; i < n; i++){
			int p = in.nextInt();
			Node curr = new Node(p, i);
			map.put(p,curr);
			if(i == n-1)
				nastya = p;
		}	

		for(int i = 0; i < m; i++){
			int p1 = in.nextInt();
			int p2 = in.nextInt();

			Node n1 = map.get(p1);
			Node n2 = map.get(p2);

			n1.adj.add(n2);
			n2.adj.add(n1);
		}

		DFS(map.get(nastya), set);

		List<Node> list = new ArrayList<Node>(set);

		Collections.sort(list);
	
		System.out.println((n-1) + " | " + list.get(0).place);	
		System.out.println(n-1-list.get(0).place);
	}

	public static void DFS(Node curr, Set<Node> set){
		set.add(curr);			
		
		for(Node adjacent : curr.adj){
			if(!set.contains(adjacent))
				DFS(adjacent, set);
		}
	}
}
