import java.util.*;
import java.awt.Point;

public class ProblemB{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int repeats = in.nextInt();

		for(int i = 0; i < repeats; i++){
			int moves = in.nextInt();
			in.nextLine();

			int x = 0;
			int y = 0;
			Map<Point, ArrayList<Point>> map = new HashMap<Point, ArrayList<Point>>();
			Point start = new Point(0,0);
			Point prev = start;
			map.put(prev, new ArrayList<Point>());
			for(int j = 0; j < moves; j++){
				char ch = in.nextLine().charAt(0);

				switch(ch){
					case 'N': y++; break;
					case 'S': y--; break;
					case 'E': x++; break; 
					case 'W': x--; break;
				}

				Point p = new Point(x,y);
				if(!map.containsKey(prev))
					map.put(prev, new ArrayList<Point>());
				if(!map.containsKey(p))
					map.put(p, new ArrayList<Point>());

				map.get(prev).add(p);
				map.get(p).add(prev);
				prev = p;
			}

			Point goal = prev;	

			Deque<Point> frontier = new ArrayDeque<Point>();
			Map<Point,Integer> dist = new HashMap<Point,Integer>();
			frontier.offer(start);
			dist.put(start, 0);
			while(!frontier.isEmpty()){
				Point curr = frontier.poll();
				if(curr.equals(goal))
					break;
				int d = dist.get(curr);
				for(Point adj : map.get(curr)){
					if(!dist.containsKey(adj) || d+1 < dist.get(adj)){
						dist.put(adj, d+1);
						frontier.offer(adj);
					}
				}
			}

			System.out.println(dist.get(goal));
		}
	}
}
