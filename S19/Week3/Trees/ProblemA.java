import java.util.*;

public class ProblemA{
	public static class Node{
		int index;
		List<Integer> children;

		public Node(int i){
			index = i;
			children = new ArrayList<Integer>();
		}

		public boolean equals(Node o){
			return o.index == this.index;
		}
	}
	static ArrayList<Integer> ans = new ArrayList<Integer>();
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int V = in.nextInt();
		Map<Integer,Node> map = new HashMap<Integer,Node>();
		in.nextLine();
		ans.add(1);
		for(int i = 1; i <= V; i++){
			map.put(i, new Node(i));	
		}

		for(int i = 1; i < V; i++){
			int a = in.nextInt();
			int b = i+1;	

			map.get(a).children.add(b);
		}	
		map.put(V, new Node(V));

/*		Stack<Node> stack = new ArrayDeque<Node>();
		stack.push(map.get(1));
		while(!stack.isEmpty()){
			Node curr = stack.pop();
			stack.push
			if(curr == map.get(V))
				break;
			for(int v : curr.children){
			}
		}*/
		backtrack(map.get(1), map.get(V), map);
		for(int n : ans){
			System.out.print(n + " ");
		}
	}

	public static boolean backtrack(Node curr, Node goal, Map<Integer,Node> map){
		if(curr.equals(goal)){
			return true;
		}
		for(int x: curr.children){
			Node n = map.get(x);
			ans.add(n.index);
			if(backtrack(n, goal, map))
				return true;
			ans.remove(ans.size()-1);
		}

			return false;
	}

}
