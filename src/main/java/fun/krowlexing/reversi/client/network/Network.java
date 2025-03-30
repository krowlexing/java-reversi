package fun.krowlexing.reversi.client.network;

import fun.krowlexing.reversi.client.data.Point;
import fun.krowlexing.reversi.messages.*;

import java.io.IOException;
import java.net.Socket;

import static fun.krowlexing.reversi.logger.Logger.print;

public class Network {

    private final Socket socket;
    private final NetworkHandler input;
    private final SocketWriter writer;

    private static Network instance;

    private Network() throws IOException {
        print("init new network");
        this.socket = new Socket("127.0.0.1", 11037);
        this.writer = new SocketWriter(socket.getOutputStream());
        input = new NetworkHandler(socket);
        input.start();
    }

    public static Network get() {
        try {
            if (instance == null) instance = new Network();
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("NEtowrwk erororr");
        }
    }

    public boolean closed() {
        return socket == null || socket.isClosed();
    }


    public Promise<RegisterResponse> register(String username, String password) throws IOException {
        var req = new RegisterRequest(username, password);
        var promise =  input.onRegister();
        writer.write(req);
        return promise;
    }

    public Promise<LoginResponse> login(String username, String password) throws IOException {
        var req = new LoginRequest(username, password);
        var promise =  input.onLogin();
        writer.write(req);
        return promise;
    }

    public void close() throws InterruptedException, IOException {
        socket.close();
        input.interrupt();
        print("interrupted");
        input.join();
        print("joined");

        print("socket closed");
    }

    public Promise<PrepareGameResponse> prepareGame(int width, int height, int time) throws IOException {
        var req = new PrepareGameRequest(width, height, time);
        var promise = input.onPrepareGame();
        writer.write(req);
        return promise;
    }

    public Promise<PairRevealResponse> pairMatched(Point selectedA, Point selectedB) throws IOException {
        var req = new PairRevealRequest(selectedA, selectedB);
        var promise = input.onPairReveal();
        writer.write(req);
        return promise;
    }

    public Promise<GameCompletedMessage> onGameCompleted() {
        return input.onGameCompleted();
    }

    public Promise<StatsResponse> stats() throws IOException {
        var req = new StatsRequest();
        var promise = input.onStats();
        writer.write(req);
        return promise;
    }
}
