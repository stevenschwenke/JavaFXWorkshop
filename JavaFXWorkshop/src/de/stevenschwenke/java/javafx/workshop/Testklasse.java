package de.stevenschwenke.java.javafx.workshop;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import org.junit.Test;

public class Testklasse {

	@Test
	public void bla() {
		IntegerProperty p = new SimpleIntegerProperty(0);
		System.out.println(p.get());
		IntegerBinding add = p.add(5);
		System.out.println(p.get());

	}

}
