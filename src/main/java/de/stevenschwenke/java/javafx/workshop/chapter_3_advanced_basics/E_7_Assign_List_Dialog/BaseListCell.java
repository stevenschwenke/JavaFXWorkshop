package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_7_Assign_List_Dialog;

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

	public static <T> Callback<ListView<T>, ListCell<T>> createCallBack(
			final StringConverter<T> converter) {
		return param -> new BaseListCell<>(converter);
	}
}
