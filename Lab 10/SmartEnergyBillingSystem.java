import java.util.ArrayList;
import java.util.List;

class Bill {
    private final int unitsConsumed;
    private final double totalCost;
    private final List<String> breakdown;

    public Bill(int unitsConsumed, double totalCost, List<String> breakdown) {
        this.unitsConsumed = unitsConsumed;
        this.totalCost = totalCost;
        this.breakdown = breakdown;
    }

    public int getUnitsConsumed() {
        return unitsConsumed;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public List<String> getBreakdown() {
        return breakdown;
    }
}

abstract class Customer {
    protected final int id;
    protected final String name;
    protected final int unitsConsumed;

    public Customer(int id, String name, int unitsConsumed) {
        this.id = id;
        this.name = name;
        this.unitsConsumed = unitsConsumed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUnitsConsumed() {
        return unitsConsumed;
    }

    public abstract String getType();

    public abstract Bill generateBill();
}

class ResidentialCustomer extends Customer {
    public ResidentialCustomer(int id, String name, int unitsConsumed) {
        super(id, name, unitsConsumed);
    }

    @Override
    public String getType() {
        return "Residential";
    }

    @Override
    public Bill generateBill() {
        List<String> breakdown = new ArrayList<>();

        int firstSlabUnits = Math.min(unitsConsumed, 100);
        int remainingUnits = Math.max(unitsConsumed - 100, 0);

        double firstSlabCost = firstSlabUnits * 5.0;
        double remainingCost = remainingUnits * 8.0;
        double subtotal = firstSlabCost + remainingCost;
        double subsidy = subtotal * 0.10;
        double total = subtotal - subsidy;

        breakdown.add(String.format("First %d units @ 5/unit   = %.0f", firstSlabUnits, firstSlabCost));
        breakdown.add(String.format("Remaining %d units @ 8/unit = %.0f", remainingUnits, remainingCost));
        breakdown.add(String.format("Subtotal                   = %.0f", subtotal));
        breakdown.add(String.format("Subsidy (-10%%)             = -%.0f", subsidy));

        return new Bill(unitsConsumed, total, breakdown);
    }
}

class CommercialCustomer extends Customer {
    public CommercialCustomer(int id, String name, int unitsConsumed) {
        super(id, name, unitsConsumed);
    }

    @Override
    public String getType() {
        return "Commercial";
    }

    @Override
    public Bill generateBill() {
        List<String> breakdown = new ArrayList<>();

        double usageCost = unitsConsumed * 10.0;
        double serviceCharges = 200.0;
        double taxableAmount = usageCost + serviceCharges;
        double tax = taxableAmount * 0.05;
        double total = taxableAmount + tax;

        breakdown.add(String.format("All units @ 10/unit        = %.0f", usageCost));
        breakdown.add(String.format("Service Charges            = %.0f", serviceCharges));
        breakdown.add(String.format("Tax (5%%)                   = %.0f", tax));

        return new Bill(unitsConsumed, total, breakdown);
    }
}

class IndustrialCustomer extends Customer {
    public IndustrialCustomer(int id, String name, int unitsConsumed) {
        super(id, name, unitsConsumed);
    }

    @Override
    public String getType() {
        return "Industrial";
    }

    @Override
    public Bill generateBill() {
        List<String> breakdown = new ArrayList<>();

        double usageCost = unitsConsumed * 12.0;
        double peakHourCharges = 300.0;
        double taxableAmount = usageCost + peakHourCharges;
        double tax = taxableAmount * 0.10;
        double total = taxableAmount + tax;

        breakdown.add(String.format("All units @ 12/unit        = %.0f", usageCost));
        breakdown.add(String.format("Peak Hour Charges          = %.0f", peakHourCharges));
        breakdown.add(String.format("Tax (10%%)                  = %.0f", tax));

        return new Bill(unitsConsumed, total, breakdown);
    }
}

public class SmartEnergyBillingSystem {
    private static void printCustomerBill(Customer customer) {
        Bill bill = customer.generateBill();

        System.out.println("Customer ID: " + customer.getId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Type: " + customer.getType());
        System.out.println("Units Consumed: " + bill.getUnitsConsumed());
        System.out.println();
        System.out.println("--- Bill Breakdown ---");

        for (String line : bill.getBreakdown()) {
            System.out.println(line);
        }

        System.out.println("---------------------------------");
        System.out.printf("Total Bill                 = %.0f PKR%n", bill.getTotalCost());
    }

    public static void main(String[] args) {
        List<Customer> customers = new ArrayList<>();
        customers.add(new ResidentialCustomer(101, "Ali Khan", 120));
        customers.add(new CommercialCustomer(102, "Sara Ahmed", 120));
        customers.add(new IndustrialCustomer(103, "Usman Tariq", 120));

        System.out.println("========== ELECTRICITY BILLING SYSTEM ==========");

        for (int i = 0; i < customers.size(); i++) {
            printCustomerBill(customers.get(i));
            System.out.println();
            if (i < customers.size() - 1) {
                System.out.println("-----------------------------------------------");
                System.out.println();
            }
        }

        System.out.println("===============================================");
        System.out.println("Summary:");
        System.out.println("Total Customers Processed: " + customers.size());
    }
}
