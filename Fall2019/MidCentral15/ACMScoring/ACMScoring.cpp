#include <iostream>
#include <string>
#include <vector>

using namespace std;
int main() {
	vector<int> score(100, 0);
	string first, second, third;

	while(true) {
		cin >> first >> second >> third;

		if (first.compare("-1") == 0) {
			break;
		}

		int time = stoi(first);
		int index = (int) (second.at(0) - 'A');
		if (third.compare("wrong") == 0) {
			score[index] -= 20;		
		} else {
			score[index] -= time;
			score[index] *= -1;
		}
	}

	int sum = 0;
	int numSolved = 0;
	for (int i = 0; i < 100; i++) {
		if (score[i] > 0) {
			sum += score[i];
			numSolved++;
		}
	}

	cout << numSolved << ' ' << sum << endl;
}
