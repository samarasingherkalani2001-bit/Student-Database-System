import java.sql.Connection;

public class Testdata {
    public static void main(String[] args) {
        try (Connection con = DatabaseConnection.getConnection()) {
            System.out.println(" Connected to MySQL successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
