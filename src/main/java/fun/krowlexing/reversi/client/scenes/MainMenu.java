package fun.krowlexing.reversi.client.scenes;


import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.Utils;
import fun.krowlexing.reversi.client.network.Network;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static fun.krowlexing.reversi.client.components.Utils.button;
import static fun.krowlexing.reversi.client.styles.Style.style;
import static fun.krowlexing.reversi.logger.Logger.print;

public class MainMenu extends Scene {
    Network network;
    public MainMenu(VBox parent){
        super(parent);
        parent.setBackground(Utils.background());

        parent.setAlignment(Pos.CENTER);
        parent.setPadding(new Insets(20));
        parent.setSpacing(10);
        String css = getClass()
            .getResource("/styles.css")
            .toExternalForm();
        this.getStylesheets().add(css);

        var pgb = button("Prepare game").style(
            style()
                .padding(20)
                .background("#ff0000")
                .border(30)
                .borderRadius(20)
                .fontSize(40)
                .borderColor("#0000ff")
                .color("#00ff00")
        ).styleClass("round-button").onClick(this::onPrepareGame);

        var stats = button("Stats")
            .styleClass("outline-button")
            .onClick(e -> Router.navigate(Stats::new));

        parent.getChildren().addAll(
            pgb,
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
