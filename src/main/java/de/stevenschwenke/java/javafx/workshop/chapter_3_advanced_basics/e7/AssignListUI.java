package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.e7;

import de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_6_ListView_searchable_by_keystroke.KeyMoveSelectionHandler;
import javafx.beans.binding.ListExpression;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

/**
 * Pane for 2 lists you can assign items to.
 * 
 * @author scavenger156
 *
 * @param <T>
 */
public class AssignListUI<T> extends BorderPane implements Initializable {
	private StringConverter<T> converter;
	@FXML
	private ListView<T> left;
	@FXML
	private ListView<T> right;
	@FXML
	private Button btnLeft;
	@FXML
	private Button btnLeftAll;
	@FXML
	private Button btnRight;
	@FXML
	private Button btnRightAll;
	@FXML
	private TextField txtText;

	private ObservableList<T> allItems = FXCollections.observableArrayList();

	/**
	 * 
	 * @param converter
	 *            Converter for object
	 */
	public AssignListUI(StringConverter<T> converter) {
		super();
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
		// Loading
		FXMLLoader fxmlLoader = new FXMLLoader(AssignListUI.class.getResource("/E_7_AssignList.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Renderer
		left.setCellFactory(BaseListCell.createCallBack(converter));
		right.setCellFactory(BaseListCell.createCallBack(converter));
	
		left.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		right.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// Navigate by keys
		left.setOnKeyReleased(new KeyMoveSelectionHandler<T>(left.getSelectionModel(), left.getItems(), converter));
		right.setOnKeyReleased(new KeyMoveSelectionHandler<T>(right.getSelectionModel(), right.getItems(), converter));
	
		// Filtering by text
		FilteredList<T> filteredItems = new FilteredList<T>(allItems, new PredicateFilterListByTxt(txtText.getText()));
		// Textchange detected using a new predicate
		txtText.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredItems.setPredicate(new PredicateFilterListByTxt(txtText.getText()));
		});
		left.setItems(filteredItems);
	
		// Buttons enabled an disabled by binding
		ListExpression<T> leftSelection = new ReadOnlyListWrapper<>(left.getSelectionModel().getSelectedItems());
		ListExpression<T> leftItemsExpression = new ReadOnlyListWrapper<>(left.getItems());
		btnRight.disableProperty().bind(leftSelection.emptyProperty());
		btnRightAll.disableProperty().bind(leftItemsExpression.emptyProperty());
	
		ListExpression<T> rightSelection = new ReadOnlyListWrapper<>(right.getSelectionModel().getSelectedItems());
		ListExpression<T> rightItemsExpression = new ReadOnlyListWrapper<>(right.getItems());
		btnLeft.disableProperty().bind(rightSelection.emptyProperty());
		btnLeftAll.disableProperty().bind(rightItemsExpression.emptyProperty());
	}

	/**
	 * Filtering the items by text see FilteredList
	 * @author scavenger156
	 *
	 */
	private class PredicateFilterListByTxt implements Predicate<T> {
		private String text;

		public PredicateFilterListByTxt(String text) {
			super();
			this.text = text;
			if (this.text != null) {
				this.text = this.text.toLowerCase();
			}
		}

		@Override
		public boolean test(T t) {

			if (text == null || "".equals(text.trim())) {
				return true;
			}
			String txt = converter.toString(t);
			if (txt.toLowerCase().startsWith(text)) {
				return true;
			}
			return false;
		}
	}

	/**
	 * 
	 * @param items
	 *            Items that are avaible
	 */
	public void setAvaibleItems(List<T> items) {
		sort(items);
		this.allItems.setAll(items);

		List<T> itemsAssigned = new ArrayList<>(right.getItems());
		itemsAssigned.retainAll(allItems);
		sort(itemsAssigned);
		right.getItems().setAll(itemsAssigned);
		allItems.removeAll(right.getItems());
	}

	/**
	 * 
	 * @param itemsAssigned
	 *            List from Items that are allready assiged
	 */
	public void setAssignedItems(List<T> itemsAssigned) {
		itemsAssigned.retainAll(allItems);
		sort(itemsAssigned);
		right.getItems().setAll(itemsAssigned);
		allItems.removeAll(itemsAssigned);
	}

	/**
	 * 
	 * @return The current assigned items
	 */
	public List<T> getAssignedItems() {
		return new ArrayList<T>(right.getItems());
	}

	private void sort(List<T> items) {
		Collections.sort(items, new Comparator<T>() {

			@Override
			public int compare(T o1, T o2) {
				String s1 = converter.toString(o1);
				String s2 = converter.toString(o2);
				if (s1 == null && s2 == null) {
					return 0;
				}
				if (s1 == null && s2 != null) {
					return -1;
				}
				if (s1 != null && s2 == null) {
					return 1;
				}
				// a negative integer, zero, or a positive integer as the
				// first argument is less than, equal to, or greater than the
				// second.
				return s1.compareToIgnoreCase(s2);
			}
		});
	}

	@FXML
	private void actionLeft() {
		List<T> selection = new ArrayList<>(right.getSelectionModel().getSelectedItems());
		allItems.addAll(selection);
		right.getItems().removeAll(selection);

		scrollToIndex(right, right.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void actionLeftAll() {
		List<T> selection = new ArrayList<>(right.getItems());
		allItems.addAll(selection);
		right.getItems().clear();
	}

	@FXML
	private void actionRight() {
		List<T> selection = new ArrayList<>(left.getSelectionModel().getSelectedItems());
		allItems.removeAll(selection);
		right.getItems().addAll(selection);
		allItems.removeAll(selection);

		scrollToIndex(left, left.getSelectionModel().getSelectedIndex());
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

	@FXML
	private void actionRightAll() {
		List<T> selection = new ArrayList<>(left.getItems());
		allItems.removeAll(selection);
		right.getItems().addAll(selection);
		allItems.clear();
	}
}