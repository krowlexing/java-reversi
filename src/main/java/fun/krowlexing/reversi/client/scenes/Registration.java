package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.Utils;
import fun.krowlexing.reversi.client.components.Column;
import fun.krowlexing.reversi.client.components.Field;
import fun.krowlexing.reversi.client.components.NumericField;
import fun.krowlexing.reversi.client.network.Network;
import fun.krowlexing.reversi.messages.RegisterResponse;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import static fun.krowlexing.reversi.client.components.Utils.*;
import static fun.krowlexing.reversi.client.styles.Style.style;
import static fun.krowlexing.reversi.logger.Logger.print;

public class Registration extends Scene {

    private final Network network;

    public Registration(Parent parent, Network network) {
        super(parent);
        Utils.loadCss(this);
        this.network = network;
    }

    public static Registration create(VBox parent, Network network) {
        parent.setBackground(Utils.background());


        var usernameField = new Field().validator(value -> {
            if (value.isEmpty()) return "This field is required";
            return null;
        });
        var passwordField = new Field().validator(value -> {
            if (value.isEmpty()) return "This field is required";
            return null;
        });
        var confirmPasswordField = new Field().validator(value -> {
            if (value.isEmpty()) return "This field is required";
            return null;
        });

        var vbox = new Column.Builder(parent).nodes(
            title("Register", "form-title"),
            column(
                label("Username:", "form-label"),
                usernameField,
                label("Password:", "form-label"),
                passwordField,
                label("Confirm password:", "form-label"),
                confirmPasswordField
            ).style(style().maxWidth(200)).box(),
            button("Register").styleClass("round-button")
                .onClick(event -> handleRegistration(
                    network,
                    usernameField,
                    passwordField,
                    confirmPasswordField
                )),
            button("I already have an account")
                .styleClass("link-btn")
                .onClick(e -> Router.navigate(b -> Login.create(b, network)))
        ).style(style().padding(20).gap(16).align(Pos.CENTER)).box();


        return new Registration(
            vbox,
            network
        );
    }

    private static void handleRegistration(
        Network network,
        Field usernameField,
        Field passwordField,
        Field confirmPasswordField
    ) {
        usernameField.touch();
        passwordField.touch();
        confirmPasswordField.touch();

        var username = usernameField.text();
        var password = passwordField.text();
        var confirmPassword = confirmPasswordField.text();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordField.setError("Password does not match");
            return;
        }

        try {

            print("reg");
            network.register(
                username,
                password
            )
            .then(response -> Platform.runLater(() -> {
                if (response.success) {
                     onRegister(response);
                } else {
                    showAlert("Такое имя пользователя уже занято");
                }
            }));
        } catch (Exception e) {
            showAlert("Network error: " + e.getMessage());
        }
    }

    private static void onRegister(RegisterResponse response) {
        if (response.success) {
            Router.navigate(MainMenu::new);
        } else {
            showAlert("Error error error :(((( " + response.message);
        }
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
