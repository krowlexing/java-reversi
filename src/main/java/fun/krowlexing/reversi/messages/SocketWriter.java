package fun.krowlexing.reversi.messages;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SocketWriter {
    DataOutputStream output;

    public SocketWriter(OutputStream output) {
        this.output = new DataOutputStream(output);
    }

    public void write(SocketMessage message)  throws IOException {
        this.write(message.type());
        message.write(this);
    }

    public void write(MessageType messageType) throws IOException {
        this.writeByte(messageType.ord());
    }

    public void writeByte(int data) throws IOException {
        output.writeByte((byte) data);
    }

    public void writeByte(byte data) throws IOException {
        output.writeByte(data);
    }

    public void writeInt(int data) throws IOException {
        output.writeInt(data);
    }

    public void write(int data) throws IOException {
        output.writeInt(data);
    }

    // Add writeString method
    public void writeString(String data) throws IOException {
        // Write the length of the string
        writeInt(data.length());

        // Write the string's bytes
        output.writeBytes(data);
    }
}
