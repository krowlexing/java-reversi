package fun.krowlexing.reversi.messages;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Rooms implements SocketMessage {
    RoomMessage[] rooms;

    public Rooms(RoomMessage[] rooms) {
        this.rooms = rooms;
    }

    public static final ServerMessageType messageType = ServerMessageType.Rooms;

    public MessageType type() { return MessageType.server(messageType); }
    public static Rooms read(InputStream input) throws IOException {
        var reader = new SocketReader(input);
        int roomCount = reader.readByte();
        RoomMessage[] rooms = new RoomMessage[roomCount];

        for(int i = 0; i < roomCount; i++) {
            rooms[i] = RoomMessage.read(reader);
        }

        return new Rooms(rooms);
    }

    public void write(SocketWriter out) throws IOException {
        var len = (byte) rooms.length;

        out.writeByte(len);

        for (var room : rooms) {
            room.write(out);
        }
    }

    @Override
    public String toString() {
        return "Rooms{" +
            "rooms=" + Arrays.toString(rooms) +
            '}';
    }
}
