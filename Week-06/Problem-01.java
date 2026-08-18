import java.util.Scanner;

abstract class Book {
    public String title;
    public abstract void setTitle(String s);

    public String getTitle() {
        return title;
    }
}

class MyBook extends Book {

    public MyBook(){
    }

    @Override
    public void setTitle(String s) {
        title = s;
    }
}

public class Solution {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String title = scan.nextLine();
        Book newBook = new MyBook();
        newBook.setTitle(title);
        System.out.println("The title is: " + newBook.getTitle());
        scan.close();
    }
}
