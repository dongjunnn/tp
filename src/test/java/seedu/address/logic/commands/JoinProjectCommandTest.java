package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.priority.Priority;
import seedu.address.model.project.Project;

/**
 * Contains integration tests (interaction with the Model) and unit tests for JoinProjectCommand.
 */
public class JoinProjectCommandTest {
    private static final String PROJECT_NAME = "Test Project";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_membersJoinProject_success() {
        Set<Person> members = new HashSet<>();
        Project project = new Project(PROJECT_NAME, Priority.HIGH, LocalDate.of(2025, 12, 31), members);
        model.addProject(project);

        JoinProjectCommand command = new JoinProjectCommand(PROJECT_NAME,
                List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));

        Set<Person> expectedMembers = new HashSet<>();
        expectedMembers.add(ALICE);
        expectedMembers.add(BENSON);
        Project expectedProject = new Project(PROJECT_NAME, Priority.HIGH, LocalDate.of(2025, 12, 31), expectedMembers);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setProject(project, expectedProject);

        String expectedMessage = String.format(JoinProjectCommand.MESSAGE_JOIN_SUCCESS
                + "%2$s, %3$s", PROJECT_NAME, ALICE.getName(), BENSON.getName());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        JoinProjectCommand firstJoinCommand = new JoinProjectCommand(PROJECT_NAME, List.of(INDEX_FIRST_PERSON));
        JoinProjectCommand secondJoinCommand = new JoinProjectCommand(PROJECT_NAME, List.of(INDEX_SECOND_PERSON));
        JoinProjectCommand joinOtherProjectCommand = new JoinProjectCommand("Other Project",
                List.of(INDEX_FIRST_PERSON));

        // same object -> returns true
        assertTrue(firstJoinCommand.equals(firstJoinCommand));

        // same values -> returns true
        assertTrue(firstJoinCommand.equals(new JoinProjectCommand(PROJECT_NAME, List.of(INDEX_FIRST_PERSON))));

        // different person -> returns false
        assertFalse(firstJoinCommand.equals(secondJoinCommand));

        // different project
        assertFalse(firstJoinCommand.equals(joinOtherProjectCommand));
    }
}
