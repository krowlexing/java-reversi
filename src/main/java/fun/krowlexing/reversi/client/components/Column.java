package fun.krowlexing.reversi.client.components;

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
            box.getChildren().addAll(args);
        }

        public Builder gap(double gap) {
            box.setSpacing(gap);
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
