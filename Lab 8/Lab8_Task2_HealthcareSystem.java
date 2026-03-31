import java.util.Scanner;

abstract class Patient {
    protected String name;
    protected int age;
    protected String condition;

    public Patient(String name, int age, String condition) {
        this.name = name;
        this.age = age;
        this.condition = condition;
    }

    public abstract String evaluateHealth();

    public String getName() {
        return name;
    }

    public String toString() {
    return name + " | Age: " + age + " | Condition: " + condition;
    }
}

class CriticalPatient extends Patient {
    private int heartRate;

    public CriticalPatient(String name, int age, String condition, int heartRate) {
        super(name, age, condition);
        this.heartRate = heartRate;
    }

    @Override
    public String evaluateHealth() {
        if (heartRate > 120 || heartRate < 50) {
                return "CRITICAL - Immediate attention required!";
        }
        return "Stable but under observation.";
    }
}

class StablePatient extends Patient {
    private double temperature;

    public StablePatient(String name, int age, String condition, double temperature) {
        super(name, age, condition);
        this.temperature = temperature;
    }

    @Override
    public String evaluateHealth() {
        if (temperature > 38.5) {
            return "Fever detected - Monitor closely.";
        }
            return "Stable - Routine monitoring.";
    }
}

class RecoveryPatient extends Patient {
    private int recoveryDays;

    public RecoveryPatient(String name, int age, String condition, int recoveryDays) {
        super(name, age, condition);
        this.recoveryDays = recoveryDays;
    }

    @Override
    public String evaluateHealth() {
        if (recoveryDays < 3) {
            return "Early recovery - Needs close care.";
        }
        return "Recovery progressing well.";
    }
}

public class Lab8_Task2_HealthcareSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Patient[] patients = new Patient[3];

        System.out.println("=== Healthcare Monitoring System ===");

        for (int i = 0; i < 3; i++) {
            System.out.println("\nPatient " + (i + 1) + " type (1=Critical, 2=Stable, 3=Recovery): ");
            int type = sc.nextInt();
            sc.nextLine();
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Age: ");
            int age = sc.nextInt();
            sc.nextLine();
            System.out.print("Condition: ");
            String condition = sc.nextLine();

            if (type == 1) {
                System.out.print("Heart Rate: ");
                int hr = sc.nextInt();
                patients[i] = new CriticalPatient(name, age, condition, hr);
            } else if (type == 2) {
                System.out.print("Temperature (C): ");
                double temp = sc.nextDouble();
                patients[i] = new StablePatient(name, age, condition, temp);
            } else {
                System.out.print("Recovery Days: ");
                int days = sc.nextInt();
                patients[i] = new RecoveryPatient(name, age, condition, days);
            }
        }

        System.out.println("\n=== Health Status Summary ===");
        for (Patient p : patients) {
            System.out.println(p);
            System.out.println("Status: " + p.evaluateHealth());
            if (p.evaluateHealth().contains("CRITICAL")) {
                System.out.println("*** ALERT: " + p.getName() + " needs immediate help! ***");
            }
            System.out.println("---");
        }
        sc.close();
    }
}