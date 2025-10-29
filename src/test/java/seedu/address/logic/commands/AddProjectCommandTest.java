package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.priority.Priority;
import seedu.address.model.project.Project;

/**
 * Tests for {@link AddProjectCommand}.
 */
public class AddProjectCommandTest {

    // -------------------- execute() --------------------

    @Test
    void execute_noMembers_throwsCommandException() throws Exception {
        String name = "IndiDex v1.3";
        LocalDate deadline = LocalDate.of(2025, 12, 31);
        Priority priority = Priority.HIGH;
        List<Index> members = List.of(); // no members

        AddProjectCommand cmd = new AddProjectCommand(name, deadline, priority, members);
        ModelStubAcceptingProjectAdded model = new ModelStubAcceptingProjectAdded();

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("Project must have at least one member.", ex.getMessage());
    }

    @Test
    void execute_duplicateProject_throwsCommandException() {
        Project existing = new Project(
                "SameName",
                Priority.LOW,
                LocalDate.of(2026, 1, 1),
                java.util.Set.of(ALICE)
        );
        Model model = new ModelStubWithProject(existing);

        AddProjectCommand cmd = new AddProjectCommand(
                "SameName", // same identity -> duplicate
                LocalDate.of(2027, 2, 2),
                Priority.HIGH,
                List.of(INDEX_SECOND_PERSON)
        );

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("This project already exists in the address book", ex.getMessage());
    }

    @Test
    void execute_invalidMemberIndex_throwsCommandException() {
        ModelStubWithPersons model = new ModelStubWithPersons(FXCollections.observableArrayList());
        AddProjectCommand cmd = new AddProjectCommand(
                "Proj",
                LocalDate.now(),
                Priority.MEDIUM,
                List.of(INDEX_FIRST_PERSON)
        );

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ex.getMessage());
    }

    // -------------------- equals() --------------------

    @Test
    void equals_contract() {
        AddProjectCommand a = new AddProjectCommand(
                "Alpha",
                LocalDate.of(2025, 1, 1),
                Priority.LOW,
                List.of()
        );
        AddProjectCommand aCopy = new AddProjectCommand(
                "Alpha",
                LocalDate.of(2025, 1, 1),
                Priority.LOW,
                List.of()
        );
        AddProjectCommand bDifferentName = new AddProjectCommand(
                "Beta",
                LocalDate.of(2025, 1, 1),
                Priority.LOW,
                List.of()
        );
        AddProjectCommand cDifferentDeadline = new AddProjectCommand(
                "Alpha",
                LocalDate.of(2026, 1, 1),
                Priority.LOW,
                List.of()
        );
        AddProjectCommand dDifferentPriority = new AddProjectCommand(
                "Alpha",
                LocalDate.of(2025, 1, 1),
                Priority.HIGH,
                List.of()
        );
        AddProjectCommand eDifferentMembers = new AddProjectCommand(
                "Alpha",
                LocalDate.of(2025, 1, 1),
                Priority.LOW,
                List.of(Index.fromOneBased(2))
        );

        // same values
        assertTrue(a.equals(aCopy));
        // same object
        assertTrue(a.equals(a));
        // null
        assertFalse(a.equals(null));
        // different type
        assertFalse(a.equals("not a command"));
        // diffs
        assertFalse(a.equals(bDifferentName));
        assertFalse(a.equals(cDifferentDeadline));
        assertFalse(a.equals(dDifferentPriority));
        assertFalse(a.equals(eDifferentMembers));
    }

    // ====================================================================
    //                            Model stubs
    // ====================================================================

    private static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError();
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            return new UserPrefs();
        }

        @Override
        public seedu.address.commons.core.GuiSettings getGuiSettings() {
            throw new AssertionError();
        }

        @Override
        public void setGuiSettings(seedu.address.commons.core.GuiSettings guiSettings) {
            throw new AssertionError();
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            throw new AssertionError();
        }

        @Override
        public void setAddressBookFilePath(java.nio.file.Path addressBookFilePath) {
            throw new AssertionError();
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError();
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError();
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError();
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError();
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError();
        }

        @Override
        public void updateFilteredPersonList(java.util.function.Predicate<Person> predicate) {
            throw new AssertionError();
        }

        @Override
        public boolean hasProject(Project project) {
            throw new AssertionError();
        }

        @Override
        public void deleteProject(Project project) {
            throw new AssertionError();
        }

        @Override
        public void addProject(Project project) {
            throw new AssertionError();
        }

        @Override
        public void setProject(Project target, Project editedProject) {
            throw new AssertionError();
        }

        @Override
        public ObservableList<Project> getFilteredProjectList() {
            throw new AssertionError();
        }

        @Override
        public void updateFilteredProjectList(java.util.function.Predicate<Project> predicate) {
            throw new AssertionError();
        }

        @Override
        public void sortPersons(java.util.Comparator<Person> comparator) {
            throw new AssertionError();
        }

    }

    /**
     * Model stub that records added projects and reports no duplicates.
     * Person list is empty by default (sufficient for tests with no members).
     */
    private static class ModelStubAcceptingProjectAdded extends ModelStub {
        final List<Project> added = new ArrayList<>();
        final ObservableList<Person> persons = FXCollections.observableArrayList();

        @Override
        public boolean hasProject(Project project) {
            // duplicate iff same identity (Project#isSameProject) i.e., same name
            return added.stream().anyMatch(p -> p.isSameProject(project));
        }

        @Override
        public void addProject(Project project) {
            added.add(project);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return persons;
        }
    }

    /**
     * Model stub that already contains a given project (to trigger duplicate detection).
     */
    private static class ModelStubWithProject extends ModelStub {
        private final Project existing;
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        private ModelStubWithProject(Project existing) {
            this.existing = existing;

            persons.add(ALICE);
            persons.add(BOB);
        }

        @Override
        public boolean hasProject(Project project) {
            return existing.isSameProject(project);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return persons; // empty list; we don't need members here
        }
    }

    /**
     * Model stub exposing a provided filtered person list (for index checks).
     */
    private static class ModelStubWithPersons extends ModelStub {
        private final ObservableList<Person> persons;

        private ModelStubWithPersons(ObservableList<Person> persons) {
            this.persons = persons;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return persons;
        }

        @Override
        public boolean hasProject(Project project) {
            return false;
        }

        @Override
        public void addProject(Project project) {
            // no-op
        }
    }
}
