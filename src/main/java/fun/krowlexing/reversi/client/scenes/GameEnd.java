package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.Utils;
import fun.krowlexing.reversi.client.styles.Style;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static fun.krowlexing.reversi.client.components.Utils.*;
import static fun.krowlexing.reversi.client.styles.Style.style;

public class GameEnd extends Scene {

    static Style centered = style().align(Pos.CENTER).build();
    public GameEnd(VBox parent) {
        super(parent);
        Utils.loadCss(this);
        parent.setBackground(Utils.background());
        parent.setAlignment(Pos.CENTER);
        parent.setSpacing(8);
    }


    public static GameEnd success(VBox parent) {
        parent.getChildren().addAll(
            row(
                label("It's a success", "form-title")
            ).style(centered).box(),
            row(
                button("Main menu").styleClass("round-button").onClick((e) ->
                    Router.navigate(MainMenu::new)
                )
            ).style(centered).box()
        );


        return new GameEnd(parent);
    }

    public static GameEnd fail(VBox parent) {
        parent.getChildren().addAll(
            label("It's a failure", "form-title"),
            button("Main menu").styleClass("round-button").onClick((e) ->
                Router.navigate(MainMenu::new)
            )
        );

        return new GameEnd(parent);
    }
}
