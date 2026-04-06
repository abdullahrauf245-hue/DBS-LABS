import java.lang.Math;

// ═══════════════════════════════════════════════════════════════
//  TASK 1 — SHAPE HIERARCHY
// ═══════════════════════════════════════════════════════════════

abstract class Shape {
    public abstract double calculateArea();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " -> Area: " + String.format("%.2f", calculateArea());
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) { this.radius = radius; }

    // Overloaded: accepts diameter instead
    public double calculateArea(double diameter) {
        double r = diameter / 2;
        return Math.PI * r * r;
    }

    @Override
    public double calculateArea() { return Math.PI * radius * radius; }

    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }
}

class Square extends Shape {
    private double side;

    public Square(double side) { this.side = side; }

    // Overloaded: accepts width and height (rectangle)
    public double calculateArea(double width, double height) { return width * height; }

    @Override
    public double calculateArea() { return side * side; }

    public double getSide() { return side; }
    public void setSide(double side) { this.side = side; }
}

class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    // Overloaded: Heron's formula given three sides
    public double calculateArea(double a, double b, double c) {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double calculateArea() { return 0.5 * base * height; }

    public double getBase() { return base; }
    public void setBase(double base) { this.base = base; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
}

// ═══════════════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════════════
public class Task1_Shapes {
    public static void main(String[] args) {

        // Polymorphism via Shape array
        Shape[] shapes = { new Circle(5), new Square(4), new Triangle(6, 8) };

        System.out.println("=== Polymorphism via Shape[] ===");
        for (Shape s : shapes) System.out.println(s);

        // Method overloading
        System.out.println("\n=== Method Overloading ===");
        Circle c = new Circle(5);
        System.out.println("Circle  area (radius=5)   : " + String.format("%.2f", c.calculateArea()));
        System.out.println("Circle  area (diameter=10): " + String.format("%.2f", c.calculateArea(10)));

        Square sq = new Square(4);
        System.out.println("Square  area (side=4)     : " + String.format("%.2f", sq.calculateArea()));
        System.out.println("Rect    area (3x7)        : " + String.format("%.2f", sq.calculateArea(3, 7)));

        Triangle t = new Triangle(6, 8);
        System.out.println("Triangle area (b=6, h=8)  : " + String.format("%.2f", t.calculateArea()));
        System.out.println("Triangle area (3,4,5)     : " + String.format("%.2f", t.calculateArea(3, 4, 5)));
    }
}