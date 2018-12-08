import java.util.*;
public class Simulation{
	Queue<Event> eventQueue = new PriorityQueue<Event>();
	int now;
	
	int remoteTime; 	// total time to fill remote prescriptions
	int instoreTime; 	// total time to fill instore prescriptions
	int remoteSize; 		// num of remote prescriptions
	int instoreSize; 		// num of instore prescriptions


	abstract class Event implements Comparable<Event>{
		int when;
		boolean priority;

		Event(int when, boolean priority){
			this.when = when;
			this.priority = priority;
		}	

		abstract void execute();

		@Override
		public int compareTo(Event that){
			int n = Integer.compare(this.when, that.when);
			if(n == 0){
				if(this.priority)
					return -1;
				if(that.priority)
					return 1;
			}
			return n;
		}
	}

	void simulate(){
		while(!eventQueue.isEmpty()){
			Event e = eventQueue.poll();
			//now = e.when;
			//System.out.println(now + " | " + e.when);
			e.execute();
		}
	}

	void addToNow(int x){
		now += x;
	}

	int idleTechnicians;
	Queue<Prescription> queue = new PriorityQueue<Prescription>();
	
	class Technician extends Event{
		Technician(int when){ super(when, true); }		
		
		void execute(){
			if(queue.isEmpty()){
				idleTechnicians++;
			} else {
				final Prescription prescription = queue.poll();
				//System.out.println(now + " | " + prescription.fill_time);
				now += prescription.fill_time;
				//System.out.println(now);
				System.out.println(prescription.isRemote);
				eventQueue.offer(new Technician(now));
				
				if(prescription.isRemote){
					remoteSize++;
					remoteTime += now - prescription.drop_time; 
				} else {
					instoreSize++;
					instoreTime += now - prescription.drop_time;
				}
				/*System.out.println("YEET : " +  now + " | " + prescription.fill_time);
				eventQueue.offer(new Event(now + prescription.fill_time){
					void execute(){
						System.out.println("BEASTMODE : " + now + " | " + prescription.fill_time);
						//System.out.println("AFTER FILL: " + now);
						eventQueue.offer(new Technician(now));
					}
				});
				//System.out.println("after: " + now);*/
			}
		}
	}
	
	Simulation(int technicians){
		for(int i = 0; i < technicians; i++)
			eventQueue.offer(new Technician(0));	
		idleTechnicians = technicians;

		remoteTime = 0;
		instoreTime = 0;
		remoteSize = 0;
		instoreSize = 0;
	}

	class Prescription implements Comparable<Prescription> {
		int drop_time;
		int fill_time;
		boolean isRemote = false;

		Prescription(int drop_time, char type, int fill_time){
			this.drop_time = drop_time;
			this.fill_time = fill_time;
			if(type == 'R')
				isRemote = true;

			

			eventQueue.offer(new Event(drop_time, !isRemote) {
				void execute(){
					fillPrescription();
				}
			});
		}

		void fillPrescription(){
			queue.offer(this);

			if(idleTechnicians > 0){
				idleTechnicians--;
				eventQueue.offer(new Technician(now));
			}
		}

		@Override
		public int compareTo(Prescription that){
			if(this.isRemote)
				return 1;
			if(that.isRemote)
				return -1;
			int dropDiff = this.drop_time - that.drop_time;
			return dropDiff;
		}

		public String toString(){
			return String.format("drop: %d | fill: %d | %b", drop_time, fill_time, isRemote);
		}
	}

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int prescriptions = in.nextInt();
		int technicians = in.nextInt();

		Simulation start = new Simulation(technicians);


		PriorityQueue<Prescription> pq = new PriorityQueue<>();
		for(int i = 0; i < prescriptions; i++){
			int drop_time = in.nextInt();
			char type = in.next().charAt(0);
			int fill_time = in.nextInt();
			final Prescription p = start.new Prescription(drop_time, type, fill_time);
		}	


		start.simulate();

		System.out.println("NOW: " + start.now);
		System.out.printf("INSTORE: %d | %d \n REMOTE: %d | %d \n", start.instoreTime, start.instoreSize, start.remoteTime, start.remoteSize);
	}
}
