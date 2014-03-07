package de.stevenschwenke.java.javafx.workshop;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.stage.Stage;

public class HelloWorld1 extends Application implements Initializable {

	@FXML
	private PieChart pieChart;

	private ObservableList<PieChart.Data> pieChartData;

	private Data junk;

	private Data fruits;

	private Data vegetables;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass()
				.getResource("helloWorld1.fxml"));
		// TODO Was ist eine Stage, was eine Scene? - Abbildungen!
		// TODO Sources einbinden!
		// TODO Workspace als zip auf 2 USB-Sticks rumgehen lassen - gleich mit
		// fxml startet Scene Builder und allen Beispielen. Sollte aber nicht
		// dieser Workspace sein, da hier Zugangsdaten für Github stehen!
		Scene scene = new Scene(root, 800, 600);

		stage.setTitle("Hello World 1");
		stage.setScene(scene);
		stage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fruits = new PieChart.Data("Fruits", 0);
		vegetables = new PieChart.Data("Vegetables", 0);
		junk = new PieChart.Data("Junkfood", 0);

		pieChartData = FXCollections.observableArrayList(fruits, vegetables,
				junk);
		pieChart.setData(pieChartData);
	}

	@FXML
	private void addOneToFruits() {
		// Update not possible on this observable list - only with implementing
		// a callback while instantiating the list!
		// fruits.pieValueProperty().add(1L);

		fruits.setPieValue(fruits.getPieValue() + 1);
	}

	@FXML
	private void subtractOneFromFruits() {
		if (fruits.getPieValue() > 0)
			fruits.setPieValue(fruits.getPieValue() - 1);
	}

	@FXML
	private void addOneToVegetables() {
		vegetables.setPieValue(vegetables.getPieValue() + 1);
	}

	@FXML
	private void subtractOneFromVegetables() {
		if (vegetables.getPieValue() > 0)
			vegetables.setPieValue(vegetables.getPieValue() - 1);
	}

	@FXML
	private void addOneToJunk() {
		junk.setPieValue(junk.getPieValue() + 1);
	}

	@FXML
	private void subtractOneFromJunk() {
		if (junk.getPieValue() > 0)
			junk.setPieValue(junk.getPieValue() - 1);
	}
}
