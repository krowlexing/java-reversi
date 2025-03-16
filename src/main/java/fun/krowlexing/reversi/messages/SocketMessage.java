package fun.krowlexing.reversi.messages;

import java.io.IOException;

public interface SocketMessage {
    MessageType type();
    void write(SocketWriter out) throws IOException;
}
