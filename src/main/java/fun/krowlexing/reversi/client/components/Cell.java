package fun.krowlexing.reversi.client.components;

import fun.krowlexing.reversi.client.styles.Style;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;


public class Cell extends StackPane {
    private final Style base = Style.create()
        .setWidth(40)
        .setHeight(40)
        .background("992233")
        .build();
    private final Style selectedStyle = base.extend().border(2).borderColor("#000000").build();
    private final Pane rect = base.apply(new Pane());
    private final Circle circle = getCircle();
    boolean added = false;
    boolean selected = false;

    int color;

    FetchColor getColor;

    private int x;
    private int y;


    public Cell(int x, int y, FetchColor getColor) {
        this.getColor = getColor;
        var children = this.getChildren();
        Style.create()
            .maxWidth(40)
            .maxHeight(40)
            .build()
            .apply(this);
        children.addAll(
            rect
        );

        this.x = x;
        this.y = y;
    }

    public void toggle() {
        if (added) {
            this.getChildren().remove(circle);
        } else {
            this.circle.setFill(getColor.fetch(
                x,
                y
            ));
            this.getChildren().add(circle);
        }
        added = !added;
    }

    public void toggleFalse() {
        if (added) toggle();
    }

    public void select() {
        selected = !selected;
        if (selected) {
            selectedStyle.apply(rect);
        } else {
            base.apply(rect);
        }
    }

    public Paint getColor() {
        return this.circle.getFill();
    }

    private Circle getCircle() {
        return new Circle(20);
    }

    public void setOnCellClicked(OnCellClick handler) {
        this.setOnMouseClicked(e -> handler.onClick(
            x,
            y
        ));
    }
}
