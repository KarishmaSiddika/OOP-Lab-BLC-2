import java.util.*;

class Person {
    String name;
    int age;
    double salary;

    Person(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return name + " - Age: " + age + ", Salary: " + salary;
    }
}

public class ComparatorExample {
    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
            new Person("Karishma", 25, 50000),
            new Person("Rahim", 30, 60000),
            new Person("Anika", 25, 55000),
            new Person("Jamil", 28, 50000)
        );

        Comparator<Person> comparator = Comparator
            .comparingInt((Person p) -> p.age)
            .thenComparingDouble(p -> p.salary)
            .thenComparing(p -> p.name);

        Collections.sort(people, comparator);

        people.forEach(System.out::println);
    }
}
