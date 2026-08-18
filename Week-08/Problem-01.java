import java.util.Scanner;
import java.util.InputMismatchException;

public class Solution {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        scan.nextLine();

        while (t-- > 0) {
            String line = scan.nextLine();
            Scanner lineScan = new Scanner(line);
            try {
                int x = lineScan.nextInt();
                int y = lineScan.nextInt();
                System.out.println(x / y);
            } catch (ArithmeticException e) {
                System.out.println("Exception thrown: " + e);
            } catch (InputMismatchException e) {
                System.out.println("Exception thrown: java.util.InputMismatchException");
            } finally {
                lineScan.close();
            }
        }
        scan.close();
    }
}
