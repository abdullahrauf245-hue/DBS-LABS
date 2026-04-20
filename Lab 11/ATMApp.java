import java.util.InputMismatchException;
import java.util.Scanner;

class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

class ATM {
    private double balance;

    public ATM(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }
        balance += amount;
    }

    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance for this withdrawal.");
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    public int divideBalanceBy(int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        return (int) balance / divisor;
    }
}

public class ATMApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM(1000.00);
        boolean running = true;

        System.out.println("=== ATM Machine Simulation ===");
        System.out.printf("Starting balance: %.2f%n", atm.getBalance());

        while (running) {
            printMenu();

            try {
                int choice = readInt(scanner, "Select an option: ");

                switch (choice) {
                    case 1:
                        handleWithdrawal(scanner, atm);
                        break;
                    case 2:
                        handleDeposit(scanner, atm);
                        break;
                    case 3:
                        System.out.printf("Current balance: %.2f%n", atm.getBalance());
                        break;
                    case 4:
                        handleCalculation(scanner, atm);
                        break;
                    case 5:
                        running = false;
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose from 1 to 5.");
                }
            } catch (RuntimeException e) {
                System.out.println("Unexpected runtime error: " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Check Balance");
        System.out.println("4. Balance Calculation (division)");
        System.out.println("5. Exit");
    }

    private static void handleWithdrawal(Scanner scanner, ATM atm) {
        double amount = readDouble(scanner, "Enter amount to withdraw: ");
        try {
            atm.withdraw(amount);
            System.out.printf("Withdrawal successful. New balance: %.2f%n", atm.getBalance());
        } catch (InvalidAmountException | InsufficientBalanceException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }

    private static void handleDeposit(Scanner scanner, ATM atm) {
        double amount = readDouble(scanner, "Enter amount to deposit: ");
        try {
            atm.deposit(amount);
            System.out.printf("Deposit successful. New balance: %.2f%n", atm.getBalance());
        } catch (InvalidAmountException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }

    private static void handleCalculation(Scanner scanner, ATM atm) {
        int divisor = readInt(scanner, "Enter integer divisor: ");
        try {
            int result = atm.divideBalanceBy(divisor);
            System.out.println("Calculation result (int balance / divisor): " + result);
        } catch (ArithmeticException e) {
            System.out.println("Calculation error: " + e.getMessage());
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a whole number.");
                scanner.nextLine();
            }
        }
    }

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.nextLine();
            }
        }
    }
}