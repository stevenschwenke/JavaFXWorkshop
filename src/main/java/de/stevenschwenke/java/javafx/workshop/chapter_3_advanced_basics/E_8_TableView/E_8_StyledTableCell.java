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
public class E_8_StyledTableCell extends TableCell<E_8_RowValue, Integer> {

    @Override
    public void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        /**
         * In JavaFX, only visible cells are part of the Scene Graph. Once a cell is scrolled out
         * of the visible area, it gets recycled and used for new cells, for example the ones that
         * gets visible while scrolling. Because of this recycling, the new cells can hold
         * properties of the recycled cells.
         *
         * Best practice: At the beginning of updateItem, you should reset the used values to avoid
         * strange behavior that's caused by recycling of cells. In this example, we set some color,
         * so we should reset it at the beginning of updateItem().
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
