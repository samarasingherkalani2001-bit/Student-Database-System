import java.sql.*;
import java.util.Scanner;

public class StudentDAO {

    private static final Scanner scanner = new Scanner(System.in);


    public static void insertStudent() {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();
        System.out.print("Enter Gender (Male/Female/Other): ");
        String gender = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();

        String query = "INSERT INTO Students (first_name, last_name, dob, gender, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, dob);
            stmt.setString(4, gender);
            stmt.setString(5, email);
            stmt.setString(6, phoneNumber);

            stmt.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAllStudents() {
        String query = "SELECT * FROM Students";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("student_id") + ", Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateStudent() {
        System.out.print("Enter Student ID to Update: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();  // consume newline
        System.out.print("Enter New Email: ");
        String newEmail = scanner.nextLine();

        String query = "UPDATE Students SET email = ? WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, studentId);

            stmt.executeUpdate();
            System.out.println("Student updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteStudent() {
        System.out.print("Enter Student ID to Delete: ");
        int studentId = scanner.nextInt();

        String query = "DELETE FROM Students WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);

            stmt.executeUpdate();
            System.out.println("Student deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}