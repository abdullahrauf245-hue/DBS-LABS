import java.util.Scanner;

abstract class Student {
    protected String name;
    protected int rollNo;

    public Student(String name, int rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }

    public abstract double calculateGrade();

    public String getPerformanceCategory() {
        double grade = calculateGrade();
        if (grade >= 85) return "Excellent";
            else if (grade >= 60) return "Good";
        return "Needs Improvement";
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name + " (Roll: " + rollNo + "): Grade=" + calculateGrade() + " [" + getPerformanceCategory() + "]";
    }
}

class ExamStudent extends Student {
    private double midterm;
    private double finalExam;

    public ExamStudent(String name, int rollNo, double midterm, double finalExam) {
        super(name, rollNo);
        this.midterm = midterm;
        this.finalExam = finalExam;
    }

    @Override
    public double calculateGrade() {
            return midterm * 0.4 + finalExam * 0.6;
    }
}

class ProjectStudent extends Student {
    private double quiz;
    private double project;
    private double presentation;

    public ProjectStudent(String name, int rollNo, double quiz, double project, double presentation) {
        super(name, rollNo);
        this.quiz = quiz;
        this.project = project;
        this.presentation = presentation;
    }

    @Override
    public double calculateGrade() {
        return quiz * 0.3 + project * 0.4 + presentation * 0.3;
    }
}

class AttendanceStudent extends Student {
    private double baseGrade;
    private double attendancePercent;

    public AttendanceStudent(String name, int rollNo, double baseGrade, double attendancePercent) {
        super(name, rollNo);
        this.baseGrade = baseGrade;
        this.attendancePercent = attendancePercent;
    }

    @Override
    public double calculateGrade() {
        double bonus = attendancePercent >= 90 ? 5 : 0;
            return baseGrade + bonus;
    }
}

public class Lab8_Task3_GradingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Student[] students = new Student[3];

        System.out.println("=== University Grading System ===");

        for (int i = 0; i < 3; i++) {
            System.out.println("\nStudent " + (i + 1) + " type (1=ExamOnly, 2=Project, 3=Attendance): ");
            int type = sc.nextInt();
            sc.nextLine();
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Roll No: ");
            int roll = sc.nextInt();

            if (type == 1) {
                System.out.print("Midterm marks: ");
                double mid = sc.nextDouble();
                System.out.print("Final exam marks: ");
                double fin = sc.nextDouble();
                students[i] = new ExamStudent(name, roll, mid, fin);
            } else if (type == 2) {
                System.out.print("Quiz marks: ");
                double q = sc.nextDouble();
                System.out.print("Project marks: ");
                double p = sc.nextDouble();
                System.out.print("Presentation marks: ");
                double pr = sc.nextDouble();
                students[i] = new ProjectStudent(name, roll, q, p, pr);
            } else {
                System.out.print("Base grade: ");
                double base = sc.nextDouble();
                System.out.print("Attendance %: ");
                double att = sc.nextDouble();
                students[i] = new AttendanceStudent(name, roll, base, att);
            }
        }

        System.out.println("\n=== Results ===");
        Student top = students[0];
        for (Student s : students) {
            System.out.println(s);
            if (s.calculateGrade() > top.calculateGrade()) {
                top = s;
            }
        }
        System.out.println("\nTop Performer: " + top.getName());
        sc.close();
    }
}