package fun.krowlexing.reversi;

import fun.krowlexing.reversi.client.network.Network;
import fun.krowlexing.reversi.client.network.NetworkHandler;
import fun.krowlexing.reversi.client.scenes.MainMenu;
import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.scenes.Registration;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import static fun.krowlexing.reversi.logger.Logger.print;

public class MemoryGame extends Application {
    Network network;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Memory");
        Router.init(stage);

        network = new Network();

        Router.navigate(parent -> Registration.create(parent, network));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        print("stopping application");
        this.network.close();
        print("network closed");
    }
}