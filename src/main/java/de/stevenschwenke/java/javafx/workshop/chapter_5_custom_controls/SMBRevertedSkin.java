package de.stevenschwenke.java.javafx.workshop.chapter_5_custom_controls;

import com.sun.javafx.scene.control.skin.SplitMenuButtonSkin;

/**
 * Created by bezze on 12.09.15.
 */
public class SMBRevertedSkin extends SplitMenuButtonSkin {

    public SMBRevertedSkin(SMBReverted control) {
        super(control);
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {

        double arrowButtonWidth = snapSize(arrowButton.prefWidth(-1));

        arrowButton.resizeRelocate(x, y, arrowButtonWidth, h);
        label.resizeRelocate(x + arrowButtonWidth - 1, y, w - arrowButtonWidth, h);
    }
}
