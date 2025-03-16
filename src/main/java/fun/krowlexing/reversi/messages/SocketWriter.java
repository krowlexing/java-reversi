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
}
