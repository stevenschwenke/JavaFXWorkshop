package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * Created by schwenks on 01.07.2015.
 */
public class JavaFX3D extends Application {

    final BorderPane root = new BorderPane();
//    final Group root = new Group();

    @Override
    public void start(Stage primaryStage) {
        //X Axis -
        //Y Axis |
        //Z Axis /
        Label lblX = new Label("x=red");
        Label lblY = new Label("y=green");
        Label lblZ = new Label("z=blue");
        FlowPane flow = new FlowPane(lblX,lblY,lblZ);
        flow.setAlignment(Pos.CENTER);
        flow.setOrientation(Orientation.VERTICAL);
        flow.setVgap(10);

        Group rootie = new Group();

        //Box and Coordinate lines
        Box box = new Box(100, 100, 100);
        Box boxx = new Box(150, 1, 1);
        Box boxy = new Box(1, 150, 1);
        Box boxz = new Box(1, 1, 150);
        Box x = new Box(10000, 1, 1);
        Box y = new Box(1, 10000, 1);
        Box z = new Box(1, 1, 10000);
        box.getStyleClass().add("box");
        box.setStyle("-fx-smooth: true");

        //Materials
        PhongMaterial pm = new PhongMaterial();
        pm.setDiffuseColor(new Color(.8, .1, .2, 1));
        pm.setSpecularColor(new Color(.2, .02, .03, 1));
        box.setMaterial(pm);
        x.setMaterial(new PhongMaterial(new Color(1, 0, 0, 1)));
        y.setMaterial(new PhongMaterial(new Color(0, 1, 0, 1)));
        z.setMaterial(new PhongMaterial(new Color(0, 0, 1, 1)));
        boxx.setMaterial(new PhongMaterial(new Color(1, 0, 0, 1)));
        boxy.setMaterial(new PhongMaterial(new Color(0, 1, 0, 1)));
        boxz.setMaterial(new PhongMaterial(new Color(0, 0, 1, 1)));

        //initial rotation
        box.getTransforms().add(new Rotate(10, Rotate.X_AXIS));
        box.getTransforms().add(new Rotate(10, Rotate.Y_AXIS));
        boxx.getTransforms().add(new Rotate(10, Rotate.X_AXIS));
        boxx.getTransforms().add(new Rotate(10, Rotate.Y_AXIS));
        boxy.getTransforms().add(new Rotate(10, Rotate.X_AXIS));
        boxy.getTransforms().add(new Rotate(10, Rotate.Y_AXIS));
        boxz.getTransforms().add(new Rotate(10, Rotate.X_AXIS));
        boxz.getTransforms().add(new Rotate(10, Rotate.Y_AXIS));

        //Lighting
        AmbientLight ambientLight = new AmbientLight(new Color(1, .5, .6, 1));
        ambientLight.setTranslateX(0);
        ambientLight.setTranslateY(0);
        ambientLight.setTranslateZ(0);
//        ambientLight.getScope().addAll(box);
        PointLight pointLight = new PointLight(new Color(1, .6, .6, 1));
        pointLight.setTranslateX(100);
        pointLight.setTranslateY(100);
        pointLight.setTranslateZ(-500);
//        pointLight.setOpacity(0.3);
        pointLight.getScope().addAll(box);

        root.setRight(flow);
        rootie.getChildren().addAll(box, boxx, boxy, boxz, ambientLight, pointLight, x, y, z);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setTranslateZ(-1000);

        Scene scene = new Scene(root, 1024, 768, true);
//        scene.getStylesheets().add("3d.css");
        SubScene subScene = new SubScene(rootie, 940, 768, true, SceneAntialiasing.BALANCED);
        subScene.prefHeight(Double.MAX_VALUE);
        subScene.prefWidth(Double.MAX_VALUE);
        subScene.setFill(Color.LIGHTGREY);
        subScene.setCamera(camera);

        root.setCenter(subScene);
        root.setMargin(flow,new Insets(20,20,20,20));
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            /////
            //Move Camera X/Y
            /////
            if (key.getCode() == KeyCode.D && !key.isShiftDown()) {
                camera.setTranslateX(camera.getTranslateX() - 10);
                return;
            }
            if (key.getCode() == KeyCode.A && !key.isShiftDown()) {
                camera.setTranslateX(camera.getTranslateX() + 10);
                return;
            }
            if (key.getCode() == KeyCode.S && !key.isShiftDown()) {
                camera.setTranslateY(camera.getTranslateY() - 10);
                return;
            }
            if (key.getCode() == KeyCode.W && !key.isShiftDown()) {
                camera.setTranslateY(camera.getTranslateY() + 10);
                return;
            }
            /////
            //Move Camera Z
            /////
            if (key.getCode() == KeyCode.PLUS) {
                camera.setTranslateZ(camera.getTranslateZ() + 10);
                return;
            }
            if (key.getCode() == KeyCode.MINUS) {
                camera.setTranslateZ(camera.getTranslateZ() - 10);
                return;
            }
            /////
            //Rotate Camera
            /////
            if (key.isShiftDown() && key.getCode() == KeyCode.D) {
                camera.getTransforms().add(new Rotate(-5, 0, 0, 0));
                return;
            }
            if (key.isShiftDown() && key.getCode() == KeyCode.A) {
                camera.getTransforms().add(new Rotate(-5, 0, 0, 0, new Point3D(0, 100, 0)));
                return;
            }
            if (key.isShiftDown() && key.getCode() == KeyCode.S) {
                camera.getTransforms().add(new Rotate(-5, 0, 0, 0, new Point3D(0, 0, 100)));
                return;
            }
            if (key.isShiftDown() && key.getCode() == KeyCode.W) {
                camera.getTransforms().add(new Rotate(-5, 0, 0, 0, new Point3D(100, 0, 0)));
                return;
            }
            /////
            //Something
            /////
            if (key.getCode() == KeyCode.LEFT && !key.isShiftDown()) {
                box.getTransforms().add(new Rotate(10, Rotate.Y_AXIS));
                boxx.getTransforms().add(new Rotate(10, Rotate.Y_AXIS));
                boxy.getTransforms().add(new Rotate(10, Rotate.Y_AXIS));
                boxz.getTransforms().add(new Rotate(10, Rotate.Y_AXIS));
                return;
            }
            if (key.getCode() == KeyCode.RIGHT && !key.isShiftDown()) {
                box.getTransforms().add(new Rotate(-10, Rotate.Y_AXIS));
                boxx.getTransforms().add(new Rotate(-10, Rotate.Y_AXIS));
                boxy.getTransforms().add(new Rotate(-10, Rotate.Y_AXIS));
                boxz.getTransforms().add(new Rotate(-10, Rotate.Y_AXIS));
                return;
            }
            if (key.getCode() == KeyCode.UP && !key.isShiftDown()) {
                box.getTransforms().add(new Rotate(-5, Rotate.X_AXIS));
                boxx.getTransforms().add(new Rotate(-5, Rotate.X_AXIS));
                boxy.getTransforms().add(new Rotate(-5, Rotate.X_AXIS));
                boxz.getTransforms().add(new Rotate(-5, Rotate.X_AXIS));
                return;
            }
            if (key.getCode() == KeyCode.DOWN && !key.isShiftDown()) {
                box.getTransforms().add(new Rotate(5, Rotate.X_AXIS));
                boxx.getTransforms().add(new Rotate(5, Rotate.X_AXIS));
                boxy.getTransforms().add(new Rotate(5, Rotate.X_AXIS));
                boxz.getTransforms().add(new Rotate(5, Rotate.X_AXIS));
                return;
            }
            if (key.isShiftDown() && key.getCode() == KeyCode.LEFT) {
                box.getTransforms().add(new Rotate(-5, Rotate.Z_AXIS));
                boxx.getTransforms().add(new Rotate(-5, Rotate.Z_AXIS));
                boxy.getTransforms().add(new Rotate(-5, Rotate.Z_AXIS));
                boxz.getTransforms().add(new Rotate(-5, Rotate.Z_AXIS));
                return;
            }
            if (key.isShiftDown() && key.getCode() == KeyCode.RIGHT) {
                box.getTransforms().add(new Rotate(5, Rotate.Z_AXIS));
                boxx.getTransforms().add(new Rotate(5, Rotate.Z_AXIS));
                boxy.getTransforms().add(new Rotate(5, Rotate.Z_AXIS));
                boxz.getTransforms().add(new Rotate(5, Rotate.Z_AXIS));
                return;
            }
            if (key.getCode() == KeyCode.DELETE) {
                //TODO reset...
                return;
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
