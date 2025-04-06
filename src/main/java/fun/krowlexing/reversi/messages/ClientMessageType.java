package fun.krowlexing.reversi.messages;

import java.io.IOException;
import java.io.InputStream;

import static fun.krowlexing.reversi.logger.Logger.print;

public enum ClientMessageType {
    RoomsRequest,
    CreateRoom,
    JoinRoom,
    LoginRequest,
    RegisterRequest,
    PrepareGame, PairRevealRequest, StatsRequest, CloseMessage;

    ClientMessageType() {
    }

    int ord() {
        return this.ordinal() + 100;
    }

    public static ClientMessageType read(InputStream i) throws IOException {
        var type = i.read(); // Read the byte from the InputStream
        print("received message: " + type);
        // Check if the type is within valid range
        if (type >= 100) {
            type -= 100;
        }
        if (type < 0 || type >= ClientMessageType.values().length) {
            throw new IOException("Unknown message type: " + type);
        }

        return ClientMessageType.values()[type];
    }
}
