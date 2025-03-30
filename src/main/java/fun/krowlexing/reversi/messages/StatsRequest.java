package fun.krowlexing.reversi.messages;

import java.io.IOException;

public class StatsRequest implements SocketMessage {

    @Override
    public MessageType type() {
        return MessageType.client(ClientMessageType.StatsRequest);
    }

    public static StatsRequest read(SocketReader in) throws IOException {
        return new StatsRequest();
    }

    @Override
    public void write(SocketWriter out) throws IOException {}
}
