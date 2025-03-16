package fun.krowlexing.reversi.client;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Function;

public class Router {
    private static Router instance;
    private static Stage stage;
    private Router(Stage stage) {
        Router.stage = stage;
    }

    public static void init(Stage stage) {
        instance = new Router(stage);
    }

    public static Router get() {
        return instance;
    }

    public static void navigate(Scene dest) {
        stage.setScene(dest);
    }

    public static void navigate(Function<VBox, Scene> generateScene) {
        var vbox = new VBox();
        var scene = generateScene.apply(vbox);
        stage.setScene(scene);
    }
}
