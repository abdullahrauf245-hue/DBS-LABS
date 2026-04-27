import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IssueManagementSystem {
    // Update these values according to your MySQL setup.
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lab12_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Abdullah";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            initializeSchema(connection);
            runMenu(connection, scanner);

        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    private static void runMenu(Connection connection, Scanner scanner) {
        while (true) {
            printMenu();
            System.out.print("Enter your choice: ");
            String choiceInput = scanner.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 8.\n");
                continue;
            }

            switch (choice) {
                case 1:
                    registerUser(connection, scanner);
                    break;
                case 2:
                    createIssue(connection, scanner);
                    break;
                case 3:
                    assignIssue(connection, scanner);
                    break;
                case 4:
                    viewIssuesWithStaff(connection);
                    break;
                case 5:
                    updateIssueStatus(connection, scanner);
                    break;
                case 6:
                    deleteIssue(connection, scanner);
                    break;
                case 7:
                    searchIssues(connection, scanner);
                    break;
                case 8:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select from 1 to 8.\n");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu");
        System.out.println("1. Register User");
        System.out.println("2. Create Issue");
        System.out.println("3. Assign Issue");
        System.out.println("4. View Issues with Staff");
        System.out.println("5. Update Issue Status");
        System.out.println("6. Delete Issue");
        System.out.println("7. Search Issues");
        System.out.println("8. Exit");
    }

    private static void initializeSchema(Connection connection) {
        String createUserTable =
            "CREATE TABLE IF NOT EXISTS `User` ("
                + "user_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(100) NOT NULL,"
                + "email VARCHAR(120) NOT NULL"
                + ")";

        String createIssueTable =
            "CREATE TABLE IF NOT EXISTS `Issue` ("
                + "issue_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "title VARCHAR(150) NOT NULL,"
                + "description TEXT NOT NULL,"
                + "status VARCHAR(30) NOT NULL"
                + ")";

        String createStaffTable =
            "CREATE TABLE IF NOT EXISTS `Staff` ("
                + "staff_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(100) NOT NULL,"
                + "role VARCHAR(80) NOT NULL"
                + ")";

        String createAssignmentTable =
            "CREATE TABLE IF NOT EXISTS `Assignment` ("
                + "assign_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "issue_id INT NOT NULL,"
                + "staff_id INT NOT NULL,"
                + "UNIQUE (issue_id),"
                + "FOREIGN KEY (issue_id) REFERENCES `Issue`(issue_id) ON DELETE RESTRICT,"
                + "FOREIGN KEY (staff_id) REFERENCES `Staff`(staff_id) ON DELETE RESTRICT"
                + ")";

        try (PreparedStatement st1 = connection.prepareStatement(createUserTable);
             PreparedStatement st2 = connection.prepareStatement(createIssueTable);
             PreparedStatement st3 = connection.prepareStatement(createStaffTable);
             PreparedStatement st4 = connection.prepareStatement(createAssignmentTable)) {

            st1.execute();
            st2.execute();
            st3.execute();
            st4.execute();

        } catch (SQLException e) {
            System.out.println("Schema initialization warning: " + e.getMessage());
        }
    }

    private static void registerUser(Connection connection, Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        if (email.isEmpty()) {
            System.out.println("Email cannot be empty.");
            return;
        }

        String sql = "INSERT INTO `User` (name, email) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "User registered successfully." : "User registration failed.");
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
    }

    private static void createIssue(Connection connection, Scanner scanner) {
        System.out.print("Enter issue title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter issue description: ");
        String description = scanner.nextLine().trim();

        if (title.isEmpty() || description.isEmpty()) {
            System.out.println("Title and description cannot be empty.");
            return;
        }

        String sql = "INSERT INTO `Issue` (title, description, status) VALUES (?, ?, 'Pending')";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, description);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Issue created with status Pending." : "Issue creation failed.");
        } catch (SQLException e) {
            System.out.println("Error creating issue: " + e.getMessage());
        }
    }

    private static void assignIssue(Connection connection, Scanner scanner) {
        if (!showAllIssues(connection) || !showAllStaff(connection)) {
            return;
        }

        int issueId = readIntInput(scanner, "Enter issue_id to assign: ");
        int staffId = readIntInput(scanner, "Enter staff_id to assign to: ");

        if (!issueExists(connection, issueId)) {
            System.out.println("Issue does not exist.");
            return;
        }

        if (!staffExists(connection, staffId)) {
            System.out.println("Staff member does not exist.");
            return;
        }

        if (isIssueAlreadyAssigned(connection, issueId)) {
            System.out.println("This issue is already assigned. Duplicate assignment is not allowed.");
            return;
        }

        String sql = "INSERT INTO `Assignment` (issue_id, staff_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, issueId);
            ps.setInt(2, staffId);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Issue assigned successfully." : "Issue assignment failed.");
        } catch (SQLException e) {
            System.out.println("Error assigning issue: " + e.getMessage());
        }
    }

    private static void viewIssuesWithStaff(Connection connection) {
        String sql = "SELECT i.issue_id, i.title, i.status, s.name AS staff_name "
            + "FROM `Issue` i "
            + "LEFT JOIN `Assignment` a ON i.issue_id = a.issue_id "
            + "LEFT JOIN `Staff` s ON a.staff_id = s.staff_id "
            + "ORDER BY i.issue_id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hasData = false;
            System.out.println("\nIssue ID | Title | Status | Staff Name");
            System.out.println("----------------------------------------");
            while (rs.next()) {
                hasData = true;
                String staffName = rs.getString("staff_name");
                if (staffName == null) {
                    staffName = "Unassigned";
                }
                System.out.printf("%d | %s | %s | %s%n",
                    rs.getInt("issue_id"),
                    rs.getString("title"),
                    rs.getString("status"),
                    staffName);
            }

            if (!hasData) {
                System.out.println("No issues found.");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing issues with staff: " + e.getMessage());
        }
    }

    private static void updateIssueStatus(Connection connection, Scanner scanner) {
        int issueId = readIntInput(scanner, "Enter issue_id to update status: ");

        String findSql = "SELECT status FROM `Issue` WHERE issue_id = ?";
        try (PreparedStatement findPs = connection.prepareStatement(findSql)) {
            findPs.setInt(1, issueId);
            try (ResultSet rs = findPs.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Issue not found.");
                    return;
                }

                String currentStatus = rs.getString("status");
                String nextStatus = getNextStatus(currentStatus);

                if (nextStatus == null) {
                    System.out.println("Status cannot be updated further from: " + currentStatus);
                    return;
                }

                String updateSql = "UPDATE `Issue` SET status = ? WHERE issue_id = ?";
                try (PreparedStatement updatePs = connection.prepareStatement(updateSql)) {
                    updatePs.setString(1, nextStatus);
                    updatePs.setInt(2, issueId);
                    int rows = updatePs.executeUpdate();
                    System.out.println(rows > 0
                        ? "Issue status updated: " + currentStatus + " -> " + nextStatus
                        : "Issue status update failed.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updating issue status: " + e.getMessage());
        }
    }

    private static String getNextStatus(String currentStatus) {
        if ("Pending".equalsIgnoreCase(currentStatus)) {
            return "In Progress";
        }
        if ("In Progress".equalsIgnoreCase(currentStatus)) {
            return "Resolved";
        }
        return null;
    }

    private static void deleteIssue(Connection connection, Scanner scanner) {
        int issueId = readIntInput(scanner, "Enter issue_id to delete: ");

        if (!issueExists(connection, issueId)) {
            System.out.println("Issue not found.");
            return;
        }

        if (isIssueAlreadyAssigned(connection, issueId)) {
            System.out.println("Cannot delete assigned issue");
            return;
        }

        String sql = "DELETE FROM `Issue` WHERE issue_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, issueId);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Issue deleted successfully." : "Issue deletion failed.");
        } catch (SQLException e) {
            System.out.println("Error deleting issue: " + e.getMessage());
        }
    }

    private static void searchIssues(Connection connection, Scanner scanner) {
        System.out.println("Search by:");
        System.out.println("1. Status");
        System.out.println("2. Issue ID");
        int choice = readIntInput(scanner, "Enter search choice: ");

        if (choice == 1) {
            System.out.print("Enter status (Pending/In Progress/Resolved): ");
            String status = scanner.nextLine().trim();
            searchByStatus(connection, status);
        } else if (choice == 2) {
            int issueId = readIntInput(scanner, "Enter issue_id: ");
            searchByIssueId(connection, issueId);
        } else {
            System.out.println("Invalid search choice.");
        }
    }

    private static void searchByStatus(Connection connection, String status) {
        String sql = "SELECT issue_id, title, status FROM `Issue` WHERE status = ? ORDER BY issue_id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                printIssueSearchResults(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error searching issues by status: " + e.getMessage());
        }
    }

    private static void searchByIssueId(Connection connection, int issueId) {
        String sql = "SELECT issue_id, title, status FROM `Issue` WHERE issue_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, issueId);
            try (ResultSet rs = ps.executeQuery()) {
                printIssueSearchResults(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error searching issue by ID: " + e.getMessage());
        }
    }

    private static void printIssueSearchResults(ResultSet rs) throws SQLException {
        boolean hasData = false;
        System.out.println("\nIssue ID | Title | Status");
        System.out.println("-------------------------");
        while (rs.next()) {
            hasData = true;
            System.out.printf("%d | %s | %s%n",
                rs.getInt("issue_id"),
                rs.getString("title"),
                rs.getString("status"));
        }

        if (!hasData) {
            System.out.println("No matching issues found.");
        }
    }

    private static boolean showAllIssues(Connection connection) {
        String sql = "SELECT issue_id, title, status FROM `Issue` ORDER BY issue_id";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hasData = false;
            System.out.println("\nAvailable Issues:");
            while (rs.next()) {
                hasData = true;
                System.out.printf("Issue ID: %d, Title: %s, Status: %s%n",
                    rs.getInt("issue_id"),
                    rs.getString("title"),
                    rs.getString("status"));
            }

            if (!hasData) {
                System.out.println("No issues available.");
            }
            return hasData;

        } catch (SQLException e) {
            System.out.println("Error listing issues: " + e.getMessage());
            return false;
        }
    }

    private static boolean showAllStaff(Connection connection) {
        String sql = "SELECT staff_id, name, role FROM `Staff` ORDER BY staff_id";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hasData = false;
            System.out.println("\nAvailable Staff:");
            while (rs.next()) {
                hasData = true;
                System.out.printf("Staff ID: %d, Name: %s, Role: %s%n",
                    rs.getInt("staff_id"),
                    rs.getString("name"),
                    rs.getString("role"));
            }

            if (!hasData) {
                System.out.println("No staff available.");
                System.out.println("Please insert staff records before assigning issues.");
            }
            return hasData;

        } catch (SQLException e) {
            System.out.println("Error listing staff: " + e.getMessage());
            return false;
        }
    }

    private static boolean issueExists(Connection connection, int issueId) {
        String sql = "SELECT 1 FROM `Issue` WHERE issue_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, issueId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error checking issue existence: " + e.getMessage());
            return false;
        }
    }

    private static boolean staffExists(Connection connection, int staffId) {
        String sql = "SELECT 1 FROM `Staff` WHERE staff_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error checking staff existence: " + e.getMessage());
            return false;
        }
    }

    private static boolean isIssueAlreadyAssigned(Connection connection, int issueId) {
        String sql = "SELECT 1 FROM `Assignment` WHERE issue_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, issueId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error checking assignment: " + e.getMessage());
            return false;
        }
    }

    private static int readIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
}
