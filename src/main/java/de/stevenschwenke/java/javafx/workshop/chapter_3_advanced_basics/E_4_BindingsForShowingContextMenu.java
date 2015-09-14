package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;

import java.util.Arrays;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Demonstrates the usage of bindings for showing a context menu in a {@link TableView} only for non-empty rows.
 * <p>
 * This code is the modified gist available at https://gist.github.com/james-d/7758918
 */
public class E_4_BindingsForShowingContextMenu extends Application {

    private TableView<Person> table = new TableView<>();
    private final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "jacob.smith@example.com"),
            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
            new Person("Ethan", "Williams", "ethan.williams@example.com"),
            new Person("Emma", "Jones", "emma.jones@example.com"),
            new Person("Michael", "Brown", "michael.brown@example.com")
    );
    private Button remove = new Button("Remove");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        scene.getStylesheets().add("table.css");
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(500);

        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.setItems(data);
        table.getColumns().addAll(Arrays.asList(firstNameCol, lastNameCol, emailCol));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        remove.setDisable(true);
        remove.setOnAction(event -> removeRow(table.getSelectionModel().getSelectedItem()));

        //////////////////////////////////////////
        // FIRST NICE BINDING HERE:
        // Only enable button remove in the top when a row is selected:
        //////////////////////////////////////////
        ObservableList<Person> selectedItems = table.getSelectionModel().getSelectedItems();
        // Wrap it to get a nice property for the binding:
        ReadOnlyListWrapper readOnlyListWrapper = new ReadOnlyListWrapper(selectedItems);
        remove.disableProperty().bind(readOnlyListWrapper.sizeProperty().isEqualTo(0));

        table.setRowFactory(tableView -> {
            final TableRow<Person> row = new TableRow<>();

            //////////////////////////////////////////
            // SECOND NICE BINDING HERE:
            // Highlight when hovered:
            //////////////////////////////////////////
            row.styleProperty().bind(
                    Bindings.when(row.hoverProperty()).then("-fx-border-color:blue").otherwise("-fx-border-color:white"));

            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem removeMenuItem = new MenuItem("Remove");
            removeMenuItem.setOnAction(event -> removeRow(row.getItem()));
            contextMenu.getItems().add(removeMenuItem);

            //////////////////////////////////////////
            // THIRD NICE BINDING HERE:
            // Set context menu on row, but use a binding to make it only show for non-empty rows:
            //////////////////////////////////////////
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu));
            return row;
        });


        BorderPane borderPane = new BorderPane(table);
        borderPane.setTop(remove);
        scene.setRoot(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    private boolean removeRow(Person row) {
        return table.getItems().remove(row);
    }

    public static class Person {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;

        private Person(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fName) {
            email.set(fName);
        }
    }
}
