package de.stevenschwenke.java.javafx.workshop.chapter_5_custom_controls;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;


/**
 * The control holding the properties and the eventhandler.
 * <p>
 * Created by bezze on 04.09.15. Inspired by H Ebberts (guigarage.com).
 */
public class TriangelControl extends Control {

    private ObjectProperty<Color> backgroundFill;

    public TriangelControl() {
        backgroundFill = new SimpleObjectProperty<>(Color.BISQUE);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new TriangleControlSkin(this);
    }

    public Color getBackgroundFill() {
        return backgroundFill.get();
    }

    public void setBackgroundFill(Color backgroundFill) {
        this.backgroundFill.set(backgroundFill);
    }

    public ObjectProperty<Color> backgroundFillProperty() {
        return backgroundFill;
    }

    // EventHandling
    public final ObjectProperty<EventHandler<ActionEvent>> onActionPoperty() {
        return onAction;
    }

    public final void setOnAction(EventHandler<ActionEvent> eh) {
        onActionPoperty().set(eh);
    }

    public final EventHandler<ActionEvent> GetonAction() {
        return onActionPoperty().get();
    }

    private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<ActionEvent>>() {

        @Override
        protected void invalidated() {
            setEventHandler(ActionEvent.ACTION, get());
        }

        @Override
        public Object getBean() {
            return TriangelControl.this;
        }

        @Override
        public String getName() {
            return "onAction";
        }
    };
}
