#include <iostream>
using namespace std;

/* 
Insight: We can combine wolves and cabbages since sheep can't be left along
with either wolves or cabbages.

Additional we can approach by only think about cases where we can succesfully
transport everything without anything being eaten. Therefore, we'll only 
consider cases which result in ans = "YES". To simplify our problem further, 
we combined wolves and cabbages into one group.

Now let's break it down into cases where we'll be successful.
WC - wolves and cabbages
S  - sheep
K  - capacity

Case 1: WC < K 
	then we can just keep WC in the boat
Case 2: WC = K and S <= 2K 
	then we can transport sheep in two trips
Case 3: WC > K and S < K
	then we can just keep sheep in the boat
Case 4: S < K
	then we can just keep sheep in the boat; same as Case 3
Case 5: S = K and WC <= 2K
	then we can transport wolves and sheep in two trips
Case 6: S > K and WC < K
	then we can just keep WC in the boat; same as Case 1

Now we only have 4 cases because we were able to combine wolves and cabbages
into 1 group, and soely focus on a success case for each group. 
*/
int main(){
	int wolves, sheeps, cabbages, capacity;	
	cin >> wolves >> sheeps >> cabbages >> capacity;
	int wolvesAndCabbages = wolves + cabbages;

	string ans = "NO";
	if (wolvesAndCabbages < capacity) { 			// Case 1
		ans = "YES";	
	}
	else if (wolvesAndCabbages == capacity 
		&& sheeps <= 2 * capacity) { 				// Case 2
		ans = "YES";	
	}
	else if (sheeps < capacity) { 					// Case 4
		ans = "YES";	
	}
	else if (sheeps == capacity 
		&& wolvesAndCabbages <= 2 * capacity) { 	// Case 5
		ans = "YES";	
	}

	cout << ans << endl;
	return 0;
}
