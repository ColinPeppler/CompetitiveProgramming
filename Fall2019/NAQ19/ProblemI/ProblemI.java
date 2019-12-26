import java.util.*;
public class ProblemI{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

			
	}

	static final long INF = Long.MAX_VALUE;

static class Entry implements Comparable {
	public long distance;
	public long repair;
	public long air;

	public Entry(long d, long r, long a) { 
		distance = d; repair = r; air = a;
	} 
	public int compareTo(Object o) {
	Entry e = (Entry)o;
	if (distance != e.distance) { 
		return (int)(distance - e.distance); 
	} 
	return air - e.air;
	}


static long dijkstra(int N, int start, int goal, long air) {
	final long [] dist = new long[N];
	Arrays.fill(dist, INF);
	dist[start] = 0;	

	int [] prev = new int[N];
	Queue<Entry> frontier = new PriorityQueue<Entry>(dist.length);
	frontier.offer(new Entry(0, start));
	while (!frontier.isEmpty()) {
		Entry e = frontier.poll();
		int u = e.index;

		if (u == goal) break;
		// discard if a shorter distance is already known

		if (dist[u] < e.distance) continue;
		for(int v : neighborsOf(u)) {
			long uv = distance2(u, v);
			int a = air - air2(u,v);
			if(dist[u] + uv  < dist[v] && a >= 0){
				prev[v] = u;
				dist[v] = dist[u] + uv;
				frontier.offer(new Entry(dist[v], v));
			}
		}
	}

	return dist[goal];
}

static List<Entry> neighborsOf(u){
	return edgeList.get(u);
}

static distance2(u,v){
	return graph[u][v].distance;
}

static air2(u,v){
	return graph[u][v].air;
}
