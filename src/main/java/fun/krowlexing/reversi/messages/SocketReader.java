package fun.krowlexing.reversi.messages;

import com.almasb.fxgl.input.Input;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SocketReader {
    DataInputStream stream;

    SocketReader(InputStream stream) {
        this.stream = new DataInputStream(stream);
    }

    public byte readByte() throws IOException {
        return (byte) this.stream.read();
    }

    public int readInt() throws IOException {
        return this.stream.readInt();
    }
}
