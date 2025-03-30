package fun.krowlexing.reversi.client.scenes;


import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.network.Network;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

        control.setAlignment(Pos.TOP_CENTER);
        control.setPadding(new Insets(20));
        control.setSpacing(10);


        var prepareGameButton = new Button("Prepare game");
        var stats = new Button("Stats");
        prepareGameButton.setOnMouseClicked(this::onPrepareGame);

        control.getChildren().addAll(
            prepareGameButton,
            stats
        );
    }


    private void startConnection() throws IOException{
        if (network != null && !network.closed()) return;

        network = Network.get();
    }

    public static MainMenu create() {
        var box = new VBox();
        return new MainMenu(box);
    }



    void onPrepareGame(MouseEvent mouseEvent) {
        Router.navigate(NewGame::new);
    }
}
