package fun.krowlexing.reversi.messages;

import java.io.IOException;

public class RoomMessage {
    byte roomNumber;
    byte players;

    public RoomMessage(byte roomNumber, byte players) {
        this.roomNumber = roomNumber;
        this.players = players;
    }

    public static RoomMessage read(SocketReader input) throws IOException {
        var roomNumber = input.readByte();
        var players = input.readByte();
        return new RoomMessage(roomNumber, players);
    }

    public void write(SocketWriter output) throws IOException {
        output.writeByte(this.roomNumber);
        output.writeByte(this.players);
    }
}
