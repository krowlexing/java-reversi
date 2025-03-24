package fun.krowlexing.reversi.client.components;

import fun.krowlexing.reversi.client.styles.Style;
import fun.krowlexing.reversi.client.styles.StyleBuilder;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class Column {

    private VBox box;
    public Column(VBox box) {
        this.box = box;
    }

    public static class Builder {
        private VBox box;
        public Builder(Node ...args) {
            box = new VBox();
            this.nodes(args);
        }

        public Builder(VBox vbox) {
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

        public Column ok() {
            return new Column(box);
        }

        public VBox box() {
            return box;
        }
    }
}
