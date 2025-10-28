package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Shows projects for a person identified by their displayed index from the address book.
 */
public class ShowProjectCommand extends Command {

    public static final String COMMAND_WORD = "pshow";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows projects for the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SHOW_PERSON_SUCCESS = "Showing projects for: %1$s";

    private final Index targetIndex;

    /**
     * Creates a ShowProjectCommand to show projects for the person at the specified {@code Index}.
     */
    public ShowProjectCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToShow = lastShownList.get(targetIndex.getZeroBased());
        // Return CommandResult with person index for UI to select
        return new CommandResult(
                String.format(MESSAGE_SHOW_PERSON_SUCCESS, personToShow.getName().fullName),
                targetIndex);
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShowProjectCommand)) {
            return false;
        }

        ShowProjectCommand otherCommand = (ShowProjectCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }

    @Override
    public int hashCode() {
        return targetIndex.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
