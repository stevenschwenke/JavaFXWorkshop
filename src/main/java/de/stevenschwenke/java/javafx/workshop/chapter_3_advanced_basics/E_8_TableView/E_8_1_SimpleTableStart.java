package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_8_TableView;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
 *   <li>Editing in the cells</li>
 *   <li>multi columns</li>
 *   <li>on mouse entered log output</li>
 * </ol>
 */
public class E_8_1_SimpleTableStart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ObservableList<E_8_1_ExtendedPerson> data = FXCollections.observableArrayList(
                new E_8_1_ExtendedPerson("Claudine", "Zillmann", "Design", true),
                new E_8_1_ExtendedPerson("Joel", "Ferreira", "Pro Gamer", false),
                new E_8_1_ExtendedPerson("Alexander", "Jorde", "Junior Developer", false),
                new E_8_1_ExtendedPerson("Holger", "Merk", "Senior Architect", false)
        );

        TableView<E_8_1_ExtendedPerson> table = new TableView();
        table.itemsProperty().setValue(data);

        TableColumn firstNameColumn = new TableColumn("First Name");
        TableColumn lastNameColumn = new TableColumn("Last Name");
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.getColumns().addAll(firstNameColumn, lastNameColumn);
        TableColumn jobColumn = new TableColumn("Job");

        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<E_8_1_ExtendedPerson,String>("firstName")
        );
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<E_8_1_ExtendedPerson,String>("lastName")
        );
        jobColumn.setCellValueFactory(
                new PropertyValueFactory<E_8_1_ExtendedPerson,String>("job")
        );

        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        jobColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        table.setEditable(true);

        table.getColumns().addAll(nameColumn, jobColumn);

        table.setOnMouseEntered((e) -> System.out.println(data));

        StackPane myPane = new StackPane();
        myPane.getChildren().add(table);
        Scene myScene = new Scene(myPane);

        primaryStage.setScene(myScene);
        primaryStage.setTitle("App");
        primaryStage.setWidth(300);
        primaryStage.setHeight(200);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
