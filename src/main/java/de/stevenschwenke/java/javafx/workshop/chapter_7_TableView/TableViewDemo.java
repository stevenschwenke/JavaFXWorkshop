package de.stevenschwenke.java.javafx.workshop.chapter_7_TableView;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Demonstrates the VirtualFlow mechanism.
 *
 * @see com.sun.javafx.scene.control.skin.VirtualFlow
 * <p>
 * Created by bezze on 04.09.15.
 */
public class TableViewDemo extends Application implements Initializable {

    @FXML
    private TableView<RowValue> table;
    @FXML
    private TableColumn<RowValue, String> colName;

    int columnCount = 100;
    int rowCount = 100;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // System.out.println("start");
        Parent root = FXMLLoader.load(getClass().getResource("/TableViewDemo.fxml"));
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("TableViewDemo");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // System.out.println("init");

        initTable();

        ObservableList<RowValue> data = buildTableData();
        table.getItems().addAll(data);
    }

    private void initTable() {
        table.setTableMenuButtonVisible(true);
        table.setEditable(false);

        colName.setCellValueFactory(p -> new ReadOnlyObjectWrapper<String>(p.getValue().getName()));

        for (int i = 0; i < columnCount; i++) {
            final int colIdx = i;

            TableColumn<RowValue, Integer> col = new TableColumn<>("Col " + i);
            col.setCellValueFactory(p ->
                    new ReadOnlyObjectWrapper<Integer>(p.getValue().getValues().get(colIdx)));
            col.setCellFactory(p -> new StyledTableCell());

            table.getColumns().add(col);
        }
    }

    private ObservableList<RowValue> buildTableData() {
        ObservableList<RowValue> retVal = FXCollections.observableArrayList();

        for (int i = 0; i < rowCount; i++) {
            RowValue value = new RowValue("Row " + i);

            for (int j = 0; j < columnCount; j++) {
                value.getValues().add(new Random().nextInt(20));
            }
            retVal.add(value);
        }
        return retVal;
    }
}
