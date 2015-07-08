package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Created by drandard on 07.07.2015.
 * JavaFX 3D-Sample
 */
public class JavaFX3DSample extends Application {

    final BorderPane root = new BorderPane();

    @Override
    public void start(Stage primaryStage) {
        //X Axis -  __  Y Axis |  __  Z Axis /
        Group subRoot = new Group();
        Group box3D = new Group();
        Group axes = new Group();

        //Box and Box-Coordinate lines
        Box box = new Box(100, 100, 100);
        Box boxXAxis = new Box(150, 1, 1);
        Box boxYAxis = new Box(1, 150, 1);
        Box boxZAxis = new Box(1, 1, 150);
        box3D.getChildren().addAll(box, boxXAxis, boxYAxis, boxZAxis);

        //Coordinate lines
        Box xAxis = new Box(10000, 1, 1);
        Box yAxis = new Box(1, 10000, 1);
        Box zAxis = new Box(1, 1, 10000);
        axes.getChildren().addAll(xAxis, yAxis, zAxis);

        //Materials
        PhongMaterial boxMat = new PhongMaterial(new Color(.8, .1, .3, 1));
        PhongMaterial xMat = new PhongMaterial(Color.RED);
        PhongMaterial yMat = new PhongMaterial(Color.GREEN);
        PhongMaterial zMat = new PhongMaterial(Color.BLUE);

        //Apply Mat's
        box.setMaterial(boxMat);
        /////
        box.setDrawMode(DrawMode.FILL);
        /////
        boxXAxis.setMaterial(xMat);
        boxYAxis.setMaterial(yMat);
        boxZAxis.setMaterial(zMat);
        xAxis.setMaterial(xMat);
        yAxis.setMaterial(yMat);
        zAxis.setMaterial(zMat);

        //Lighting
        AmbientLight ambientLightGlobal = new AmbientLight();
        ambientLightGlobal.getScope().addAll(boxXAxis, boxYAxis, boxZAxis, xAxis, yAxis, zAxis);

        AmbientLight ambientLightBox = new AmbientLight(new Color(.3, 0, .01, 1));
        ambientLightBox.getScope().addAll(box);

        PointLight pointLight = new PointLight();
        pointLight.setTranslateX(100);
        pointLight.setTranslateY(-100);
        pointLight.setTranslateZ(-150);
        pointLight.getScope().addAll(box);

        //Lighting - Visualization
        Sphere pointBox = new Sphere(20);
        pointBox.setMaterial(new PhongMaterial(pointLight.getColor()));
        pointBox.setTranslateX(pointLight.getTranslateX() * 2);
        pointBox.setTranslateY(pointLight.getTranslateY() * 2);
        pointBox.setTranslateZ(pointLight.getTranslateZ() * 2);

        //Initial rotation
        box3D.getTransforms().add(new Rotate(5, Rotate.X_AXIS));
        box3D.getTransforms().add(new Rotate(5, Rotate.Y_AXIS));
        axes.getTransforms().add(new Rotate(5, Rotate.X_AXIS));
        axes.getTransforms().add(new Rotate(5, Rotate.Y_AXIS));

        //Camera setup
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(2000.0);
        camera.setNearClip(0.1);
        camera.setFieldOfView(60);
        camera.setTranslateX(30);
        camera.setTranslateY(-20);
        camera.setTranslateZ(-500);
        camera.getTransforms().add(new Rotate(-5, Rotate.Y_AXIS));
        camera.getTransforms().add(new Rotate(-5, Rotate.X_AXIS));

        //Legend
        Label lblControls = new Label("Cam Controls:");
        Label lblX = new Label("X-Axis\nCam: A/D\nBox: Up/Down");
        Label lblY = new Label("Y-Axis\nCam: W/S\nBox: Left/Right");
        Label lblZ = new Label("Z-Axis\nCam: Q/E\nBox: Sh+L/Sh+R");
        Label lblT = new Label("Tilt\nAxis: Sh+Q/Sh+E");
        Label lblP = new Label("Pan\nCam: Sh+WASD");
        lblX.getStyleClass().add("labelX");
        lblY.getStyleClass().add("labelY");
        lblZ.getStyleClass().add("labelZ");
        Label lblCamera = new Label("\n\nCamera Pos:");
        Label lblCameraX = new Label();
        Label lblCameraY = new Label();
        Label lblCameraZ = new Label();
        lblCameraX.getStyleClass().add("labelX");
        lblCameraY.getStyleClass().add("labelY");
        lblCameraZ.getStyleClass().add("labelZ");
        Bindings.bindBidirectional(lblCameraX.textProperty(), camera.translateXProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(lblCameraY.textProperty(), camera.translateYProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(lblCameraZ.textProperty(), camera.translateZProperty(), new NumberStringConverter());

        //Legend - FlowPane
        FlowPane flow = new FlowPane(lblControls, lblX, lblY, lblZ, lblT, lblP, lblCamera, lblCameraX, lblCameraY, lblCameraZ);
        flow.setOrientation(Orientation.VERTICAL);
        flow.setVgap(10);
        flow.setAlignment(Pos.CENTER_LEFT);

        //SubScene Setting
        subRoot.getChildren().addAll(box3D, ambientLightGlobal, pointLight, pointBox, axes, ambientLightBox);
        SubScene subScene = new SubScene(subRoot, 920, 770, true, SceneAntialiasing.BALANCED);
        subScene.prefHeight(Double.MAX_VALUE);
        subScene.prefWidth(Double.MAX_VALUE);
        subScene.setFill(Color.LIGHTGREY);
        subScene.setCamera(camera);

        //root Setting
        root.setRight(flow);
        root.setCenter(subScene);
        BorderPane.setAlignment(flow, Pos.CENTER_LEFT);
        BorderPane.setAlignment(subScene, Pos.CENTER_LEFT);
        BorderPane.setMargin(flow, new Insets(20, 20, 20, 0));

        //Scene/Stage Setting
        Scene scene = new Scene(root, 1024, 768, true);
        scene.getStylesheets().add("JavaFX3DSample.css");
        primaryStage.setTitle("Java3D Sample");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1070);
        primaryStage.show();

        //Controls
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.isShiftDown()) {
                switch (key.getCode()) {
                    //Rotate Camera
                    case W:
                        camera.getTransforms().add(new Rotate(5, Rotate.X_AXIS));
                        return;
                    case A:
                        camera.getTransforms().add(new Rotate(-5, Rotate.Y_AXIS));
                        return;
                    case S:
                        camera.getTransforms().add(new Rotate(-5, Rotate.X_AXIS));
                        return;
                    case D:
                        camera.getTransforms().add(new Rotate(5, Rotate.Y_AXIS));
                        return;
                    case Q:
                        camera.getTransforms().add(new Rotate(5, Rotate.Z_AXIS));
                        return;
                    case E:
                        camera.getTransforms().add(new Rotate(-5, Rotate.Z_AXIS));
                        return;

                    //Rotate Box
                    case LEFT:
                        box3D.getTransforms().add(new Rotate(-5, Rotate.Z_AXIS));
                        return;
                    case RIGHT:
                        box3D.getTransforms().add(new Rotate(5, Rotate.Z_AXIS));
                        return;
                    default:
                }
            } else {
                switch (key.getCode()) {
                    //Move Camera
                    case W:
                        camera.setTranslateY(camera.getTranslateY() - 10);
                        return;
                    case A:
                        camera.setTranslateX(camera.getTranslateX() - 10);
                        return;
                    case S:
                        camera.setTranslateY(camera.getTranslateY() + 10);
                        return;
                    case D:
                        camera.setTranslateX(camera.getTranslateX() + 10);
                        return;
                    case Q:
                        camera.setTranslateZ(camera.getTranslateZ() + 10);
                        return;
                    case E:
                        camera.setTranslateZ(camera.getTranslateZ() - 10);
                        return;

                    //Rotate Box
                    case UP:
                        box3D.getTransforms().add(new Rotate(-5, Rotate.X_AXIS));
                        return;
                    case DOWN:
                        box3D.getTransforms().add(new Rotate(5, Rotate.X_AXIS));
                        return;
                    case LEFT:
                        box3D.getTransforms().add(new Rotate(10, Rotate.Y_AXIS));
                        return;
                    case RIGHT:
                        box3D.getTransforms().add(new Rotate(-10, Rotate.Y_AXIS));
                        return;
                    default:
                }
            }
        });
    }
}
