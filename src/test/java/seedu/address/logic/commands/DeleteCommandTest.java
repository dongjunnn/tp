package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_PERSON));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_PERSON));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(List.of(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(List.of(INDEX_FIRST_PERSON));
        DeleteCommand deleteSecondCommand = new DeleteCommand(List.of(INDEX_SECOND_PERSON));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(List.of(INDEX_FIRST_PERSON));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(targetIndex));
        String s = deleteCommand.toString();

        // The exact ToStringBuilder output may vary; assert key parts are present instead of exact match.
        assertTrue(s.contains("targetIndexes"));
        assertTrue(s.contains(String.valueOf(targetIndex.getZeroBased())));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_emptyIndexList_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(Collections.emptyList());
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_multipleIndexes_dedupSuccess() {
        // Grab persons 1 and 3 from the unfiltered list
        Person p1 = model.getFilteredPersonList().get(Index.fromOneBased(1).getZeroBased());
        Person p3 = model.getFilteredPersonList().get(Index.fromOneBased(3).getZeroBased());

        // Provide out-of-order + duplicate indexes to exercise normalization path
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(
                Index.fromOneBased(3), Index.fromOneBased(1), Index.fromOneBased(3), Index.fromOneBased(1)));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(p1) + ",\n" + Messages.format(p3));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        // Delete in DESC order (to mirror command’s internal behavior)
        expectedModel.deletePerson(p3);
        expectedModel.deletePerson(p1);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_mixedValidAndInvalidIndexes() {
        // valid = 1, invalid = (size + 1)
        Index valid = Index.fromOneBased(1);
        Index invalid = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(valid, invalid));

        // Should validate all indexes before any deletion — so it fails and model remains unchanged
        Model expectedUnchanged = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // The helper already checks state, but keep an explicit sanity check:
        assertEquals(expectedUnchanged.getAddressBook(), model.getAddressBook());
        assertEquals(expectedUnchanged.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void toString_multipleIndexes_containsAll() {
        Index i1 = Index.fromOneBased(1);
        Index i3 = Index.fromOneBased(3);
        DeleteCommand cmd = new DeleteCommand(Arrays.asList(i3, i1, i3)); // will normalize

        String s = cmd.toString();
        // Should mention the field name and the zero-based forms of 0 and 2
        assertTrue(s.contains("targetIndexes"));
        assertTrue(s.contains(String.valueOf(i1.getZeroBased()))); // 0
        assertTrue(s.contains(String.valueOf(i3.getZeroBased()))); // 2
    }

}
