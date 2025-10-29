package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.priority.Priority;
import seedu.address.model.project.Project;


/**
 * Contains integration tests (interaction with the Model and unit tests for LeaveProjectCommand.
 */
public class LeaveProjectCommandTest {

    private static final String PROJECT_NAME = "Test Project";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_memberLeavesProject_success() {
        Set<Person> members = new HashSet<>();
        members.add(ALICE);
        members.add(BOB);
        Project project = new Project(PROJECT_NAME, Priority.HIGH, LocalDate.of(2025, 12, 31), members);
        model.addProject(project);

        LeaveProjectCommand command = new LeaveProjectCommand(PROJECT_NAME, List.of(INDEX_FIRST_PERSON));

        Set<Person> expectedMembers = new HashSet<>();
        expectedMembers.add(BOB);
        Project expectedProject = new Project(PROJECT_NAME, Priority.HIGH, LocalDate.of(2025, 12, 31), expectedMembers);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setProject(project, expectedProject);

        String expectedMessage = String.format(LeaveProjectCommand.MESSAGE_LEAVE_SUCCESS
                + "\n%2$s", PROJECT_NAME, ALICE.getName());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_projectNotFound_failure() {
        LeaveProjectCommand command = new LeaveProjectCommand("Nonexistent", List.of(INDEX_FIRST_PERSON));
        assertCommandFailure(command, model,
                String.format(LeaveProjectCommand.MESSAGE_PROJECT_NOT_FOUND, "Nonexistent"));
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Set<Person> members = new HashSet<>();
        members.add(ALICE);
        Project project = new Project(PROJECT_NAME, Priority.HIGH, LocalDate.now(), members);
        model.addProject(project);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LeaveProjectCommand command = new LeaveProjectCommand(PROJECT_NAME, List.of(outOfBoundIndex));

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_memberNotInProject_failure() {
        Set<Person> members = new HashSet<>();
        members.add(ALICE);
        Project project = new Project(PROJECT_NAME, Priority.LOW, LocalDate.now(), members);
        model.addProject(project);

        LeaveProjectCommand command = new LeaveProjectCommand(PROJECT_NAME, List.of(INDEX_SECOND_PERSON));

        String expectedMessage = String.format(LeaveProjectCommand.MESSAGE_MEMBER_NOT_IN_PROJECT, BENSON.getName());
        assertCommandFailure(command, model, expectedMessage);
    }
}
