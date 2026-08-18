#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
#include <cassert>
using namespace std;

class Student {
    private:
        vector<int> scores;
    public:
        void input() {
            int n;
            cin >> n;
            for (int i = 0; i < n; i++) {
                int score;
                cin >> score;
                scores.push_back(score);
            }
        }

        int calculateTotalScore() {
            int total = 0;
            for (int i = 0; i < scores.size(); i++) {
                total += scores[i];
            }
            return total;
        }
};

int main() {
    int n; // number of students
    cin >> n;
    Student* s = new Student[n];
    for (int i = 0; i < n; i++) {
        s[i].input();
    }

    int kristenScore = s[0].calculateTotalScore();

    int count = 0;
    for (int i = 1; i < n; i++) {
        int totalScore = s[i].calculateTotalScore();
        if (totalScore > kristenScore) {
            count++;
        }
    }

    cout << count << endl;
    return 0;
}
