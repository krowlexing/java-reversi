package fun.krowlexing.reversi.messages;

import java.io.IOException;
import java.io.InputStream;

import static fun.krowlexing.reversi.logger.Logger.print;

public enum ClientMessageType {
    RoomsRequest,
    Authorize,
    CreateRoom,
    JoinRoom;

    ClientMessageType() {

    }


    int ord() {
        return this.ordinal() + 100;
    }

    public static ClientMessageType read(InputStream i) throws IOException {
        var type = i.read();
        if (type == -1) {
            type = i.read();
        }
        if (Authorize.ord() == type) return Authorize;
        if (RoomsRequest.ord() == type) return RoomsRequest;
        if (CreateRoom.ord() == type) return CreateRoom;
        if (JoinRoom.ord() == type) return JoinRoom;
        if (type == -1) {
            print("reached end of stream");
            throw new RuntimeException("reached end of stream");
        }
        //        if (type == -1) throw new IOException("Reached end of stream");

        throw new IOException("Unknown message type: " + type);
    }
}
