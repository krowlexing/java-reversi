package fun.krowlexing.reversi.client.components;


import fun.krowlexing.reversi.client.data.Point;
import fun.krowlexing.reversi.client.data.Size;
import fun.krowlexing.reversi.client.network.Network;
import fun.krowlexing.reversi.server.services.ColorService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board extends VBox {

    private Skin skin = new Skin();
    private Point selectedA;
    private Point selectedB;
    private List<Point> disabledPoints = new ArrayList<>();
    private ColorService colors;
    private Size size;

    private Action onMatchHandler;

    public Board(Size size, ColorService colorService) {
        colorService.init(size);
        this.colors = colorService;
        this.size = size;
        var children = this.getChildren();

        var hasHole = hasHole();
        for (int y = 0; y < size.height; y++) {
            BoardRow row;
            if (hasHole && y == (size.height / 2)) {
                row = BoardRow.withHole(
                    y,
                    size.width
                );
            } else {
                row = BoardRow.create(
                    y,
                    size.width
                );
            }

            row.setOnCellClick(this::onCellClick);
            children.add(row);
        }
    }

    public void onCellClick(int x, int y) throws IOException {
        if (disabledPoints.contains(new Point(x, y))) {
            return;
        }

        if (!isFirstCellSelected()) {
            selectedA = new Point(x, y);
            selectCell(selectedA);
            return;
        }

        if (!isSecondCellSelected()) {
            selectedB = new Point(x, y);
            selectCell(selectedA);

            if (!(selectedA.equals(selectedB))) {
                var cellA = selectedA;
                var cellB = selectedB;
                checkNetwork(cellA, cellB);
                selectedA = null;
                selectedB = null;
//                getCell(
//                    cellA
//                ).toggleFalse();
//                selectedA = null;
            } else {
                selectedB = null;
            }
        }


    }

    private void checkNetwork(Point cellA, Point cellB) throws IOException {
        disableCell(cellA);
        disableCell(cellB);
        Network.get().pairMatched(
            cellA,
            cellB
        ).then((r) -> Platform.runLater(() -> {
            toggleCell(
                cellA, r.colorA
            );
            toggleCell(
                cellB, r.colorB
            );
            if (r.success) {
                match(cellA, cellB);
            } else {
                setTimeout(
                    () -> {
                        toggleCell(
                            cellA, r.colorA
                        );
                        toggleCell(
                            cellB, r.colorB
                        );
                        enableCell(cellA);
                        enableCell(cellB);
                    },
                    1000
                );

            }
        }));
    }

    private boolean isFirstCellSelected() {
        return selectedA != null;
    }

    private boolean isSecondCellSelected() {
        return selectedB != null;
    }

    private void match(Point cellA, Point cellB) {
        disableCell(cellA);
        disableCell(cellB);
        setTimeout(
            () -> {
                var rowA = (BoardRow) getChildren().get(cellA.y);
                var rowB = (BoardRow) getChildren().get(cellB.y);
                rowA.getChildren().set(cellA.x, new EmptyCell());
                rowB.getChildren().set(cellB.x, new EmptyCell());
            },
            1000
        );

        if (onMatchHandler != null) onMatchHandler.execute();
    }

    public void disableCell(int x, int y) {
        disableCell(new Point(x, y));
    }

    public void disableCell(Point cell) {
        this.disabledPoints.add(cell);
    }

    public void enableCell(Point point) {
        var contained = this.disabledPoints.remove(point);
    }

    public void toggleCell(int x, int y, Paint color) {
        var cell = getCell(
            x,
            y
        );
        cell.setColor(color);
        cell.toggle();
    }

    public void toggleCell(Point point, int colorId) {
        toggleCell(point.x, point.y, skin.map(colorId));
    }

    public void selectCell(Point point) {
        getCell(point.x, point.y).select();
    }

    public Cell getCell(int x, int y) {
        int cellIndex = cellIndex(
            x,
            y
        );
        var row = (BoardRow) getChildren().get(y);
        return (Cell) row.getChildren().get(cellIndex);
    }

    public Cell getCell(Point point) {
        return getCell(point.x, point.y);
    }

    public int cellIndex(int x, int y) {
//        if (isHoleRow(y) && x > y) {
//            return x - 1;
//        }
        return x;
    }

    private boolean hasHole() {
        return (size.width % 2 == 1) && (size.height % 2 == 1);
    }

    private boolean isHoleRow(int y) {
        return hasHole() && y == size.width / 2;
    }

    private void setTimeout(Runnable runnable, int timeout) {
        Timeline timeline = new Timeline(new KeyFrame(
            Duration.millis(timeout),
            event -> {
                runnable.run();
            }
        ));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public Board onMatch(Action action) {
        onMatchHandler = action;
        return this;
    }
}
