package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import static javafx.beans.binding.Bindings.when;

/**
 * These changes have been introduced with JDK 8u40.
 */
public class E_15_New_Features_In_Java_8 extends Application {

    /*
        Naming:
        Java 7 -> "JavaFX 2.x"
        Java 8 -> "JavaFX 8"
     */

    // new properties and methods annotated with
    // @since JavaFX 8.0

    /**
     * New component: DatePicker (finally!) that uses new Date and Time API.
     * Also: TransformationList -> FilteredList + OrderedList
     */
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setSpacing(15);

        // DatePicker

        DatePicker datePicker = new DatePicker();
        datePicker.setShowWeekNumbers(true);
        setFormattingToGermanDateFormat(datePicker);
        setSomeForeignChronologies(datePicker);

        Button btn = new Button();
        btn.setText("getValue()");
        // Note that output in the console is default formatting, not formatting of the DatePicker!
        btn.setOnAction(event -> System.out.println(datePicker.getValue()));

        HBox datePickerContainer = new HBox();
        datePickerContainer.setSpacing(10);
        datePickerContainer.getChildren().add(datePicker);
        datePickerContainer.getChildren().add(btn);

        root.getChildren().add(new VBox(createTextFlowLabel("DatePicker"), datePickerContainer));

        // treetableview
        final TreeItem<String> childNode1 = new TreeItem<>("Child Node 1");
        final TreeItem<String> childNode2 = new TreeItem<>("Child Node 2");
        final TreeItem<String> childNode3 = new TreeItem<>("Child Node 3");
        final TreeItem<String> rootTreeItem = new TreeItem<>("Root node");
        rootTreeItem.setExpanded(true);
        rootTreeItem.getChildren().setAll(childNode1, childNode2, childNode3);
        TreeTableColumn<String, String> column = new TreeTableColumn<>("Column");
        column.setPrefWidth(150);
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> p) ->
                                       new ReadOnlyStringWrapper(p.getValue().getValue()));

        final TreeTableView<String> treeTableView = new TreeTableView<>(rootTreeItem);
        treeTableView.getColumns().add(column);
        treeTableView.setPrefWidth(152);
        treeTableView.setMinHeight(130);
        treeTableView.setPrefHeight(130);
        treeTableView.setShowRoot(true);
        root.getChildren().add(new VBox(createTextFlowLabel("TreeTableView"), treeTableView));

        // ListFiltering

        // New TransformationList is a wrapper around a normal list and has two implementations: FilteredList and
        // SortedList. The following is an example for the FilteredList, SortedList is similar.

        ObservableList<String>
            list =
            FXCollections.observableArrayList("one", "two", "three", "four");
        FilteredList<String> filteredList = new FilteredList<>(list);
        ListView<String> listView = new ListView<>(filteredList);
        TextField textField = new TextField();
        textField.textProperty().addListener(
            (e) -> filteredList.setPredicate((v) -> (v.contains(textField.getText()))));
        VBox
            listFilteringContainer =
            new VBox(createTextFlowLabel("ListFiltering"), textField, listView);

        root.getChildren().add(listFilteringContainer);

        // Task: updateValue

        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                long startTime = System.currentTimeMillis();
                while (true) {
                    updateValue(System.currentTimeMillis() - startTime + "");
                    Thread.sleep(1);
                }
            }
        };
        Button button = new Button();
        button.setOnAction((e) -> {
            if (task.isRunning()) {
                task.cancel();
            } else {
                // The executor will execute the task only when it didn't run yet. Additional calls will be ignored.
                Executors.newSingleThreadExecutor().execute(task);
            }
        });
        button.textProperty().bind(
            when(task.valueProperty().isNotNull()).then(task.valueProperty()).otherwise("Start"));
        VBox threadContainer = new VBox(createTextFlowLabel("Task.updateValue()"), button);
        root.getChildren().add(threadContainer);

        // new class: Scheduled Service

        ScheduledService<Void> service = new ScheduledService<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        System.out.println("Do something");
                        return null;
                    }
                };
            }
        };
        service.setRestartOnFailure(true);
        service.setPeriod(Duration.seconds(5));
        service.setMaximumFailureCount(10);
        service.setMaximumCumulativePeriod(Duration.minutes(2));
        Button startScheduledService = new Button("Start scheduled service");
        startScheduledService.setOnAction(eventHandler -> service.start());
        root.getChildren()
            .add(new VBox(createTextFlowLabel("ScheduledService"), startScheduledService));

        // New CSS theme Modena is new default theme!
        Button toggleThemes = new Button();
        SimpleBooleanProperty modena = new SimpleBooleanProperty(true);
        toggleThemes.textProperty()
            .bind(Bindings.when(modena).then("Switch to Caspian").otherwise("Switch to Modena"));
        toggleThemes.setOnAction(eventHandler -> {
            setUserAgentStylesheet(modena.get() ? STYLESHEET_CASPIAN : STYLESHEET_MODENA);
            modena.set(getUserAgentStylesheet().equals(STYLESHEET_MODENA));
        });
        // (If getUserAgendStylesheet() would be a property, the above code would be way smaller.)
        root.getChildren().add(new VBox(createTextFlowLabel("toggle themes"), toggleThemes));

        // Print support
        Button print = new Button("Print");
        print.setOnAction(eventHandler -> {
            Printer printer = Printer.getDefaultPrinter();
            PageLayout
                pageLayout =
                printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
                                         Printer.MarginType.DEFAULT);
            double scaleX = pageLayout.getPrintableWidth() / root.getBoundsInParent().getWidth();
            double scaleY = pageLayout.getPrintableHeight() / root.getBoundsInParent().getHeight();
            Scale printScale = new Scale(scaleX, scaleY);
            root.getTransforms().add(printScale);

            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null) {
                boolean success = job.printPage(root);
                if (success) {
                    job.endJob();
                }
            }

            // Don't forget to remove printer scaling here because otherwise your app looks bad!
            root.getTransforms().remove(printScale);
        });
        root.getChildren().add(new VBox(createTextFlowLabel("Print support"), print));

        // Dialogs
        Button dialogs = new Button("Dialogs");
        dialogs.setOnAction(eventHandler -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("There are dialogs!");
            alert.setHeaderText(null);
            alert.setContentText(
                "Dialogs have been imported from ControlsFX and can now be used with default Java.");

            alert.showAndWait();

            // More examples here: http://code.makery.ch/blog/javafx-dialogs-official/
        });
        root.getChildren().add(new VBox(createTextFlowLabel("Dialogs"), dialogs));

        // JAVAFX 3D
        // See https://www.youtube.com/watch?v=TS5RvqDsEoU (JavaFX 3D and Leap Motion: a short space adventure )
        // See https://www.youtube.com/watch?v=8_xiv1pV3tI (Rigged Hand Animation with JavaFX and Leap Motion )

        // Setup GUI

        Scene scene = new Scene(root, 300, 680);
        primaryStage.setTitle("JavaFX in Java 8");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a nice-looking label with the new {@link TextFlow} component. It can also display
     * images!
     *
     * @param text for the label
     * @return {@link TextFlow} component that can be added as a child
     */
    private TextFlow createTextFlowLabel(String text) {
        Text datePickerLabelText = new Text(text);
        datePickerLabelText.setFill(Color.BLUE);
        datePickerLabelText.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        return new TextFlow(datePickerLabelText);
    }

    /**
     * Sets the formatting of a {@link javafx.scene.control.DatePicker} to the german date format
     * "dd.MM.yyyy".
     *
     * @param datePicker to set format for
     */
    private void setFormattingToGermanDateFormat(DatePicker datePicker) {
        // Convert date in text field manually:
        String pattern = "dd.MM.yyyy";
        datePicker.setPromptText(pattern.toLowerCase());

        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    /**
     * Shows the support of {@link javafx.scene.control.DatePicker} for different cultural time
     * systems.
     *
     * @param datePicker to set chronology for
     */
    private void setSomeForeignChronologies(DatePicker datePicker) {
        // Japanese calendar.
//        datePicker.setChronology(JapaneseChronology.INSTANCE);

        // Hijrah calendar.
//        datePicker.setChronology(HijrahChronology.INSTANCE);

        // Minguo calendar.
//        datePicker.setChronology(MinguoChronology.INSTANCE);

        // Buddhist calendar.
//        datePicker.setChronology(ThaiBuddhistChronology.INSTANCE);
    }
}
