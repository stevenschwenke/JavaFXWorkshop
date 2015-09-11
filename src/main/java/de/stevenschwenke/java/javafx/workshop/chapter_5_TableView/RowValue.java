package de.stevenschwenke.java.javafx.workshop.chapter_5_TableView;

import java.util.ArrayList;
import java.util.List;

/**
 * RowValue for the demo.
 * <p>
 * Created by bezze on 04.09.15.
 */
public class RowValue {
    private String name;
    private List<Integer> values = new ArrayList<>();

    public RowValue(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getValues() {
        return values;
    }
}
