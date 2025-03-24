package fun.krowlexing.reversi.server.repos;

import fun.krowlexing.reversi.server.repos.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class Repository implements AutoCloseable {

    private final Connection connection;
    public UserRepository users;

    public Repository(Connection connection) {
        this.connection = connection;
        this.users = new UserRepository(connection);
    }

    public void init() throws SQLException {
        this.users.createTable();
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
