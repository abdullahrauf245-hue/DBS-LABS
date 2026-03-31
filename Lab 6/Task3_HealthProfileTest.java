// Task#3: HealthProfile Test Application
// File: Task3_HealthProfileTest.java

import java.util.Scanner;

public class Task3_HealthProfileTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // Prompt for person's information
        System.out.println("=== Health Profile Test Application ===");
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        
        System.out.print("Enter gender (Male/Female): ");
        String gender = input.nextLine();
        
        System.out.print("Enter birth month (1-12): ");
        int birthMonth = input.nextInt();
        
        System.out.print("Enter birth day: ");
        int birthDay = input.nextInt();
        
        System.out.print("Enter birth year: ");
        int birthYear = input.nextInt();
        
        System.out.print("Enter height in inches: ");
        double height = input.nextDouble();
        
        System.out.print("Enter weight in pounds: ");
        double weight = input.nextDouble();
        
        // Create HealthProfile object
        Task3_HealthProfile profile = new Task3_HealthProfile(
            firstName, lastName, gender, 
            birthMonth, birthDay, birthYear,
            height, weight
        );
        
        // Display person's information
        System.out.println("\n=== Person's Information ===");
        System.out.println("First Name: " + profile.getFirstName());
        System.out.println("Last Name: " + profile.getLastName());
        System.out.println("Gender: " + profile.getGender());
        System.out.println("Date of Birth: " + profile.getBirthMonth() + "/" + 
                           profile.getBirthDay() + "/" + profile.getBirthYear());
        System.out.println("Height: " + profile.getHeightInches() + " inches");
        System.out.println("Weight: " + profile.getWeightPounds() + " pounds");
        
        // Calculate and display age in years
        System.out.println("\n=== Age Information ===");
        System.out.println("Age in years: " + profile.calculateAge());
        
        // Calculate and display maximum heart rate
        System.out.println("\n=== Heart Rate Information ===");
        System.out.println("Maximum Heart Rate: " + profile.calculateMaxHeartRate() + " bpm");
        System.out.println("Target Heart Rate Range: " + profile.calculateTargetHeartRate() + " bpm");
        
        // Calculate and display BMI
        System.out.println("\n=== BMI Information ===");
        System.out.printf("BMI: %.2f%n", profile.calculateBMI());
        System.out.println("BMI Category: " + profile.getBMICategory());
        
        // Display BMI values chart
        profile.displayBMIChart();
        
        input.close();
    }
}
