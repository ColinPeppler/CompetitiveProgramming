#include <iostream>
#include <vector>
using namespace std;

using ll = long long; 		// because integers can be between -10^9 and 10^9
							// long long can handle up to 10^18
using vll = vector<ll>; 	// vectors can work with a template class
							// where arrays only store primitive data types
using vvll = vector<vll>;

// this infinity declaration didn't work?
//const auto INFINITY = std::numeric_limits<ll>::infinity();
static const ll INFINITY = 0xffffffffll * 100000ll;

// Bottom-up DP
int main(){
	int n, k;
	cin >> n >> k;
	
	vll nums(n, 0);
	for (int i = 0; i < n; i++){
		cin >> nums[i];
	}

	vvll maxSumOfKSubArrays(n, vll(k + 1, -INFINITY));
	vvll solution(n, vll(k + 1, -INFINITY));

	// initial state
	for (int i = 0; i < n; i++){
		solution[i][0] = 0;
	}
	maxSumOfKSubArrays[0][1] = nums[0];
	solution[0][1] = nums[0];

	for (int i = 1; i < n; i++){
		for (int j = 1; j <= k; j++){
			maxSumOfKSubArrays[i][j] = max(solution[i-1][j-1],
				maxSumOfKSubArrays[i-1][j]) + nums[i];
			solution[i][j] = max(solution[i-1][j], maxSumOfKSubArrays[i][j]);

		}
	}

	cout << solution[n-1][k] << endl;
	return 0;

	// maxSum = max(maxSum of last elem new subarray,
	//					ans of last elem same subarray) + current elem
	// knapsack
	// maxSum(i, j) = max(maxSum(i-1, j), Ans(i-1, j-1)) + nums[i]
	//	ans = max(ans of last elem new subarray,
	//				maxSum of curr elem new subarray)	
	// single subarray dp
	// Ans(i,j) = max(Ans(i-1,j, maxSum(i,j))
}
