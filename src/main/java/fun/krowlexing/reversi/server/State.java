package fun.krowlexing.reversi.server;

import fun.krowlexing.reversi.messages.Rooms;
import fun.krowlexing.reversi.messages.RoomMessage;

import java.util.HashMap;

public class State {
    HashMap<Integer, Room> rooms = new HashMap<>();

    synchronized public Rooms getRooms() {
        var len = (byte) rooms.size();
        var rooms = new RoomMessage[len];
        var iter = this.rooms.entrySet().iterator();
        for(int i = 0; i < len; i++) {
            var entry = iter.next();
            rooms[i] = new RoomMessage(entry.getKey().byteValue(), (byte) 0);
        }

        return new Rooms(rooms);
    }
}
