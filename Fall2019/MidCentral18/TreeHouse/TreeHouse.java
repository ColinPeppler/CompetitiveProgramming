import java.util.*;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D;

public class TreeHouse {
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int e = in.nextInt();
		int p = in.nextInt();
		List<Point2D.Double> nodes = new ArrayList<Point2D.Double>();
		List<Cable> edges = new ArrayList<Cable>();

		for (int i = 0; i < n; i++){
			double x = in.nextDouble();
			double y = in.nextDouble();
			Point2D.Double point = new Point2D.Double(x,y);
						
			for (int j = 0; j < nodes.size(); j++){
				Point2D.Double prevPoint = nodes.get(j);
				double xPrev = prevPoint.getX();	
				double yPrev = prevPoint.getY();	
				double dist = (i < e) ? 0 : Math.hypot(x - xPrev, y - yPrev);

				int from = i; int to = j;
				Cable cable = new Cable(from, to, dist);
				edges.add(cable);
			}

			nodes.add(point);
		}

		for (int i = 0; i < p; i++){
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;

			Cable alreadyPlacedCable = new Cable(a, b, 0);
			edges.add(alreadyPlacedCable);
		}

		edges.sort((a, b) -> (int) Math.signum(a.distance - b.distance));
		UnionFind disjointSet = new UnionFind(n);
		double totalDistance = 0;
				
		for (Cable currCable : edges){
			Cable currCable = edges.remove(0);	
			int from = currCable.from;
			int to = currCable.to;

			if (disjointSet.find(from) == disjointSet.find(to))
				continue;

			totalDistance += currCable.distance;	
			disjointSet.union(from, to);
			i++;
		}

		System.out.println(totalDistance);
	}

	public static class Cable {
		int from;
		int to;
		double distance;

		public Cable(int from, int to, double distance){
			this.from = from;
			this.to = to;
			this.distance = distance;
		}

	}

	public static class UnionFind {
		int count;   // Remaining count of disjoint sets
		private int[] id;

		public UnionFind(int numElements) {
			this.count = numElements;
			id = new int[numElements];
			Arrays.fill(id, -1);
		}

		public int union(int a, int b) {
			int roota = find(a);
			int rootb = find(b);

			if (roota == rootb) {   // same root already
				return 0;
			}

			count--;
			// union-by-rank
			if (id[rootb] < id[roota]) {
				id[roota] = rootb;
				return -1;
			} else
			if (id[rootb] > id[roota]) {
				id[rootb] = roota;
			} else {
				id[rootb] = roota;
				id[roota]--;  // Increase the rank if tie
			}
			return 1;
		}

		public int find(int a) {
			return id[a] < 0 ? a : (id[a] = find(id[a]));
		}
	}
}
