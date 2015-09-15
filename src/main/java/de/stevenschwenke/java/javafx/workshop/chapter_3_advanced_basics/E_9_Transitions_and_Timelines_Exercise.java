package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class demonstrates transitions and timelines.
 *
 * Your task is to make the rectangle move around the visible screen.
 */
public class E_9_Transitions_and_Timelines_Exercise extends Application {

  private AnchorPane root;

  @Override
  public void start(Stage stage) throws Exception {
    root =  new AnchorPane();
    Scene scene = new Scene(root, 600, 600);

    // Transition
    final Rectangle rect1 = new Rectangle(10, 10, 100, 100);
    rect1.setArcHeight(20);
    rect1.setArcWidth(20);
    rect1.setFill(Color.RED);
    root.getChildren().add(rect1);

    FadeTransition ft = new FadeTransition(Duration.millis(3000), rect1);
    ft.setFromValue(1.0);
    ft.setToValue(0.1);
    ft.setCycleCount(Timeline.INDEFINITE);
    ft.setAutoReverse(true);
    ft.play();

    // Timelines
    Timeline timelineUp = new Timeline();
    final KeyValue kvUp1 = new KeyValue(rect1.translateYProperty(), 10);
    final KeyValue kvUp2 = new KeyValue(rect1.translateYProperty(), 450);
    final KeyFrame kfUp = new KeyFrame(Duration.millis(3000), kvUp1, kvUp2);
    timelineUp.getKeyFrames().addAll(kfUp);
    timelineUp.play();

    stage.setTitle("Transitions and Timelines");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
