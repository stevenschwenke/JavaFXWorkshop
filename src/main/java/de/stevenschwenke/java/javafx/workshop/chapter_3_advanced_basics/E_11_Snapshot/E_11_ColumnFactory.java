package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_11_Snapshot;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;

public class E_11_ColumnFactory implements Callback<TableColumn<E_11_UserFX, String>, TableCell<E_11_UserFX, String>> {

    private E_11_Start controller;

    private String upOrDown;

    public E_11_ColumnFactory(E_11_Start controller, String upOrDown) {
        super();
        this.controller = controller;
        this.upOrDown = upOrDown;
    }

    @Override
    public TableCell<E_11_UserFX, String> call(TableColumn<E_11_UserFX, String> arg0) {
        TableCell<E_11_UserFX, String> myTableCell = new TableCell<E_11_UserFX, String>(
        ) {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (!isNoDataTableRow()) {
                    FlowPane pane = new FlowPane();
                    pane.setHgap(10);
                    Button actionButton = null;

                    // Icon
                    if(upOrDown.equals("up")) {
                        actionButton= new Button("Add!");
                        Image imageOk = new Image(getClass().getResourceAsStream("/32px-Gnome-go-up.svg.png"));
                        actionButton.setGraphic(new ImageView(imageOk));
                    } else {
                        actionButton= new Button("Remove!");
                        Image imageOk = new Image(getClass().getResourceAsStream("/32px-Gnome-go-down.svg.png"));
                        actionButton.setGraphic(new ImageView(imageOk));
                    }

                    // Action
                    actionButton.setOnAction(e -> {
                        E_11_UserFX vo = getRowItem();
                        if (upOrDown.equals("up"))
                            controller.moveUp(vo);
                        else
                            controller.moveDown(vo);
                    });
                    pane.getChildren().add(actionButton);

                    setGraphic(pane);
                } else {
                    setGraphic(null);
                }
            }

            private boolean isNoDataTableRow() {
                return getIndex() == -1 || getIndex() >= getTableView().getItems().size();
            }

            protected E_11_UserFX getRowItem() {
                E_11_UserFX item = (E_11_UserFX) getTableRow().getItem();

                if(isNoDataTableRow())
                    return null;

                if (item == null) {
                    item = getTableView().getItems().get(getIndex());
                }
                return item;
            }
        };

        return myTableCell;
    }
}
