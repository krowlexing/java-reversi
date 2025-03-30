package fun.krowlexing.reversi.messages;

import java.io.IOException;

public class StatsResponse implements SocketMessage {
    public Stat[] stats;

    public StatsResponse(Stat[] stats) {
        this.stats = stats;
    }

    @Override
    public MessageType type() {
        return MessageType.server(ServerMessageType.StatsResponse);
    }

    public static StatsResponse read(SocketReader in) throws IOException {
        var len = in.readInt();
        var stats = new Stat[len];
        for(int i = 0; i < len; i++) {
            stats[i] = Stat.read(in);

        }
        return new StatsResponse(stats);
    }

    @Override
    public void write(SocketWriter out) throws IOException {
        out.write(stats.length);
        for (var stat: stats) {
            stat.write(out);
        }
    }
}
