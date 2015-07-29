package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by schwenks on 17.07.2015.
 */
public class E_10_SVG_and_Pixelation extends Application {

    private Display display = Display.BUTTON;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Button imageView = new Button("Hello World");
        BorderPane myPane = new BorderPane();
        ImageView imageView = new ImageView();
        Image javafxLogo = new Image("/Javafx_logo_color.png");
        Image svgLogo = new Image("/SVG_logo.svg"); // TODO read svg
        Label label = new Label("This is a label");
        imageView.setImage(javafxLogo);


        myPane.setCenter(imageView);

        EventHandler<MouseEvent> mouseEventEventHandler = event -> {
            if (myPane.getCenter().equals(imageView)) {
                myPane.setCenter(label);
            } else if (myPane.getCenter().equals(label)) {
                myPane.setCenter(imageView);
            }
        };
        imageView.onMouseClickedProperty().set(mouseEventEventHandler);
        label.onMouseClickedProperty().set(mouseEventEventHandler);

        VBox menu = new VBox();
        Slider zoomSlider = new Slider(0.2, 20, 1);
        zoomSlider.valueProperty().addListener(e -> {
            myPane.getCenter().setScaleX(zoomSlider.getValue());
            myPane.getCenter().setScaleY(zoomSlider.getValue());
        });
        Slider rotateSlider = new Slider(-180, 180, 0);
        rotateSlider.valueProperty().addListener(e -> {
            myPane.getCenter().setRotate(rotateSlider.getValue());
        });
        menu.getChildren().addAll(zoomSlider, rotateSlider);
        myPane.setBottom(menu);
        myPane.setBackground(null);
        Scene myScene = new Scene(myPane);
        myScene.setFill(Color.DARKORANGE);
        primaryStage.setScene(myScene);
        primaryStage.setTitle("App");
        primaryStage.setWidth(600);
        primaryStage.setHeight(500);
        primaryStage.show();

    }

    enum Display {
        BUTTON, PNG, SVG
    }
}
