package de.stevenschwenke.java.javafx.workshop.chapter_6_testing_FX_applications;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Place openjfx-monocle-1.8.0_20.jar in your JDK\jre\lib\ext folder. Then run with these arguments:
 * -Dtestfx.robot=glass -Dglass.platform=Monocle -Dmonocle.platform=Headless
 *
 * Created by drandard on 21.07.2015.
 */
class TestedAppHeadlessTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/testedApp.fxml"));
        Scene scene = new Scene(parent, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Tests some of the features of the framework.
     */
    @Test void testFXTest() {
        ApplicationTest x = new ApplicationTest() {
            @Override
            public void start(Stage stage) throws Exception {

            }
        };
        //You can click directly with ID or Caption and you can chain these methods
        clickOn("#minus").clickOn("-");

        //It's also possible to save to Nodes to variables
        Button minusID = lookup("#minus").query();
        Button minusCap = lookup("-").query();
        //Tests whether Nodes found by ID and Caption are equal
        assertEquals(minusID, minusCap);

        //Tests whether loops are handled properly (Delay w/ Click, No Delay w/ Drag)
        for (int i = 0; i < 4; i++) {
            clickOn((Button) lookup("#plus").query());
        }

        //Tests whether casting lookup()-Results directly is possible
        assertEquals("4", ((TextField) lookup("#count").query()).getText());
    }

    /**
     * Tests the interaction between the plus/minus-Buttons and the Textfield.
     */
    @Test void counterTest() {
        //Tests finding the Nodes with their Caption and ID's
        clickOn("+").clickOn("+").clickOn("#plus");
        assertEquals(((TextField) lookup("#count").query()).getText(), "3");
        clickOn("-").clickOn("#minus");
        assertEquals(((TextField) lookup("#count").query()).getText(), "1");

        Button minus = lookup("#minus").query();
        TextField count = lookup("#count").query();
        //Loops are possible as well
        for (int i = 0; i < 4; i++) {
            clickOn(minus);
        }
        assertEquals(count.getText(), "0");

    }

    /**
     * Tests the selection of CheckBoxes, RadioButtons(w/ Groups) and a ChoiceBox.
     */
    @Test void selectionTest() {
        //Should be empty when nothing is selected
        clickOn("#refresh");
        Label selection = lookup("#selection").query();
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
        ChoiceBox<String> choice = lookup("#choice").query();
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
    @Test void sliderTest() {
        //If you want to work with nodes in other ways than clicking etc., you should save the to a variable
        Slider sliderX = lookup("#sliderX").query();
        Slider sliderY = lookup("#sliderY").query();
        TextField textX = lookup("#textX").query();
        TextField textY = lookup("#textY").query();

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
    @Test void typeTest() {
        //Click on the TextArea first to focus it
        TextArea area = lookup("#area").query();
        clickOn(area).write("Hello FX-World...\n\n\n\n\n\n\n\n");

        //Hard to access Scrollbars - ScenicView helps a lot!
        ScrollBar ScrollY = ((ScrollBar) ((ScrollPane) area.getChildrenUnmodifiable().get(0)).getChildrenUnmodifiable().get(1));
        drag(ScrollY).dropBy(0, -10);
    }

}