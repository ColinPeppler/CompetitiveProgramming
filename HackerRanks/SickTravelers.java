import java.util.*;
/*
	PLAN:
   	Location
	- Name: String
  	- PeopleList: List<People> or Map<People,Boolean>
	- infect() (make all people sick)

	/ remove(Person)
	

	People
	- Name: String
  	- Health: String	
	- currLoc: Location
	- Locations: List<Location>

	/ update()
		-if sick -> currLoc.infect(), recover
		-if recover -> currLoc.infect(), healthy
		location++;
		currLoc.remove(People);
 */
public class SickTravelers{
	public static class Location{
		String name;
		Set<Person> travelers;

		public Location(String name){
			this.name = name;
			travelers = new HashSet<Person>();
		}

		void infect(){
			for(Person p : travelers){
				if(!p.isHealthy())
					p.update();
				else
					p.becomeSick();	
			}
		}

		void update(){
			for(Person p : travelers)
				p.update();
		}

		void nextDay(){
			boolean flag = false;
			for(Person p :travelers){
				if(!p.isHealthy())
					flag = true;
			}
			if(flag)
				infect();
			else{
				update();	
			}
		}

		void add(Person p){
			travelers.add(p);
		}

		void remove(Person p){
			travelers.remove(p);	
		}
	}

	public static class Person{
		String name;
		String health;
		Location currLoc;
		int locIndex;
		List<Location> destinations;

		public Person(String name, String health, List<Location> dest){
			this.name = name;
			this.health = health;
			destinations = dest;
			locIndex = 0;
			currLoc = destinations.get(0);
		}	

		void update(){
			if(health.equals("SICK")){
				health = "RECOVERING";
			}
			else if(health.equals("RECOVERING")){
				health = "HEALTHY";
			}
			currLoc.remove(this);
			currLoc = destinations.get(locIndex % destinations.size());
			currLoc.add(this);
		}

		void becomeSick(){
			health = "SICK";
			currLoc.remove(this);
			currLoc = destinations.get(locIndex % destinations.size());
			currLoc.add(this);
		}

		boolean isHealthy(){
			return health.equals("HEALTHY");
		}

		String getName(){
			return name;
		}

		String getHealth(){
			return health;
		}
	}

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.nextLine();
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < n; i++){
			list.add(in.nextLine());
		}

		parse(list);
	}

	public static void parse(List<String> list){
		Map<String,Location> strToLoc = new HashMap<String,Location>();
		Set<Location> locations = new HashSet<Location>();
		List<Person> people = new ArrayList<Person>();
		for(String str : list){
			String[] split = str.split(" ");
			String name = split[0];
			String health = split[1];

			List<Location> destinations = new ArrayList<Location>();
				
			for(int i = 2; i < split.length; i++){
				String locName = split[i];
				if(!strToLoc.containsKey(locName)){
					Location loc = new Location(locName);
					strToLoc.put(locName, loc);
				}
				destinations.add(strToLoc.get(locName));
			}
			
			Person p = new Person(name, health, destinations);		
			people.add(p);
		}

		for(Map.Entry<String,Location> e : strToLoc.entrySet())
			locations.add(e.getValue());

		solve(people, locations);
	}

	public static void solve(List<Person> travelers, Set<Location> locations){
		for(Person p : travelers){
			System.out.print(p.getName() + " ");
		}
		System.out.println();
		
		int days = 1;
		while(days <= 365 && !allHealthy(travelers)){
			nextDay(travelers, locations);	
			days++;
		}		
	}

	public static boolean allHealthy(List<Person> travelers){
		for(Person p : travelers){
			if(!p.isHealthy())
				return false;	
		}
		return true;
	}

	public static void nextDay(List<Person> travelers, Set<Location> locations){
		for(Person p : travelers){
			System.out.print(p.getHealth() + " ");
		}	
		System.out.println();
		
		for(Location loc : locations){
			loc.nextDay();
		}
	}
}
