package lab03.db;

import lombok.Getter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class JDBCConnection {
    @Getter
    private static final Connection connection;

    static {
        try {
            Properties properties = new Properties();
            properties.load(JDBCConnection.class.getClassLoader().getResourceAsStream("db.cfg"));

            String url = properties.getProperty("jakarta.persistence.jdbc.url");
            String user = properties.getProperty("jakarta.persistence.jdbc.user");
            String password = properties.getProperty("jakarta.persistence.jdbc.password");
            String driver = properties.getProperty("jakarta.persistence.jdbc.driver");

            // Загружаем драйвер JDBC
            Class.forName(driver);

            connection = DriverManager.getConnection(url, user, password);

        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.err.println("Ошибка подключения к базе данных: " + e.getMessage());
            throw new RuntimeException("Не удалось подключиться к базе данных", e);
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
        }
    }
}
