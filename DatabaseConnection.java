
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/job_tracker";

    private static final String USER =
            System.getenv("DB_USERNAME");

    private static final String PASSWORD =
            System.getenv("DB_PASSWORD");

    public static Connection getConnection()
            throws SQLException {

        if (USER == null || PASSWORD == null) {
            throw new SQLException(
                "Database credentials are not configured. " +
                "Set DB_USERNAME and DB_PASSWORD."
            );
        }

        return DriverManager.getConnection(
            URL, USER, PASSWORD
        );
    }
}
