package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import static fun.krowlexing.reversi.client.components.Utils.column;

public class NewGame extends Scene {
    public NewGame(VBox parent) {
        super(parent);
        parent.setPadding(new Insets(20));
        parent.setSpacing(30);

        var widthInput = new TextField();
        widthInput.setPromptText("Enter field's width");
        var heightInput = new TextField();
        heightInput.setPromptText("Enter field's height");
        var button = new Button();
        button.setText("Start game");
        button.setOnMouseClicked((e) -> Router.navigate(Game::new));
        parent.getChildren().addAll(
            column(
                widthInput,
                heightInput
            ).gap(20).box(),
            button
        );
    }
}
