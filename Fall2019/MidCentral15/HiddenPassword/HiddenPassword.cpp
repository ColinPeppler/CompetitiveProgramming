#include <iostream>
#include <string>

using namespace std;

int main(){
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);

	string password, string;
	cin >> password >> string;

	int count = 0;
	for (char c : string) {
		if (password.find(c, count) != std::string::npos) {
			if (c == password[count])
				count++;
			else 
				break;
		}
	}

	std::string ans = count == password.length() ? "PASS" : "FAIL";;
	cout << ans << endl;

	return 0;
}
