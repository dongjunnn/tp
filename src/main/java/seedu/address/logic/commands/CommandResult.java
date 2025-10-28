package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.project.Project;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The index of the person to select (for pshow command). */
    private final Index personIndexToSelect;

    /** The project to show details for (for pdetails command). */
    private final Project projectToShow;

    /**
     * Constructs a {@code CommandResult} with all fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         Index personIndexToSelect, Project projectToShow) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.personIndexToSelect = personIndexToSelect;
        this.projectToShow = projectToShow;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null, null);
    }

    /**
     * Constructs a {@code CommandResult} for help or exit commands.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, null, null);
    }

    /**
     * Constructs a {@code CommandResult} with person index to select (for pshow).
     */
    public CommandResult(String feedbackToUser, Index personIndexToSelect) {
        this(feedbackToUser, false, false, personIndexToSelect, null);
    }

    /**
     * Constructs a {@code CommandResult} with project to show (for pdetails).
     */
    public CommandResult(String feedbackToUser, Project projectToShow) {
        this(feedbackToUser, false, false, null, projectToShow);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public Index getPersonIndexToSelect() {
        return personIndexToSelect;
    }

    public boolean hasPersonIndexToSelect() {
        return personIndexToSelect != null;
    }

    public Project getProjectToShow() {
        return projectToShow;
    }

    public boolean hasProjectToShow() {
        return projectToShow != null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && Objects.equals(personIndexToSelect, otherCommandResult.personIndexToSelect)
                && Objects.equals(projectToShow, otherCommandResult.projectToShow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, personIndexToSelect, projectToShow);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("personIndexToSelect", personIndexToSelect)
                .add("projectToShow", projectToShow)
                .toString();
    }

}
