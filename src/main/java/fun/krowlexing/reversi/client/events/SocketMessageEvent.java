package fun.krowlexing.reversi.client.events;

import fun.krowlexing.reversi.messages.SocketMessage;

public interface SocketMessageEvent<T extends SocketMessage> {
    void onEvent(T data);
}
