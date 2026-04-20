import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String URL  = "jdbc:mysql://127.0.0.1:3306/movierentalsystem";
    static final String USER = "root";
    static final String PASS = "Abdullah";

    // ─── CONNECTION ───────────────────────────────────────────────
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // ─── MENU ─────────────────────────────────────────────────────
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("✅ Connected to MovieRentalSystem!");

        do {
            System.out.println("\n==============================");
            System.out.println("   Movie Rental System Menu   ");
            System.out.println("==============================");
            System.out.println("1. Insert Record");
            System.out.println("2. View Records");
            System.out.println("3. Update Record");
            System.out.println("4. Delete Record");
            System.out.println("5. View Joined Data");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> insertMenu(sc);
                case 2 -> viewMenu(sc);
                case 3 -> updateRecord(sc);
                case 4 -> deleteRecord(sc);
                case 5 -> viewJoinedData();
                case 6 -> System.out.println("Exiting... Goodbye!");
                default -> System.out.println("Invalid choice, try again.");
            }
        } while (choice != 6);

        sc.close();
    }

    // ─── INSERT MENU ──────────────────────────────────────────────
    static void insertMenu(Scanner sc) {
        System.out.println("\n-- Insert Into --");
        System.out.println("1. Users");
        System.out.println("2. Movies");
        System.out.println("3. Rentals");
        System.out.println("4. Payments");
        System.out.print("Choose table: ");
        int t = sc.nextInt();
sc.nextLine(); // flush
switch (t) {
            case 1 -> insertUser(sc);
            case 2 -> insertMovie(sc);
            case 3 -> insertRental(sc);
            case 4 -> insertPayment(sc);
            default -> System.out.println("Invalid.");
        }
    }

    static void insertUser(Scanner sc) {
        sc.nextLine();
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Join Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Subscription Plan (Basic/Premium/Gold): ");
        String plan = sc.nextLine();

        String sql = "INSERT INTO Users (FullName, Email, JoinDate, SubscriptionPlan) VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, date);
            ps.setString(4, plan);
            ps.executeUpdate();
            System.out.println("✅ User inserted!");
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    static void insertMovie(Scanner sc) {
        sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Genre: ");
        String genre = sc.nextLine();
        System.out.print("Release Year: ");
        int year = sc.nextInt();
        System.out.print("Rental Price: ");
        double price = sc.nextDouble();
        System.out.print("Stock Count: ");
        int stock = sc.nextInt();

        String sql = "INSERT INTO Movies (Title, Genre, ReleaseYear, RentalPrice, StockCount) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, genre);
            ps.setInt(3, year);
            ps.setDouble(4, price);
            ps.setInt(5, stock);
            ps.executeUpdate();
            System.out.println("✅ Movie inserted!");
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    static void insertRental(Scanner sc) {
        sc.nextLine();
        System.out.print("User ID: ");
        int uid = sc.nextInt();
        System.out.print("Movie ID: ");
        int mid = sc.nextInt();
        sc.nextLine();
        System.out.print("Rental Date (YYYY-MM-DD): ");
        String rdate = sc.nextLine();
        System.out.print("Return Date (YYYY-MM-DD or leave blank): ");
        String retdate = sc.nextLine();
        System.out.print("Status (Rented/Returned): ");
        String status = sc.nextLine();

        String sql = "INSERT INTO Rentals (UserID, MovieID, RentalDate, ReturnDate, Status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, uid);
            ps.setInt(2, mid);
            ps.setString(3, rdate);
            ps.setString(4, retdate.isEmpty() ? null : retdate);
            ps.setString(5, status);
            ps.executeUpdate();
            System.out.println("✅ Rental inserted!");
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    static void insertPayment(Scanner sc) {
        sc.nextLine();
        System.out.print("Rental ID: ");
        int rid = sc.nextInt();
        System.out.print("Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.print("Payment Method (Card/Cash/Online): ");
        String method = sc.nextLine();
        System.out.print("Transaction Date (YYYY-MM-DD): ");
        String tdate = sc.nextLine();

        String sql = "INSERT INTO Payments (RentalID, Amount, PaymentMethod, TransactionDate) VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, rid);
            ps.setDouble(2, amount);
            ps.setString(3, method);
            ps.setString(4, tdate);
            ps.executeUpdate();
            System.out.println("✅ Payment inserted!");
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // ─── VIEW MENU ────────────────────────────────────────────────
    static void viewMenu(Scanner sc) {
        System.out.println("\n-- View Records From --");
        System.out.println("1. Users");
        System.out.println("2. Movies");
        System.out.println("3. Rentals");
        System.out.println("4. Payments");
        System.out.print("Choose table: ");
        int t = sc.nextInt();

        String sql = switch (t) {
            case 1 -> "SELECT * FROM Users";
            case 2 -> "SELECT * FROM Movies";
            case 3 -> "SELECT * FROM Rentals";
            case 4 -> "SELECT * FROM Payments";
            default -> null;
        };

        if (sql == null) { System.out.println("Invalid."); return; }

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();

            // print column headers
            for (int i = 1; i <= cols; i++)
                System.out.printf("%-20s", meta.getColumnName(i));
            System.out.println();
            System.out.println("-".repeat(cols * 20));

            // print rows
            while (rs.next()) {
                for (int i = 1; i <= cols; i++)
                    System.out.printf("%-20s", rs.getString(i));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // ─── UPDATE ───────────────────────────────────────────────────
    static void updateRecord(Scanner sc) {
        System.out.println("\n-- Update --");
        System.out.println("1. Update User Subscription Plan");
        System.out.println("2. Update Rental Status");
        System.out.print("Choose: ");
        int t = sc.nextInt();

        if (t == 1) {
            System.out.print("Enter UserID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("New Subscription Plan: ");
            String plan = sc.nextLine();

            String sql = "UPDATE Users SET SubscriptionPlan = ? WHERE UserID = ?";
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, plan);
                ps.setInt(2, id);
                int rows = ps.executeUpdate();
                System.out.println(rows > 0 ? "✅ Updated!" : "❌ User not found.");
            } catch (SQLException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }

        } else if (t == 2) {
            System.out.print("Enter RentalID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("New Status (Rented/Returned): ");
            String status = sc.nextLine();

            String sql = "UPDATE Rentals SET Status = ? WHERE RentalID = ?";
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, status);
                ps.setInt(2, id);
                int rows = ps.executeUpdate();
                System.out.println(rows > 0 ? "✅ Updated!" : "❌ Rental not found.");
            } catch (SQLException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────
    static void deleteRecord(Scanner sc) {
        System.out.println("\n-- Delete --");
        System.out.println("1. Delete User (removes their rentals too)");
        System.out.println("2. Delete Movie");
        System.out.print("Choose: ");
        int t = sc.nextInt();

        if (t == 1) {
            System.out.print("Enter UserID to delete: ");
            int id = sc.nextInt();

            try (Connection con = getConnection()) {
                con.setAutoCommit(false);
                try {
                    // delete payments linked to user's rentals first
                    PreparedStatement ps1 = con.prepareStatement(
                        "DELETE FROM Payments WHERE RentalID IN (SELECT RentalID FROM Rentals WHERE UserID = ?)");
                    ps1.setInt(1, id);
                    ps1.executeUpdate();

                    // delete rentals
                    PreparedStatement ps2 = con.prepareStatement(
                        "DELETE FROM Rentals WHERE UserID = ?");
                    ps2.setInt(1, id);
                    ps2.executeUpdate();

                    // delete user
                    PreparedStatement ps3 = con.prepareStatement(
                        "DELETE FROM Users WHERE UserID = ?");
                    ps3.setInt(1, id);
                    int rows = ps3.executeUpdate();

                    con.commit();
                    System.out.println(rows > 0 ? "✅ User deleted!" : "❌ User not found.");
                } catch (SQLException e) {
                    con.rollback();
                    System.out.println("❌ Error (rolled back): " + e.getMessage());
                }
            } catch (SQLException e) {
                System.out.println("❌ Connection error: " + e.getMessage());
            }

        } else if (t == 2) {
            System.out.print("Enter MovieID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM Movies WHERE MovieID = ?";
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                System.out.println(rows > 0 ? "✅ Movie deleted!" : "❌ Movie not found.");
            } catch (SQLException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    // ─── JOIN QUERY ───────────────────────────────────────────────
    static void viewJoinedData() {
        String sql = """
            SELECT Users.FullName, Movies.Title, Rentals.RentalDate,
                   Rentals.ReturnDate, Rentals.Status, Payments.Amount
            FROM Rentals
            JOIN Users   ON Rentals.UserID  = Users.UserID
            JOIN Movies  ON Rentals.MovieID = Movies.MovieID
            LEFT JOIN Payments ON Rentals.RentalID = Payments.RentalID
            """;

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- Rental History (Joined) ---");
            System.out.printf("%-15s %-15s %-12s %-12s %-10s %-10s%n",
                "User", "Movie", "RentalDate", "ReturnDate", "Status", "Amount");
            System.out.println("-".repeat(75));

            while (rs.next()) {
                System.out.printf("%-15s %-15s %-12s %-12s %-10s %-10s%n",
                    rs.getString("FullName"),
                    rs.getString("Title"),
                    rs.getString("RentalDate"),
                    rs.getString("ReturnDate") != null ? rs.getString("ReturnDate") : "N/A",
                    rs.getString("Status"),
                    rs.getString("Amount") != null ? rs.getString("Amount") : "Unpaid");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}