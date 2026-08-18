import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private int balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.println("Deposited: " + amount + ", Balance: " + balance);
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(int amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrew: " + amount + ", Balance: " + balance);
            } else {
                System.out.println("Insufficient funds for withdrawal of " + amount);
            }
        } finally {
            lock.unlock();
        }
    }

    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    // Demo main method
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);

        Runnable depositTask = () -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(100);
            }
        };

        Runnable withdrawTask = () -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(50);
            }
        };

        Thread t1 = new Thread(depositTask);
        Thread t2 = new Thread(withdrawTask);
        Thread t3 = new Thread(withdrawTask);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Balance: " + account.getBalance());
    }
}
