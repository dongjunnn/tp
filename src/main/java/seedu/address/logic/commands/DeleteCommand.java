package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
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

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person(s):\n%1$s";

    private final List<Index> targetIndexes;

    /**
     * Constructs a DeleteCommand that deletes the persons at the given indexes.
     * A defensive copy of {@code targetIndexes} is made.
     *
     * @param targetIndexes the list of indexes (one-based) to delete
     */
    public DeleteCommand(List<Index> targetIndexes) {
        requireNonNull(targetIndexes);
        this.targetIndexes = new ArrayList<>(targetIndexes);
    }

    /**
     * Executes the delete command: validates requested indexes, prepares the readable message
     * in ascending/display order, then performs deletions in descending order so earlier
     * deletions do not shift later indexes.
     *
     * @param model the model to operate on
     * @return the command result containing the deleted person names
     * @throws CommandException if any requested index is invalid
     */
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

        // Create a sorted list of zero-based indexes in ascending display order (for readable output)
        List<Integer> indexesAsc = new ArrayList<>(zeroBasedSet);
        Collections.sort(indexesAsc); // ascending order by displayed index

        List<String> deletedNamesForMessage = new ArrayList<>();
        for (Integer i : indexesAsc) {
            deletedNamesForMessage.add(Messages.format(lastShownList.get(i)));
        }

        // Delete by descending index so earlier deletions don't shift later indexes
        List<Integer> indexesDesc = new ArrayList<>(indexesAsc);
        Collections.reverse(indexesDesc); // now descending
        for (Integer idx : indexesDesc) {
            Person p = lastShownList.get(idx);
            model.deletePerson(p);
        }

        // Build final message showing names in ascending/display order
        String resultMessage = String.join(",\n", deletedNamesForMessage);
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
