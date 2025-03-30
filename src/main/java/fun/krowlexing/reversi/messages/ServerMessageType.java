package fun.krowlexing.reversi.messages;

import java.io.IOException;
import java.io.InputStream;

public enum ServerMessageType {
    Rooms,
    PrepareGameResponse,
    RegisterResponse,
    LoginResponse, PairRevealResponse, GameCompleted, StatsResponse;

    public static ServerMessageType read(InputStream i) throws IOException {
        var type = i.read(); // Read the byte from the InputStream

        // Check if the type is within valid range
        if (type < 0 || type >= ServerMessageType.values().length) {
            throw new IOException("Unknown message type: " + type);
        }

        return ServerMessageType.values()[type];
    }
}
