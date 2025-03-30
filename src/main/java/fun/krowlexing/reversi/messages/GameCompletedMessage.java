package fun.krowlexing.reversi.messages;

import java.io.IOException;

public class GameCompletedMessage implements SocketMessage {
    public final boolean success;
    public final String reason;

    public GameCompletedMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    @Override
    public MessageType type() {
        return MessageType.server(ServerMessageType.GameCompleted);
    }

    public static GameCompletedMessage read(SocketReader input) throws IOException {
        boolean success = input.readByte() == 1;
        String reason = input.readString();
        return new GameCompletedMessage(success, reason);
    }

    @Override
    public void write(SocketWriter out) throws IOException {
        out.writeByte(success ? 1 : 0);
        out.writeString(reason);
    }
}
