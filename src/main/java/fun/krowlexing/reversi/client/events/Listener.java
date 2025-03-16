package fun.krowlexing.reversi.client.events;

import fun.krowlexing.reversi.messages.SocketMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fun.krowlexing.reversi.logger.Logger.print;

public class Listener<Message extends SocketMessage> {

    private final List<SocketMessageEvent<Message>> listeners = new ArrayList<>();
    private final Map<SocketMessageEvent<Message>, Integer> limitedHandlers = new HashMap<>();

    // Register Listener
    public void add(SocketMessageEvent<Message> listener) {
        listeners.add(listener);
    }

    public void handleOnce(SocketMessageEvent<Message> handler) {
        limitedHandlers.put(handler, 1);
        add(handler);
    }

    // Unregister Listener
    public void remove(SocketMessageEvent<Message> listener) {
        listeners.remove(listener);
    }

    // Emit Event
    public void emit(Message message) {
        if (listeners.size() == 0) {
            print("No listeners registered for message: " + message.toString());
        }

        for (var listener : listeners) {
            listener.onEvent(message);
            applyLimits(listener);
        }
    }

    public void applyLimits(SocketMessageEvent<Message> listener) {
        var val = limitedHandlers.get(listener);
        if (val != null) {
            if (val - 1 <= 0) {
                limitedHandlers.remove(listener);
            } else {
                limitedHandlers.put(listener, val - 1);
            }
        }
    }
}
