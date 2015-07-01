package de.stevenschwenke.java.javafx.workshop.chapter_3_additional_controls;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Demonstrates the fullscreen mode.
 */
public class FullScreenDemo extends Application {

    private Label label = new Label("Click maximize (in the toolbar) to go to fullscreen");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        root.setCenter(label);
        Scene scene = new Scene(root, 418, 550);
        stage.setTitle("Full Screen Demo");
        stage.setScene(scene);

        // Exit fullscreen with escape and tell when entering fullscreen mode
        stage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.ESCAPE));
        stage.setFullScreenExitHint("Quit fullscreen-mode with ESC");

        InvalidationListener fullscreenListener = observable -> {
            boolean fullscreen = ((ReadOnlyBooleanProperty) observable).get();

            stage.setFullScreen(fullscreen);

            if (fullscreen) {
                label.setText("Set fullscreen on. Quit fullscreen-mode with ESC");
            } else {
                label.setText("Reset to default. Click maximize (in the toolbar) to go to fullscreen");
                // When leaving the fullscreen-mode, the application is maximized, which causes the maximize-button to
                // be a minimize-button.
                stage.setMaximized(false);
            }
        };

        // Use our logic above when entering and leaving fullscreen-mode
        stage.maximizedProperty().addListener(fullscreenListener);
        stage.fullScreenProperty().addListener(fullscreenListener);

        stage.show();
    }
}

