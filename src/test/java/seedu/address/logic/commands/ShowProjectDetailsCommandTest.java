package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * Contains integration tests (interaction with the Model) for
 * {@code ShowProjectDetailsCommand}.
 */
public class ShowProjectDetailsCommandTest {

    @Test
    public void execute_existingProject_success() throws Exception {
        Project p1 = new Project(
                "IndiDex Website Revamp",
                Priority.HIGH,
                LocalDate.of(2025, 12, 31),
                java.util.Set.of()
        );
        Project p2 = new Project(
                "Other Project",
                Priority.LOW,
                LocalDate.of(2026, 1, 1),
                java.util.Set.of()
        );
        ObservableList<Project> list = FXCollections.observableArrayList(List.of(p1, p2));
        ModelStubWithProjects model = new ModelStubWithProjects(list);

        ShowProjectDetailsCommand cmd = new ShowProjectDetailsCommand("IndiDex Website Revamp");
        CommandResult result = cmd.execute(model);

        String expectedMsg = String.format(
                ShowProjectDetailsCommand.MESSAGE_SHOW_PROJECT_DETAILS_SUCCESS, "IndiDex Website Revamp"
        );
        assertEquals(expectedMsg, result.getFeedbackToUser());
        assertTrue(result.hasProjectToShow());
        assertEquals(p1, result.getProjectToShow());
    }

    @Test
    public void execute_nonExistentProject_throwsCommandException() {
        Project p2 = new Project(
                "Other Project",
                Priority.LOW,
                LocalDate.of(2026, 1, 1),
                java.util.Set.of()
        );
        ObservableList<Project> list = FXCollections.observableArrayList(List.of(p2));
        ModelStubWithProjects model = new ModelStubWithProjects(list);

        String missing = "Missing Project";
        ShowProjectDetailsCommand cmd = new ShowProjectDetailsCommand(missing);

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(String.format(Messages.MESSAGE_PROJECT_NOT_FOUND_BY_NAME, missing), ex.getMessage());
    }

    @Test
    public void execute_caseSensitiveMatch_success() throws Exception {
        // Test that exact case match is required
        Project p1 = new Project(
                "ProjectName",
                Priority.MEDIUM,
                LocalDate.of(2025, 10, 1),
                java.util.Set.of()
        );
        ObservableList<Project> list = FXCollections.observableArrayList(List.of(p1));
        ModelStubWithProjects model = new ModelStubWithProjects(list);

        // Exact case should work
        ShowProjectDetailsCommand cmd = new ShowProjectDetailsCommand("ProjectName");
        CommandResult result = cmd.execute(model);
        assertTrue(result.hasProjectToShow());

        // Different case should fail
        ShowProjectDetailsCommand cmdWrongCase = new ShowProjectDetailsCommand("projectname");
        assertThrows(CommandException.class, () -> cmdWrongCase.execute(model));
    }

    @Test
    public void equals_contract() {
        ShowProjectDetailsCommand a = new ShowProjectDetailsCommand("Alpha");
        ShowProjectDetailsCommand bSame = new ShowProjectDetailsCommand("Alpha");
        ShowProjectDetailsCommand cDiff = new ShowProjectDetailsCommand("Beta");

        // same object returns true
        assertTrue(a.equals(a));

        // same name returns true (equals uses case-insensitive compare)
        assertTrue(a.equals(bSame));

        // null returns false
        assertFalse(a.equals(null));

        // different type returns false
        assertFalse(a.equals("string"));

        // different name returns false
        assertFalse(a.equals(cDiff));

        // same name means same hashCode
        assertEquals(a.hashCode(), bSame.hashCode());
    }

    @Test
    public void toStringMethod() {
        ShowProjectDetailsCommand cmd = new ShowProjectDetailsCommand("TestProject");
        String s = cmd.toString();

        // Assert project name is present
        assertTrue(s.contains("projectName"));
        assertTrue(s.contains("TestProject"));
    }

    // ----------------------------------------------------------------
    //                            Model stubs
    // ----------------------------------------------------------------

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
        public Project getProjectByName(String projectName) {
            return null;
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

    private static class ModelStubWithProjects extends ModelStub {
        private final ObservableList<Project> projects;

        private ModelStubWithProjects(ObservableList<Project> projects) {
            this.projects = projects;
        }

        @Override
        public ObservableList<Project> getFilteredProjectList() {
            return projects;
        }
    }
}
