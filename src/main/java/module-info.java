module fun.krowlexing.reversi {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;

    opens fun.krowlexing.reversi to javafx.fxml;
    exports fun.krowlexing.reversi;
    exports fun.krowlexing.reversi.client.components;
    opens fun.krowlexing.reversi.client.components to javafx.fxml;
}