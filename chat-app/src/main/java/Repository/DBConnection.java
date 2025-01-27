package Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/chat",
                        "root",
                        "password"
                );
            } catch (SQLException e) {
                System.out.println("Database connection error: " + e.getMessage());
            }
        }
        return connection;
    }

}
