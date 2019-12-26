import java.util.*;

public class WorkoutForADumbbell {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		final static int NUM_MACHINES = 10;

		Queue<Event> eventQueue = new PriorityQueue<>();
		int[] usageJim = new int[NUM_MACHINES];
		int[] recoveryJim = new int[NUM_MACHINES];

		for (int i = 0; i < NUM_MACHINES; i++) {
			usageJim[i] = in.nextInt();
			recoveryJim[i] = in.nextInt();
		}

		// using 0 based index for machines
		eventQueue.offer(new Event(0, 0, EventType.JIM_START,
										usageJim[0], recoveryJim[0]);

		/*int[] usageOther = new int[NUM_MACHINES];
		int[] recoveryOther = new int[NUM_MACHINES];*/

		for (int i = 0; i < NUM_MACHINES; i++) {
			int machine = i;
			/*usageOther[i] = in.nextInt(); 
			recoveryOther[i] = in.nextInt(); */
			int usage = in.nextInt();
			int recovery = in.nextInt();
			long startTime = in.nextLong(); 

			Event otherStart = new Event(startTime, machine, 
									EventType.OTHER_START, usage, recovery);
			eventQueue.offer(otherStart);
		}

		boolean[] isMachineAvailable = new boolean[NUM_MACHINES];
		while (!eventQueue.isEmpty()) {
			Event currEvent = eventQueue.poll();
			int time = currEvent.time;
			int machine = currEvent.machine;
			int usage = currEvent.usage;
			int recovery = currEvent.recovery;
			EventType eventType = currEvent.type;
			boolean isCurrMachineAvailable = isMachineAvailable[machine];

			switch (eventType) {
				case EventType.OTHER_START:
					if (isCurrMachineAvailable) {
						isMachineAvailable[machine] = false;
						int finishTime = startTime + usage;
						Event otherFinish = new Event(finishTime, machine, 
														EventType.OTHER_FINISH, 
														usage, recover);
						eventQueue.offer(otherFinish);
					}
					else {
						Event otherStart = new Event(startTime+1, machine,
														EventType.OTHER_START, 
														usage, recover);
						eventQueue.offer(otherStart);
					}
					break;
				case EventType.JIM_START:
					if (
					break;
				case EventType.OTHER_FINISH:
					break;
				case EventType.JIM_FINISH:
					break;
			}
		}
	}

	public static enum EventType {
		OTHER_START, JIM_START, OTHER_FINISH, JIM_FINISH;
	}

	public static class Event implements Comparable {
		long time;
		int machine;
		EventType type;
		int usage;
		int recovery;

		public Event(long time, int machine, EventType type, int usage, int recovery) {
			this.time = time;
			this.machine = machine;
			this.type = type;
			this.usage = usage;
			this.recovery = recovery;
		}

		@Override
		public int compareTo(Event e) {
			int timeDiff = this.time - e.time;
			if (timeDiff == 0) {
				return this.type.compareTo(e.type);
			}
			return timeDiff;
		}
	}
}
