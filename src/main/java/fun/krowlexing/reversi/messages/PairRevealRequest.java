package fun.krowlexing.reversi.messages;

import fun.krowlexing.reversi.client.data.Point;
import java.io.IOException;

public class PairRevealRequest implements SocketMessage {
    public final Point first;
    public final Point second;

    public PairRevealRequest(Point first, Point second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public MessageType type() {
        return MessageType.client(ClientMessageType.PairRevealRequest);
    }

    public static PairRevealRequest read(SocketReader input) throws IOException {
        return new PairRevealRequest(
            new Point(input.readInt(), input.readInt()),
            new Point(input.readInt(), input.readInt())
        );
    }

    @Override
    public void write(SocketWriter out) throws IOException {
        out.writeInt(first.x);
        out.writeInt(first.y);
        out.writeInt(second.x);
        out.writeInt(second.y);
    }
}
