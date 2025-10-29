package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;

/**
 * Shows details for a project identified by its exact name (case-sensitive).
 * Finds the project in the model and displays its details by selecting a person who is a member.
 */
public class ShowProjectDetailsCommand extends Command {

    public static final String COMMAND_WORD = "pdetails";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows details for the project by *exact* name (case-sensitive).\n"
            + "Parameters: n/NAME\n"
            + "Example: " + COMMAND_WORD + " n/IndiDex Website Revamp";

    public static final String MESSAGE_SHOW_PROJECT_DETAILS_SUCCESS = "Showing details for project: %1$s";

    private final String projectName;

    /**
     * Creates a ShowProjectDetailsCommand to show details for the project with the specified name.
     *
     * @param projectName The exact name of the project (case-sensitive)
     */
    public ShowProjectDetailsCommand(String projectName) {
        requireNonNull(projectName);
        this.projectName = projectName.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> projectList = model.getFilteredProjectList();

        // Find project by exact name (case-sensitive)
        Project targetProject = projectList.stream()
                .filter(p -> p.getName() != null && p.getName().equals(projectName))
                .findFirst()
                .orElse(null);

        if (targetProject == null) {
            throw new CommandException(String.format(Messages.MESSAGE_PROJECT_NOT_FOUND_BY_NAME, projectName));
        }

        // Return CommandResult with project for UI to display
        return new CommandResult(
                String.format(MESSAGE_SHOW_PROJECT_DETAILS_SUCCESS, projectName),
                targetProject);
    }

    /**
     * Returns the project name to show details for.
     *
     * @return The project name
     */
    public String getProjectName() {
        return projectName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShowProjectDetailsCommand)) {
            return false;
        }

        ShowProjectDetailsCommand otherCommand = (ShowProjectDetailsCommand) other;
        return projectName.equalsIgnoreCase(otherCommand.projectName);
    }

    @Override
    public int hashCode() {
        return projectName.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("projectName", projectName)
                .toString();
    }
}
