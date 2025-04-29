package fun.krowlexing.reversi.client.components;

import fun.krowlexing.reversi.client.styles.Style;
import fun.krowlexing.reversi.client.styles.StyleBuilder;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import static fun.krowlexing.reversi.client.styles.Style.style;

public class Utils {

    public static Column.Builder column(Node ...args) {
        return new Column.Builder(args);
    }

    public static Column.Builder column(VBox parent) {
        return new Column.Builder(parent);
    }

    public static Row.Builder row(Node ...args) {
        return new Row.Builder(args);
    }

    public static ButtonBuilder button(String text) {
        return new ButtonBuilder().text(text);
    }

    public static Label label(String text, String className) {
        var label = new Label(text);
        label.getStyleClass().add(className);
        return label;
    }
    public static Label label(String text) {
        return new Label(text);
    }

    public static Label title(String text) {
        var label = new Label(text);
        label.setFont(new Font( 18));
        return label;
    }

    public static Label title(String text, String className) {
        var label = new Label(text);
        label.setFont(new Font( 18));
        label.getStyleClass().add(className);
        return label;
    }

    public static class ButtonBuilder {
        private final Button button;
        public ButtonBuilder( ) {
            button = new Button();
        }

        public ButtonBuilder text(String text) {
            button.setText(text);
            return this;
        }

        public ButtonBuilder onClickAnd(EventHandler<MouseEvent> handler) {
            button.setOnMouseClicked(handler);
            return this;
        }

        public Button onClick(EventHandler<MouseEvent> handler) {
            return this.onClickAnd(handler).done();
        }

        public Button done() {
            return button;
        }

        public ButtonBuilder style(StyleBuilder style) {
            var finalStyle = style.build();
            finalStyle.apply(this.button);
            return this;
        }

        public ButtonBuilder styleClass(String className) {
            this.button.getStyleClass().add(className);
            return this;
        }
    }
}
