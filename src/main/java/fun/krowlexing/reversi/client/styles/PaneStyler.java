package fun.krowlexing.reversi.client.styles;

import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public class PaneStyler {
    private Style style;

    public PaneStyler(Style style) {
        this.style = style;
    }

    public Pane apply(Pane parent) {
        var background = style.background();

        if (background != null) {
            parent.setBackground(background);
        }

        if (style.width != 0) {
            parent.setMinWidth(style.width);
        }

        if (style.maxWidth != 0) {
            parent.setMaxWidth(style.maxWidth);
        }

        if (style.height != 0) {
            parent.setMinHeight(style.height);
        }

        if (style.maxHeight != 0) {
            parent.setMaxHeight(style.maxHeight);
        }

        if (style.align != null) {
            if (parent instanceof VBox vbox) {
                vbox.setAlignment(style.align);
            } else if (parent instanceof HBox hbox) {
                hbox.setAlignment(style.align);
            }
        }

        var padding = style.padding();
        if (padding != null) {
            parent.setPadding(padding);
        }

        if (parent instanceof VBox) {
            var box = (VBox) parent;

            box.setSpacing(style.gap);
        }

        var border = style.border();
        if (border != null) {
            parent.setBorder(border);
        }
        return parent;
    }
}
