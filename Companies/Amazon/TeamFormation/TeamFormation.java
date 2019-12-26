/*
Input list has n elements, each element has a score. The goal is to assemble a group (of size ‘team’) of elements together and to find their total score.

Inputs:

score [] of size n,
team which is the team size,
m tells us how to slice the list
Selection of elements from the list is done in the following way:

During each selection, we have to choose an element with the highest score among either the first m in available elements or the last m available elements in the list. 
The element is then removed from the list and added to the team total score.
If there are multiple elements with the same highest score among the first or last in available elements, the element closest to the start of the line is picked.
If there are less than in available elements. then we pick the highest scored element from all available elements.
 */
import java.util.*;

public class TeamFormation{
	private static class Skill implements Comparable<Skill>{
		int val;
		int index;

		public Skill(int val, int index){
			this.val = val;
			this.index = index;
		}

		@Override
		public int compareTo(Skill other){
			if(this.val - other.val != 0)
				return other.val-this.val;
			return this.index-other.index;
		}
	}

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		List<Skill> skills = new ArrayList<Skill>();
		for(int i = 0; i < n; i++){
			Skill sk = new Skill(in.nextInt(), i);
			skills.add(sk);
		}
		int team = in.nextInt();
		int m = in.nextInt();
	
		Queue<Skill> pq = new PriorityQueue<Skill>();	
		int count = 0;



		if(skills.size() <= 2*m){
			for(Skill s : skills)
				pq.offer(s);

			for(int i = 0; i < team; i++)
				count += pq.poll().val;
		} else {

			for(int i = 0; i < m; i++){
				pq.offer(skills.get(i)); 	// Add the mth from the front
				if(i == skills.size()-i)
					break;
				pq.offer(skills.get(skills.size()-i-1)); 	// Add the mth from the end
			}

			for(int i = 0; i < team; i++){
				Skill currSkill = pq.poll();
				count += currSkill.val;

				if(skills.size() >= 2*m){ 	// If there is available employees add them
					if(currSkill.index <= m){
						Skill front = skills.remove(m);
						pq.offer(front);
					} else {
						Skill back = skills.remove(skills.size()-m-1);
						pq.offer(back);
					}
				}
			}
		}

		System.out.println(count);	
	}
}
