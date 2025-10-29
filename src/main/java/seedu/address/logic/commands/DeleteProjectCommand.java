package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;

/**
 * Deletes a project identified by its exact name from the address book.
 */
public class DeleteProjectCommand extends Command {
    public static final String COMMAND_WORD = "pdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a project by *exact* name (case-sensitive).\n"
            + "Parameters: n/NAME\n"
            + "Example: " + COMMAND_WORD + " n/IndiDex Website Revamp";

    public static final String MESSAGE_DELETE_PROJECT_SUCCESS = "Deleted Project:\n%1$s";

    private final String name;

    public DeleteProjectCommand(String name) {
        this.name = requireNonNull(name).trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> shown = model.getFilteredProjectList();

        Project target = shown.stream()
                .filter(p -> p.getName() != null && p.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (target == null) {
            throw new CommandException(String.format(Messages.MESSAGE_PROJECT_NOT_FOUND_BY_NAME, name));
        }

        model.deleteProject(target);
        return new CommandResult(String.format(MESSAGE_DELETE_PROJECT_SUCCESS, Messages.format(target)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteProjectCommand
                && name.equals(((DeleteProjectCommand) other).name));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
