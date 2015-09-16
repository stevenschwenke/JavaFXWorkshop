package de.stevenschwenke.java.javafx.workshop.chapter_X_Swing_interop;

import java.awt.*;

import javax.swing.*;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * This class demonstrates that JavaFX can be embedded in Swing.
 *
 * This example is an enhanced copy from an example of Hendrik Ebbers. See https://github.com/guigarage/mastering-javafx-controls.
 */
public class SwingInterop {

    private static JButton swingButton;
    private static Button jfxButton;

    public static void main(String[] args) {

        final JFXPanel jfxPanel = new JFXPanel();

        SwingUtilities.invokeLater(() -> {
            JFrame swingFrame = new JFrame("Integrate JavaFX in Swing");
            swingFrame.getContentPane().setLayout(new BorderLayout());
            swingButton = new JButton("I'm a Swing button");
            swingFrame.getContentPane().add(BorderLayout.NORTH, swingButton);
            swingButton.addActionListener((e) -> {
                // This is necessary in some cases so the FX-code can run on the FX Application
                // Thread.
                //Platform.runLater(() -> {
                jfxButton.setDisable(!jfxButton.isDisable());
                //});
            });

            swingFrame.getContentPane().add(BorderLayout.CENTER, jfxPanel);
            jfxButton = new Button("I'm a JavaFX button");
            StackPane jfxPane = new StackPane(jfxButton);
            Scene jfxScene = new Scene(jfxPane);
            jfxPanel.setScene(jfxScene);
            jfxButton.setOnAction((e) -> {
                // This is necessary in some cases so the Swing-code can run on the Event
                // Dispatcher Thread (EDT)
                //SwingUtilities.invokeLater(() -> {
                swingButton.setEnabled(!swingButton.isEnabled());
                //});
            });
            swingFrame.setVisible(true);
            swingFrame.setSize(200, 200);
        });
    }
}