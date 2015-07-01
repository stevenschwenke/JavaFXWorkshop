package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Demonstrates different {@link StageStyle}s.
 */
public class E_2_StageStyles extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        new StyledStage(StageStyle.UTILITY, Color.AZURE, 0);
        new StyledStage(StageStyle.UNIFIED, Color.LIGHTSKYBLUE,100);
        new StyledStage(StageStyle.UNDECORATED, Color.AQUA,200);
        new StyledStage(StageStyle.TRANSPARENT, null,300);
        new StyledStage(StageStyle.DECORATED, Color.BLANCHEDALMOND,400);
    }

    public class StyledStage extends Application {

        private final Color backgroundColor;
        private StageStyle stageStyle;
        private int positionOnScreen;

        public StyledStage(StageStyle style, Color backgroundColor, int positionOnScreen) {
            this.stageStyle = style;
            this.backgroundColor = backgroundColor;
            this.positionOnScreen = positionOnScreen;
            start(new Stage());
        }

        @Override
        public void start(Stage stage) {
            stage.initStyle(stageStyle);
            Text text = new Text(stageStyle.name());
            text.setFont(new Font(40));

            VBox box = new VBox();
            box.getChildren().add(text);
            final Scene scene = new Scene(box, 300, 250);
            stage.setScene(scene);
            scene.setFill(backgroundColor);
            stage.setX(positionOnScreen);
            stage.setY(positionOnScreen);
            stage.show();
        }
    }
}
