package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.e7;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * Shows a dialog to choose items from a list
 * 
 * @author scavenger156
 *
 */

public class E_7_AssignListDialog extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// All Items
		List<String> allItems = Arrays.asList("Wolfsburg", "Dresden", "Emden", "Zwickau-Mosel", "Bratislava", "Palmela", "Portugal", "Pamplona", "Spanien", "Puebla", "Curitiba", "Brasilien", "Uitenhage", "Hannover", "Posen", "Polen", "Düsseldorf", "Ludwigsfelde");
		// A ListView to display the items from the choosing

		ListView<String> toEnhance = new ListView<String>();
		Button btnSelect = new Button("Select items");
		btnSelect.setOnAction((evt) -> {
			// Show the dialog
				Optional<List<String>> choosed = showDialog("Choose your items", allItems, toEnhance.getItems(), null);
				if (choosed.isPresent()) {
					toEnhance.getItems().setAll(choosed.get());
				}
			});

		BorderPane container = new BorderPane(toEnhance);

		container.setBottom(btnSelect);

		primaryStage.setScene(new Scene(container));

		primaryStage.show();
	}

	public static <T> Optional<List<T>> showDialog(String title, List<T> all, List<T> assigned, StringConverter<T> converter) {
		// Create a FX-Dialog

		Alert alert = new Alert(AlertType.NONE, null, ButtonType.APPLY, ButtonType.CANCEL);
		alert.setTitle(title);
		//UI Container
		AssignListUI<T> uic = new AssignListUI<T>(converter);
		uic.setAvaibleItems(all);
		uic.setAssignedItems(assigned);
		alert.getDialogPane().setContent(uic);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			if (result.get() == ButtonType.APPLY) {
				return Optional.of(uic.getAssignedItems());
			}
		}

		return Optional.empty();
	}

}
