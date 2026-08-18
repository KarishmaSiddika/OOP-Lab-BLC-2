public class Singleton {

    private static volatile Singleton instance;


    private Singleton() {
        System.out.println("Singleton instance created!");
    }


    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }


    public void showMessage() {
        System.out.println("Hello from Singleton!");
    }


    public static void main(String[] args) {
        Singleton obj1 = Singleton.getInstance();
        Singleton obj2 = Singleton.getInstance();

        obj1.showMessage();

        System.out.println("Are both objects same? " + (obj1 == obj2));
    }
}
