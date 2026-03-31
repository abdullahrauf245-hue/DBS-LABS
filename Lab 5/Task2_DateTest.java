// Task#2: Date Test Application
// File: Task2_DateTest.java
import java.util.Scanner;
public class Task2_DateTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Create a Date object using user input
        System.out.println("=== Date Test Application ===");
        System.out.print("Enter month: ");
        int month = input.nextInt();
        System.out.print("Enter day: ");
        int day = input.nextInt();
        System.out.print("Enter year: ");
        int year = input.nextInt();
        // Create Date object
        Task2_Date date = new Task2_Date(month, day, year);
        // Display the date
        System.out.println("\n=== Date Information ===");
        System.out.println("Date: " + date.displayDate());
        // Demonstrate set and get methods
        System.out.println("\n=== Using Get Methods ===");
        System.out.println("Month: " + date.getMonth());
        System.out.println("Day: " + date.getDay());
        System.out.println("Year: " + date.getYear());
        // Test set methods
        System.out.println("\n=== Testing Set Methods ===");
        date.setMonth(12);
        date.setDay(25);
        date.setYear(2023);
        System.out.println("After setting new values:");
        System.out.println("Date: " + date.displayDate());
        input.close();
    }
}
