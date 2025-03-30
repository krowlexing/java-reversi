package fun.krowlexing.reversi.messages;

import java.io.IOException;

public class PairRevealResponse implements SocketMessage {
    public final boolean success;
    public int colorA;
    public int colorB;

    public PairRevealResponse(boolean success, int colorA, int colorB) {
        this.success = success;
        this.colorA = colorA;
        this.colorB = colorB;
    }

    @Override
    public MessageType type() {
        return MessageType.server(ServerMessageType.PairRevealResponse);
    }

    public static PairRevealResponse read(SocketReader input) throws IOException {
        return new PairRevealResponse(input.readByte() == 1, input.readInt(), input.readInt());
    }

    @Override
    public void write(SocketWriter out) throws IOException {
        out.writeByte(success ? 1 : 0);
        out.writeInt(colorA);
        out.writeInt(colorB);
    }
}
