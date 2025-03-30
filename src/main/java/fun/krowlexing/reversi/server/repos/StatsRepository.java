package fun.krowlexing.reversi.server.repos;

import fun.krowlexing.reversi.client.data.Size;
import fun.krowlexing.reversi.server.entities.Stats;

import java.sql.*;

public class StatsRepository implements AutoCloseable {

    private Connection connection;

    public StatsRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        var query = "CREATE TABLE IF NOT EXISTS game_stats (" +
            "id INTEGER PRIMARY KEY, " +
            "player_id INTEGER NOT NULL, " +
            "field_width INTEGER NOT NULL, " +
            "field_height INTEGER NOT NULL, " +
            "time_used INTEGER NOT NULL, " +
            "max_time INTEGER NOT NULL, " +
            "pairs_checked INTEGER NOT NULL, " +
            "played_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "FOREIGN KEY(player_id) REFERENCES users(id));";

        try (var stmt = connection.createStatement()) {
            stmt.execute(query);
        }
    }

    public void insertGameStats(int playerId, int width, int height, int timeUsed, int maxTime, int pairsChecked) throws SQLException {
        var query = "INSERT INTO game_stats(player_id, field_width, field_height, time_used, max_time, pairs_checked) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

        try (var stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            stmt.setInt(2, width);
            stmt.setInt(3, height);
            stmt.setInt(4, timeUsed);
            stmt.setInt(5, maxTime);
            stmt.setInt(6, pairsChecked);
            stmt.execute();
        }
    }

    public void insertSuccess(int playerId, Size size, int timeUsed, int maxTime, int pairChecked) throws SQLException {
        insertGameStats(playerId, size.width, size.height, timeUsed, maxTime, pairChecked);
    }

    public Stats getPlayerStats(int playerId) throws SQLException {
        var query = "SELECT * FROM game_stats WHERE player_id = ? ORDER BY played_at DESC LIMIT 1";

        try (var stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            try (var result = stmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    int fieldWidth = result.getInt("field_width");
                    int fieldHeight = result.getInt("field_height");
                    int timeUsed = result.getInt("time_used");
                    int maxTime = result.getInt("max_time");
                    int pairsChecked = result.getInt("pairs_checked");
                    Timestamp playedAt = result.getTimestamp("played_at");

                    return new Stats(id, playerId, fieldWidth, fieldHeight, timeUsed, maxTime, pairsChecked, playedAt);
                }
            }
        }
        return null;
    }

    public void close() throws Exception {
        this.connection.close();
    }
}
