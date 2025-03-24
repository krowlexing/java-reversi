package fun.krowlexing.reversi.client.components;

import fun.krowlexing.reversi.client.styles.Style;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import static fun.krowlexing.reversi.client.styles.Style.style;

public class Utils {

    public static Column.Builder column(Node ...args) {
        return new Column.Builder(args);
    }

    public static ButtonBuilder button(String text) {
        return new ButtonBuilder().text(text);
    }

    public static Label label(String text) {
        return new Label(text);
    }

    public static Label title(String text) {
        var label = new Label(text);
        label.setFont(new Font( 18));
        return label;
    }

    public static class ButtonBuilder {
        private Button button;
        public ButtonBuilder( ) {
            button = new Button();
        }

        public ButtonBuilder text(String text) {
            button.setText(text);
            return this;
        }

        public ButtonBuilder onClick(EventHandler<MouseEvent> handler) {
            button.setOnMouseClicked(handler);
            return this;
        }

        public Button done() {
            return button;
        }
    }
}
