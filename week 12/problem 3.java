
public class BankAccount {

    private boolean isOpen;
    private int balance;

    public BankAccount() {
        this.isOpen = false;
        this.balance = 0;
    }

    public synchronized void open() throws BankAccountActionInvalidException {
        if (this.isOpen) {
            throw new BankAccountActionInvalidException("Account already open");
        }
        this.isOpen = true;
        this.balance = 0;
    }

    public synchronized int getBalance() throws BankAccountActionInvalidException {
        ensureOpen();
        return this.balance;
    }

    public synchronized void deposit(int amount) throws BankAccountActionInvalidException {
        ensureOpen();
        if (amount <= 0) {
            throw new BankAccountActionInvalidException("Cannot deposit non-positive amount");
        }
        this.balance += amount;
    }

    public synchronized void withdraw(int amount) throws BankAccountActionInvalidException {
        ensureOpen();
        if (amount <= 0) {
            throw new BankAccountActionInvalidException("Cannot withdraw non-positive amount");
        }
        if (amount > this.balance) {
            throw new BankAccountActionInvalidException("Cannot withdraw more money than is in account");
        }
        this.balance -= amount;
    }

    public synchronized void close() throws BankAccountActionInvalidException {
        ensureOpen();
        this.isOpen = false;
        this.balance = 0;
    }

    private void ensureOpen() throws BankAccountActionInvalidException {
        if (!this.isOpen) {
            throw new BankAccountActionInvalidException("Account closed");
        }
    }
}
public class BankAccountActionInvalidException extends Exception {

    public BankAccountActionInvalidException(String message) {
        super(message);
    }
} 
