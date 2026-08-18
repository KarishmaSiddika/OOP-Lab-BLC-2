class Student extends Person {
    private int[] testScores;

    Student(String firstName, String lastName, int id, int[] scores) {
        super(firstName, lastName, id);
        this.testScores = scores;
    }

    char calculate() {
        int sum = 0;
        for (int score : testScores) {
            sum += score;
        }
        int average = sum / testScores.length;

        if (average >= 90) return 'O';
        else if (average >= 80) return 'E';
        else if (average >= 70) return 'A';
        else if (average >= 55) return 'P';
        else if (average >= 40) return 'D';
        else return 'T';
    }
}
