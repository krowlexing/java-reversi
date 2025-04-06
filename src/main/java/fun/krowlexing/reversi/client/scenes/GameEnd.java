package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.styles.Style;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static fun.krowlexing.reversi.client.components.Utils.button;
import static fun.krowlexing.reversi.client.components.Utils.row;
import static fun.krowlexing.reversi.client.styles.Style.style;

public class GameEnd extends Scene {

    static Style centered = style().align(Pos.CENTER).build();
    public GameEnd(VBox parent) {
        super(parent);
    }


    public static GameEnd success(VBox parent) {
        parent.getChildren().addAll(
            row(
                new Label("It's a success")
            ).style(centered).box(),
            row(
                button("Main menu").onClick((e) ->
                    Router.navigate(MainMenu::new)
                )
            ).style(centered).box()

        );

        return new GameEnd(parent);
    }

    public static GameEnd fail(VBox parent) {
        parent.getChildren().addAll(
            new Label("It's a failure"),
            button("Main menu").onClick((e) ->
                Router.navigate(MainMenu::new)
            )
        );

        return new GameEnd(parent);
    }
}
