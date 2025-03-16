package fun.krowlexing.reversi.messages;

import java.io.IOException;
import java.io.InputStream;

public class RoomsRequest implements SocketMessage {

    public RoomsRequest() {}

    public static final ClientMessageType messageType = ClientMessageType.RoomsRequest;

    public MessageType type() { return MessageType.client(messageType); }

    public static RoomsRequest read(InputStream input) throws IOException {
        var reader = new SocketReader(input);
        return new RoomsRequest();
    }

    public void write(SocketWriter out) throws IOException {}
}
