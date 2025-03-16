package fun.krowlexing.reversi.client.scenes;


import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.network.Network;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static fun.krowlexing.reversi.logger.Logger.print;

public class MainMenu extends Scene {
    Network network;
    public MainMenu(VBox control){
        super(control);

        var button = new Button("Get rooms");
        button.setOnMouseClicked(this::onRoomsClick);

        var prepareGameButton = new Button("Prepare game");
        prepareGameButton.setOnMouseClicked(this::onPrepareGame);

        control.getChildren().addAll(
            new Label("Hello world!"),
            prepareGameButton,
            button
        );
    }


    private void startConnection() throws IOException{
        if (network != null && !network.closed()) return;

        network = new Network();
    }

    public static MainMenu create() {
        var box = new VBox();
        return new MainMenu(box);
    }

    void onRoomsClick(MouseEvent mouseEvent) {
        try {
            startConnection();
            network.requestRooms(rooms -> {
                print("accepted rooms");
                print(rooms.toString());
            });
        } catch (IOException e) {
            print("failed to fetch rooms");
            e.printStackTrace();
        }
    }

    void onPrepareGame(MouseEvent mouseEvent) {
        Router.navigate(NewGame::new);
    }
}
