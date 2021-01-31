package net.mysmirnov.quiz.utils;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {

    private static Connection connection;
    private static final String USER = "root";
    private static final String PASSWORD = "rei36djg";
    private static final String URL = "jdbc:mysql://localhost:3306/quiz";

    public static boolean createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            // can only log
        }
    }
}
