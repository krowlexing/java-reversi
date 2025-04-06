package fun.krowlexing.reversi.messages;

public class MessageType {
    ClientMessageType client;
    ServerMessageType server;

    static MessageType server(ServerMessageType type) {
        var messageType = new MessageType();
        messageType.server = type;
        return messageType;
    }

    public static MessageType client(ClientMessageType type) {
        var messageType = new MessageType();
        messageType.client = type;
        return messageType;
    }

    public int ord() {
        if (client != null) return client.ord();
        if (server != null) return server.ordinal();

        throw new NullPointerException("MessageType is not initialized");
    }
}
