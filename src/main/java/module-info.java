module fun.krowlexing.reversi {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens fun.krowlexing.reversi to javafx.fxml;
    exports fun.krowlexing.reversi;
    exports fun.krowlexing.reversi.client.components;
    exports fun.krowlexing.reversi.client.styles;
    exports fun.krowlexing.reversi.messages;
    opens fun.krowlexing.reversi.client.components to javafx.fxml;
}