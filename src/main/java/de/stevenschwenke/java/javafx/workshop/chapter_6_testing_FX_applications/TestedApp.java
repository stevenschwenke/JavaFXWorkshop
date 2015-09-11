package de.stevenschwenke.java.javafx.workshop.chapter_6_testing_FX_applications;

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
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A simple App, supposed to be tested.
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
    private TextField textX;
    @FXML
    private TextField textY;

    private IntegerProperty counter = new SimpleIntegerProperty();

    private IntegerProperty proxyX = new SimpleIntegerProperty();
    private IntegerProperty proxyY = new SimpleIntegerProperty();
    NumberStringConverter numberStringConverter = new NumberStringConverter();

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
        //Everything here is potentially not thread-safe

        //Listeners that are supposed to run before a binding need to be added before those!!!
        textX.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || Integer.parseInt(newValue) > 10)
                textX.setText(oldValue);
        });
        textY.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || Integer.parseInt(newValue) > 10)
                textY.setText(oldValue);
        });

        //Binding the Slider with an Integer-Proxy-Property to bind it to a Text-Property of a label.
        proxyX.bindBidirectional(sliderX.valueProperty());
        proxyY.bindBidirectional(sliderY.valueProperty());

        textX.textProperty().bindBidirectional(proxyX, numberStringConverter);
        textY.textProperty().bindBidirectional(proxyY, numberStringConverter);

        count.textProperty().bind(counter.asString());
    }

    @FXML
    public void minusCount() {
        if (counter.get() >= 1) {
            counter.set(counter.get() - 1);
        }
    }

    @FXML
    public void plusCount() {
        counter.set(counter.get() + 1);
    }

    @FXML
    public void refresh() {
        //CheckBoxes
        String checkBoxes = "";
        if (opt1.isSelected())
            checkBoxes = "Opt1";
        if (opt2.isSelected()) {
            if (!checkBoxes.isEmpty())
                checkBoxes = checkBoxes + ", ";
            checkBoxes = checkBoxes + "Opt2";
        }

        //RadioButtons
        String radio = "";

        if (optA.isSelected()) radio = radio + "OptA";
        if (optB.isSelected())
            radio = radio + "OptB";
        if (!checkBoxes.isEmpty() && !radio.isEmpty())
            radio = " & " + radio;


        //ChoiceBox
        String choices = "";
        if (choice.getValue() != null) {
            if (!checkBoxes.isEmpty() || !radio.isEmpty())
                choices = " & ";

            choices = choices + choice.getValue();
        }

        selection.setText("You have selected: " + checkBoxes + radio + choices);
    }

}

