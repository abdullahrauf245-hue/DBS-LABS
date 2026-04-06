import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// ═══════════════════════════════════════════════════════════════
//  TASK 2 — HOSPITAL SYSTEM
// ═══════════════════════════════════════════════════════════════

enum Gender { MALE, FEMALE }

// ── Patient ────────────────────────────────────────────────────
class Patient {
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private LocalDate admissionDate;
    private String report;
    private Doctor treatingDoctor;
    private int daysOfStay;

    public Patient(String name, LocalDate birthDate, Gender gender,
                   LocalDate admissionDate, String report,
                   Doctor treatingDoctor, int daysOfStay) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.admissionDate = admissionDate;
        this.report = report;
        this.treatingDoctor = treatingDoctor;
        this.daysOfStay = daysOfStay;
    }

    public String getName()             { return name; }
    public LocalDate getBirthDate()     { return birthDate; }
    public Gender getGender()           { return gender; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public String getReport()           { return report; }
    public Doctor getTreatingDoctor()   { return treatingDoctor; }
    public int getDaysOfStay()          { return daysOfStay; }

    public void setName(String name)            { this.name = name; }
    public void setBirthDate(LocalDate d)       { this.birthDate = d; }
    public void setGender(Gender g)             { this.gender = g; }
    public void setAdmissionDate(LocalDate d)   { this.admissionDate = d; }
    public void setReport(String report)        { this.report = report; }
    public void setTreatingDoctor(Doctor d)     { this.treatingDoctor = d; }
    public void setDaysOfStay(int days)         { this.daysOfStay = days; }

    @Override
    public String toString() {
        return "Patient{name='" + name + "', gender=" + gender +
               ", admitted=" + admissionDate + ", days=" + daysOfStay +
               ", report='" + report + "'}";
    }
}

// ── TeamMember (abstract base) ─────────────────────────────────
abstract class TeamMember {
    private String name;
    private String id;
    private Gender gender;
    private LocalDate joinDate;
    private static final int MAX_WORKING_HOURS = 12;

    public TeamMember(String name, String id, Gender gender, LocalDate joinDate) {
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.joinDate = joinDate;
    }

    public int getMaxWorkingHours() { return MAX_WORKING_HOURS; }
    public String getName()         { return name; }
    public String getId()           { return id; }
    public Gender getGender()       { return gender; }
    public LocalDate getJoinDate()  { return joinDate; }

    public void setName(String name)        { this.name = name; }
    public void setId(String id)            { this.id = id; }
    public void setGender(Gender g)         { this.gender = g; }
    public void setJoinDate(LocalDate d)    { this.joinDate = d; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name='" + name + "', id='" + id + "', gender=" + gender + "}";
    }
}

// ── Nurse ──────────────────────────────────────────────────────
class Nurse extends TeamMember {
    public Nurse(String name, String id, Gender gender, LocalDate joinDate) {
        super(name, id, gender, joinDate);
    }
}

// ── Doctor (abstract) ──────────────────────────────────────────
abstract class Doctor extends TeamMember {
    private String specialty;

    public Doctor(String name, String id, Gender gender, LocalDate joinDate, String specialty) {
        super(name, id, gender, joinDate);
        this.specialty = specialty;
    }

    public abstract void treatPatient(Patient patient);

    public void checkPatientReport(Patient patient) {
        System.out.println("[" + getName() + " checks report for " + patient.getName() + "]");
        System.out.println("  Report         : " + patient.getReport());
        System.out.println("  Treating doctor: " +
            (patient.getTreatingDoctor() != null ? patient.getTreatingDoctor().getName() : "None"));
        System.out.println("  Days of stay   : " + patient.getDaysOfStay());
    }

    public String getSpecialty()               { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
}

// ── SeniorDoctor ───────────────────────────────────────────────
class SeniorDoctor extends Doctor {
    private List<Patient> patients = new ArrayList<>();

    public SeniorDoctor(String name, String id, Gender gender,
                        LocalDate joinDate, String specialty) {
        super(name, id, gender, joinDate, specialty);
    }

    public void addPatient(Patient p)    { patients.add(p); }
    public void removePatient(Patient p) { patients.remove(p); }
    public List<Patient> getPatients()   { return patients; }

    @Override
    public void treatPatient(Patient patient) {
        System.out.println("[SeniorDoctor] " + getName() + " treats " + patient.getName() +
            " using evidence-based protocols and coordinates with nursing staff.");
    }
}

// ── Intern ─────────────────────────────────────────────────────
class Intern extends Doctor {
    private SeniorDoctor supervisor;

    public Intern(String name, String id, Gender gender,
                  LocalDate joinDate, String specialty, SeniorDoctor supervisor) {
        super(name, id, gender, joinDate, specialty);
        this.supervisor = supervisor;
    }

    public SeniorDoctor getSupervisor()       { return supervisor; }
    public void setSupervisor(SeniorDoctor s) { this.supervisor = s; }

    @Override
    public void treatPatient(Patient patient) {
        System.out.println("[Intern] " + getName() + " treats " + patient.getName() +
            " under supervision of Dr. " + supervisor.getName() + ".");
    }
}

// ── Surgeon ────────────────────────────────────────────────────
class Surgeon extends Doctor {
    private List<Patient> patients = new ArrayList<>();

    public Surgeon(String name, String id, Gender gender,
                   LocalDate joinDate, String specialty) {
        super(name, id, gender, joinDate, specialty);
    }

    public void addPatient(Patient p)    { patients.add(p); }
    public void removePatient(Patient p) { patients.remove(p); }
    public List<Patient> getPatients()   { return patients; }

