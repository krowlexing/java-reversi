package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.Utils;
import fun.krowlexing.reversi.client.components.Board;
import fun.krowlexing.reversi.client.components.Skin;
import fun.krowlexing.reversi.client.components.Timer;
import fun.krowlexing.reversi.client.data.GameSettings;
import fun.krowlexing.reversi.client.data.Size;
import fun.krowlexing.reversi.server.services.ColorService;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import static fun.krowlexing.reversi.client.components.Utils.row;
import static fun.krowlexing.reversi.client.styles.Style.style;

public class Game extends Scene {

    public int width = 3;
    public int height = 3;

    public int matchedPairs = 0;

    private final Timer timer;

    public Game(VBox parent) {
        this(
            parent,
            new GameSettings(3,
                3,
                10
            )
        );
    }

    public Game(VBox parent, GameSettings settings) {
        super(parent);
        Utils.loadCss(this);
        parent.setBackground(Utils.background());

        width = settings.width;
        height = settings.height;
        var skin = new Skin();
        var colors = new ColorService(skin);

        timer = new Timer();
        var board = new Board(
            new Size(
                width,
                height
            ),
            colors
        ).onMatch(this::onPairMatched);

        var centered = style().align(Pos.CENTER);

        timer.setTime(settings.seconds);
        timer.start();
        timer.onTimeOut(this::onTimeOut);
        parent.getChildren().addAll(
            row(timer).style(centered).box(),
            row(board).style(centered).box()
        );
        parent.setAlignment(Pos.CENTER);
    }


    public void onTimeOut() {
        Router.navigate(GameEnd::fail);
    }

    public void onPairMatched() {
        matchedPairs += 1;
        if (matchedPairs == width * height / 2) {
            timer.stop();
            Router.navigate(GameEnd::success);
        }
    }
}
