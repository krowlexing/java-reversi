package fun.krowlexing.reversi.server;

import fun.krowlexing.reversi.server.repos.Repository;
import fun.krowlexing.reversi.server.services.UserService;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.DriverManager;
import java.sql.SQLException;

import static fun.krowlexing.reversi.logger.Logger.print;

public class Server {
    public static void main(String[] args) {
        try {
            var connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            var repo = new Repository(connection);
            repo.init();
            var userService = new UserService(repo.users);

            try (ServerSocket socket = new ServerSocket(11037)) {
                print("bound to socket");
                while (true) {
                    var client = socket.accept();
                    var thread = new ClientThread(
                        client,
                        userService
                    );
                    thread.start();
                }
            } catch (IOException e) {
                print("io exception");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            print("SQLException:");
            e.printStackTrace();
        }
    }
}
