package fun.krowlexing.reversi.client.network;

import fun.krowlexing.reversi.client.events.Listener;
import fun.krowlexing.reversi.messages.Rooms;
import fun.krowlexing.reversi.messages.ServerMessageType;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static fun.krowlexing.reversi.logger.Logger.print;

public class NetworkHandler extends Thread {

    private Socket socket;

    public Listener<Rooms> rooms = new Listener<>();
    public NetworkHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();

        try (var input = socket.getInputStream()) {
            while (true) {
                var type = ServerMessageType.read(input);

                if (ServerMessageType.Rooms == type) handleRooms(input);
            }
        } catch (IOException e) {
            print("IO Exception while handling messages from server");
            e.printStackTrace();
        }

    }

    void handleRooms(InputStream input) throws IOException {
        var rooms = Rooms.read(input);
        this.rooms.emit(rooms);
    }
}

