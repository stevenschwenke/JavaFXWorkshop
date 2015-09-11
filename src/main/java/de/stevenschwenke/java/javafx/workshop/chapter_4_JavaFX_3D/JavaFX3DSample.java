package de.stevenschwenke.java.javafx.workshop.chapter_4_JavaFX_3D;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Interactive JavaFX 3D-Sample.
 *
 * @author drandard (https://github.com/drandard)
 * @since 07.07.2015
 */
public class JavaFX3DSample extends Application {

    final StackPane root = new StackPane();
    final DoubleProperty negCamX = new SimpleDoubleProperty(0);
    final DoubleProperty negCamY = new SimpleDoubleProperty(0);
    final DoubleProperty negCamZ = new SimpleDoubleProperty(0);

    @Override
    public void start(Stage primaryStage) {
        //X Axis   _____
        //         -   +

        //Y Axis   |   +
        //         |   -

        //Z Axis   /   +
        //       /   -
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
        Label lblW = new Label("W");
        Label lblA = new Label("A");
        Label lblS = new Label("S");
        Label lblD = new Label("D");
        Label lblQ = new Label("Q");
        Label lblE = new Label("E");
        lblW.setTranslateY(-150);
        lblA.setTranslateX(-150);
        lblS.setTranslateY(150);
        lblD.setTranslateX(150);
        lblQ.setTranslateZ(150);
        lblE.setTranslateZ(-150);
        axes.getChildren().addAll(xAxis, yAxis, zAxis, lblW, lblA, lblS, lblD, lblQ, lblE);
        axes.getChildren().forEach((node) -> {
            if (node instanceof Label) node.setStyle("-fx-text-fill: #000000");
        });

        //Materials
        PhongMaterial boxMat = new PhongMaterial(new Color(.8, .1, .3, 1));
        PhongMaterial xMat = new PhongMaterial(Color.RED);
        PhongMaterial yMat = new PhongMaterial(Color.GREEN);
        PhongMaterial zMat = new PhongMaterial(Color.BLUE);

        //Apply Mat's
        box.setMaterial(boxMat);
        box.setDrawMode(DrawMode.FILL);
        boxXAxis.setMaterial(xMat);
        boxYAxis.setMaterial(yMat);
        boxZAxis.setMaterial(zMat);
        xAxis.setMaterial(xMat);
        yAxis.setMaterial(yMat);
        zAxis.setMaterial(zMat);

        //Lighting
        AmbientLight ambientLightGlobal = new AmbientLight();
        AmbientLight ambientLightBox = new AmbientLight(new Color(.3, 0, .01, 1));

        PointLight pointLight = new PointLight();
        pointLight.setTranslateX(100);
        pointLight.setTranslateY(-100);
        pointLight.setTranslateZ(-150);

        //Lighting - Visualization
        Sphere pointSphere = new Sphere(20);
        pointSphere.setMaterial(new PhongMaterial(Color.LIGHTYELLOW));
        pointSphere.setTranslateX(pointLight.getTranslateX() * 1.5);
        pointSphere.setTranslateY(pointLight.getTranslateY() * 1.5);
        pointSphere.setTranslateZ(pointLight.getTranslateZ() * 1.5);

        //Lighting - Scoping
        ambientLightGlobal.getScope().addAll(boxXAxis, boxYAxis, boxZAxis, xAxis, yAxis, zAxis, pointSphere);
        ambientLightBox.getScope().addAll(box);
        pointLight.getScope().addAll(box);

        //Camera setup
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(2000.0);
        camera.setNearClip(0.1);
        camera.setFieldOfView(60);
        camera.setTranslateX(40);
        camera.setTranslateY(-20);
        camera.setTranslateZ(-450);
        camera.getTransforms().add(new Rotate(-5, Rotate.Y_AXIS));
        camera.getTransforms().add(new Rotate(-5, Rotate.X_AXIS));

        //Camera-Rotation-Setup
        negCamX.bind(camera.translateXProperty().negate());
        negCamY.bind(camera.translateYProperty().negate());
        negCamZ.bind(camera.translateZProperty().negate());

        //Initial rotation
        box3D.getTransforms().add(new Rotate(5, Rotate.X_AXIS));
        box3D.getTransforms().add(new Rotate(5, Rotate.Y_AXIS));
        axes.getTransforms().add(new Rotate(5, Rotate.X_AXIS));
        axes.getTransforms().add(new Rotate(5, Rotate.Y_AXIS));
        camera.getTransforms().add(new Rotate(-5, negCamX.get(), negCamY.get(), negCamZ.get(), Rotate.X_AXIS));
        camera.getTransforms().add(new Rotate(-5, negCamX.get(), negCamY.get(), negCamZ.get(), Rotate.Y_AXIS));
        camera.getTransforms().add(new Rotate(-1, negCamX.get(), negCamY.get(), negCamZ.get(), Rotate.Z_AXIS));

        //Overlay
        Text txtControls = new Text("\n\nCam Controls:\n");
        Text txtX = new Text("X-Axis\nCam: A/D\nBox: Up/Down\n\n");
        Text txtY = new Text("Y-Axis\nCam: W/S\nBox: Left/Right\n\n");
        Text txtZ = new Text("Z-Axis\nCam: Q/E\nBox: Sh+L/Sh+R\n\n");
        Text txtT = new Text("Tilt\nAxis: Sh+Q/Sh+E\n\n");
        Text txtP = new Text("Pan\nCam: Sh+WASD\n\n");
        Text txtCamera = new Text("Camera Pos:\n");
        Text txtCameraX = new Text();
        Text txtCameraY = new Text();
        Text txtCameraZ = new Text();
        //Overlay - Coloring
        new Group(txtControls, txtT, txtP, txtCamera).getChildren().forEach((node) -> ((Text) node).setFill(Color.WHITE));
        new Group(txtX, txtCameraX).getChildren().forEach((node) -> ((Text) node).setFill(Color.RED.brighter()));
        new Group(txtY, txtCameraY).getChildren().forEach((node) -> ((Text) node).setFill(Color.GREEN.brighter().brighter()));
        new Group(txtZ, txtCameraZ).getChildren().forEach((node) -> ((Text) node).setFill(Color.BLUE.brighter()));
        //Overlay - Integration
        TextFlow instructions = new TextFlow(txtControls, txtX, txtY, txtZ, txtT, txtP, txtCamera, txtCameraX, new Text("\n"), txtCameraY, new Text("\n"), txtCameraZ);
        instructions.getChildren().forEach(node -> ((Text) node).setFont(Font.font("Arial", 11)));
        instructions.setTextAlignment(TextAlignment.CENTER);
        instructions.setBackground(new Background(new BackgroundFill(new Color(0, 0, 0, .6), new CornerRadii(25), Insets.EMPTY)));
        instructions.setMaxWidth(120);
        instructions.setMaxHeight(335);
        //Overlay - Binding
        Bindings.bindBidirectional(txtCameraX.textProperty(), camera.translateXProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtCameraY.textProperty(), camera.translateYProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtCameraZ.textProperty(), camera.translateZProperty(), new NumberStringConverter());

        //SubScene Setting
        subRoot.getChildren().addAll(box3D, ambientLightGlobal, pointLight, pointSphere, axes, ambientLightBox);
        SubScene subScene = new SubScene(subRoot, 1300, 768, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);

        //root Setting
        root.getChildren().addAll(subScene, instructions);
        root.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        StackPane.setAlignment(instructions, Pos.TOP_RIGHT);
        StackPane.setAlignment(subScene, Pos.CENTER);
        StackPane.setMargin(instructions, new Insets(10));

        //Scene/Stage Setting
        Scene scene = new Scene(root, 1300, 768, true);
        primaryStage.setTitle("Java3D Sample");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Controls
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.isShiftDown()) {
                switch (key.getCode()) {
                    //Rotate Camera
                    case W:
                        camera.getTransforms().add(new Rotate(-5, negCamX.get(), negCamY.get(), negCamZ.get(), Rotate.X_AXIS));
                        return;
                    case A:
                        camera.getTransforms().add(new Rotate(5, negCamX.get(), negCamY.get(), negCamZ.get(), Rotate.Y_AXIS));
                        return;
                    case S:
                        camera.getTransforms().add(new Rotate(5, negCamX.get(), negCamY.get(), negCamZ.get(), Rotate.X_AXIS));
                        return;
                    case D:
                        camera.getTransforms().add(new Rotate(-5, negCamX.get(), negCamY.get(), negCamZ.get(), Rotate.Y_AXIS));
                        return;
                    case Q:
                        camera.getTransforms().add(new Rotate(-5, negCamX.get(), negCamY.get(), negCamZ.get(), Rotate.Z_AXIS));
                        return;
                    case E:
                        camera.getTransforms().add(new Rotate(5, negCamX.get(), negCamY.get(), negCamZ.get(), Rotate.Z_AXIS));
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
