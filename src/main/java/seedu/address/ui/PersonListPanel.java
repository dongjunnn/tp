package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 * When a person is selected, triggers a callback to display their projects.
 */
public class PersonListPanel extends UiPart<Region> {

    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList} of persons.
     * Original constructor for backward compatibility.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        this(personList, null);
    }

    /**
     * Creates a {@code PersonListPanel} with the given list and selection callback.
     *
     * @param personList The list of persons to display. Must not be null.
     * @param onPersonSelected Callback when a person is selected. Can be null.
     */
    public PersonListPanel(ObservableList<Person> personList, Consumer<Person> onPersonSelected) {
        super(FXML);
        requireNonNull(personList, "Person list cannot be null");

        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        // Listen to person selection
        if (onPersonSelected != null) {
            personListView.getSelectionModel().selectedItemProperty().addListener((
                    observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            logger.info("Person selected: " + newValue.getName().fullName);
                            onPersonSelected.accept(newValue);
                        }
                    });
        }
    }

    /**
     * Programmatically selects the person at the specified index in the list.
     *
     * @param index The index of the person to select (zero-based)
     */
    public void selectPerson(Index index) {
        requireNonNull(index);
        int zeroBasedIndex = index.getZeroBased();

        if (zeroBasedIndex >= 0 && zeroBasedIndex < personListView.getItems().size()) {
            personListView.getSelectionModel().select(zeroBasedIndex);
            personListView.scrollTo(zeroBasedIndex);
            logger.info("Programmatically selected person at index: " + (zeroBasedIndex + 1));
        } else {
            logger.warning("Attempted to select invalid index: " + (zeroBasedIndex + 1));
        }
    }

    /**
     * Custom {@code ListCell} that displays a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }
}
