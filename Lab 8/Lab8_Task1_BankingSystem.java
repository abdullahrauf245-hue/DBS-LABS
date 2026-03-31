import java.util.Scanner;

class BankAccount {
    protected String accountNumber;
    protected String holderName;
    protected double balance;

    public BankAccount(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
    }

    public void withdraw(double amount) {
    if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getHolderName() {
        return holderName;
    }

    public String toString() {
        return holderName + " | Balance: " + balance;
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, String holderName, double balance, double interestRate) {
        super(accountNumber, holderName, balance);
        this.interestRate = interestRate;
    }

    public double calculateInterest() {
        return balance * interestRate / 100;
    }

    public void applyInterest() {
            balance += calculateInterest();
        System.out.println("Interest applied. New balance: " + balance);
    }
}

class CurrentAccount extends BankAccount {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String holderName, double balance, double overdraftLimit) {
        super(accountNumber, holderName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance + overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + " | Balance: " + balance);
        } else {
        System.out.println("Overdraft limit exceeded.");
        }
    }
}

public class Lab8_Task1_BankingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankAccount[] accounts = new BankAccount[3];

        System.out.println("=== Banking System ===");

        for (int i = 0; i < 3; i++) {
            System.out.println("\nAccount " + (i + 1) + " type (1=Savings, 2=Current): ");
            int type = sc.nextInt();
            sc.nextLine();
            System.out.print("Holder Name: ");
            String name = sc.nextLine();
            System.out.print("Account Number: ");
            String accNo = sc.nextLine();
            System.out.print("Initial Balance: ");
            double bal = sc.nextDouble();

            if (type == 1) {
                System.out.print("Interest Rate (%): ");
                double rate = sc.nextDouble();
                SavingsAccount sa = new SavingsAccount(accNo, name, bal, rate);
                sa.applyInterest();
                accounts[i] = sa;
            } else {
                System.out.print("Overdraft Limit: ");
                double limit = sc.nextDouble();
                accounts[i] = new CurrentAccount(accNo, name, bal, limit);
            }
        }

        // Find highest balance and total
        double total = 0;
        BankAccount highest = accounts[0];
        for (BankAccount acc : accounts) {
            total += acc.getBalance();
            if (acc.getBalance() > highest.getBalance()) {
                highest = acc;
            }
        }

        System.out.println("\n=== Summary ===");
        System.out.println("Total Bank Balance: " + total);
        System.out.println("Highest Balance Account: " + highest);
        sc.close();
    }
}