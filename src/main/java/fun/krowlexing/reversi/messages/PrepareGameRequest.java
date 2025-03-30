package fun.krowlexing.reversi.messages;

import java.io.IOException;

public class PrepareGameRequest implements SocketMessage {
    public int width;
    public int height;
    public int seconds;

    public PrepareGameRequest(int width, int height, int seconds) {
        this.width = width;
        this.height = height;
        this.seconds = seconds;
    }

    @Override
    public MessageType type() {
        return MessageType.client(ClientMessageType.PrepareGame);
    }

    public static PrepareGameRequest read(SocketReader input) throws IOException {
        var width = input.readInt();
        var height = input.readInt();
        var seconds = input.readInt();

        return new PrepareGameRequest(width, height, seconds);
    }
    @Override
    public void write(SocketWriter out) throws IOException {
        out.writeInt(width);
        out.writeInt(height);
        out.writeInt(seconds);
    }
}
