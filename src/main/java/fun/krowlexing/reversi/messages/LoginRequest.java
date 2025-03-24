package fun.krowlexing.reversi.messages;

import java.io.IOException;

public class LoginRequest implements SocketMessage {
    public String username;
    public String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public MessageType type() {
        return MessageType.client(ClientMessageType.LoginRequest);
    }

    public static LoginRequest read(SocketReader input) throws IOException {
        String username = input.readString();
        String password = input.readString();
        return new LoginRequest(username, password);
    }

    @Override
    public void write(SocketWriter out) throws IOException {
        out.writeString(username);
        out.writeString(password);
    }
}
