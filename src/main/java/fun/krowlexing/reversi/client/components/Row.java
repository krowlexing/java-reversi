package fun.krowlexing.reversi.client.components;

import fun.krowlexing.reversi.client.styles.Style;
import fun.krowlexing.reversi.client.styles.StyleBuilder;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;

public class Row {

    private HBox box;
    public Row(HBox box) {
        this.box = box;
    }

    public static class Builder {
        private HBox box;
        public Builder(Node ...args) {
            box = new HBox();
            this.nodes(args);
        }

        public Builder(HBox vbox) {
            box = vbox;
        }

        public Builder nodes(Node ...args) {
            box.getChildren().addAll(args);
            return this;
        }

        public Builder gap(double gap) {
            box.setSpacing(gap);
            return this;
        }

        public Builder style(StyleBuilder style) {
            return this.style(style.build());
        }

        public Builder style(Style style) {
            style.apply(this.box);
            return this;
        }

        public Row ok() {
            return new Row(box);
        }

        public HBox box() {
            return box;
        }
    }
}
