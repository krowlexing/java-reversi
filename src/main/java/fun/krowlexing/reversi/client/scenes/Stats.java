package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.network.Network;
import fun.krowlexing.reversi.messages.Stat;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static fun.krowlexing.reversi.client.components.Utils.*;

public class Stats extends Scene {

    public Stats(VBox parent) {
        super(parent);

        TableView<Stat> table = new TableView<>();


        var fieldWidthColumn = makeColumn("Field width", "fieldWidth");
        var fieldHeightColumn = makeColumn("Field height", "fieldHeight");
        var timeUsedColumn = makeColumn("Time Used", "timeUsed");
        var totalTimeColumn = makeColumn("Total time", "totalTime");
        var pairTriedColumn = makeColumn("Pair tried", "pairTried");

        try {
            Network.get().stats().then(r -> Platform.runLater(() -> {
                table.getItems().addAll(r.stats);
            }));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        table.getColumns().addAll(fieldWidthColumn, fieldHeightColumn, pairTriedColumn, timeUsedColumn, totalTimeColumn);

        column(parent).nodes(
            label("Statistics"),
            table,
            button("Back").onClick(
                e -> Router.navigate(MainMenu::new)
            ).done());
    }

    <T> TableColumn<Stat, T> makeColumn(String name, String property) {
        TableColumn<Stat, T> column = new TableColumn<>(name);
        column.setMinWidth(100);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }
}

