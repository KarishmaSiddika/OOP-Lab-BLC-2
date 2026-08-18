class BankAccount {
    private long balance;
    private boolean open;

    BankAccount() {
        this.balance = 0;
        this.open = false;
    }

    synchronized void open() {
        if (open) {
            throw new BankAccountActionInvalidException("Account is already open");
        }
        open = true;
        balance = 0;
    }

    synchronized long getBalance() {
        if (!open) {
            throw new BankAccountActionInvalidException("Account is not open");
        }
        return balance;
    }

    synchronized void deposit(long amount) {
        if (!open) {
            throw new BankAccountActionInvalidException("Account is not open");
        }
        if (amount < 0) {
            throw new BankAccountActionInvalidException("Cannot deposit a negative amount");
        }
        balance += amount;
    }

    synchronized void withdraw(long amount) {
        if (!open) {
            throw new BankAccountActionInvalidException("Account is not open");
        }
        if (amount < 0) {
            throw new BankAccountActionInvalidException("Cannot withdraw a negative amount");
        }
        if (amount > balance) {
            throw new BankAccountActionInvalidException("Cannot withdraw more than the balance");
        }
        balance -= amount;
    }

    synchronized void close() {
        if (!open) {
            throw new BankAccountActionInvalidException("Account is not open");
        }
        open = false;
        balance = 0;
    }
}
