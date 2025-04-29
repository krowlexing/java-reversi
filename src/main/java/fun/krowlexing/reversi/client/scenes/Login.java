package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.Utils;
import fun.krowlexing.reversi.client.components.Column;
import fun.krowlexing.reversi.client.components.Field;
import fun.krowlexing.reversi.client.network.Network;
import javafx.application.Platform;
import javafx.geometry.Pos;
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
        Utils.loadCss(this);
        this.network = network;
    }

    public static Login create(VBox parent, Network network) {
        parent.setBackground(Utils.background());
        var usernameField = new Field().validator(value -> {
            if (value.isEmpty()) return "This field is required";
            return null;
        });

        var passwordField = new Field().validator(value -> {
            if (value.isEmpty()) return "This field is required";
            return null;
        });

        var vbox = new Column.Builder(parent).nodes(
                title("Login", "form-title"),
                column(
                    label("Username:", "form-label"),
                    usernameField,
                    label("Password", "form-label"),
                    passwordField
                ).style(style().maxWidth(200)).box(),
                button("Login")
                    .styleClass("round-button")
                    .onClick(event -> handleLogin(
                        network,
                        usernameField,
                        passwordField
                    ))
        ).style(style().gap(8).padding(20).align(Pos.CENTER)).box();

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
                .then(response -> Platform.runLater(()-> {
                    if (response.success) {
                        Router.navigate(MainMenu::new);
                    } else {
                        showAlert("Неправильное имя пользователя или пароль");
                    }
                }));
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
