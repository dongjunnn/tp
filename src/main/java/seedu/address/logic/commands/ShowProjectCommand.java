package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

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
            + ": Shows projects for the person identified by index, or shows all projects.\n"
            + "Parameters: INDEX (must be a positive integer) or 'all'\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " all";

    public static final String MESSAGE_SHOW_PERSON_SUCCESS = "Showing projects for: %1$s";
    public static final String MESSAGE_SHOW_ALL_PROJECTS_SUCCESS = "Showing all projects";

    private final Index targetIndex;
    private final boolean showAll;

    /**
     * Creates a ShowProjectCommand to show all projects.
     */
    public ShowProjectCommand() {
        this.targetIndex = null;
        this.showAll = true;
    }

    /**
     * Creates a ShowProjectCommand to show projects for the person at the specified {@code Index}.
     */
    public ShowProjectCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.showAll = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (showAll) {
            return new CommandResult(MESSAGE_SHOW_ALL_PROJECTS_SUCCESS, true);
        }

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

    /**
     * Returns the target index if in index mode, empty Optional if in showAll mode.
     */
    public Optional<Index> getTargetIndex() {
        return Optional.ofNullable(targetIndex);
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
        if (showAll != otherCommand.showAll) {
            return false;
        }

        // If both are showAll, they're equal
        if (showAll) {
            return true;
        }

        // Otherwise compare indices
        return targetIndex.equals(otherCommand.targetIndex);
    }

    @Override
    public int hashCode() {
        if (showAll) {
            return Boolean.hashCode(true);
        }
        return targetIndex.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("showAll", showAll)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
