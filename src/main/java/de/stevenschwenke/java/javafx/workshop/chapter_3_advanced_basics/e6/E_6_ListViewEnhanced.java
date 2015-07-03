package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.e6;

import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollToEvent;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * Enhancing the ListView by searching elements with da key.
 * 
 * @author scavenger156
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
		ListView<String> toEnhance = new ListView<String>(elements);
		KeyMoveSelectionHandler<String> keyHelper = new KeyMoveSelectionHandler<>(toEnhance.getSelectionModel(), toEnhance.getItems(), null);

		// this or the other
		toEnhance.setOnKeyReleased(keyHelper);
		// toEnhance.addEventHandler(KeyEvent.KEY_RELEASED, keyHelper);

		primaryStage.setScene(new Scene(toEnhance));

		primaryStage.show();
	}

	
}
