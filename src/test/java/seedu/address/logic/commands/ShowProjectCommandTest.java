package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ShowProjectCommand}.
 */
public class ShowProjectCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToShow = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ShowProjectCommand showProjectCommand = new ShowProjectCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ShowProjectCommand.MESSAGE_SHOW_PERSON_SUCCESS,
                personToShow.getName().fullName);

        // CommandResult should have personIndexToSelect set
        CommandResult expectedResult = new CommandResult(expectedMessage, INDEX_FIRST_PERSON);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(showProjectCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ShowProjectCommand showProjectCommand = new ShowProjectCommand(outOfBoundIndex);

        assertCommandFailure(showProjectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_showAll_success() {
        ShowProjectCommand showProjectCommand = new ShowProjectCommand(); // showAll mode

        String expectedMessage = ShowProjectCommand.MESSAGE_SHOW_ALL_PROJECTS_SUCCESS;

        // CommandResult should have isShowAllProjects() return true
        CommandResult expectedResult = new CommandResult(expectedMessage, true);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(showProjectCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void equals() {
        ShowProjectCommand showFirstCommand = new ShowProjectCommand(INDEX_FIRST_PERSON);
        ShowProjectCommand showSecondCommand = new ShowProjectCommand(INDEX_SECOND_PERSON);
        ShowProjectCommand showAllCommand = new ShowProjectCommand();

        // same object returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values returns true
        ShowProjectCommand showFirstCommandCopy = new ShowProjectCommand(INDEX_FIRST_PERSON);
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types returns false
        assertFalse(showFirstCommand.equals(1));

        // null returns false
        assertFalse(showFirstCommand.equals(null));

        // different person returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));

        // showAll vs index returns false
        assertFalse(showFirstCommand.equals(showAllCommand));

        // both showAll returns true
        ShowProjectCommand showAllCommandCopy = new ShowProjectCommand();
        assertTrue(showAllCommand.equals(showAllCommandCopy));
    }

    @Test
    public void hashCode_consistency() {
        ShowProjectCommand showFirst = new ShowProjectCommand(INDEX_FIRST_PERSON);
        ShowProjectCommand showFirstCopy = new ShowProjectCommand(INDEX_FIRST_PERSON);
        ShowProjectCommand showAll = new ShowProjectCommand();
        ShowProjectCommand showAllCopy = new ShowProjectCommand();

        // same values means same hashCode
        assertEquals(showFirst.hashCode(), showFirstCopy.hashCode());
        assertEquals(showAll.hashCode(), showAllCopy.hashCode());
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ShowProjectCommand showProjectCommand = new ShowProjectCommand(targetIndex);
        String s = showProjectCommand.toString();

        // Assert key parts are present
        assertTrue(s.contains("showAll"));
        assertTrue(s.contains("targetIndex"));
    }

    @Test
    public void toStringMethod_showAll() {
        ShowProjectCommand showProjectCommand = new ShowProjectCommand();
        String s = showProjectCommand.toString();

        // Assert showAll=true is present
        assertTrue(s.contains("showAll=true"));
    }
}
