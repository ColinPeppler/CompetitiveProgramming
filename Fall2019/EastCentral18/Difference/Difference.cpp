#include <iostream>
#include <vector>
#include <bitset>
using namespace std;

const int MAX = 200000000; 
bitset<MAX> haveSeen; 		// initialized outside main to allocate on heap
							// allocating on stack (in main) leads to overflow

int main(){
	ios_base::sync_with_stdio(false);
    cin.tie(NULL);

	int firstTerm, target;
	cin >> firstTerm >> target;

	haveSeen[firstTerm] = true; 			
	vector<int> terms = { firstTerm };

	int count = 0;
	int smallestValNotSeen = 0; 
	// smallest value which hasn't appeared either in the sequence or as a 
	// difference between any term in the sequence

	if (target == firstTerm){
		cout << 1 << endl;
		return 0;
	}

	while (!haveSeen[target]){
		// Find smallest difference not found yet 
		while (haveSeen[smallestValNotSeen]){ 	
			smallestValNotSeen++;				
		}

		// Calculate next term in the sequence A_n = A_n-1 + d
		int nextTerm = terms[terms.size() - 1] + smallestValNotSeen;
		haveSeen[nextTerm] = true;
		terms.push_back(nextTerm);

		// Mark the difference between any term in the sequence as seen
		for(int term : terms){
			haveSeen[nextTerm - term] = true;
		}

		count++;
	}

	cout << count << endl;

	return 0;
}
