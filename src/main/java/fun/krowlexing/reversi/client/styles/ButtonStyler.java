package fun.krowlexing.reversi.client.styles;

import javafx.scene.control.Button;

public class ButtonStyler {

    public static Button apply(Button parent, Style style) {
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

        var padding = style.padding();
        if (padding != null) {
            parent.setPadding(padding);
        }

        var border = style.border();
        if (border != null) {
            parent.setBorder(border);
        }

        var font = style.font();
        if (font != null) parent.setFont(font);

        if (style.color != null) parent.setTextFill(style.color);

        return parent;
    }
}
