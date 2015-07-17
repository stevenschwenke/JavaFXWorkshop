package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_6_ListView_searchable_by_keystroke;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Collections;

/**
 * Enhanced ListView that searches elements with key strokes.
 * 
 * @author scavenger156 (https://github.com/Scavenger156)
 *
 */
public class E_6_ListViewEnhanced extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ObservableList<String> elements = FXCollections.observableArrayList("Wolfsburg", "Dresden", "Emden", "Zwickau-Mosel", "Bratislava", "Palmela", "Portugal", "Pamplona", "Spanien", "Puebla", "Curitiba", "Brasilien", "Uitenhage", "Hannover", "Posen", "Polen", "Düsseldorf",
				"Ludwigsfelde");
		Collections.sort(elements);
		ListView<String> listView = new ListView<>(elements);

		// Notice the last parameter is null because the default string converter is used. You could use your own here without
		// altering the selection handler.
		KeyMoveSelectionHandler<String> keyHelper = new KeyMoveSelectionHandler<>(listView.getSelectionModel(), listView.getItems(), null);

		// Two ways of attaching the handler to the list:
		//listView.setOnKeyReleased(keyHelper);
		 listView.addEventHandler(KeyEvent.KEY_RELEASED, keyHelper);

		BorderPane borderPane = new BorderPane(listView);
		borderPane.setTop(new Label("Set focus in the list by typing the first letter of the city:"));
		primaryStage.setScene(new Scene(borderPane));
		primaryStage.show();
	}
}
