package de.stevenschwenke.java.javafx.workshop.chapter_5_custom_controls;

import javafx.scene.control.MenuItem;
import javafx.scene.control.Skin;
import javafx.scene.control.SplitMenuButton;


/**
 * Created by bezze on 12.09.15.
 */
public class SMBReverted extends SplitMenuButton {


    public SMBReverted() {
        super();
    }

    public SMBReverted(MenuItem... items) {
        super(items);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SMBRevertedSkin(this);
    }
}
