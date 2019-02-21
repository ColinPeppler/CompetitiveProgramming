import java.util.*;

public class ProblemA{
	static List<Integer> ans = new ArrayList<Integer>();
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		while(in.hasNextLine()){
			ans = new ArrayList<Integer>();
			int n = in.nextInt();
			in.nextLine();
			Map<String, Integer> nameMap = new HashMap<String, Integer>();
			Map<Integer, String> indexMap = new HashMap<Integer, String>();
			PriorityQueue<String> pq = new PriorityQueue<String>();
			for(int i = 0; i < n; i++){
				String name = in.nextLine();
				pq.offer(name);
			}
			int index = 0;
			while(!pq.isEmpty()){
				String name = pq.poll();
				nameMap.put(name, index);
				indexMap.put(index++, name); 
			}
			List<ArrayList<Integer>> hateList = new ArrayList<ArrayList<Integer>>();				
			for(int i = 0; i < n; i++){
				ArrayList<Integer> arrList = new ArrayList<Integer>();
				hateList.add(arrList);
			}

			int m = in.nextInt();
			in.nextLine();
			for(int i = 0; i < m; i++){
				String line = in.nextLine();
				String[] split = line.split(" ");
				int p1 = nameMap.get(split[0]);
				for(int j = 1; j < split.length; j++){
					int p2 = nameMap.get(split[j]);
					hateList.get(p1).add(p2);
					hateList.get(p2).add(p1);	
				}			
			}

			boolean[] visited = new boolean[n];
			boolean flag = backtrack(visited, hateList, 0);

			if(!flag){
				System.out.println("You all need therapy.");	
			} else {
				StringBuilder builder = new StringBuilder();	
				for(int i: ans){
					String name = indexMap.get(i);
					builder.append(name + " ");
				}
				builder.deleteCharAt(builder.length()-1);
				System.out.println(builder.toString());
			}

		}
	}

	public static boolean backtrack(boolean[] visited, List<ArrayList<Integer>> hateList, int index){
		if(index == hateList.size()){
			return true;	
		}	
		for(int i = 0; i < hateList.size(); i++){
			if(visited[i])
				continue;
			if(index != 0){
				int k = ans.get(index-1);
				if(hateList.get(k).contains(i)) continue;;
			}
			visited[i] = true;
			ans.add(i);
			if (backtrack(visited, hateList, index + 1)) return true;
			visited[i] = false;
			ans.remove(ans.size()-1);
		}
		return false;
	}
}
