package de.stevenschwenke.java.javafx.workshop.chapter_6_custom_controls;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * <p>
 * Created by bezze on 04.09.15.
 */
public class TriangleControlDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        StackPane root = new StackPane();
        root.getChildren().add(initControl());
        Scene scene = new Scene(root, 600, 600);

        stage.setTitle("TriangleControlDemo");
        stage.setScene(scene);
        stage.show();
    }

    private TriangelControl initControl() {
        TriangelControl c = new TriangelControl();
        c.setId("trialngle_control");
        c.setPadding(new Insets(20));

        c.setOnAction((e) -> {
            Random r = new Random(System.currentTimeMillis());
            c.setBackgroundFill(Color.color(r.nextDouble(), r.nextDouble(), r.nextDouble()));
        });
        return c;
    }
}
