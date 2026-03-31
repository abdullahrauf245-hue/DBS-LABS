// Task#5: QuadraticEquation Class
// File: Task5_QuadraticEquation.java

import java.util.Scanner;

public class Task5_QuadraticEquation {
    private double a;
    private double b;
    private double c;

    // Constructor
    public Task5_QuadraticEquation(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    // Getters
    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }
    // Logic Methods
    public double getDiscriminant() {
        return (b * b) - (4 * a * c);
    }

    public double getRoot1() {
        double disc = getDiscriminant();
        return (disc < 0) ? 0 : (-b + Math.sqrt(disc)) / (2 * a);
    }

    public double getRoot2() {
        double disc = getDiscriminant();
        return (disc < 0) ? 0 : (-b - Math.sqrt(disc)) / (2 * a);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("--- Quadratic Equation Solver ($ax^2 + bx + c = 0$) ---");
        
        System.out.print("Enter a: ");
        double a = input.nextDouble();
        
        // Improvement: Check if the equation is actually quadratic
        if (a == 0) {
            System.out.println("The coefficient 'a' cannot be zero for a quadratic equation.");
            input.close();
            return;
        }

        System.out.print("Enter b: ");
        double b = input.nextDouble();
        System.out.print("Enter c: ");
        double c = input.nextDouble();

        Task5_QuadraticEquation equation = new Task5_QuadraticEquation(a, b, c);
        double discriminant = equation.getDiscriminant();

        System.out.println("\n--- Results ---");
        System.out.printf("Discriminant: %.2f\n", discriminant);

        if (discriminant > 0) {
            System.out.printf("The equation has two roots: %.5f and %.5f\n", 
                              equation.getRoot1(), equation.getRoot2());
        } else if (discriminant == 0) {
            System.out.printf("The equation has one root: %.5f\n", equation.getRoot1());
        } else {
            System.out.println("The equation has no real roots.");
        }

        input.close();
    }
}