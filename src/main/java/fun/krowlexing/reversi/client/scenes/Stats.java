package fun.krowlexing.reversi.client.scenes;

import fun.krowlexing.reversi.client.Router;
import fun.krowlexing.reversi.client.Utils;
import fun.krowlexing.reversi.client.network.Network;
import fun.krowlexing.reversi.messages.Stat;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.io.IOException;

import static fun.krowlexing.reversi.client.components.Utils.*;

public class Stats extends Scene {

    public Stats(VBox parent) {
        super(parent);
        Utils.loadCss(this);

        parent.setAlignment(Pos.CENTER);
        TableView<Stat> table = new TableView<>();

        int maxVisibleRows = 10;
        double rowHeight = 24;


        table.setMaxWidth(500);
//        table.setFixedCellSize(rowHeight);
        table.prefHeightProperty().bind(
            Bindings.createDoubleBinding(() -> {
                int rows = Math.min(table.getItems().size(), maxVisibleRows);
                return rows * rowHeight + 28;
            }, table.getItems())
        );

        var usernameColumn = makeColumn("Username", "username");
        var fieldWidthColumn = makeColumn("Cards", "cells");
        var timeUsedColumn = makeColumn("Time Used", "timeUsed");
        var pairTriedColumn = makeColumn("Pair tried", "pairTried");
        var averageTime = makeColumn("Average time", "averageTime");

        usernameColumn.getStyleClass().add("transparent-table");
        fieldWidthColumn.getStyleClass().add("transparent-table");
        timeUsedColumn.getStyleClass().add("transparent-table");
        pairTriedColumn.getStyleClass().add("transparent-table");
        averageTime.getStyleClass().add("transparent-table");


//        fieldWidthColumn.setPrefWidth(120);   // about 120 px wide
//        fieldWidthColumn.setMinWidth(120);
//        fieldWidthColumn.setMaxWidth(120);

        try {
            Network.get().stats().then(r -> Platform.runLater(() -> {
                table.getItems().addAll(r.stats);
            }));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        table.getColumns().addAll(usernameColumn, fieldWidthColumn, pairTriedColumn, timeUsedColumn, averageTime);

        column(parent).nodes(
            label("Statistics", "form-title"),
            table,
            button("Back").styleClass("outline-button").onClick(
                e -> Router.navigate(MainMenu::new)
            ));

        table.getStyleClass().add("transparent-table");
        parent.setBackground(Utils.background());
    }

    <T> TableColumn<Stat, T> makeColumn(String name, String property) {
        TableColumn<Stat, T> column = new TableColumn<>(name);
        column.setMinWidth(100);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }
}

