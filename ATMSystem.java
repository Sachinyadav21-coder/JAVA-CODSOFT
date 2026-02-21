import java.util.ArrayList;
import java.util.Scanner;

// Bank Account Class
class BankAccount {
    private double balance;
    private ArrayList<String> transactionHistory;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        transactionHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        transactionHistory.add("Deposited " + amount);
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        transactionHistory.add("Withdrawn " + amount);
        return true;
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("\n----- Transaction History -----");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}


// ATM Class
class ATM {
    private BankAccount account;
    private String userName;
    private final int PIN = 1234; // Default PIN

    public ATM(BankAccount account, String userName) {
        this.account = account;
        this.userName = userName;
    }

    // PIN Authentication
    public boolean authenticate(int enteredPin) {
        return enteredPin == PIN;
    }

    public void showMenu() {
        System.out.println("\n=================================");
        System.out.println("           ATM MACHINE");
        System.out.println("User    : " + userName);
        System.out.println("Balance : " + account.getBalance());
        System.out.println("=================================");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transaction History");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    public void deposit(double amount) {
        if (account.deposit(amount)) {
            System.out.println(" Deposit successful! " + amount);
        } else {
            System.out.println(" Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println(" Invalid withdrawal amount.");
        } 
        else if (amount > account.getBalance()) {
            System.out.println(" Insufficient balance.");
        } 
        else if (account.withdraw(amount)) {
            System.out.println(" Withdrawal successful! " + amount);
        }
    }

    public void checkBalance() {
        System.out.println(" Current Balance: " + account.getBalance());
    }

    public void showHistory() {
        account.showTransactionHistory();
    }
}


// Main Class
public class ATMSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        BankAccount userAccount = new BankAccount(0);
        ATM atm = new ATM(userAccount, name);

        // PIN Security (3 attempts)
        int attempts = 3;
        boolean accessGranted = false;

        while (attempts > 0) {
            System.out.print("Enter 4-digit PIN: ");
            int enteredPin = sc.nextInt();

            if (atm.authenticate(enteredPin)) {
                accessGranted = true;
                break;
            } else {
                attempts--;
                System.out.println(" Incorrect PIN. Attempts left: " + attempts);
            }
        }

        if (!accessGranted) {
            System.out.println(" Too many incorrect attempts. Account locked.");
            sc.close();
            return;
        }

        int choice;

        do {
            atm.showMenu();

            while (!sc.hasNextInt()) {
                System.out.println(" Invalid input. Enter a number.");
                sc.next();
            }

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    atm.checkBalance();
                    break;

                case 2:
                    System.out.print("Enter amount to deposit: ");
                    atm.deposit(sc.nextDouble());
                    break;

                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    atm.withdraw(sc.nextDouble());
                    break;

                case 4:
                    atm.showHistory();
                    break;

                case 5:
                    System.out.println(" Thank you for using the ATM, " + name + "!");
                    break;

                default:
                    System.out.println(" Invalid choice. Please select 1-5.");
            }

        } while (choice != 5);

        sc.close();
    }
}
