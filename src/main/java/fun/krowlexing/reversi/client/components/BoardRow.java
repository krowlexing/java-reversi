package fun.krowlexing.reversi.client.components;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class BoardRow extends HBox {


    private BoardRow() {

    }


    public static BoardRow create(int y, int width) {
        var row = new BoardRow();
        var children = row.getChildren();
        for (int x = 0; x < width; x++) {
            var cell = new Cell(x, y);
            children.add(cell);
        }
        return row;
    }

    public static BoardRow withHole(int y, int width) {
        var row = new BoardRow();
        var children = row.getChildren();
        for (int x = 0; x < width; x++) {
            var control = x == (width / 2) ? new EmptyCell() : new Cell(x, y);
            children.add(control);
        }
        return row;
    }

    public void setOnCellClick(OnCellClick handler) {
        for (var cell: getChildren()) {
            if (cell instanceof Cell) {
                ((Cell) cell).setOnCellClicked(handler);
            }

        }
    }
}
