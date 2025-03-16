package fun.krowlexing.reversi.messages;

import java.io.IOException;
import java.io.InputStream;

public enum ServerMessageType {
    Rooms,
    PrepareGameResponse;

    public static ServerMessageType read(InputStream i) throws IOException {
        var type = i.read();

        if (Rooms.ordinal() == type) return Rooms;
        if (PrepareGameResponse.ordinal() == type) return PrepareGameResponse;

        throw new IOException("Unknown message type: " + type);
    }
}
