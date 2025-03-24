package fun.krowlexing.reversi.server;

import fun.krowlexing.reversi.logger.Logger;
import fun.krowlexing.reversi.messages.*;
import fun.krowlexing.reversi.server.exceptions.PersistenceException;
import fun.krowlexing.reversi.server.exceptions.UserExistsException;
import fun.krowlexing.reversi.server.services.IOAction;
import fun.krowlexing.reversi.server.services.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ClientThread extends Thread {

    private final Socket client;
    private final UserService service;
    private final ClientState state = new ClientState();

    ClientThread(Socket client, UserService service) {
        this.client = client;
        this.service = service;
    }

    private InputStream inputStream() throws IOException {
        return client.getInputStream();
    }

    private OutputStream outputStream() throws IOException {
        return client.getOutputStream();
    }



    private void print(String message) {
        Logger.print("Thread #" + this.getId() + ": " + message);
    }

    @Override
    public void run() {
        super.run();

        print("Accepted client");
        try (var in = inputStream(); var out = outputStream()) {
            var writer = new SocketWriter(out);
            var reader = new SocketReader(in);
            var router = setupRouter(writer, reader);

            while (true) {
                var bytes = in.available();
                print("available bytes: " + bytes);
                var type = ClientMessageType.read(in);
                print("Client send message: " + type.name());


                var route = router.get(type);
                if (route == null) {
                    print("No route for type " + type.name());
                } else {
                    route.execute();
                }

            }
        } catch (IOException e) {
            if (client.isClosed()) {
                print("Socket is closed");
                e.printStackTrace();
            } else {
                e.printStackTrace();
            }

        } finally {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private HashMap<ClientMessageType, IOAction> setupRouter(SocketWriter writer, SocketReader reader) {
        var router = new HashMap<ClientMessageType, IOAction>();
        router.put(ClientMessageType.RegisterRequest, () -> handleRegister(writer, reader));
        router.put(ClientMessageType.PrepareGame, () -> {
            var request = PrepareGameRequest.read(reader);
            state.width = request.width;
            state.height = request.height;
        });
        router.put(ClientMessageType.LoginRequest, () -> handleLogin(writer, reader));
        return router;
    }

    private void handleLogin(SocketWriter writer, SocketReader reader) throws IOException {
        try {
            var request = LoginRequest.read(reader);
            var exists = service.exists(request.username, request.password);

            if (exists) {
                state.auth = true;
                writer.write(new LoginResponse(true, "okkkk"));
            } else {
                writer.write(new LoginResponse(false, "wrong username/password"));
            }

        } catch (PersistenceException e) {
            writer.write(new LoginResponse(false, "try again"));
        }
    }

    private void handleRegister(SocketWriter writer, SocketReader reader) throws IOException {
        var request = RegisterRequest.read(reader);
        try {
            service.create(
                request.username, request.password
            );
            writer.write(new RegisterResponse(true, "man"));
        } catch (PersistenceException ex) {
            writer.write(new RegisterResponse(false, "try again"));
        } catch (UserExistsException ex) {
            writer.write(new RegisterResponse(false, "user with username '" + request.username + "' already exists"));
        }
    }
}
