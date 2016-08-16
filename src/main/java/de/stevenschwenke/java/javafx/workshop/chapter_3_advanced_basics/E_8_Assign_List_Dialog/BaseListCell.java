package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_8_Assign_List_Dialog;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;
/**
 * Simple ListCell with a StringConverter.
 *
 * @author scavenger156 (https://github.com/Scavenger156)
 *
 * @param <T> type
 */
public class BaseListCell<T> extends ListCell<T> {
	private StringConverter<T> converter;

	public BaseListCell(StringConverter<T> converter) {
		super();
		this.converter = converter;
	}

	@Override
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
		if (empty || item == null) {
			setText(null);
		} else {
			setText(converter.toString(item));
		}
	}

	/**
	 * This method could also be located in a factory class because it creates instances of cell factories
	 * {@link BaseListCell}. However, this class is so small that it can be located here.
	 *
	 * @param stringConverter to convert from and to strings
	 * @param <T> type parameter of the manufactured cell
	 * @return instance of a cell
	 */
	public static <T> Callback<ListView<T>, ListCell<T>> createCellFactory(final StringConverter<T> stringConverter) {
		return (ListView<T> listView) -> new BaseListCell<>(stringConverter);
	}
}
