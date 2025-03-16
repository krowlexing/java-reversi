package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.components.Board;
import fun.krowlexing.reversi.client.components.EmptyCell;
import fun.krowlexing.reversi.client.components.Skin;
import fun.krowlexing.reversi.client.components.Timer;
import fun.krowlexing.reversi.client.data.Size;
import fun.krowlexing.reversi.client.services.ColorService;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class Game extends Scene {

    public int width = 7;
    public int height = 8;

    public Game(VBox parent) {
        super(parent);

        var skin = new Skin();
        var colors = new ColorService(skin);

        var timer = new Timer();
        var board = new Board(
            new Size(
                width,
                height
            ),
            colors
        );
        var cell = new EmptyCell();

        timer.setTime(60);
        timer.start();
        parent.getChildren().addAll(
            cell,
            timer,
            board
        );
    }
}
