package net.mysmirnov.quiz.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcProvider {
    private Connection connection;
    private String user;
    private String password;
    private String url;

    public JdbcProvider(String user, String password, String url) {
        this.user = user;
        this.password = password;
        this.url = url;
    }

    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public void destroy() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
