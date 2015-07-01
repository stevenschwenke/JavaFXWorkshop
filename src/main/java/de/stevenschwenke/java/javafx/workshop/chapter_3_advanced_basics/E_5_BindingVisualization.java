package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Visualizes the binding API.
 */
public class E_5_BindingVisualization extends Application implements Initializable {

    @FXML
    private TextField t1;
    @FXML
    private TextField t2;
    @FXML
    private TextField t3;

    /**
     * Binds 1 on 2 and is placed right before 2.
     */
    @FXML
    private Circle c_1_to_2;
    @FXML
    private Circle c_2_to_1;
    @FXML
    private Circle c_2_to_3;
    @FXML
    private Circle c_3_to_2;
    @FXML
    private Circle c_3_to_1;
    @FXML
    private Circle c_1_to_3;

    private SimpleBooleanProperty t1BoundToT2, t2BoundToT1, t2BoundToT3, t3BoundToT2, t3BoundToT1, t1BoundToT3;

    @FXML
    public void click1To2() {
        t1BoundToT2.set(!t1BoundToT2.get());
        System.out.println("t1BoundTo2 now " + t1BoundToT2.get());
    }

    @FXML
    public void click2To1() {
        t2BoundToT1.set(!t2BoundToT1.get());
        System.out.println("t2BoundTo1 now " + t2BoundToT1.get());
    }

    @FXML
    public void click2To3() {
        t2BoundToT3.set(!t2BoundToT3.get());
        System.out.println("t2BoundTo3 now " + t2BoundToT3.get());
    }

    @FXML
    public void click3To2() {
        t3BoundToT2.set(!t3BoundToT2.get());
        System.out.println("t3BoundTo2 now " + t3BoundToT2.get());
    }

    @FXML
    public void click3To1() {
        t3BoundToT1.set(!t3BoundToT1.get());
        System.out.println("t3BoundTo1 now " + t3BoundToT1.get());
    }

    @FXML
    public void click1To3() {
        t1BoundToT3.set(!t1BoundToT3.get());
        System.out.println("t1BoundTo3 now " + t1BoundToT3.get());
    }

    @Override
    public void start(Stage stage) throws Exception {

        // Loading out GUI from the fxml file. Binding to fields above happens
        // here.
        Parent root = FXMLLoader.load(getClass().getResource("/E_5_BindingVisualization.fxml"));
        Scene scene = new Scene(root, 260, 250);

        stage.setTitle("Binding Visualization");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        t1BoundToT2 = new SimpleBooleanProperty(false);
        t1BoundToT2.addListener(observable -> {
            resetBindings();
            c_1_to_2.setFill(t1BoundToT2.getValue() ? Color.DODGERBLUE : Color.LIGHTGRAY);
        });
        t2BoundToT1 = new SimpleBooleanProperty(false);
        t2BoundToT1.addListener(observable -> {
            resetBindings();
            c_2_to_1.setFill(t2BoundToT1.getValue() ? Color.DODGERBLUE : Color.LIGHTGRAY);
        });
        t2BoundToT3 = new SimpleBooleanProperty(false);
        t2BoundToT3.addListener(observable -> {
            resetBindings();
            c_2_to_3.setFill(t2BoundToT3.getValue() ? Color.DODGERBLUE : Color.LIGHTGRAY);
        });
        t3BoundToT2 = new SimpleBooleanProperty(false);
        t3BoundToT2.addListener(observable -> {
            resetBindings();
            c_3_to_2.setFill(t3BoundToT2.getValue() ? Color.DODGERBLUE : Color.LIGHTGRAY);
        });

        t3BoundToT1 = new SimpleBooleanProperty(false);
        t3BoundToT1.addListener(observable -> {
            resetBindings();
            c_3_to_1.setFill(t3BoundToT1.getValue() ? Color.DODGERBLUE : Color.LIGHTGRAY);
        });
        t1BoundToT3 = new SimpleBooleanProperty(false);
        t1BoundToT3.addListener(observable -> {
            resetBindings();
            c_1_to_3.setFill(t1BoundToT3.getValue() ? Color.DODGERBLUE : Color.LIGHTGRAY);
        });
    }

    private void resetBindings() {
        t1.textProperty().unbind();
        t1.textProperty().unbindBidirectional(t2.textProperty());
        t1.textProperty().unbindBidirectional(t3.textProperty());

        t2.textProperty().unbind();
        t2.textProperty().unbindBidirectional(t1.textProperty());
        t2.textProperty().unbindBidirectional(t3.textProperty());

        t3.textProperty().unbind();
        t3.textProperty().unbindBidirectional(t1.textProperty());
        t3.textProperty().unbindBidirectional(t2.textProperty());

        if (t1BoundToT2.and(t2BoundToT1).get()) {
            t1.textProperty().bindBidirectional(t2.textProperty());
        } else if (t1BoundToT2.get()) {
            t2.textProperty().bind(t1.textProperty());
        } else if (t2BoundToT1.get()) {
            t1.textProperty().bind(t2.textProperty());
        }

        if (t2BoundToT1.and(t1BoundToT2).get()) {
            t2.textProperty().bindBidirectional(t1.textProperty());
        } else if (t2BoundToT1.get()) {
            t1.textProperty().bind(t2.textProperty());
        } else if (t1BoundToT2.get()) {
            t2.textProperty().bind(t1.textProperty());
        }

        if (t2BoundToT3.and(t3BoundToT2).get()) {
            t2.textProperty().bindBidirectional(t3.textProperty());
        } else if (t2BoundToT3.get()) {
            t3.textProperty().bind(t2.textProperty());
        } else if (t3BoundToT2.get()) {
            t2.textProperty().bind(t3.textProperty());
        }

        if (t3BoundToT2.and(t2BoundToT3).get()) {
            t3.textProperty().bindBidirectional(t2.textProperty());
        } else if (t3BoundToT2.get()) {
            t2.textProperty().bind(t3.textProperty());
        } else if (t2BoundToT3.get()) {
            t3.textProperty().bind(t2.textProperty());
        }

        if (t3BoundToT1.and(t1BoundToT3).get()) {
            t3.textProperty().bindBidirectional(t1.textProperty());
        } else if (t3BoundToT1.get()) {
            t1.textProperty().bind(t3.textProperty());
        } else if (t1BoundToT3.get()) {
            t3.textProperty().bind(t1.textProperty());
        }

        if (t1BoundToT3.and(t3BoundToT1).get()) {
            t1.textProperty().bindBidirectional(t3.textProperty()); // TODO WEG
        } else if (t1BoundToT3.get()) {
            t3.textProperty().bind(t1.textProperty());
        } else if (t3BoundToT1.get()) {
            t1.textProperty().bind(t3.textProperty());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
