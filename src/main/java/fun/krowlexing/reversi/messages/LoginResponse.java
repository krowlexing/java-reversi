package fun.krowlexing.reversi.messages;

import java.io.IOException;

public class LoginResponse implements SocketMessage {
    public boolean success;
    public String message;  // Optionally provide a message (e.g., success or error message)

    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public MessageType type() {
        return MessageType.server(ServerMessageType.LoginResponse);
    }

    public static LoginResponse read(SocketReader input) throws IOException {
        boolean success = input.readByte() == 0;
        String message = input.readString();
        return new LoginResponse(success, message);
    }

    @Override
    public void write(SocketWriter out) throws IOException {
        out.writeByte(success ? 0 : 1);
        out.writeString(message);
    }
}
