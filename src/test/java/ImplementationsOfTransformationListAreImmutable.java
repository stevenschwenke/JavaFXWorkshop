
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by schwenks on 08.08.2016.
 */
public class ImplementationsOfTransformationListAreImmutable {

    @Test void implementationsOfTransformationListAreImmutable() {
        ObservableList<String>
            list = FXCollections.observableArrayList("one", "two", "three", "four");
        FilteredList<String> filteredList = new FilteredList<>(list);

        // Backing list can be mutated:
        list.remove(0, 1);
        assertEquals(3, list.size());

        // All implementations of TransformationList are immutable, hence changing the list will throw an exception:
        assertThrows(Exception.class, () -> filteredList.add("EXCEPTION!"));
    }

}
