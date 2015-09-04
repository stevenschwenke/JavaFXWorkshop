package de.stevenschwenke.java.javafx.workshop.chapter_7_TableView;

import javafx.scene.control.TableCell;

/**
 * A TableCell with validation.
 * <p>
 * Created by bezze on 04.09.15.
 */
public class StyledTableCell extends TableCell<RowValue, Integer> {

    @Override
    public void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        /**
         * Reset the style!
         * Try uncommenting and see what is happening!
         */
        //setStyle("-fx-text-fill: black; -fx-font-weight: normal");

        if (!empty) {
            setText(item.toString());
            if (item < 6) {
                setStyle("-fx-text-fill: red; -fx-font-weight: bold");
            } else if (item > 14) {
                setStyle("-fx-text-fill: green; -fx-font-weight: bold");
            }
        } else {
            setText(null);
        }
    }
}
