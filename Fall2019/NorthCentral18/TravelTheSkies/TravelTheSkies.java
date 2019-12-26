import java.util.*;

public class TravelTheSkies {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		final int NUM_AIRPORTS = in.nextInt();
		final int NUM_DAYS = in.nextInt();
		final int NUM_FLIGHTS = in.nextInt();

		List<Event> eventQueue = new ArrayList<>();
		for (int i = 0; i < NUM_FLIGHTS; i++) {
			int fromAirport = in.nextInt();	
			int toAirport = in.nextInt();	
			int day = in.nextInt();	
			long capacity = in.nextLong();	

			Event departure = new Event(day, EventType.DEPARTURE, fromAirport, capacity);
			Event arrival = new Event(day, EventType.ARRIVAL, toAirport, capacity);
			eventQueue.add(departure);
			eventQueue.add(arrival);
		}

		for (int i = 0; i < NUM_AIRPORTS * NUM_DAYS; i++) {
			int airport = in.nextInt();
			int day = in.nextInt();
			long numPeople = in.nextLong();

			Event boarding = new Event(day, EventType.BOARDING, airport, numPeople);
			eventQueue.add(boarding);
		}

		Collections.sort(eventQueue);	

		long[] airportCapacity = new long[NUM_AIRPORTS];

		while (!eventQueue.isEmpty()) {
			Event currEvent = eventQueue.remove(0);
			int day = currEvent.day;
			EventType currEventType = currEvent.type;
			int airport = currEvent.airport - 1;
			long numPeople = currEvent.capacity;

			if (currEvent.type == EventType.DEPARTURE) {
				if (airportCapacity[airport] < numPeople) { 	// ppl left behind
					System.out.println("suboptimal");
					System.exit(0);
				}
				airportCapacity[airport] -= numPeople; 		// full send
			} 
			else { 											// ARRIVAL or BOARDING
				airportCapacity[airport] += numPeople;	
			}
		}
		
		System.out.println("optimal");
	}

	public static enum EventType {
		// depart before arrive, since a plane can't arrive unless it departs first
		BOARDING, DEPARTURE, ARRIVAL; 
	}

	public static class Event implements Comparable<Event> {
		public int day;	
		public EventType type;
		public int airport;
		public long capacity;

		public Event(int day, EventType type, int airport, long capacity) {
			this.day = day;
			this.type = type;
			this.airport = airport;
			this.capacity = capacity;
		}

		@Override
		public int compareTo(Event e) {
			int dayDiff = this.day - e.day;
			if (dayDiff == 0){
				return this.type.compareTo(e.type);
			}
			return dayDiff;
		}
	}
}
