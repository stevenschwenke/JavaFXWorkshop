package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_7_TableView;

import java.net.URL;
import java.util.Comparator;
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
 * This table has several problems such as
 * <ol>
 *   <li>missing columns: ...</li>
 *   <li>ghost column is shown</li>
 *   <li>empty rows can be selected</li>
 *   <li>display errors when scrolling wildly</li>
 * </ol>
 *
 * Created by bezze on 12.09.15.
 */
public class TableGame_BrokenTable extends Application implements Initializable {

    @FXML
    private TableView<Person> table;
    @FXML
    private TableColumn<Person, String> colFirstName;
    @FXML
    private TableColumn<Person, String> colName;
    @FXML
    private TableColumn<Person, String> colDateOfBirth;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/TableGame_BrokenTable.fxml"));
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Enjoy it!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();

        table.getItems().addAll(buildTableData());
    }

    private void initTable() {
        table.getSelectionModel().setCellSelectionEnabled(true);

        colFirstName.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getFirstName()));
        colFirstName.setComparator(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });
        colName.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getName()));
        colDateOfBirth.setCellValueFactory(
            p -> new ReadOnlyObjectWrapper<>(p.getValue().getDateOfBirth()));
    }

    private ObservableList<Person> buildTableData() {
        ObservableList<Person> data = FXCollections.observableArrayList();

        data.add(new Person("Mops", "Otto", "03.04.65"));
        data.add(new Person("Hans", "Wurst", "11.11.11"));
        data.add(new Person("Farin", "Urlaub", "05.04.69"));
        data.add(new Person("Klöbner", "Karin", "12.08.55"));
        data.add(new Person("Musterfrau", "Ursula", "22.11.77"));

        return data;
    }

    private class Person {
        private String name;
        private String firstName;
        private String dateOfBirth;

        public Person(String name, String firstName, String dateOfBirth) {
            this.name = name;
            this.firstName = firstName;
            this.dateOfBirth = dateOfBirth;
        }

        public String getName() {
            return name;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }
    }
}
