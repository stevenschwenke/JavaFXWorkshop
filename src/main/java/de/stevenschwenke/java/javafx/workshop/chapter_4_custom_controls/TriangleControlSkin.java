package de.stevenschwenke.java.javafx.workshop.chapter_4_custom_controls;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.SkinBase;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * The Skin defines the look and feel of our control.
 * It also defines the events and actions that will be called and handled on input actions.
 * <p>
 * Created by bezze on 04.09.15.
 */
public class TriangleControlSkin extends SkinBase<TriangelControl> {

    // The Shape
    private Path triangle;
    private boolean invalidTriangle = true;

    private ScaleTransition scaleTransition;
    private RotateTransition rotateTransition;
    private InnerShadow innerShadow;

    /**
     * Constructor for all SkinBase instances.
     *
     * @param control The control for which this Skin should attach to.
     */
    protected TriangleControlSkin(TriangelControl control) {
        super(control);

        initShadow();
        // listning to backgroundFill changes
        control.backgroundFillProperty().addListener(observable -> updateTriangleColor());

        // mouse pressed/released -> effect
        control.setOnMousePressed(event -> control.setEffect(innerShadow));
        control.setOnMouseReleased(event -> control.setEffect(null));
    }

    // ShapeMethods

    public void updateTriangleColor() {
        if (triangle != null) {
            triangle.setFill(getSkinnable().getBackgroundFill());
            getSkinnable().requestLayout();
        }
    }

    public void updateTriagle(double width, double height) {
        if (triangle != null) {
            getChildren().remove(triangle);
        }

        triangle = new Path();
        triangle.getElements().add(new MoveTo(width / 2, 0));
        triangle.getElements().add(new LineTo(width, height));
        triangle.getElements().add(new LineTo(0, height));

        //connect the elements
        triangle.getElements().addAll(new ClosePath());
        // colors
        triangle.setStroke(Color.BLACK);
        triangle.setFill(getSkinnable().getBackgroundFill());

        // mouse click -> fire action
        triangle.setOnMouseClicked(e -> getSkinnable().fireEvent(new ActionEvent()));

        // mouse in/out -> transition
        triangle.setOnMouseEntered(event -> zoomIn());
        triangle.setOnMouseExited(event -> {
            // rotate();
            zoomOut();
        });

        getChildren().add(triangle);
    }

    @Override
    public void dispose() {
        // cleanup
        if (scaleTransition != null) {
            scaleTransition.stop();
            scaleTransition = null;
        }
        if (rotateTransition != null) {
            rotateTransition.stop();
            rotateTransition = null;
        }
        super.dispose();
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {

        if (invalidTriangle) {
            updateTriagle(contentWidth, contentHeight);
            invalidTriangle = false;
        }
        layoutInArea(triangle, contentX, contentY, contentWidth, contentHeight, -1, HPos.CENTER, VPos.CENTER);
    }

    // Trasitions and effect
    private void zoomIn() {
        if (scaleTransition != null) {
            scaleTransition.pause();
        }
        scaleTransition = new ScaleTransition(Duration.millis(250), triangle);
        scaleTransition.setFromX(triangle.getScaleX());
        scaleTransition.setFromY(triangle.getScaleY());
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.play();
    }

    private void zoomOut() {
        if (scaleTransition != null) {
            scaleTransition.pause();
        }
        scaleTransition = new ScaleTransition(Duration.millis(250), triangle);
        scaleTransition.setFromX(triangle.getScaleX());
        scaleTransition.setFromY(triangle.getScaleY());
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }

    private void rotate() {
        if (rotateTransition != null) {
            rotateTransition.pause();
        }
        rotateTransition = new RotateTransition(Duration.millis(50), triangle);
        rotateTransition.setFromAngle(triangle.getRotate());
        rotateTransition.setToAngle(triangle.getRotate() == 0 ? 180 : 0);
        rotateTransition.play();
    }

    private void initShadow() {
        innerShadow = new InnerShadow();
        innerShadow.setOffsetX(1.0);
        innerShadow.setOffsetY(1.0);
    }

    // ComputeSizeMethods
    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return topInset + bottomInset + 200;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return rightInset + leftInset + 200;
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return topInset + bottomInset + 20;
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return rightInset + leftInset + 20;
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }
}
