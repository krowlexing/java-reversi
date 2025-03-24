package fun.krowlexing.reversi.client.network;

import fun.krowlexing.reversi.messages.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class NetworkHandler extends Thread {

    private Socket socket;
    private Promise<Rooms> roomsPromise;
    private Promise<RegisterResponse> registerPromise;
    private Promise<LoginResponse> loginPromise;

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
                    var response = RegisterResponse.read(reader);
                    if (registerPromise != null)
                        this.registerPromise.resolve(response);
                } else if (ServerMessageType.LoginResponse == type) {
                    var response = LoginResponse.read(reader);
                    if (loginPromise != null)
                        this.loginPromise.resolve(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
}
