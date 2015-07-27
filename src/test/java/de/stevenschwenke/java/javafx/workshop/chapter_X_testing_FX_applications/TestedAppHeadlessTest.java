package de.stevenschwenke.java.javafx.workshop.chapter_X_testing_FX_applications;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static org.junit.Assert.*;

//Extend the Test-Class with GuiTest

/**
 * Run with these arguments: -Dtestfx.robot=glass -Dglass.platform=Monocle -Dmonocle.platform=Headless
 * Created by drandard on 21.07.2015.
 */
public class TestedAppHeadlessTest extends GuiTest {

    /**
     * Needs to be overwritten to obtain a Parent-/Root-Node.
     *
     * @return Root-/Parent-Node
     */
    @Override
    protected Parent getRootNode() {
        try {
            //Pick the FXML-File of your Application.
            return FXMLLoader.load(getClass().getResource("/testedApp.fxml"));
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Tests some of the features of the framework.
     */
    @Test
    public void testFXTest() {
        //You can click directly with ID or Caption and you can chain these methods
        clickOn("#minus").clickOn("-");

        //It's also possible to save to Nodes to variables
        Button minusID = find("#minus");
        Button minusCap = find("-");
        //Tests whether Nodes found by ID and Caption are equal
        assertEquals(minusID, minusCap);

        //Tests whether loops are handled properly (Delay w/ Click, No Delay w/ Drag)
        for (int i = 0; i < 4; i++) {
            clickOn((Button) find("#plus"));
        }

        //Tests whether casting find()-Results directly is possible
        assertEquals("4", ((TextField) find("#count")).getText());
    }

    /**
     * Tests the interaction between the plus/minus-Buttons and the Textfield.
     */
    @Test
    public void counterTest() {
        //Tests finding the Nodes with their Caption and ID's
        clickOn("+").clickOn("+").clickOn("#plus");
        assertEquals(((TextField) find("#count")).getText(), "3");
        clickOn("-").clickOn("#minus");
        assertEquals(((TextField) find("#count")).getText(), "1");

        Button minus = find("#minus");
        TextField count = find("#count");
        //Loops are possible as well
        for (int i = 0; i < 4; i++) {
            clickOn(minus);
        }
        assertEquals(count.getText(), "0");

    }

    /**
     * Tests the selection of CheckBoxes, RadioButtons(w/ Groups) and a ChoiceBox.
     */
    @Test
    public void selectionTest() {
        //Should be empty when nothing is selected
        clickOn("#refresh");
        Label selection = find("#selection");
        assertFalse(selection.getText().contains("Opt"));

        //Lets select some CheckBoxes
        clickOn("#opt1").clickOn("#refresh");
        assertTrue(selection.getText().contains("Opt1") && !selection.getText().contains("Opt2"));
        clickOn("#opt2").clickOn("#refresh");
        assertTrue(selection.getText().contains("Opt1") && selection.getText().contains("Opt2"));
        clickOn("#opt1").clickOn("#refresh");
        assertTrue(!selection.getText().contains("Opt1") && selection.getText().contains("Opt2"));

        //Now lets try the RadioButtons
        clickOn("#optA").clickOn("#refresh");
        assertTrue(selection.getText().contains("OptA"));
        clickOn("#optB").clickOn("#refresh");
        assertTrue(selection.getText().contains("OptB") && !selection.getText().contains("OptA"));

        //Then some ChoiceBox Testing
        ChoiceBox<String> choice = find("#choice");
        choice.getItems().forEach((item) -> {
            clickOn(choice);
            clickOn(item).clickOn("#refresh");
            assertTrue(selection.getText().contains(choice.getValue()));
        });

        //Finally let's test our final result
        assertTrue(selection.getText().contains("Opt2") && selection.getText().contains("OptB") && selection.getText().contains("Opt/"));
    }

    /**
     * Tests dragging around the slider-point.
     */
    @Test
    public void sliderTest() {
        //If you want to work with nodes in other ways than clicking etc., you should save the to a variable
        Slider sliderX = find("#sliderX");
        Slider sliderY = find("#sliderY");
        TextField textX = find("#textX");
        TextField textY = find("#textY");

        //Pretty hard to handle Sliders - ScenicView helps a lot!
        //Moving Slider by Keyboard
        clickOn(sliderX.getChildrenUnmodifiable().get(2)).type(KeyCode.RIGHT, 4).type(KeyCode.LEFT, 2);

        //Moving Slider with Mouse (You can't use .moveBy in headless Mode
        while (Integer.parseInt(textY.getText()) != 6) {
            if (Integer.parseInt(textY.getText()) < 6)
                drag(sliderY.getChildrenUnmodifiable().get(2)).dropBy(0, -5);
            else
                drag(sliderY.getChildrenUnmodifiable().get(2)).dropBy(0, 5);
        }

        //Checks the results
        assertTrue((int) sliderX.getValue() == (int) Double.parseDouble(textX.getText()));
        assertTrue((int) sliderY.getValue() == (int) Double.parseDouble(textY.getText()));

        assertTrue(Integer.parseInt(textX.getText()) == 2);
        assertTrue(Integer.parseInt(textY.getText()) == 6);
    }

    /**
     * Types something inside the TextArea and then scrolls back up.
     */
    @Test
    public void typeTest() {
        //Click on the TextArea first to focus it
        TextArea area = find("#area");
        clickOn(area).write("Hello FX-World...\n\n\n\n\n\n\n\n");

        //Hard to access Scrollbars - ScenicView helps a lot!
        ScrollBar ScrollY = ((ScrollBar) ((ScrollPane) area.getChildrenUnmodifiable().get(0)).getChildrenUnmodifiable().get(1));
        drag(ScrollY).dropBy(0, -10);
    }

}