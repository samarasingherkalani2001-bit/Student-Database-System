import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {


    private static final String DB_NAME = "studentdb2";


    private static final String URL =
            "jdbc:mysql://localhost:3306/" + DB_NAME
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=UTC";


    private static final String USER = "root";
    private static final String PASS = "Root@1234";


    private DatabaseConnection() {}


    public static Connection getConnection() throws SQLException {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found! Ensure mysql-connector-j is in your classpath.", e);
        }


        return DriverManager.getConnection(URL, USER, PASS);
    }
}
