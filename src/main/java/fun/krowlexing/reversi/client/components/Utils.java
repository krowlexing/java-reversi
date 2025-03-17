package fun.krowlexing.reversi.client.components;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Utils {

    public static Column.Builder column(Node ...args) {
        return new Column.Builder(args);
    }

    public static ButtonBuilder button(String text) {
        return new ButtonBuilder().text(text);
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
