package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_8_TableView;

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
 * Demonstrates the VirtualFlow mechanism. Run it and scroll around in the table.</br>
 *
 * The displayed values are random values =< 20.</br>
 * Values < 6 -> red.</br>
 * Values > 14 -> green.</br>
 *
 * Before restarting comment line 20 in E_8_StyledTableCell and scroll (wild!) in the table.</br>
 * The table reuses the E_8_StyledTableCell and therefor gets mixed up with the colors.
 *
 * @see com.sun.javafx.scene.control.skin.VirtualFlow
 * <p>
 * Created by bezze on 04.09.15.
 */
public class E_8_Start extends Application implements Initializable {

    @FXML
    private TableView<E_8_RowValue> table;
    @FXML
    private TableColumn<E_8_RowValue, String> colName;

    int columnCount = 100;
    int rowCount = 100;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/TableViewDemo.fxml"));
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("TableViewDemo");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTable();

        ObservableList<E_8_RowValue> data = buildTableData();
        table.getItems().addAll(data);
    }

    private void initTable() {
        // Attention: Menu button lets you hide columns. Awesome. :)
        table.setTableMenuButtonVisible(true);

        table.setEditable(false);

        // the first column
        colName.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getName()));

        // dynamic columns
        for (int i = 0; i < columnCount; i++) {
            final int colIdx = i;

            TableColumn<E_8_RowValue, Integer> col = new TableColumn<>("Col " + i);
            col.setCellValueFactory(p ->
                    new ReadOnlyObjectWrapper<>(p.getValue().getValues().get(colIdx)));
            col.setCellFactory(p -> new E_8_StyledTableCell());

            table.getColumns().add(col);
        }
    }

    /**
     * @return List of {@link E_8_RowValue}s that each hold a list of values for one row.
     */
    private ObservableList<E_8_RowValue> buildTableData() {
        ObservableList<E_8_RowValue> retVal = FXCollections.observableArrayList();

        for (int i = 0; i < rowCount; i++) {
            E_8_RowValue value = new E_8_RowValue("Row " + i);

            for (int j = 0; j < columnCount; j++) {
                value.getValues().add(new Random().nextInt(20));
            }
            retVal.add(value);
        }
        return retVal;
    }
}
