package fun.krowlexing.reversi.messages;

import java.io.IOException;

public class PrepareGameResponse implements SocketMessage {
    public final boolean success;
    public final String message;

    public PrepareGameResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public MessageType type() {
        return MessageType.server(ServerMessageType.PrepareGameResponse);
    }

    public static PrepareGameResponse read(SocketReader input) throws IOException {
        boolean success = input.readByte() == 1;
        String message = input.readString();
        return new PrepareGameResponse(success, message);
    }

    @Override
    public void write(SocketWriter out) throws IOException {
        out.writeByte(success ? 1 : 0);
        out.writeString(message);
    }
}
