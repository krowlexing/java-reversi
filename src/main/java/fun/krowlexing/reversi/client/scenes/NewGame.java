package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class NewGame extends Scene {
    public NewGame(VBox parent) {
        super(parent);

        var button = new Button();
        button.setText("Start game");
        button.setOnMouseClicked((e) -> Router.navigate(Game::new));
        parent.getChildren().add(button);


    }
}
