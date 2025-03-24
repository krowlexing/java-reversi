package fun.krowlexing.reversi.client.network;

import fun.krowlexing.reversi.messages.*;

import java.io.IOException;
import java.net.Socket;

import static fun.krowlexing.reversi.logger.Logger.print;

public class Network {

    private Socket socket;
    private NetworkHandler input;
    private SocketWriter writer;

    public Network() throws IOException {
        this.socket = new Socket("127.0.0.1", 11037);
        this.writer = new SocketWriter(socket.getOutputStream());
        input = new NetworkHandler(socket);
        input.start();
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
}
