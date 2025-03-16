package fun.krowlexing.reversi;

import fun.krowlexing.reversi.client.scenes.MainMenu;
import fun.krowlexing.reversi.client.Router;
import javafx.application.Application;
import javafx.stage.Stage;

public class MemoryGame extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Memory");
        Router.init(stage);
        Router.navigate(MainMenu::new);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}