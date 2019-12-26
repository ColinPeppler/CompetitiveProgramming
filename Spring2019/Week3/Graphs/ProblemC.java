import java.util.*;

public class ProblemC{
	public static class Node implements Comparable<Node>{
		int index;
		List<Node> adj;

		public Node(int x){
			index = x;
			adj = new ArrayList<Node>();
		}

		public int compareTo(Node oth){
			return this.index-oth.index; 
		}

		public boolean equals(Node oth){
			return this.index == oth.index;
		}
	}
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);	
		
		int V = in.nextInt();
		int E = in.nextInt();

		Map<Integer,Node> map = new HashMap<Integer,Node>();

		for(int i = 0; i < E; i++){
			int a = in.nextInt();
			int b = in.nextInt();

			if(!map.containsKey(a)){
				map.put(a, new Node(a));	
			}	
			if(!map.containsKey(b)){
				map.put(b, new Node(b));	
			}	

			Node to = map.get(a);
			Node from = map.get(b);

			to.adj.add(from);
			from.adj.add(to);
		}

		for(Map.Entry<Integer,Node> e : map.entrySet()){
			Collections.sort(e.getValue().adj);
		}

		Node start = map.get(1);
		Node goal = map.get(V);
		String seq = "1";
		/*Map<Node,String> seqMap = new HashMap<Node,String>();
		Queue<Node> pq = new PriorityQueue<Node>();	
		seqMap.put(start, seq);
		pq.offer(start);
		while(!pq.isEmpty()){
			Node curr = pq.poll();
			String s = seqMap.get(curr);	
			if(curr.equals(goal)){
				break;
			}

			for(int k = 0; k < curr.adj.size(); k++){
				Node v = curr.adj.get(k);
				System.out.println(curr.index + " | " + v.index);
				pq.offer(v);
				seqMap.put(v, s + " " + v.index);		
				curr.adj.remove(k);
			}
		}*/
		ArrayList<Integer> ans = new ArrayList<Integer>();
		DFS(start,goal,ans);
		for(int n : ans){
			System.out.print(n + " ");
		}
	}	

	public static boolean DFS(Node curr, Node goal, ArrayList<Integer> list){
		if(curr.equals(goal)){
			return true;
		}
		int size = curr.adj.size();
		for(int i = 0; i < size; i++){
			Node v = curr.adj.get(0);
			list.add(v.index);		
			if(!DFS(v, goal, list))
				list.remove(list.size()-1);
			curr.adj.remove(0);
		}
		return false;
	}
}
