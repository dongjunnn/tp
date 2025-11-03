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
        public Project getProjectByName(String projectName) {
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

    @Test
    void execute_blankName_throwsCommandException() {
        ModelStubWithPersons model = new ModelStubWithPersons(
                FXCollections.observableArrayList(ALICE)); // index 1 is valid
        AddProjectCommand cmd = new AddProjectCommand(
                "   ", // blank after trim
                LocalDate.now().plusDays(1),
                Priority.LOW,
                List.of(Index.fromOneBased(1))
        );

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("Invalid parameter: name must not be blank.", ex.getMessage());
    }

    @Test
    void execute_nameTooLong_throwsCommandException() {
        String tooLong = "A".repeat(36); // > MAX_NAME_LENGTH (35)
        ModelStubWithPersons model = new ModelStubWithPersons(
                FXCollections.observableArrayList(ALICE)); // index 1 is valid
        AddProjectCommand cmd = new AddProjectCommand(
                tooLong,
                LocalDate.now().plusDays(1),
                Priority.MEDIUM,
                List.of(Index.fromOneBased(1))
        );

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("Invalid parameter: name must be at most 35 characters.", ex.getMessage());
    }

    @Test
    void execute_deadlineInPast_throwsCommandException() {
        ModelStubWithPersons model = new ModelStubWithPersons(
                FXCollections.observableArrayList(ALICE)); // index 1 is valid
        AddProjectCommand cmd = new AddProjectCommand(
                "Proj",
                LocalDate.now().minusDays(1), // past
                Priority.HIGH,
                List.of(Index.fromOneBased(1))
        );

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("Invalid parameter: deadline cannot be in the past.", ex.getMessage());
    }

    @Test
    void execute_duplicateMemberIndexes_throwsCommandException() {
        // Need at least 2 persons so both indices are in range
        ModelStubWithPersons model = new ModelStubWithPersons(
                FXCollections.observableArrayList(ALICE, BOB));
        AddProjectCommand cmd = new AddProjectCommand(
                "Proj",
                LocalDate.now().plusDays(2),
                Priority.LOW,
                List.of(Index.fromOneBased(1), Index.fromOneBased(1)) // duplicate indices
        );

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("Invalid parameter: duplicate member indexes are not allowed.", ex.getMessage());
    }

    @Test
    void execute_success_addsProjectAndReturnsMessage() throws Exception {
        // Use accepting stub and seed its person list so member indices resolve
        ModelStubAcceptingProjectAdded model = new ModelStubAcceptingProjectAdded();
        model.persons.addAll(ALICE, BOB);

        AddProjectCommand cmd = new AddProjectCommand(
                "IndiDex v1.4",
                LocalDate.now().plusDays(10),
                Priority.MEDIUM,
                List.of(Index.fromOneBased(1), Index.fromOneBased(2))
        );

        var result = cmd.execute(model);

        // Project recorded
        assertEquals(1, model.added.size());

        // Feedback includes header and each member on its own line with (index)
        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.startsWith("New project added: "));
        assertTrue(feedback.contains("Members (each shown on a new line):"));
        assertTrue(feedback.contains(ALICE.getName().fullName + " (1)"));
        assertTrue(feedback.contains(BOB.getName().fullName + " (2)"));
    }

    // -------------------- equals(), hashCode(), toString() --------------------

    @Test
    void equalsCaseInsensitiveName() {
        AddProjectCommand a = new AddProjectCommand(
                "Alpha",
                LocalDate.of(2025, 1, 1),
                Priority.LOW,
                List.of()
        );
        AddProjectCommand b = new AddProjectCommand(
                "alpha", // different case
                LocalDate.of(2025, 1, 1),
                Priority.LOW,
                List.of()
        );
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    void hashCode_caseInsensitiveName_equal() {
        AddProjectCommand a = new AddProjectCommand(
                "MiXeD",
                LocalDate.of(2025, 1, 1),
                Priority.HIGH,
                List.of(Index.fromOneBased(2))
        );
        AddProjectCommand b = new AddProjectCommand(
                "mixed", // same after toLowerCase()
                LocalDate.of(2025, 1, 1),
                Priority.HIGH,
                List.of(Index.fromOneBased(2))
        );
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void toString_containsAllFields() {
        LocalDate d = LocalDate.of(2025, 1, 1);
        AddProjectCommand cmd = new AddProjectCommand(
                "Zeta",
                d,
                Priority.LOW,
                List.of(Index.fromOneBased(3))
        );
        String s = cmd.toString();
        assertTrue(s.contains("name=Zeta"));
        assertTrue(s.contains("deadline=" + d));
        assertTrue(s.contains("priority=LOW"));
        assertTrue(s.contains("memberIndexes"));
    }

    // -------------------- constructor null-guards --------------------

    @Test
    void constructor_nullGuards_throwNullPointerException() {
        List<Index> list = List.of(Index.fromOneBased(1));

        assertThrows(NullPointerException.class, () -> new AddProjectCommand(null, LocalDate.now(),
                Priority.LOW, list));
        assertThrows(NullPointerException.class, () -> new AddProjectCommand("Name", null, Priority.LOW, list));
        assertThrows(NullPointerException.class, () -> new AddProjectCommand("Name", LocalDate.now(), null, list));
        assertThrows(NullPointerException.class, () -> new AddProjectCommand("Name", LocalDate.now(),
                Priority.HIGH, null));
    }

}
