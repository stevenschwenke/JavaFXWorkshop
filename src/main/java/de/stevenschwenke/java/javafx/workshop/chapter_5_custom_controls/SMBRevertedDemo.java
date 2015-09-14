package de.stevenschwenke.java.javafx.workshop.chapter_5_custom_controls;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Demonstrates the use of Skin.layoutChildren(double x, double y, double w, double h).
 * <p>
 * Created by bezze on 11.09.15.
 */
public class SMBRevertedDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/SMBReverted.fxml"));
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Reverted SplitMenuButton");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void doSomething() {
        System.out.println("I did something!");
    }
}
