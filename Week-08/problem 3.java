import java.io.*;
import java.util.*;

class Calculator {
    
    int power(int n, int p) throws Exception {
        if (n < 0 || p < 0) {
            throw new Exception("n and p should be non-negative");
        }
        
        int result = 1;
        for (int i = 0; i < p; i++) {
            result *= n;
        }
        return result;
    }
}

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        Calculator my_calculator = new Calculator();
        while (t-- > 0) {
            int n = in.nextInt();
            int p = in.nextInt();
            try {
                System.out.println(my_calculator.power(n, p));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
