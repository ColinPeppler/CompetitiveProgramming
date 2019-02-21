import java.util.*;

public class JohnWorkout {

	public static final int MAXM = 10;

	public static Times[] jim = new Times[MAXM];
	public static Times[] others = new Times[MAXM];
	public static Scanner in = new Scanner(System.in);

	public static void main(String [] args)
	{
   		int n = 10;
   		for(int i=0; i<n; i++) {
			jim[i] = new Times();
   		    jim[i].usage = in.nextInt();
   		    jim[i].recovery = in.nextInt();
   		    jim[i].total = jim[i].usage + jim[i].recovery;
   		}
   		for(int i=0; i<n; i++) {
			others[i] = new Times();
   		    others[i].usage = in.nextInt();
		 	others[i].recovery = in.nextInt();
		 	others[i].start = in.nextInt();
   		    others[i].total = others[i].usage + others[i].recovery;
   		}
   		int t=0;
   		for(int reps = 1; reps <= 3; reps++) {
   	    	for(int m=0; m<n; m++) {
   	        	if (t >= others[m].start && (t-others[m].start)%others[m].total <= others[m].usage)
   	            	t += others[m].usage - (t-others[m].start)%others[m].total;
   	        	if (t >= others[m].start)
   	            	others[m].start = t - (t-others[m].start)%(others[m].total);          // update most recent start time for person m
   	        	if (others[m].start + others[m].total < t+ jim[m].usage) {
   	            	others[m].start = t+jim[m].usage;
   	        	}
   	        	else if (t < others[m].start && t+jim[m].usage > others[m].start) {
   	            	others[m].start = t+jim[m].usage;
   	        	}
   	        	t += jim[m].total;
   	    	}
   		}
   		System.out.println(t-jim[n-1].recovery);
	}
}

class Times
{
    public int usage;
    public int recovery;
    public int total;
    public int start;
}
