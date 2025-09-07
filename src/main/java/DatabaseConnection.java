import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    // ✅ Database name
    private static final String DB_NAME = "studentdb2";

    // ✅ JDBC URL with recommended options
    private static final String URL =
            "jdbc:mysql://localhost:3306/" + DB_NAME
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=UTC";

    // ✅ MySQL credentials
    private static final String USER = "root";
    private static final String PASS = "Root@1234";

    // Prevent instantiation
    private DatabaseConnection() {}

    /**
     * Get a connection to the database.
     * @return Active DB connection
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Explicitly load MySQL driver (good for older JVMs / servers)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found! Ensure mysql-connector-j is in your classpath.", e);
        }

        // ✅ Create and return connection
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
