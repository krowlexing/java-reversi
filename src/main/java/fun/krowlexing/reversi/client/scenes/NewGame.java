package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.Utils;
import fun.krowlexing.reversi.client.components.NumericField;
import fun.krowlexing.reversi.client.data.GameSettings;
import fun.krowlexing.reversi.client.data.Size;
import fun.krowlexing.reversi.client.network.Network;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static fun.krowlexing.reversi.client.components.Utils.*;
import static fun.krowlexing.reversi.client.styles.Style.style;

public class NewGame extends Scene {
    NumericField widthInput;
    NumericField heightInput;
    NumericField timeInput;

    public NewGame(VBox parent) {
        super(parent);
        Utils.loadCss(this);
        parent.setBackground(Utils.background());
        parent.setPadding(new Insets(20));
        parent.setSpacing(30);

        widthInput = new NumericField().promptText("Enter field's width");
        heightInput = new NumericField().promptText("Enter field's height");
        timeInput = new NumericField().promptText("Enter total time");


        parent.setAlignment(Pos.CENTER);
        parent.getChildren().addAll(
            column(
                column(
                    label("Field's width:", "form-label"),
                    widthInput
                ).box(),
                column(
                    label("Field's height:", "form-label"),
                    heightInput
                ).box(),
                column(
                    label("Available time:", "form-label"),
                    timeInput
                ).box()
            ).gap(20).style(style().maxWidth(200)).box(),
            button("Start game")
                .styleClass("round-button")
                .onClick((e) -> {
                    var gameSettings = validate();
                    if (gameSettings != null) {
                        try {
                            Network.get().onGameCompleted().then(r -> Platform.runLater(() ->
                                Router.navigate((b) ->
                                    r.success ?
                                        GameEnd.success(b) :
                                        GameEnd.fail(b)
                                )));
                            Network.get()
                                .prepareGame(gameSettings.width, gameSettings.height, 20)
                                .then(v -> Platform.runLater(() ->
                                    Router.navigate(p -> new Game(p, gameSettings))));

                        } catch (IOException ex) {

                        }
                    }
                })
        );
    }

    public static Integer validateInt(NumericField field, int start, int end, String error) {
        var widthString = field.text();
        var value = Integer.parseInt(widthString);
        if(value < start || value > end) {
            field.setError(error);
            return null;
        } else field.setError("");

        return value;
    }

    private GameSettings validate() {
        var valid = true;
        var width = 0;
        var height = 0;
        var time = 0;
        try {
            var result = validateInt(widthInput, 3, 10, "Must be a number from 3 to 10");
            if (result == null) {
                valid = false;
            } else width = result;


        } catch (NumberFormatException e) {
            valid = false;
            widthInput.setError("Must be valid number");
        }
        try {
            var result = validateInt(heightInput, 3, 10, "Must be a number from 3 to 10");
            if (result == null) {
                valid = false;
            } else height = result;
        } catch (NumberFormatException e) {
            valid = false;
            heightInput.setError("Must be valid number");
        }

        try {
            var result = validateInt(timeInput, 20, 120, "Must be between 20 and 120 seconds");
            if (result == null) {
                valid = false;
            } else time = result;
        } catch (NumberFormatException e) {
            valid = false;
            timeInput.setError("Must be valid number");
        }

        if (valid) {
            return new GameSettings(width, height, time);
        }

        return null;
    }
}
