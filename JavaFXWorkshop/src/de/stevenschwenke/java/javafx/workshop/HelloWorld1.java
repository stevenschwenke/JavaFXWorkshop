package de.stevenschwenke.java.javafx.workshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloWorld1 extends Application {

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
		Scene scene = new Scene(root, 600, 400);

		stage.setTitle("Hello World 1");
		stage.setScene(scene);
		stage.show();
		
	}

}
