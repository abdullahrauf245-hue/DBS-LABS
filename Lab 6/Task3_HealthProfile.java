// Task#3: HealthProfile Class
// File: Task3_HealthProfile.java

import java.util.Scanner;

public class Task3_HealthProfile {

    // Instance variables
    private String firstName;
    private String lastName;
    private String gender;
    private int birthMonth;
    private int birthDay;
    private int birthYear;
    private double heightInches;
    private double weightPounds;

    // Constructor
    public Task3_HealthProfile(String firstName, String lastName, String gender,
                               int birthMonth, int birthDay, int birthYear,
                               double heightInches, double weightPounds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.birthYear = birthYear;
        this.heightInches = heightInches;
        this.weightPounds = weightPounds;
    }

    // Getters and Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getFirstName() { return firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getLastName() { return lastName; }

    public void setGender(String gender) { this.gender = gender; }
    public String getGender() { return gender; }

    public void setBirthMonth(int birthMonth) { this.birthMonth = birthMonth; }
    public int getBirthMonth() { return birthMonth; }

    public void setBirthDay(int birthDay) { this.birthDay = birthDay; }
    public int getBirthDay() { return birthDay; }

    public void setBirthYear(int birthYear) { this.birthYear = birthYear; }
    public int getBirthYear() { return birthYear; }

    public void setHeightInches(double heightInches) { this.heightInches = heightInches; }
    public double getHeightInches() { return heightInches; }

    public void setWeightPounds(double weightPounds) { this.weightPounds = weightPounds; }
    public double getWeightPounds() { return weightPounds; }

    // Logic Methods
    public int calculateAge() {
        // Updated to current year 2026
        int currentYear = 2026; 
        int currentMonth = 2;
        int currentDay = 16;

        int age = currentYear - birthYear;
        if (birthMonth > currentMonth || (birthMonth == currentMonth && birthDay > currentDay)) {
            age--;
        }
        return age;
    }

    public int calculateMaxHeartRate() {
        return 220 - calculateAge();
    }

    public String calculateTargetHeartRate() {
        int maxHR = calculateMaxHeartRate();
        int minTarget = (int) (maxHR * 0.50);
        int maxTarget = (int) (maxHR * 0.85);
        return minTarget + " - " + maxTarget;
    }

    public double calculateBMI() {
        return (weightPounds / (heightInches * heightInches)) * 703;
    }

    public void displayBMIChart() {
        System.out.println("\n=== BMI Values Chart ===");
        System.out.println("BMI Categories:");
        System.out.println("Underweight:    BMI < 18.5");
        System.out.println("Normal weight:  BMI = 18.5 - 24.9");
        System.out.println("Overweight:     BMI = 25 - 29.9");
        System.out.println("Obesity:        BMI >= 30");
    }

    public String getBMICategory() {
        double bmi = calculateBMI();
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal weight";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }

    // Main Method
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("--- Enter Health Profile Information ---");
        
        System.out.print("First Name: ");
        String fName = input.nextLine();
        
        System.out.print("Last Name: ");
        String lName = input.nextLine();
        
        System.out.print("Gender: ");
        String gender = input.nextLine();
        
        System.out.print("Birth Month (1-12): ");
        int month = input.nextInt();
        
        System.out.print("Birth Day: ");
        int day = input.nextInt();
        
        System.out.print("Birth Year: ");
        int year = input.nextInt();
        
        System.out.print("Height (inches): ");
        double height = input.nextDouble();
        
        System.out.print("Weight (pounds): ");
        double weight = input.nextDouble();

        // Create the HealthProfile object
        Task3_HealthProfile profile = new Task3_HealthProfile(fName, lName, gender, 
                                                              month, day, year, 
                                                              height, weight);

        // Display results with formatting
        System.out.println("\n===============================");
        System.out.println("      HEALTH REPORT CARD       ");
        System.out.println("===============================");
        System.out.printf("Patient Name: %s %s\n", profile.getFirstName(), profile.getLastName());
        System.out.printf("Gender:       %s\n", profile.getGender());
        System.out.printf("Age:          %d years\n", profile.calculateAge());
        System.out.printf("BMI Score:    %.2f (%s)\n", profile.calculateBMI(), profile.getBMICategory());
        System.out.printf("Max HR:       %d bpm\n", profile.calculateMaxHeartRate());
        System.out.printf("Target Range: %s bpm\n", profile.calculateTargetHeartRate());

        profile.displayBMIChart();

        // Properly closing the resource
        input.close();
    }
}
