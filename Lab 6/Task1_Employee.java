// Task#1: Employee Class
// File: Task1_Employee.java

public class Task1_Employee {
    // Instance variables
    private String firstName;
    private String lastName;
    private double monthlySalary;

    // Constructor
    public Task1_Employee(String firstName, String lastName, double monthlySalary) {
        this.firstName = firstName;
        this.lastName = lastName;
        // Only set salary if it's positive
        if (monthlySalary > 0) {
            this.monthlySalary = monthlySalary;
        }
    }

    // Set and Get methods for firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    // Set and Get methods for lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    // Set and Get methods for monthlySalary
    public void setMonthlySalary(double monthlySalary) {
        if (monthlySalary > 0) {
            this.monthlySalary = monthlySalary;
        }
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    // Method to get yearly salary
    public double getYearlySalary() {
        return monthlySalary * 12;
    }
}
