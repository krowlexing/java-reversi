package fun.krowlexing.reversi.client.components;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class BoardRow extends HBox {
    private FetchColor fetchColor;

    private BoardRow(FetchColor fetchColor) {
        this.fetchColor = fetchColor;
    }


    public static BoardRow create(int y, int width, FetchColor fetchColor) {
        var row = new BoardRow(fetchColor);
        var children = row.getChildren();
        for (int x = 0; x < width; x++) {
            var cell = new Cell(x, y, fetchColor);
            children.add(cell);
        }
        return row;
    }

    public static BoardRow withHole(int y, int width, FetchColor fetchColor) {
        var row = new BoardRow(fetchColor);
        var children = row.getChildren();
        for (int x = 0; x < width; x++) {
            var control = x == (width / 2) ? new EmptyCell() : new Cell(x, y, fetchColor);
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
