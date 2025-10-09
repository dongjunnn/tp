package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons identified by the index numbers used in the displayed person list.\n"
            + "Parameters: INDEX [INDEX]... (each must be a positive integer). You may also provide ranges like 3-5.\n"
            + "Example: " + COMMAND_WORD + " 1 3 5";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person(s): %1$s";

    private final List<Index> targetIndexes;

    public DeleteCommand(List<Index> targetIndexes) {
        requireNonNull(targetIndexes);
        this.targetIndexes = new ArrayList<>(targetIndexes);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndexes.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // Convert to zero-based ints and deduplicate
        Set<Integer> zeroBasedSet = new HashSet<>();
        for (Index idx : targetIndexes) {
            zeroBasedSet.add(idx.getZeroBased());
        }

        // Validate all indexes BEFORE deleting to avoid partial state changes
        List<Integer> invalid = zeroBasedSet.stream()
                .filter(i -> i < 0 || i >= lastShownList.size())
                .collect(Collectors.toList());
        if (!invalid.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // Map to Person objects
        List<Person> personsToDelete = zeroBasedSet.stream()
                .map(lastShownList::get)
                .collect(Collectors.toList());

        // Delete in descending order of their positions in the displayed list so index-shifts don't break us
        personsToDelete.sort(Comparator.comparingInt(lastShownList::indexOf).reversed());

        List<String> deletedNames = new ArrayList<>();
        for (Person p : personsToDelete) {
            model.deletePerson(p);
            deletedNames.add(Messages.format(p));
        }

        String resultMessage = String.join(", ", deletedNames);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, resultMessage));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand otherCommand = (DeleteCommand) other;
        return new HashSet<>(this.targetIndexes).equals(new HashSet<>(otherCommand.targetIndexes));
    }

    @Override
    public int hashCode() {
        return new HashSet<>(targetIndexes).hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexes", targetIndexes)
                .toString();
    }
}
