package fun.krowlexing.reversi.server;

import fun.krowlexing.reversi.logger.Logger;
import fun.krowlexing.reversi.messages.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static fun.krowlexing.reversi.logger.Logger.print;

public class ClientThread extends Thread {

    private final Socket client;

    private InputStream inputStream() throws IOException {
        return client.getInputStream();
    }

    private OutputStream outputStream() throws IOException {
        return client.getOutputStream();
    }

    ClientThread(Socket client) {
        this.client = client;
    }

    private void print(String message) {
        Logger.print("Thread #" + this.getId() + ": " + message);
    }

    @Override
    public void run() {
        super.run();

        print("Accepted client");
        try (var stream = inputStream(); var out = outputStream()){
            var writer = new SocketWriter(out);
            while (true) {
                var bytes = stream.available();
                print("available bytes: " + bytes);
                var type = ClientMessageType.read(stream);
                if (type == ClientMessageType.RoomsRequest) {
                    var request = RoomsRequest.read(stream);
                    print("recognized rooms message");
                    var rooms = new Rooms(new RoomMessage[0]);
                    writer.write(rooms);
                }
                print("Client send message: " + type.name());
            }

        } catch (IOException e) {
            if (client.isClosed()) {
                print("Socket is closed");
                e.printStackTrace();
            } else {
                e.printStackTrace();
            }
        }

    }
}
