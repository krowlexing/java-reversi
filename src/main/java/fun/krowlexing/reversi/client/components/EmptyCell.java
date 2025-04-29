package fun.krowlexing.reversi.client.components;

import fun.krowlexing.reversi.client.styles.Style;
import javafx.scene.layout.Pane;

public class EmptyCell extends Pane {
    public EmptyCell() {
        var style = Style.create()
            .width(40)
            .maxWidth(40)
            .height(40)
            .maxHeight(40)
            .background("#ffffff00")
            .build();
        style.apply(this);
    }
}
