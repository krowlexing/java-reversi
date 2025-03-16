package fun.krowlexing.reversi.client.network;

import fun.krowlexing.reversi.client.events.SocketMessageEvent;
import fun.krowlexing.reversi.messages.Rooms;
import fun.krowlexing.reversi.messages.RoomsRequest;
import fun.krowlexing.reversi.messages.SocketWriter;

import java.io.IOException;
import java.net.Socket;

public class Network {

    private Socket socket;
    private NetworkHandler input;
    private SocketWriter writer;

    public Network() throws IOException {
        this.socket = new Socket("127.0.0.1", 11037);
        this.writer = new SocketWriter(socket.getOutputStream());
        input = new NetworkHandler(socket);
        input.start();
    }

    public boolean closed() {
        if (socket == null) return true;
        return socket.isClosed();
    }

    public void requestRooms(SocketMessageEvent<Rooms> callback) throws IOException {
        var req = new RoomsRequest();
        writer.write(req);
        input.rooms.handleOnce(callback);
    }
}
