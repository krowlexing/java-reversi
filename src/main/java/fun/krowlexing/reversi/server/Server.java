package fun.krowlexing.reversi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static fun.krowlexing.reversi.logger.Logger.print;

public class Server {
    static State state = new State();
    public static void main(String[] args) {
        System.out.println("init game server");
        while (true) try (ServerSocket socket = new ServerSocket(11037)) {
            var client = socket.accept();
            var thread = new ClientThread(client);
            thread.start();
        } catch (IOException e) {
            print("io exception");
            e.printStackTrace();
        }
    }
}
