package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_8_TableView;

import javafx.scene.control.TableCell;

/**
 * A TableCell with validation. The Cell is reused due to the TableViews VirtualFlow mechanism.</br>
 * Values < 6 -> red.</br>
 * Values > 14 -> green.</br>
 *
 * <p>
 * Created by bezze on 04.09.15.
 */
public class StyledTableCell extends TableCell<RowValue, Integer> {

    @Override
    public void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        /**
         * Best practice: At the beginning of updateItem, you should reset the used values to avoid
         * strange behavior that's caused by recycling of cells.
         *
         * Comment this line and see what's happening.
         */
        setStyle("-fx-text-fill: black; -fx-font-weight: normal");

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
