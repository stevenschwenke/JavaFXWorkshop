package de.stevenschwenke.java.javafx.workshop.chapter_X_testing_FX_applications;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by drandard on 20.07.2015.
 */
public class TestedApp extends Application implements Initializable {

    /**
     * UI components with FX-mapping to the .fxml - file
     */
    @FXML
    private TextField count;

    @FXML
    private ChoiceBox<String> choice;
    @FXML
    private RadioButton optA;
    @FXML
    private RadioButton optB;
    @FXML
    private CheckBox opt1;
    @FXML
    private CheckBox opt2;
    @FXML
    private Label selection;

    @FXML
    private Slider sliderX;
    @FXML
    private Slider sliderY;
    @FXML
    private Label labelX;
    @FXML
    private Label labelY;

    private IntegerProperty counter = new SimpleIntegerProperty();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Loading out GUI from the fxml file. Binding to fields above happens
        // here.
        Parent root = FXMLLoader.load(getClass().getResource("/testedApp.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Tested App");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        count.textProperty().bind(counter.asString());
    }

    @FXML
    public void minusCount() {
        if (counter.get() >= 1) counter.set(counter.get() - 1);
    }

    @FXML
    public void plusCount() {
        counter.set(counter.get() + 1);
    }

    @FXML
    public void refresh() {

    }

}
