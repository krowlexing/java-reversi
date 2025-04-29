package fun.krowlexing.reversi.client;

import fun.krowlexing.reversi.client.scenes.Registration;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class Utils {
    public static Background background() {
        var bgImage = new Image(
            Registration.class.getResource("/background.png").toExternalForm()
        );
        BackgroundImage backgroundImage = new BackgroundImage(
            bgImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(
                100, 100, true, true, true, false
            )
        );

        return new Background(backgroundImage);
    }

    public static void loadCss(Scene scene) {
        String css = scene.getClass()
            .getResource("/styles.css")
            .toExternalForm();
        scene.getStylesheets().add(css);
    }
}
