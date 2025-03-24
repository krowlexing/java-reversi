package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.components.Column;
import fun.krowlexing.reversi.client.components.Field;
import fun.krowlexing.reversi.client.network.Network;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import static fun.krowlexing.reversi.client.components.Utils.*;
import static fun.krowlexing.reversi.client.styles.Style.style;

public class Login extends Scene {

    private final Network network;

    public Login(Parent parent, Network network) {
        super(parent);
        this.network = network;
    }

    public static Login create(VBox parent, Network network) {
        var usernameField = new Field().validator(value -> {
            if (value.isEmpty()) return "This field is required";
            return null;
        });

        var passwordField = new Field().validator(value -> {
            if (value.isEmpty()) return "This field is required";
            return null;
        });

        var vbox = new Column.Builder(parent).nodes(
            column(
                title("Login"),
                column(
                    usernameField,
                    passwordField
                ).box(),
                button("Login")
                    .onClick(event -> handleLogin(
                        network,
                        usernameField,
                        passwordField
                    )).done()
            ).style(style().gap(16)).box()
        ).style(style().padding(20)).box();

        return new Login(vbox, network);
    }

    private static void handleLogin(
        Network network,
        Field usernameField,
        Field passwordField
    ) {
        usernameField.touch();
        passwordField.touch();

        var username = usernameField.text();
        var password = passwordField.text();

        if (username.isEmpty() || password.isEmpty()) {
            return;
        }

        try {
            network.login(username, password)
                .then(response -> {
                    Platform.runLater(() -> Router.navigate(MainMenu::new));
                });
        } catch (Exception e) {
            showAlert("Network error: " + e.getMessage());
        }
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
