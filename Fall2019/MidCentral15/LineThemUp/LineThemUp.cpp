#include <iostream>
#include <string>
#include <vector>
using namespace std;

int main(){
	ios_base::sync_with_stdio(false);
    cin.tie(NULL);

	int n;
	cin >> n;	
	vector<string> players(n, ""); 	// better than initializing empty vec and
									// pushing due to memory allocation

	bool increasing;
	string prev;
	cin >> prev;
	for (int i = 1; i < n; i++){
		string curr;
		cin >> curr;

		if (i == 1){
			increasing = prev.compare(curr) < 0;
		}
		if ((increasing && prev.compare(curr) > 0) ||
			(!increasing && prev.compare(curr) < 0)) {
			cout << "NEITHER" << endl;
			return 0;	
		}

		prev = curr;
	}

	string ans = increasing ? "INCREASING" : "DECREASING";
	cout << ans << endl;
	return 0;
}
