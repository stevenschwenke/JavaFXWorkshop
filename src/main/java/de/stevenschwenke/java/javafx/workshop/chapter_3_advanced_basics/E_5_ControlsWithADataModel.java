package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This example is an enhanced copy from an example of Hendrik Ebbers.
 * See https://github.com/guigarage/mastering-javafx-controls.
 *
 * Some controls have own data models, like the ComboBox. Changing the underlying data model
 * immediately changes the contents in the box.
 */
public class E_5_ControlsWithADataModel extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ComboBox<String> comboBox = new ComboBox<>();
        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll("Darth Vader", "Luke Skywalker", "Yoda");
        comboBox.itemsProperty().setValue(data);
        TextField inputField = new TextField();
        inputField.setPromptText("insert new data type");
        inputField.setOnAction(e -> data.add(inputField.getText()));
        VBox box = new VBox(6, inputField, comboBox);
        box.setAlignment(Pos.CENTER);
        Scene myScene = new Scene(box);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}