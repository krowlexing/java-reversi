package fun.krowlexing.reversi.server.repos;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepository implements AutoCloseable {

    private Connection connection;

    UserRepository(Connection connection) {
        this.connection = connection;
    }


    public void createTable() throws SQLException {
        var query = "CREATE TABLE IF NOT EXISTS users (" +
            "id integer primary key," +
            "username text not null," +
            "password text not null" +
            ");";

        try (var stmt = connection.createStatement()) {
            stmt.execute(query);
        }
    }

    public boolean exists(String username) throws SQLException {
        var query = "SELECT * FROM users WHERE username = ?";
        try (var stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try(var result = stmt.executeQuery()) {
                return !result.next();
            }
        }
    }

    public boolean exists(String username, String password) throws SQLException {
        var query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (var stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try(var result = stmt.executeQuery()) {
                return !result.next();
            }
        }
    }

    public void insert(String username, String password) throws SQLException {
        var query = "INSERT INTO users(username, password) VALUES (?, ?)";

        try (var stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.execute();
        }
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
