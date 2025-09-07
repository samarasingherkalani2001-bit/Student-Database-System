import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class StudentDAO {

    private static final Scanner in = new Scanner(System.in);


    public static void insertStudent() {
        try (Connection conn = DatabaseConnection.getConnection()) {


            try (Statement s = conn.createStatement();
                 ResultSet r = s.executeQuery("SELECT DATABASE()")) {
                if (r.next()) System.out.println("Using DB: " + r.getString(1));
            }

            System.out.print("Name: ");
            String name = in.nextLine().trim();

            System.out.print("Email: ");
            String email = in.nextLine().trim();

            System.out.print("Course: ");
            String course = in.nextLine().trim();

            System.out.print("GPA (0.00 - 4.00): ");
            BigDecimal gpa = new BigDecimal(in.nextLine().trim());
            if (gpa.compareTo(new BigDecimal("0.00")) < 0 || gpa.compareTo(new BigDecimal("4.00")) > 0) {
                System.out.println(" GPA must be between 0.00 and 4.00");
                return;
            }

            String sql = "INSERT INTO student (name, email, course, gpa) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, course);
                ps.setBigDecimal(4, gpa);
                int rows = ps.executeUpdate();

                if (rows == 1) {
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) System.out.println(" Added. New ID = " + keys.getInt(1));
                        else System.out.println("Added.");
                    }
                }
            } catch (SQLIntegrityConstraintViolationException dup) {
                System.out.println(" Email already exists. Use another email.");
            }

        } catch (Exception e) {
            System.out.println(" Insert failed: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void listStudents() {
        String sql = "SELECT id, name, email, course, gpa FROM student ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean any = false;
            while (rs.next()) {
                any = true;
                System.out.printf("ID:%d | %s | %s | %s | GPA:%s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course"),
                        rs.getBigDecimal("gpa"));
            }
            if (!any) System.out.println("(no records)");
        } catch (SQLException e) {
            System.out.println(" Read failed: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void updateStudent() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter ID to update: ");
            int id = Integer.parseInt(in.nextLine().trim());

            System.out.print("Field to update (name/email/course/gpa): ");
            String field = in.nextLine().trim().toLowerCase();

            String sql;
            switch (field) {
                case "name"   -> sql = "UPDATE student SET name=? WHERE id=?";
                case "email"  -> sql = "UPDATE student SET email=? WHERE id=?";
                case "course" -> sql = "UPDATE student SET course=? WHERE id=?";
                case "gpa"    -> sql = "UPDATE student SET gpa=? WHERE id=?";
                default -> {
                    System.out.println(" Unknown field.");
                    return;
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                if ("gpa".equals(field)) {
                    System.out.print("New GPA (0.00 - 4.00): ");
                    BigDecimal gpa = new BigDecimal(in.nextLine().trim());
                    if (gpa.compareTo(new BigDecimal("0.00")) < 0 || gpa.compareTo(new BigDecimal("4.00")) > 0) {
                        System.out.println(" GPA must be between 0.00 and 4.00");
                        return;
                    }
                    ps.setBigDecimal(1, gpa);
                } else {
                    System.out.print("New value: ");
                    ps.setString(1, in.nextLine().trim());
                }
                ps.setInt(2, id);

                int rows = ps.executeUpdate();
                System.out.println(rows == 1 ? " Updated." : " ID not found / no change.");
            } catch (SQLIntegrityConstraintViolationException dup) {
                System.out.println(" Email already exists. Use another email.");
            }

        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void deleteStudent() {
        String sql = "DELETE FROM student WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.print("Enter ID to delete: ");
            int id = Integer.parseInt(in.nextLine().trim());

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows == 1 ? " Deleted." : " ID not found.");

        } catch (Exception e) {
            System.out.println(" Delete failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
