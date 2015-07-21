package de.stevenschwenke.java.javafx.workshop.chapter_X_testing_FX_applications;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

//Extend the Test-Class with GuiTest

/**
 * Test with Head
 * Created by drandard on 21.07.2015.
 */
public class TestedAppHeadTest extends GuiTest {

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
        //It's also possible to save to Nodes to variables
        Button minusID = find("#minus");
        Button minusCap = find("-");
        //Tests whether Nodes for by ID or Caption are equal
        Assert.assertEquals(minusID, minusCap);

        //Tests whether loops are handled properly
        for (int i = 0; i < 4; i++) {
            clickOn((Button) find("#plus"));
        }
        //You can also click directly with ID or Caption and you can chain these methods
        clickOn("#minus").clickOn("-");
        //Tests whether casting find()-Results directly is possible
        Assert.assertEquals(((TextField) find("#count")).getText(), "2");
    }

    /**
     * Tests the interaction between the plus/minus-Buttons and the Textfield.
     */
    @Test
    public void counterTest() {
        //Tests finding the Nodes with their Caption and ID's
        clickOn("+").clickOn("+").clickOn("#plus");
        Assert.assertEquals(((TextField) find("#count")).getText(), "3");
        clickOn("-").clickOn("#minus");
        Assert.assertEquals(((TextField) find("#count")).getText(), "1");

        Button minus = find("#minus");
        TextField count = find("#count");
        //Loops are possible as well
        for (int i = 0; i < 4; i++) {
            clickOn(minus);
        }
        Assert.assertEquals(count.getText(), "0");

    }

    /**
     * Tests the selection of CheckBoxes, RadioButtons and a ChoiceBox.
     */
    @Test
    public void selectionTest() {
        //Should be empty when nothing is selected
        clickOn("#refresh");
        Label selection = find("#selection");
        Assert.assertFalse(selection.getText().contains("Opt"));

        //Lets select a CheckBox
        clickOn("#opt1").clickOn("#refresh");
        Assert.assertTrue(selection.getText().contains("Opt1") && !selection.getText().contains("Opt2"));
        clickOn("#opt2").clickOn("#refresh");
        Assert.assertTrue(selection.getText().contains("Opt1") && selection.getText().contains("Opt2"));
        clickOn("#opt1").clickOn("#refresh");
        Assert.assertTrue(!selection.getText().contains("Opt1") && selection.getText().contains("Opt2"));

        //Now lets try the RadioButtons
        clickOn("#optA").clickOn("#refresh");
        Assert.assertTrue(selection.getText().contains("OptA"));
        clickOn("#optB").clickOn("#refresh");
        Assert.assertTrue(selection.getText().contains("OptB") && !selection.getText().contains("OptA"));

        //Finally some ChoiceBox Testing
        ChoiceBox<String> choice = find("#choice");
        choice.getItems().forEach((item) -> {
            clickOn(choice);
            clickOn(item).clickOn("#refresh");
            Assert.assertTrue(selection.getText().contains(choice.getValue()));
        });

        //For the finish let's test our final result
        Assert.assertTrue(selection.getText().contains("Opt2") && selection.getText().contains("OptB") && selection.getText().contains("Opt/"));
    }

    @Test
    public void sliderTest() {
        Slider sliderX = find("#sliderX");
        Slider sliderY = find("#sliderY");
        Label labelX = find("#labelX");
        Label labelY = find("#labelY");
        drag(sliderX.getChildrenUnmodifiable().get(1)).dropBy(20, 0);
        drag(sliderY.getChildrenUnmodifiable().get(1)).dropBy(0, -20);

        Assert.assertTrue((((int) sliderX.getValue() == 5)));
        Assert.assertTrue((((int) sliderY.getValue() == 4)));
    }

    @Test
    public void typeTest() {
        TextArea area = find("#area");
        ScrollBar ScrollY = ((ScrollBar) ((ScrollPane) area.getChildrenUnmodifiable().get(0)).getChildrenUnmodifiable().get(1));
        clickOn(area).write("Hello FX-World...\n.\n.\n.\n.\n.\n.\n.\n.");
        drag(ScrollY).dropBy(0, -10);
    }

}