package fun.krowlexing.reversi.server;

import fun.krowlexing.reversi.client.data.Size;
import fun.krowlexing.reversi.logger.Logger;
import fun.krowlexing.reversi.messages.*;
import fun.krowlexing.reversi.server.exceptions.PersistenceException;
import fun.krowlexing.reversi.server.exceptions.UserExistsException;
import fun.krowlexing.reversi.server.repos.StatsRepository;
import fun.krowlexing.reversi.server.services.ColorService;
import fun.krowlexing.reversi.server.services.IOAction;
import fun.krowlexing.reversi.server.services.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;

public class ClientThread extends Thread {

    private final Socket client;
    private final ClientState state;
    private final UserService service;
    private final StatsRepository stats;

    ClientThread(Socket client, UserService service, ColorService colorService, StatsRepository stats) {
        this.client = client;
        this.service = service;
        this.stats = stats;
        state = new ClientState(colorService);
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

            state.onTimeout = () -> {
                try {
                    var playerId = state.playerId();
                    stats.insertSuccess(playerId, new Size(5, 5), 90, 90, 24);
                    writer.write(new GameCompletedMessage(false, "timeout"));
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            };

            state.onSuccess = (secondsLeft) -> {
                var s = this.state;
                try {
                    var playerId = state.playerId();
                    print(playerId + " is captured") ;
                    var timeUsed = s.totalTime - secondsLeft;
                    stats.insertSuccess(playerId, new Size(5, 5), (int) timeUsed, s.totalTime, 24);
                    writer.write(new GameCompletedMessage(true, "well done"));
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            };
            state.prepareGame(request.width, request.height, request.seconds);
            writer.write(new PrepareGameResponse(true, ""));
        });
        router.put(ClientMessageType.LoginRequest, () -> handleLogin(writer, reader));
        router.put(ClientMessageType.PairRevealRequest, () -> handleRevealPair(writer, reader));

        return router;
    }

    private void handleRevealPair(SocketWriter writer, SocketReader reader) throws IOException {
        var request = PairRevealRequest.read(reader);
        print("pair reveal request accepted");
        boolean match = state.isMatch(request.first, request.second);
        writer.write(new PairRevealResponse(match, state.color(request.first), state.color(request.second)));
    }


    private void handleLogin(SocketWriter writer, SocketReader reader) throws IOException {
        try {
            var request = LoginRequest.read(reader);
            var playerId = service.getId(request.username, request.password);

            if (playerId != -1) {
                print("authed user with id " + playerId);
                this.state.auth = true;
                this.state.setPlayerId(playerId);
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
