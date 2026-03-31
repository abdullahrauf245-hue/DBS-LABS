// Task#1: Employee Test Application
// File: Task1_EmployeeTest.java

public class Task1_EmployeeTest {
    public static void main(String[] args) {
        // Create two Employee objects
        Task1_Employee employee1 = new Task1_Employee("John", "Smith", 3000.0);
        Task1_Employee employee2 = new Task1_Employee("Jane", "Doe", 4500.0);

        // Display each object's yearly salary
        System.out.println("=== Initial Yearly Salaries ===");
        System.out.println("Employee 1: " + employee1.getFirstName() + " " + employee1.getLastName());
        System.out.println("Yearly Salary: $" + String.format("%.2f", employee1.getYearlySalary()));
        System.out.println();
        System.out.println("Employee 2: " + employee2.getFirstName() + " " + employee2.getLastName());
        System.out.println("Yearly Salary: $" + String.format("%.2f", employee2.getYearlySalary()));
        System.out.println();

        // Give each Employee a 10% raise
        double raise1 = employee1.getMonthlySalary() * 0.10;
        double raise2 = employee2.getMonthlySalary() * 0.10;
        
        employee1.setMonthlySalary(employee1.getMonthlySalary() + raise1);
        employee2.setMonthlySalary(employee2.getMonthlySalary() + raise2);

        // Display each Employee's yearly salary after 10% raise
        System.out.println("=== After 10% Raise ===");
        System.out.println("Employee 1: " + employee1.getFirstName() + " " + employee1.getLastName());
        System.out.println("Yearly Salary: $" + String.format("%.2f", employee1.getYearlySalary()));
        System.out.println();
        System.out.println("Employee 2: " + employee2.getFirstName() + " " + employee2.getLastName());
        System.out.println("Yearly Salary: $" + String.format("%.2f", employee2.getYearlySalary()));
        
        // Test with negative salary (should not be set)
        System.out.println("\n=== Testing Negative Salary ===");
        Task1_Employee employee3 = new Task1_Employee("Test", "User", -1000.0);
        System.out.println("Monthly Salary (should be 0): $" + String.format("%.2f", employee3.getMonthlySalary()));
    }
}
