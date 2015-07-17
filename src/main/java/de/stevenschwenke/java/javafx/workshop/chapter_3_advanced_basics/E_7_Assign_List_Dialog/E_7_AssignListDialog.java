package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_7_Assign_List_Dialog;

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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Shows a dialog to choose items from a list.
 *
 * @author scavenger156 (https://github.com/Scavenger156)
 *
 */
public class E_7_AssignListDialog extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		List<String> allItems = Arrays.asList("Wolfsburg", "Dresden", "Emden", "Zwickau-Mosel", "Bratislava", "Palmela", "Portugal", "Pamplona", "Spanien", "Puebla", "Curitiba", "Brasilien", "Uitenhage", "Hannover", "Posen", "Polen", "Düsseldorf", "Ludwigsfelde");
		ListView<String> listView = new ListView<>();
		Button btnSelect = new Button("Select items");
		btnSelect.setOnAction((evt) -> {

			// This dialog returns an Optional because it could be canceled or applied:
			Optional<List<String>> chosen = showDialog("Choose your items", allItems, listView.getItems(), null);
			if (chosen.isPresent()) {
				listView.getItems().setAll(chosen.get());
			}
		});

		BorderPane container = new BorderPane(listView);
		container.setBottom(btnSelect);
		primaryStage.setScene(new Scene(container));
		primaryStage.show();
	}

	/**
	 * Shows the dialog to select items.
	 *
	 * @param title of the dialog
	 * @param allItems list of all items to choose from
	 * @param chosenItems list of chosen items
	 * @param stringConverter to display items as strings and get items from strings
	 * @param <T> Type
	 * @return (optional) list of chosen items
	 */
	public static <T> Optional<List<T>> showDialog(String title, List<T> allItems, List<T> chosenItems, StringConverter<T> stringConverter) {

		Alert alert = new Alert(AlertType.NONE, null, ButtonType.APPLY, ButtonType.CANCEL);
		alert.setTitle(title);
		AssignListUI<T> uic = new AssignListUI<>(stringConverter);
		uic.setAvaibleItems(allItems);
		uic.setAssignedItems(chosenItems);
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
