package fun.krowlexing.reversi.client.components;

import fun.krowlexing.reversi.client.styles.Style;
import javafx.scene.control.Label;

public class ErrorLabel extends Label {

    private final static Style style = Style.create().color("#ff3322").build();

    ErrorLabel() {
        super();

        style.apply(this);
    }
}
