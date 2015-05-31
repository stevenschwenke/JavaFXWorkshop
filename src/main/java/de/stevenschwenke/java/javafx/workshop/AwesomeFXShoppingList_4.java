package de.stevenschwenke.java.javafx.workshop;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/*
 * Explain this part of the workshop in the following order: 
 * 1. code stays the same!
 * 2. write CSS file / copy it from github
 * 3. reference it in SceneBuilder - buttons look nice
 * 4. set custom class to header and sum-label
 */
public class AwesomeFXShoppingList_4 extends Application implements Initializable {

	/** UI components with FX-mapping to the .fxml - file */
	@FXML
	private PieChart pieChart;
	@FXML
	private Label potatoesLabel;
	@FXML
	private Label applesLabel;
	@FXML
	private Label chipsLabel;
	@FXML
	private Label sumLabel;

	/**
	 * Properties to hold the amount of stuff we are going to buy. Similar to
	 * old value classes / literals but with ninja power.
	 */
	private IntegerProperty amountOfPotatoes = new SimpleIntegerProperty();
	private IntegerProperty amountOfApples = new SimpleIntegerProperty();
	private IntegerProperty amountOfChips = new SimpleIntegerProperty();
	private IntegerProperty sum = new SimpleIntegerProperty();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Loading out GUI from the fxml file. Binding to fields above happens
		// here.
		Parent root = FXMLLoader.load(getClass().getResource("/awesomeFXShoppingList_4.fxml"));
		Scene scene = new Scene(root, 418, 550);

		stage.setTitle("Awesome FX Shopping List");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// building pie chart
		final Data fruits = new PieChart.Data("Fruits", 0);
		final Data vegetables = new PieChart.Data("Vegetables", 0);
		final Data junk = new PieChart.Data("Junkfood", 0);

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(fruits, vegetables, junk);
		pieChart.setData(pieChartData);

		// set Labels when properties change
		potatoesLabel.textProperty().bind(amountOfPotatoes.asString());
		applesLabel.textProperty().bind(amountOfApples.asString());
		chipsLabel.textProperty().bind(amountOfChips.asString());

		// You can do math with properties!
		sum.bind(amountOfApples.add(amountOfChips).add(amountOfPotatoes));

		sumLabel.textProperty().bind(sum.asString());

		// set chart when properties change
		vegetables.pieValueProperty().bind(amountOfPotatoes);
		fruits.pieValueProperty().bind(amountOfApples);
		junk.pieValueProperty().bind(amountOfChips);
	}

	@FXML
	private void addOneToApples() {
		amountOfApples.set(amountOfApples.get() + 1);
	}

	@FXML
	private void subtractOneFromApples() {
		if (amountOfApples.greaterThan(0).get())
			amountOfApples.set(amountOfApples.get() - 1);
	}

	@FXML
	private void addOneToPotatoes() {
		amountOfPotatoes.set(amountOfPotatoes.get() + 1);
	}

	@FXML
	private void subtractOneFromPotatoes() {
		if (amountOfPotatoes.greaterThan(0).get()) {
			amountOfPotatoes.set(amountOfPotatoes.get() - 1);
		}
	}

	@FXML
	private void addOneToChips() {
		amountOfChips.set(amountOfChips.get() + 1);
	}

	@FXML
	private void subtractOneFromChips() {
		if (amountOfChips.greaterThan(0).get()) {
			amountOfChips.set(amountOfChips.get() - 1);
		}
	}
}
