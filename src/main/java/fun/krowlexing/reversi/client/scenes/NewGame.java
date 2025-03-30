package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.components.Field;
import fun.krowlexing.reversi.client.data.GameSettings;
import fun.krowlexing.reversi.client.data.Size;
import fun.krowlexing.reversi.client.network.Network;
import fun.krowlexing.reversi.client.network.NetworkHandler;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static fun.krowlexing.reversi.client.components.Utils.column;

public class NewGame extends Scene {
    Field widthInput;
    Field heightInput;

    public NewGame(VBox parent) {
        super(parent);
        parent.setPadding(new Insets(20));
        parent.setSpacing(30);

        widthInput = new Field().promptText("Enter field's width");
        heightInput = new Field().promptText("Enter field's height");
        var button = new Button();
        button.setText("Start game");
        button.setOnMouseClicked((e) -> {
            var size = validate();
            try {
                if (size != null) {
                    Network.get().onGameCompleted().then(r -> Platform.runLater(() -> Router.navigate((b) -> r.success ? GameEnd.success(b) : GameEnd.fail(b)))

                    );
                    Network.get()
                        .prepareGame(size.width, size.height, 20)
                        .then(v -> Platform.runLater(() -> Router.navigate(p -> new Game(p, new GameSettings(size.width, size.height, 20)))));
                }
            } catch (IOException ex) {

            }
        });
        parent.getChildren().addAll(
            column(
                widthInput,
                heightInput
            ).gap(20).box(),
            button
        );
    }



    private Size validate() {
        var valid = true;
        var width = 0;
        var height = 0;
        try {
            var widthString = widthInput.text();
            width = Integer.parseInt(widthString);
            if(width < 3 || width > 10) {
                valid = false;
                widthInput.setError("Must be a number from 3 to 10");
            }

        } catch (NumberFormatException e) {
            valid = false;
            widthInput.setError("Must be valid number");
        }
        try {
            var heightString = heightInput.text();
            height = Integer.parseInt(heightString);
            if(height < 3 || height > 10) {
                valid = false;
                heightInput.setError("Must be a number from 3 to 10");
            }
        } catch (NumberFormatException e) {
            valid = false;
            heightInput.setError("Must be valid number");
        }

        if (valid) {
            return new Size(width, height);
        }

        return null;
    }
}
