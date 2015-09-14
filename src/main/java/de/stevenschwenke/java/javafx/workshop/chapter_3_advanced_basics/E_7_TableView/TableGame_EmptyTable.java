package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_7_TableView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is an empty table stub. Your task is to fill it with functionality.
 *
 * Created by bezze on 12.09.15.
 */
public class TableGame_EmptyTable extends Application implements Initializable {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/TableGame.fxml"));
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Enjoy it!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTable();

    }

    private void initTable() {
        // TODO
    }
}
