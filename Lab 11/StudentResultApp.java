import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

class StudentResultProcessor {
    private static final double MAX_MARKS_PER_SUBJECT = 100.0;
    private final List<Double> marks;

    public StudentResultProcessor() {
        this.marks = new ArrayList<>();
    }

    public void addMark(double mark) throws InvalidMarksException {
        if (mark < 0) {
            throw new InvalidMarksException("Marks cannot be negative.");
        }
        if (mark > MAX_MARKS_PER_SUBJECT) {
            throw new InvalidMarksException("Marks cannot exceed " + MAX_MARKS_PER_SUBJECT + ".");
        }
        marks.add(mark);
    }

    public double calculateTotal() {
        double total = 0;
        for (double mark : marks) {
            total += mark;
        }
        return total;
    }

    public double calculateAverage() {
        if (marks.isEmpty()) {
            throw new ArithmeticException("Cannot calculate average: number of subjects is zero.");
        }
        return calculateTotal() / marks.size();
    }

    public int getSubjectCount() {
        return marks.size();
    }
}

public class StudentResultApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentResultProcessor processor = new StudentResultProcessor();

        System.out.println("=== Student Result Processing System ===");

        int subjectCount = readNonNegativeInt(scanner, "Enter number of subjects: ");

        for (int i = 1; i <= subjectCount; i++) {
            boolean markSaved = false;

            while (!markSaved) {
                double mark = readDouble(scanner, "Enter marks for subject " + i + " (0 - 100): ");
                try {
                    processor.addMark(mark);
                    markSaved = true;
                } catch (InvalidMarksException e) {
                    System.out.println("Invalid marks: " + e.getMessage());
                }
            }
        }

        try {
            double total = processor.calculateTotal();
            double average = processor.calculateAverage();

            System.out.println();
            System.out.println("Result Summary");
            System.out.println("Subjects entered: " + processor.getSubjectCount());
            System.out.printf("Total marks: %.2f%n", total);
            System.out.printf("Average marks: %.2f%n", average);
        } catch (ArithmeticException e) {
            System.out.println("Calculation error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Unexpected runtime error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static int readNonNegativeInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();

                if (value < 0) {
                    System.out.println("Input cannot be negative. Please enter 0 or greater.");
                    continue;
                }

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
                System.out.println("Invalid input. Please enter numeric marks.");
                scanner.nextLine();
            }
        }
    }
}