package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * This is another example for binding properties of different types to each other. The values in
 * the tableview are of type IntProperty. The textfield above the table has a StringProperty-typed
 * content. To bind them, we need a converter.
 */
public class E_5_BindingsWithConverters extends Application {

    private TextField textField = new TextField();
    private TableView<TableItem> tableView = new TableView<>();

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        root.setTop(textField);
        root.setCenter(tableView);

        //////////////////////
        // Option 1: Unidirectional binding. Conversion takes place in the Bindings-class.
        //////////////////////
//        textField.textProperty().bind(
//            Bindings.selectString(tableView.getSelectionModel().selectedItemProperty(), "myInt"));

        //////////////////////
        // Option 2: Bidirectional binding needs a listener because the the properties of both
        // sides are needed for the binding.
        //////////////////////
        tableView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {

                System.out.println("old: " + oldValue + "     new: " + newValue);

                if (oldValue != null) {
                    textField.textProperty().unbindBidirectional(oldValue.myIntProperty());
                }

                textField.textProperty()
                    .bindBidirectional(newValue.myIntProperty(), new NumberStringConverter());
            });

        tableView.setEditable(true);
        TableColumn<TableItem, String> column = new TableColumn<>("Some Integer");
        column.setCellValueFactory(new PropertyValueFactory<>("myInt"));
        tableView.getColumns().add(column);

        ObservableList<TableItem>
            itemList = FXCollections.observableArrayList(new TableItem(1), new TableItem(2));
        tableView.setItems(itemList);

        Scene scene = new Scene(root, 418, 550);
        stage.setTitle("Bindings with Converters");
        stage.setScene(scene);
        stage.show();
    }

    public class TableItem {
        private SimpleIntegerProperty myInt;

        TableItem(Integer myInt) {
            this.myInt = new SimpleIntegerProperty(myInt);
        }

        public int getMyInt() {
            return myInt.get();
        }

        public SimpleIntegerProperty myIntProperty() {
            return myInt;
        }

        public void setMyInt(int myInt) {
            this.myInt.set(myInt);
        }

        @Override
        public String toString() {
            return "TableItem(" + myInt + ')';
        }
    }
}
