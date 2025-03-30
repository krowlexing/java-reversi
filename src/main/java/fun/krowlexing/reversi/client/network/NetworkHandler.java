package fun.krowlexing.reversi.client.network;

import fun.krowlexing.reversi.messages.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import static fun.krowlexing.reversi.logger.Logger.print;

public class NetworkHandler extends Thread {

    private Socket socket;
    private Promise<Rooms> roomsPromise;
    private Promise<RegisterResponse> registerPromise;
    private Promise<LoginResponse> loginPromise;
    private Promise<PrepareGameResponse> prepareGamePromise;
    private Promise<PairRevealResponse> pairRevealPromise;
    private Promise<GameCompletedMessage> gameCompletedPromise;

    public NetworkHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();

        try (var input = socket.getInputStream()) {
            var reader = new SocketReader(input);
            while (true) {
                var type = ServerMessageType.read(input);

               if (ServerMessageType.RegisterResponse == type) {
                   handle(RegisterResponse.class, reader, registerPromise);
               } else if (ServerMessageType.LoginResponse == type) {
                    handle(LoginResponse.class, reader, loginPromise);
               } else if (ServerMessageType.PrepareGameResponse == type) {
                   print("pgr");
                   handle(PrepareGameResponse.class, reader, prepareGamePromise);
               } else if (ServerMessageType.PairRevealResponse == type) {
                   handle(PairRevealResponse.class, reader, pairRevealPromise);
               } else if (ServerMessageType.GameCompleted == type) {
                   handle(GameCompletedMessage.class, reader, gameCompletedPromise);
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public <T> void handle(Class<T> tClass, SocketReader reader, Promise<T> consumer) {
        try {
            var method = tClass.getMethod("read", SocketReader.class);
            var response = (T) method.invoke(null, reader);
            if (consumer != null) {
                consumer.resolve(response);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public Promise<RegisterResponse> onRegister() {
        registerPromise = new Promise<>();
        return registerPromise;
    }

    public Promise<LoginResponse> onLogin() {
        loginPromise = new Promise<>();
        return loginPromise;
    }

    public Promise<PrepareGameResponse> onPrepareGame() {
        prepareGamePromise = new Promise<>();
        return prepareGamePromise;
    }

    public Promise<PairRevealResponse> onPairReveal() {
        pairRevealPromise = new Promise<>();
        return pairRevealPromise;
    }

    public Promise<GameCompletedMessage> onGameCompleted() {
        gameCompletedPromise = new Promise<>();
        return gameCompletedPromise;
    }
}
