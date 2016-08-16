package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_9_TableView;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This example is an enhanced copy from an example of Hendrik Ebbers.
 * See https://github.com/guigarage/mastering-javafx-controls.
 *
 * This class implements a table with several features:
 * <ol>
 *   <li>Using property-binding to wire the table to the data object</li>
 *   <li>Editing in the cells</li>
 *   <li>Multi columns</li>
 *   <li>On mouse entered log output</li>
 *   <li>Difference between linking attributes to columns either with strings (bad!) or properties
 *   (fancy and ultra cool)</li>
 * </ol>
 */
public class E_9_1_SimpleTableStart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ObservableList<E_9_1_ExtendedPerson> data = FXCollections.observableArrayList(
                new E_9_1_ExtendedPerson("Claudine", "Zillmann", "Design", true),
                new E_9_1_ExtendedPerson("Joel", "Ferreira", "Pro Gamer", false),
                new E_9_1_ExtendedPerson("Alexander", "Jorde", "Junior Developer", false),
                new E_9_1_ExtendedPerson("Holger", "Merk", "Senior Architect", false)
        );

        TableView<E_9_1_ExtendedPerson> table = new TableView();
        table.itemsProperty().setValue(data);

        TableColumn firstNameColumn = new TableColumn("First Name");
        TableColumn lastNameColumn = new TableColumn("Last Name");
        TableColumn nameColumn = new TableColumn("Name");
        // Columns can be wrapped in columns. Awesome!
        nameColumn.getColumns().addAll(firstNameColumn, lastNameColumn);
        TableColumn jobColumn = new TableColumn("Job");
        // Note: Every columns should be parameterized. The above columns are not parameterized to
        // show that Java tolerates unparameterized values.
        // However, sometimes it is necessary to provide a type, for example when writing an owm
        // cell factory instead of using standard ones. Like here:
        TableColumn<E_9_1_ExtendedPerson, Boolean> employeeOfTheMonthColumn = new TableColumn<>("Employee of the month");

        // Standard CellValueFactories:
        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<E_9_1_ExtendedPerson,String>("firstName")
        );
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<E_9_1_ExtendedPerson,String>("lastName")
        );
        jobColumn.setCellValueFactory(
                new PropertyValueFactory<E_9_1_ExtendedPerson,String>("job")
        );
        // The above way of creating cell value factories are fast to write. However, they use
        // reflection and are also not refactor-proof. Imagine you rename the variable this column
        // displays. The string-typed link above won't change in the refactoring and therefore
        // break the table. Here's a better way to write cell value factories:
        employeeOfTheMonthColumn.setCellValueFactory(
            (e) -> e.getValue().employeeOfTheMonthProperty()
            // Note: This only works if your data model object uses properties!
            // Our data model uses properties for every attribute. However, the text attributes
            // are not used as properties to demonstrate the difference.
        );

        // Standard CellFactories:
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        jobColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Custom CellFactory:
        employeeOfTheMonthColumn.setCellFactory(CheckBoxTableCell.forTableColumn(
            (i) -> data.get(i).employeeOfTheMonthProperty()));

        // We want to edit our table. To do that, the following has to be true:
        // 1. TableView property "editable" = true
        // 2. TableColumn property "editable" = true (by default true)
        // 3. TableCell property "editable" = true (by default true)
        table.setEditable(true);

        table.getColumns().addAll(nameColumn, jobColumn, employeeOfTheMonthColumn);

        StackPane myPane = new StackPane();
        myPane.getChildren().add(table);
        Scene myScene = new Scene(myPane);

        primaryStage.setScene(myScene);
        primaryStage.setTitle("App");
        primaryStage.setWidth(450);
        primaryStage.setHeight(200);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
