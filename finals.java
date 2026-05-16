import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class finals {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc_practice";
        String user = "root";
        String password = "Abdullah";

        // Query 1: Simple Statement to list all students
        String allStudentsSql = "SELECT id, name, email FROM students";

        // Query 2: PreparedStatement to find students enrolled in a specific course with a specific grade
        String filterSql = "SELECT s.id, s.name, e.grade, c.title " +
                           "FROM students s " +
                           "JOIN enrollments e ON s.id = e.student_id " +
                           "JOIN courses c ON e.course_id = c.id " +
                           "WHERE c.id = ? AND e.grade = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to jdbc_practice database successfully.\n");

            // --- 1. Execute Simple Statement ---
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(allStudentsSql)) {

                System.out.println("=== All Students ===");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    System.out.println(id + " | " + name + " | " + email);
                }
                System.out.println();
            }

            // --- 2. Execute Parameterized PreparedStatement ---
            try (PreparedStatement pstmt = conn.prepareStatement(filterSql)) {
                // Parameter 1: course_id = 2 (Java Programming)
                pstmt.setInt(1, 2); 
                // Parameter 2: grade = 'A-'
                pstmt.setString(2, "A-"); 

                try (ResultSet filtered = pstmt.executeQuery()) {
                    System.out.println("=== Filtered Enrolled Students (Course ID: 2, Grade: A-) ===");
                    
                    // Track if any records were returned
                    boolean hasRecords = false;
                    while (filtered.next()) {
                        hasRecords = true;
                        int id = filtered.getInt("id");
                        String name = filtered.getString("name");
                        String courseTitle = filtered.getString("title");
                        String grade = filtered.getString("grade");
                        
                        System.out.println(id + " | " + name + " | " + courseTitle + " | Grade: " + grade);
                    }
                    
                    if (!hasRecords) {
                        System.out.println("No matching enrollment records found.");
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error occurred!");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("General error occurred!");
            e.printStackTrace();
        }
    }
}