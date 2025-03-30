package fun.krowlexing.reversi.server.repos;

import fun.krowlexing.reversi.server.repos.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class Repository implements AutoCloseable {

    private final Connection connection;
    public UserRepository users;
    public StatsRepository stats;

    public Repository(Connection connection) {
        this.connection = connection;
        this.users = new UserRepository(connection);
        this.stats = new StatsRepository(connection);
    }

    public void init() throws SQLException {
        this.users.createTable();
        this.stats.createTable();
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
