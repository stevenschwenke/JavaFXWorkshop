package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

/**
 * Created by schwenks on 01.07.2015.
 */
public class JavaFX3D extends Application {

    final Group root = new Group();

    @Override
    public void start(Stage primaryStage) {

        Box box = new Box(100, 100, 100);
        Sphere sphere = new Sphere(50);

        AmbientLight ambientLight = new AmbientLight(Color.AQUA);
        ambientLight.setTranslateX(-180);
        ambientLight.setTranslateY(-90);
        ambientLight.setTranslateZ(-120);
        ambientLight.setOpacity(0.9);
        //ambientLight.getScope().addAll(box, sphere);

        PointLight pointLight = new PointLight(Color.AQUA);
        pointLight.setTranslateX(180);
        pointLight.setTranslateY(190);
        pointLight.setTranslateZ(180);
        //pointLight.getScope().addAll(box, sphere);

        root.getChildren().addAll(box, sphere, ambientLight, pointLight);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(1000.0);
        camera.setTranslateZ(-1000);

        Scene scene = new Scene(root, 1024, 768, true);
        scene.setFill(Color.GREY);
        scene.setCamera(camera);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.A) {
                camera.setLayoutX(camera.getLayoutX() - 10);
            }
            if (key.getCode() == KeyCode.D) {
                camera.setLayoutX(camera.getLayoutX() + 10);
            }
            if (key.getCode() == KeyCode.W) {
                camera.setLayoutY(camera.getLayoutY() - 10);
            }
            if (key.getCode() == KeyCode.S) {
                camera.setLayoutY(camera.getLayoutY() + 10);
            }
        });

        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
//                mouseOldX = mousePosX;
//                mouseOldY = mousePosY;
//                mousePosX = me.getX();
//                mousePosY = me.getY();
//                mouseDeltaX = mousePosX - mouseOldX;
//                mouseDeltaY = mousePosY - mouseOldY;
//                if (me.isAltDown() && me.isShiftDown() && me.isPrimaryButtonDown()) {
//                    cam.rz.setAngle(cam.rz.getAngle() - mouseDeltaX);
//                } else if (me.isAltDown() && me.isPrimaryButtonDown()) {
//                    cam.ry.setAngle(cam.ry.getAngle() - mouseDeltaX);
//                    cam.rx.setAngle(cam.rx.getAngle() + mouseDeltaY);
//                } else if (me.isAltDown() && me.isSecondaryButtonDown()) {
//                    double scale = cam.s.getX();
//                    double newScale = scale + mouseDeltaX * 0.01;
//                    cam.s.setX(newScale);
//                    cam.s.setY(newScale);
//                    cam.s.setZ(newScale);
//                } else if (me.isAltDown() && me.isMiddleButtonDown()) {
//                    cam.t.setX(cam.t.getX() + mouseDeltaX);
//                    cam.t.setY(cam.t.getY() + mouseDeltaY);
//                }
            }
        });

        primaryStage.setTitle("Molecule Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
