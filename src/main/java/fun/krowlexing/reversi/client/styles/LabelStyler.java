package fun.krowlexing.reversi.client.styles;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class LabelStyler {

    public static Label apply(Label label, Style style) {
        if (style.color != null) {
            label.setTextFill(style.color);
        }

        label.setFont(Font.font(14));

        return label;
    }
}