    @Override
    public void treatPatient(Patient patient) {
        System.out.println("[Surgeon] " + getName() + " treats " + patient.getName() +
            " by performing a surgical procedure in the operating theatre.");
    }
}

// ── Department ─────────────────────────────────────────────────
class Department {
    private String name;
    private List<TeamMember> staff = new ArrayList<>();

    public Department(String name) { this.name = name; }

    public void addStaff(TeamMember m) {
        staff.add(m);
        System.out.println("  Added " + m.getName() + " to " + name);
    }

    public void removeStaff(TeamMember m) {
        if (staff.remove(m))
            System.out.println("  Removed " + m.getName() + " from " + name);
        else
            System.out.println("  " + m.getName() + " not found in " + name);
    }

    public String getName()            { return name; }
    public void setName(String name)   { this.name = name; }
    public List<TeamMember> getStaff() { return staff; }

    public void printStaff() {
        System.out.println("  Department: " + name);
        for (TeamMember m : staff) System.out.println("    - " + m);
    }
}

// ── Hospital ───────────────────────────────────────────────────
class Hospital {
    private String name;
    private String address;
    private List<Patient> patients       = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();

    public Hospital(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addDepartment(Department d) {
        departments.add(d);
        System.out.println("Department '" + d.getName() + "' added to " + name);
    }

    public void addPatient(Patient p) {
        patients.add(p);
        System.out.println("Patient '" + p.getName() + "' admitted to " + name);
    }

    public void removePatient(Patient p) {
        if (patients.remove(p))
            System.out.println("Patient '" + p.getName() + "' discharged from " + name);
        else
            System.out.println("Patient '" + p.getName() + "' not found.");
    }

    public String getName()                  { return name; }
    public void setName(String name)         { this.name = name; }
    public String getAddress()               { return address; }
    public void setAddress(String address)   { this.address = address; }
    public List<Patient> getPatients()       { return patients; }
    public List<Department> getDepartments() { return departments; }

    public void printDetails() {
        System.out.println("\n============================");
        System.out.println("Hospital : " + name);
        System.out.println("Address  : " + address);
        System.out.println("Patients : " + patients.size());
        for (Department d : departments) d.printStaff();
        System.out.println("============================\n");
    }
}

// ═══════════════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════════════
public class Task2_Hospital {
    public static void main(String[] args) {

        // 1. Create hospital
        Hospital hospital = new Hospital("City General Hospital", "123 Main Street, Rawalpindi");

        // 2. Create departments
        Department cardiology = new Department("Cardiology");
        Department pediatrics = new Department("Pediatrics");
        hospital.addDepartment(cardiology);
        hospital.addDepartment(pediatrics);

        // 3. Create staff
        SeniorDoctor drAli = new SeniorDoctor(
            "Dr. Ali Khan", "D001", Gender.MALE,
            LocalDate.of(2015, 3, 10), "Cardiology");

        Intern drSara = new Intern(
            "Dr. Sara Malik", "D002", Gender.FEMALE,
            LocalDate.of(2023, 8, 1), "Cardiology", drAli);

        Surgeon drOmar = new Surgeon(
            "Dr. Omar Riaz", "D003", Gender.MALE,
            LocalDate.of(2010, 6, 15), "Pediatric Surgery");

        Nurse nurseAyesha = new Nurse(
            "Ayesha Noor", "N001", Gender.FEMALE,
            LocalDate.of(2020, 1, 20));

        // 4. Add staff to departments
        System.out.println("\n--- Adding staff ---");
        cardiology.addStaff(drAli);
        cardiology.addStaff(drSara);
        cardiology.addStaff(nurseAyesha);
        pediatrics.addStaff(drOmar);

        // 5. Create and admit patients
        Patient p1 = new Patient(
            "Ahmed Raza", LocalDate.of(1990, 5, 22), Gender.MALE,
            LocalDate.of(2024, 4, 1),
            "Diagnosis: Hypertension, prescribed medication", drAli, 5);

        Patient p2 = new Patient(
            "Fatima Shah", LocalDate.of(2010, 11, 3), Gender.FEMALE,
            LocalDate.of(2024, 4, 2),
            "Diagnosis: Appendicitis, surgery required", drOmar, 7);

        System.out.println("\n--- Admitting patients ---");
        hospital.addPatient(p1);
        hospital.addPatient(p2);

        // 6. Assign patients to doctors
        drAli.addPatient(p1);
        drOmar.addPatient(p2);

        // 7. Polymorphism — treatPatient()
        System.out.println("\n--- Treating patients (Polymorphism) ---");
        Doctor[] doctors = { drAli, drSara, drOmar };
        for (Doctor doc : doctors) doc.treatPatient(p1);
        drOmar.treatPatient(p2);

        // 8. Check patient reports
        System.out.println("\n--- Checking patient reports ---");
        drAli.checkPatientReport(p1);
        drSara.checkPatientReport(p1);
        drOmar.checkPatientReport(p2);

        // 9. Encapsulation demo
        System.out.println("\n--- Encapsulation demo ---");
        System.out.println("Patient name (getter)     : " + p1.getName());
        p1.setReport("Updated: Hypertension controlled, dosage adjusted.");
        System.out.println("Updated report (setter)   : " + p1.getReport());
        System.out.println("Max working hours (Dr.Ali): " + drAli.getMaxWorkingHours() + "h");

        // 10. Remove staff and patient
        System.out.println("\n--- Removing staff and patients ---");
        cardiology.removeStaff(nurseAyesha);
        hospital.removePatient(p1);

        // 11. Final hospital details
        hospital.printDetails();
    }
}