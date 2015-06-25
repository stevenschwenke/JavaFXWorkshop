package de.stevenschwenke.java.javafx.workshop.chapter_3_additional_controls;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This application is maximized to full screen mode.
 */
public class FullScreenDemo extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new BorderPane(), 418, 550);
        stage.setTitle("Full Screen Demo");
        stage.setScene(scene);

        stage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.ESCAPE));
        stage.setFullScreenExitHint("Quit fullscreen-mode with Control+Q");

        InvalidationListener invalidationListener = observable -> {
            boolean fullscreen = ((ReadOnlyBooleanProperty) observable).get();

            stage.setFullScreen(fullscreen);

            System.out.println("Set fullscreen " + (fullscreen ? "on" : "off"));
            if (!fullscreen) {
                System.out.println("Reset to default");
                // When leaving the fullscreen-mode, the application is maximized, which causes the maximize-button to
                // be a minimize-button.
                stage.setMaximized(false);
            }
            stage.centerOnScreen();
        };

        // Overwrite the maximize-function with entering fullscreen mode
        stage.maximizedProperty().addListener(invalidationListener);
        // for leaving the fullscreen mode
        stage.fullScreenProperty().addListener(invalidationListener);

        stage.show();
        stage.centerOnScreen();
    }
}

