import java.util.*;
import java.io.*;

public class ProblemC{
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
	public static void main(String[] args){
		FastScanner in = new FastScanner();
		int numOfPorts = in.nextInt();
		int numOfWires = in.nextInt();
		List<Integer> ports = new ArrayList<Integer>();	
		PriorityQueue<Integer> pq_ports = new PriorityQueue<Integer>();
		List<Integer> wires = new ArrayList<Integer>();

		for(int i = 0; i < numOfPorts; i++){
			int port = in.nextInt();
			for(int p : ports){
				pq_ports.offer(port-p);
			}	
			ports.add(port);
		}

		for(int i = 0; i < numOfWires; i++){
			int wire = in.nextInt();
			//pq_wires.offer(wire);	
			if(!pq_ports.isEmpty() && pq_ports.peek() <= wire)
				pq_ports.poll();
			else
				wires.add(wire);
		}

		/*int size = pq_wires.size();
		for(int i = 0; i < size; i++){
			if(pq_ports.isEmpty())
				break;
			if(pq_wires.peek() >= pq_ports.peek()){
				pq_wires.poll();
				pq_ports.poll();
			}
		}*/

		if(wires.isEmpty()){
			System.out.println("yes");
		} else {
			System.out.println("no");
		}
	}
}
