package fun.krowlexing.reversi.messages;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SocketReader {
    DataInputStream stream;

    public SocketReader(InputStream stream) {
        this.stream = new DataInputStream(stream);
    }

    public byte readByte() throws IOException {
        return (byte) this.stream.read();
    }

    public int readInt() throws IOException {
        return this.stream.readInt();
    }

    // Add readString method
    public String readString() throws IOException {
        // Read the length of the string
        int length = this.stream.readInt();
        byte[] bytes = new byte[length];

        // Read the string bytes
        this.stream.readFully(bytes);

        // Convert the bytes to a string
        return new String(bytes);
    }
}
