import java.io.*;
import java.util.*;

interface AdvancedArithmetic {
    int divisorSum(int n);
}

class MyCalculator implements AdvancedArithmetic {
    public int divisorSum(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                sum += i;
            }
        }
        return sum;
    }
}

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        MyCalculator my_calculator = new MyCalculator();
        int d = my_calculator.divisorSum(n);
        System.out.println("I implemented: " + my_calculator.getClass().getInterfaces()[0].getName());
        System.out.println(d);
    }
}
