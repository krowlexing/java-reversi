package fun.krowlexing.reversi.messages;

import java.io.IOException;

public class PrepareGameResponse implements SocketMessage {
    public boolean isReady;

    public PrepareGameResponse(boolean ready) {
        this.isReady = ready;
    }

    public MessageType type() {
        return MessageType.server(ServerMessageType.PrepareGameResponse);
    }

    public static PrepareGameResponse read(SocketReader in) throws IOException {
        var ready = in.readByte() == 0;
        return new PrepareGameResponse(ready);
    }

    public void write(SocketWriter out) throws IOException {
        if (isReady) out.writeByte(0);
        else out.writeByte(1);
    }
}
