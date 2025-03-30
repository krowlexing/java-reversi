package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.network.Network;
import fun.krowlexing.reversi.messages.Stat;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;

import static fun.krowlexing.reversi.client.components.Utils.column;
import static fun.krowlexing.reversi.client.components.Utils.label;
import static fun.krowlexing.reversi.logger.Logger.print;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Stats extends Scene {

    public Stats(VBox parent) {
        super(parent);
        print("init stats");
        TableView<Stat> table = new TableView<>();

        TableColumn<Stat, String> nameColumn = new TableColumn<>("Time Used");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("timeUsed"));

        TableColumn<Stat, Integer> ageColumn = new TableColumn<>("Field");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("fieldWidth"));

        try {
            Network.get().stats().then(r -> Platform.runLater(() -> {
                table.getItems().addAll(r.stats);
            }));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        table.getColumns().addAll(nameColumn, ageColumn);

        column(parent).nodes(label("supa tabl"), table);

    }
}

