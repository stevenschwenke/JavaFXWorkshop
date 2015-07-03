package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;

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
 * @author andre
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
		KeyMoveSelection<String> keyHelper = new KeyMoveSelection<>(toEnhance.getSelectionModel(), toEnhance.getItems(), null);

		// this or the other
		toEnhance.setOnKeyReleased(keyHelper);
		// toEnhance.addEventHandler(KeyEvent.KEY_RELEASED, keyHelper);

		primaryStage.setScene(new Scene(toEnhance));

		primaryStage.show();
	}

	private class KeyMoveSelection<T> implements EventHandler<KeyEvent> {
		private SelectionModel<T> selectionModel;
		private List<T> backingList;
		private StringConverter<T> converter;

		public KeyMoveSelection(SelectionModel<T> selectionModel, List<T> backingList, StringConverter<T> converter) {
			super();
			this.selectionModel = selectionModel;
			this.backingList = backingList;
			this.converter = converter;
			if (this.converter == null) {
				this.converter = new StringConverter<T>() {

					@Override
					public String toString(T object) {

						return object.toString();
					}

					@Override
					public T fromString(String string) {
						return null;
					}
				};
			}
		}

		@Override
		public void handle(KeyEvent event) {
			// Text from the keyevent
			String keyText = event.getText();
			if (keyText != null && !"".equals(keyText)) {
				keyText = keyText.toLowerCase();

				int firstPosition = findNext(keyText, 0);
				int positionAfterCurrentSelection = findNext(keyText, selectionModel.getSelectedIndex() != -1 ? selectionModel.getSelectedIndex() + 1 : 0);
				if (positionAfterCurrentSelection == -1) {
					if (firstPosition != -1) {
						selectionModel.clearAndSelect(firstPosition);
						scrollToIndex((Control) event.getSource(), firstPosition);
					}
				} else {
					selectionModel.clearAndSelect(positionAfterCurrentSelection);
					scrollToIndex((Control) event.getSource(), positionAfterCurrentSelection);
				}
			}
		}

		/**
		 * Finding the next element starting with "txt"
		 * 
		 * @param txt
		 * @param startIndex
		 * @return Index from the next element
		 */
		private int findNext(String txt, int startIndex) {
			for (int i = startIndex; i < backingList.size(); i++) {
				String rendered = converter.toString(backingList.get(i));
				if (rendered != null && rendered.toLowerCase().startsWith(txt)) {
					return i;
				}
			}
			return -1;
		}

		/**
		 * Copy from Code in FX
		 * 
		 * @param control
		 * @param index
		 */
		private void scrollToIndex(final Control control, int index) {
			Event.fireEvent(control, new ScrollToEvent<>(control, control, ScrollToEvent.scrollToTopIndex(), index != -1 ? index : 0));
		}
	}
}
