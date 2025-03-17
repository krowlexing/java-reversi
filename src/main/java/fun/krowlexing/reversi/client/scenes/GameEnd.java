package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static fun.krowlexing.reversi.client.components.Utils.button;

public class GameEnd extends Scene {
    public GameEnd(VBox parent) {
        super(parent);
    }


    public static GameEnd success(VBox parent) {
        parent.getChildren().addAll(
            new Label("Это победа"),
            button("Повторить").onClick((e) -> Router.navigate(Game::new)).done(),
            button("Главное меню").done()
        );

        return new GameEnd(parent);
    }

    public static GameEnd fail(VBox parent) {
        parent.getChildren().addAll(
            new Label("Это провал"),
            button("Повторить").onClick((e) -> Router.navigate(Game::new)).done(),
            button("Главное меню").onClick((e) -> Router.navigate(MainMenu::new)).done()
        );

        return new GameEnd(parent);
    }
}
