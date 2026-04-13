import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

enum VehicleType {
    BIKE("Bike", 20),
    CAR("Car", 50),
    TRUCK("Truck", 100);

    private final String label;
    private final int hourlyRate;

    VehicleType(String label, int hourlyRate) {
        this.label = label;
        this.hourlyRate = hourlyRate;
    }

    public String getLabel() {
        return label;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }
}

enum SlotType {
    SMALL("Small Slot"),
    MEDIUM("Medium Slot"),
    LARGE("Large Slot");

    private final String label;

    SlotType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

class Vehicle {
    private final String number;
    private final VehicleType type;
    private ParkingSlot slot;
    private LocalTime entryTime;

    public Vehicle(String number, VehicleType type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public VehicleType getType() {
        return type;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public void setSlot(ParkingSlot slot) {
        this.slot = slot;
    }

    public LocalTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalTime entryTime) {
        this.entryTime = entryTime;
    }
}

class ParkingSlot {
    private final String id;
    private final SlotType type;
    private boolean occupied;

    public ParkingSlot(String id, SlotType type) {
        this.id = id;
        this.type = type;
        this.occupied = false;
    }

    public String getId() {
        return id;
    }

    public SlotType getType() {
        return type;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}

class ParkingRecord {
    private final Vehicle vehicle;
    private final LocalTime entry;
    private final LocalTime exit;
    private final long hours;
    private final int totalBill;

    public ParkingRecord(Vehicle vehicle, LocalTime entry, LocalTime exit, long hours, int totalBill) {
        this.vehicle = vehicle;
        this.entry = entry;
        this.exit = exit;
        this.hours = hours;
        this.totalBill = totalBill;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalTime getEntry() {
        return entry;
    }

    public LocalTime getExit() {
        return exit;
    }

    public long getHours() {
        return hours;
    }

    public int getTotalBill() {
        return totalBill;
    }
}

class ParkingLot {
    private final List<ParkingSlot> slots = new ArrayList<>();
    private final List<ParkingRecord> history = new ArrayList<>();
    private int totalVehiclesParked = 0;

    public ParkingLot() {
        slots.add(new ParkingSlot("S1", SlotType.SMALL));
        slots.add(new ParkingSlot("S2", SlotType.SMALL));
        slots.add(new ParkingSlot("S3", SlotType.SMALL));

        slots.add(new ParkingSlot("M1", SlotType.MEDIUM));
        slots.add(new ParkingSlot("M2", SlotType.MEDIUM));
        slots.add(new ParkingSlot("M3", SlotType.MEDIUM));
        slots.add(new ParkingSlot("M4", SlotType.MEDIUM));

        slots.add(new ParkingSlot("L1", SlotType.LARGE));
        slots.add(new ParkingSlot("L2", SlotType.LARGE));
        slots.add(new ParkingSlot("L3", SlotType.LARGE));
    }

    private SlotType requiredSlotType(VehicleType vehicleType) {
        if (vehicleType == VehicleType.BIKE) {
            return SlotType.SMALL;
        }
        if (vehicleType == VehicleType.CAR) {
            return SlotType.MEDIUM;
        }
        return SlotType.LARGE;
    }

    public boolean parkVehicle(Vehicle vehicle, LocalTime entryTime) {
        SlotType needed = requiredSlotType(vehicle.getType());

        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied() && slot.getType() == needed) {
                slot.setOccupied(true);
                vehicle.setSlot(slot);
                vehicle.setEntryTime(entryTime);
                totalVehiclesParked++;
                printEntry(vehicle);
                return true;
            }
        }
        return false;
    }

    public void exitVehicle(Vehicle vehicle, LocalTime exitTime) {
        long hours = Duration.between(vehicle.getEntryTime(), exitTime).toHours();
        int hourlyRate = vehicle.getType().getHourlyRate();
        int base = (int) (hours * hourlyRate);
        int extra = vehicle.getType() == VehicleType.TRUCK ? 200 : 0;
        int total = base + extra;

        history.add(new ParkingRecord(vehicle, vehicle.getEntryTime(), exitTime, hours, total));

        if (vehicle.getSlot() != null) {
            vehicle.getSlot().setOccupied(false);
        }

        printExit(vehicle, exitTime, hours, hourlyRate, extra, total);
    }

    public int getTotalVehiclesParked() {
        return totalVehiclesParked;
    }

    public int getAvailableSlots() {
        int available = 0;
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied()) {
                available++;
            }
        }
        return available;
    }

    private static String formatTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("h:mm a"));
    }

    private void printEntry(Vehicle vehicle) {
        System.out.println("Vehicle Entered:");
        System.out.println("Type: " + vehicle.getType().getLabel());
        System.out.println("Number: " + vehicle.getNumber());
        System.out.println();
        System.out.println("Assigned Slot: " + vehicle.getSlot().getId() + " (" + vehicle.getSlot().getType().getLabel() + ")");
        System.out.println("Entry Time: " + formatTime(vehicle.getEntryTime()));
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println();
    }

    private void printExit(Vehicle vehicle, LocalTime exitTime, long hours, int hourlyRate, int extra, int total) {
        System.out.println("Vehicle: " + vehicle.getNumber() + " (" + vehicle.getType().getLabel() + ")");
        System.out.println("Exit Time: " + formatTime(exitTime));
        System.out.println("Duration: " + hours + " hours");
        System.out.println();
        System.out.println("--- Bill Breakdown ---");
        System.out.println("Rate: " + hourlyRate + "/hour");

        if (extra > 0) {
            System.out.println("Extra Space Charge = " + extra);
            System.out.println("Total = (" + hours + " x " + hourlyRate + ") + " + extra + " = " + total + " PKR");
        } else {
            System.out.println("Total = " + hours + " x " + hourlyRate + " = " + total + " PKR");
        }

        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println();
    }
}

public class SmartParkingSystem {
    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot();

        Vehicle bike = new Vehicle("BK-101", VehicleType.BIKE);
        Vehicle car = new Vehicle("CAR-202", VehicleType.CAR);
        Vehicle truck = new Vehicle("TRK-303", VehicleType.TRUCK);

        System.out.println("========== SMART PARKING SYSTEM ==========");
        System.out.println();

        lot.parkVehicle(bike, LocalTime.of(10, 0));
        lot.parkVehicle(car, LocalTime.of(10, 5));
        lot.parkVehicle(truck, LocalTime.of(10, 10));

        System.out.println("=========================================");
        System.out.println("            VEHICLE EXIT");
        System.out.println("=========================================");
        System.out.println();

        lot.exitVehicle(bike, LocalTime.of(12, 0));
        lot.exitVehicle(car, LocalTime.of(13, 5));
        lot.exitVehicle(truck, LocalTime.of(14, 10));

        System.out.println("=========================================");
        System.out.println("Summary:");
        System.out.println("Total Vehicles Parked: " + lot.getTotalVehiclesParked());
        System.out.println("Available Slots Remaining: " + (10 - lot.getTotalVehiclesParked()));
    }
}
