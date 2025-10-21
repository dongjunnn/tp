package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code TagCommand}.
 */
public class TagCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexSingleTagUnfilteredList_success() {
        Person personToTag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tag newTag = new Tag("friend");
        Set<Tag> tagsToAdd = Set.of(newTag);
        TagCommand tagCommand = new TagCommand(List.of(INDEX_FIRST_PERSON), tagsToAdd);

        Person updatedPerson = new Person(
                personToTag.getName(),
                personToTag.getPhone(),
                personToTag.getEmail(),
                personToTag.getSocials(),
                personToTag.getAddress(),
                union(personToTag.getTags(), tagsToAdd)
        );

        String expectedMessage = String.format(
                TagCommand.MESSAGE_SUCCESS,
                String.join(", ", tagsToAdd.stream().map(Tag::toString).toList()),
                1
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person personInExpectedModel = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.setPerson(personInExpectedModel, updatedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validMultipleIndexesMultipleTags_success() {
        List<Index> indexes = List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        Set<Tag> tagsToAdd = Set.of(new Tag("team"), new Tag("urgent"));
        TagCommand tagCommand = new TagCommand(indexes, tagsToAdd);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        for (Index index : indexes) {
            Person personInExpectedModel = expectedModel.getFilteredPersonList().get(index.getZeroBased());
            Person updatedPerson = new Person(
                    personInExpectedModel.getName(),
                    personInExpectedModel.getPhone(),
                    personInExpectedModel.getEmail(),
                    personInExpectedModel.getSocials(),
                    personInExpectedModel.getAddress(),
                    union(personInExpectedModel.getTags(), tagsToAdd)
            );
            expectedModel.setPerson(personInExpectedModel, updatedPerson);
        }

        String expectedMessage = String.format(
                TagCommand.MESSAGE_SUCCESS,
                String.join(", ", tagsToAdd.stream().map(Tag::toString).toList()),
                indexes.size()
        );

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TagCommand tagCommand = new TagCommand(List.of(outOfBoundIndex), Set.of(new Tag("invalid")));

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToTag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tagsToAdd = Set.of(new Tag("filtered"));
        TagCommand tagCommand = new TagCommand(List.of(INDEX_FIRST_PERSON), tagsToAdd);

        Person updatedPerson = new Person(
                personToTag.getName(),
                personToTag.getPhone(),
                personToTag.getEmail(),
                personToTag.getSocials(),
                personToTag.getAddress(),
                union(personToTag.getTags(), tagsToAdd)
        );

        String expectedMessage = String.format(
                TagCommand.MESSAGE_SUCCESS,
                String.join(", ", tagsToAdd.stream().map(Tag::toString).toList()),
                1
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        // Important: filter expected model the same way as the actual model
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        Person personInExpectedModel = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.setPerson(personInExpectedModel, updatedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        TagCommand tagCommand = new TagCommand(List.of(outOfBoundIndex), Set.of(new Tag("failTag")));

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        List<Index> firstList = List.of(INDEX_FIRST_PERSON);
        List<Index> secondList = List.of(INDEX_SECOND_PERSON);
        Set<Tag> firstTagSet = Set.of(new Tag("alpha"));
        Set<Tag> secondTagSet = Set.of(new Tag("beta"));

        TagCommand tagFirstCommand = new TagCommand(firstList, firstTagSet);
        TagCommand tagSecondCommand = new TagCommand(secondList, secondTagSet);

        // same object -> returns true
        assertTrue(tagFirstCommand.equals(tagFirstCommand));

        // same values -> returns true
        TagCommand tagFirstCommandCopy = new TagCommand(firstList, firstTagSet);
        assertTrue(tagFirstCommand.equals(tagFirstCommandCopy));

        // different types -> returns false
        assertFalse(tagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(tagFirstCommand.equals(null));

        // different indexes -> returns false
        assertFalse(tagFirstCommand.equals(tagSecondCommand));

        // same indexes but different tags -> returns false
        TagCommand tagDifferentTags = new TagCommand(firstList, secondTagSet);
        assertFalse(tagFirstCommand.equals(tagDifferentTags));
    }

    @Test
    public void toStringMethod() {
        List<Index> targetIndexes = List.of(INDEX_FIRST_PERSON);
        Set<Tag> tags = Set.of(new Tag("toStringTag"));
        TagCommand tagCommand = new TagCommand(targetIndexes, tags);
        String s = tagCommand.toString();

        assertTrue(s.contains("targetIndexes"));
        assertTrue(s.contains("tagsToAdd"));
        assertTrue(s.contains("toStringTag"));
    }

    /**
     * Returns the union of two sets of tags.
     */
    private static Set<Tag> union(Set<Tag> original, Set<Tag> toAdd) {
        Set<Tag> combined = new HashSet<>(original);
        combined.addAll(toAdd);
        return combined;
    }
}
