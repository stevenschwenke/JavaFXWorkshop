package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics.E_9_Snapshot;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Demonstrates the snapshot function that allows to create an image from every node.
 */
public class E_9_Start extends Application {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TableView<E_9_UserFX> tableView1;
    @FXML
    private TableColumn<E_9_UserFX, String> nameCol1;
    @FXML
    private TableColumn<E_9_UserFX, String> userIdCol1;
    @FXML
    private TableColumn<E_9_UserFX, String> actionsCol1;

    ObservableList<E_9_UserFX> observableList1;

    @FXML
    private TableView<E_9_UserFX> tableView2;
    @FXML
    private TableColumn<E_9_UserFX, String> nameCol2;
    @FXML
    private TableColumn<E_9_UserFX, String> userIdCol2;
    @FXML
    private TableColumn<E_9_UserFX, String> actionsCol2;

    private ObservableList<E_9_UserFX> observableList2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/E_9_Snapshot.fxml"));

        Scene scene = new Scene(root, 600, 600);

        stage.setTitle("Table to table transition");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void initialize() {

        nameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        userIdCol1.setCellValueFactory(new PropertyValueFactory<>("userId"));
        actionsCol1.setCellFactory(new E_9_ColumnFactory(this, "down"));


        nameCol1.prefWidthProperty().bind(tableView1.widthProperty().divide(3).subtract(2));
        userIdCol1.prefWidthProperty().bind(tableView1.widthProperty().divide(3).subtract(2));
        actionsCol1.prefWidthProperty().bind(tableView1.widthProperty().divide(3).subtract(2));

        nameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        userIdCol2.setCellValueFactory(new PropertyValueFactory<>("userId"));
        actionsCol2.setCellFactory(new E_9_ColumnFactory(this, "up"));

        nameCol2.prefWidthProperty().bind(tableView2.widthProperty().divide(3).subtract(2));
        userIdCol2.prefWidthProperty().bind(tableView2.widthProperty().divide(3).subtract(2));
        actionsCol2.prefWidthProperty().bind(tableView2.widthProperty().divide(3).subtract(2));

        observableList1 = FXCollections.observableArrayList(
                new E_9_UserFX("Max Meyer", "u01"),
                new E_9_UserFX("Gordon Freeman", "u02")
        );
        tableView1.setItems(observableList1);

        observableList2 = FXCollections.observableArrayList(
                new E_9_UserFX("Jack the Hack", "u03"),
                new E_9_UserFX("Mister Minister", "u04")
        );
        tableView2.setItems(observableList2);
    }


    void moveUp(final E_9_UserFX vo) {

        // Position and Snapshot of the row in table 2
        TableRow<E_9_UserFX> rowTable2 = findRow(tableView2, vo);
        double yTable2 = rowTable2.localToScene(0, 0).getY();
        WritableImage snapshot = rowTable2.snapshot(new SnapshotParameters(), null);

        // remove entry from table 2
        observableList2.remove(vo);

        // add new object to table 1 (now this entry is in both tables!)
        final E_9_UserFX newUser = new E_9_UserFX(vo.getName(), vo.getUserId());
        observableList1.add(newUser);

        // select new entry in table 1 and scroll to it
        tableView1.getSelectionModel().select(newUser);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int index = observableList1.indexOf(newUser);
                tableView1.scrollTo(index);
            }
        });

        // Animation
        TableRow<E_9_UserFX> rowTable1 = findRow(tableView1, newUser);
        double yTable1 = rowTable1.localToScene(0, 0).getY();
        doTheFancyAnimation(yTable1, yTable2, snapshot);
    }

    void moveDown(final E_9_UserFX vo) {

        // Position and Snapshot of the row in table 1
        TableRow<E_9_UserFX> rowTable1 = findRow(tableView1, vo);
        double yTable1 = rowTable1.localToScene(0, 0).getY();
        WritableImage snapshot = rowTable1.snapshot(new SnapshotParameters(), null);

        // remove entry from table 1
        observableList1.remove(vo);

        // add new object to table 2 (now this entry is in both tables!)
        final E_9_UserFX newUser = new E_9_UserFX(vo.getName(), vo.getUserId());
        observableList2.add(newUser);

        // select new entry in table 2 and scroll to it
        tableView2.getSelectionModel().select(newUser);
        Platform.runLater(() -> {
            int index = observableList2.indexOf(newUser);
            tableView2.scrollTo(index);
        });

        // Animation
        TableRow<E_9_UserFX> rowTable2 = findRow(tableView2, newUser);
        double yTable2 = rowTable2.localToScene(0, 0).getY();
        doTheFancyAnimation(yTable2, yTable1, snapshot);
    }

    /**
     * @param y1       Y-Coordinate to start from
     * @param y2       Y-Coordinate to go to
     * @param snapshot that gets translated
     */
    private void doTheFancyAnimation(final double y1, final double y2, final WritableImage snapshot) {

        Platform.runLater(() -> {

            final FlowPane overlayPane = new FlowPane();
            overlayPane.setStyle("-fx-border-width: 5px; -fx-border-color: lightblue");
            overlayPane.getChildren().add(new ImageView(snapshot));

            rootAnchorPane.getChildren().add(overlayPane);

            Timeline timeline = new Timeline();

            KeyValue keyValueOpacity1 = new KeyValue(overlayPane.opacityProperty(), 0);
            KeyValue keyValueY1 = new KeyValue(overlayPane.translateYProperty(), y2);
            KeyFrame kf1 = new KeyFrame(Duration.millis(0), keyValueOpacity1, keyValueY1);

            KeyValue keyValueOpacity2 = new KeyValue(overlayPane.opacityProperty(), 1);
            KeyValue keyValueY2 = new KeyValue(overlayPane.translateYProperty(), y1);
            KeyFrame kf2 = new KeyFrame(Duration.millis(500), keyValueOpacity2, keyValueY2);

            timeline.getKeyFrames().addAll(kf1, kf2);
            timeline.play();
            timeline.onFinishedProperty().set(arg0 -> {
                rootAnchorPane.getChildren().remove(overlayPane);
                tableView1.requestFocus();
            });
        });
    }

    private <ROW> TableRow<ROW> findRow(TableView<ROW> table, final ROW vo) {
        ObservableList<ROW> items = table.getItems();
        for (Node n : table.lookupAll("TableRow")) {
            if (n instanceof TableRow) {

                @SuppressWarnings("unchecked")
                TableRow<ROW> row = (TableRow<ROW>) n;

                if (items.size() > row.getIndex() && items.get(row.getIndex()).equals(vo)) {
                    return row;
                }
            }
        }
        return null;
    }
}
