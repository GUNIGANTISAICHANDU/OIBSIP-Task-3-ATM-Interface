import java.util.ArrayList;
import java.util.Scanner;

// Account class
class Account {
    private String userId;
    private String userPin;
    private double balance;

    public Account(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public void transfer(Account targetAccount, double amount) {
        if (balance >= amount) {
            balance -= amount;
            targetAccount.deposit(amount);
        } else {
            System.out.println("Insufficient balance to transfer!");
        }
    }
}

// Transaction class
class Transaction {
    private String type;
    private double amount;
    private double balanceAfterTransaction;

    public Transaction(String type, double amount, double balanceAfterTransaction) {
        this.type = type;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    @Override
    public String toString() {
        return type + ": " + amount + ", Balance After Transaction: " + balanceAfterTransaction;
    }
}

// ATM class
class ATM {
    private Account currentAccount;
    private ArrayList<Transaction> transactionHistory;

    public ATM(Account account) {
        this.currentAccount = account;
        this.transactionHistory = new ArrayList<>();
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transaction history.");
        } else {
            for (Transaction transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public void deposit(double amount) {
        currentAccount.deposit(amount);
        transactionHistory.add(new Transaction("Deposit", amount, currentAccount.getBalance()));
        System.out.println("Deposit successful! Current balance: " + currentAccount.getBalance());
    }

    public void withdraw(double amount) {
        double prevBalance = currentAccount.getBalance();
        currentAccount.withdraw(amount);
        if (prevBalance != currentAccount.getBalance()) {
            transactionHistory.add(new Transaction("Withdraw", amount, currentAccount.getBalance()));
            System.out.println("Withdrawal successful! Current balance: " + currentAccount.getBalance());
        }
    }

    public void transfer(Account targetAccount, double amount) {
        double prevBalance = currentAccount.getBalance();
        currentAccount.transfer(targetAccount, amount);
        if (prevBalance != currentAccount.getBalance()) {
            transactionHistory.add(new Transaction("Transfer", amount, currentAccount.getBalance()));
            System.out.println("Transfer successful! Current balance: " + currentAccount.getBalance());
        }
    }

    public void quit() {
        System.out.println("Thank you for using the ATM. Goodbye!");
    }
}

// ATMSystem class
class ATMSystem {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Creating two sample accounts
        Account account1 = new Account("user1", "pin1", 1000.0);
        Account account2 = new Account("user2", "pin2", 500.0);

        System.out.println("Welcome to the ATM System!");

        // Authentication
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String userPin = scanner.nextLine();

        // Verify user login
        Account currentAccount = null;
        if (userId.equals(account1.getUserId()) && userPin.equals(account1.getUserPin())) {
            currentAccount = account1;
        } else if (userId.equals(account2.getUserId()) && userPin.equals(account2.getUserPin())) {
            currentAccount = account2;
        } else {
            System.out.println("Invalid User ID or PIN. Exiting system.");
            return;
        }

        // Initialize ATM with the authenticated account
        ATM atm = new ATM(currentAccount);

        // ATM operation loop
        while (true) {
            System.out.println("\nATM Operations:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    atm.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    atm.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    atm.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    atm.transfer(account2, transferAmount); // Transfer to account2 as an example
                    break;
                case 5:
                    atm.quit();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
