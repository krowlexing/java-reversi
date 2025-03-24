package fun.krowlexing.reversi.messages;

import java.io.IOException;

public class RegisterRequest implements SocketMessage {
    public String username;
    public String password;

    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public MessageType type() {
        return MessageType.client(ClientMessageType.RegisterRequest);
    }

    public static RegisterRequest read(SocketReader input) throws IOException {
        String username = input.readString();
        String password = input.readString();
        return new RegisterRequest(username, password);
    }

    @Override
    public void write(SocketWriter out) throws IOException {
        out.writeString(username);
        out.writeString(password);
    }
}
