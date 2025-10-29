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

    /** Show all projects (for pshow all command). */
    private final boolean showAllProjects;

    /**
     * Constructs a {@code CommandResult} with all fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         Index personIndexToSelect, Project projectToShow, boolean showAllProjects) {
        this.feedbackToUser = requireNonNull(feedbackToUser);

        // Validate that only one action is specified (mutually exclusive)
        int actionCount = 0;
        if (showHelp) {
            actionCount++;
        }
        if (exit) {
            actionCount++;
        }
        if (personIndexToSelect != null) {
            actionCount++;
        }
        if (projectToShow != null) {
            actionCount++;
        }
        if (showAllProjects) {
            actionCount++;
        }

        if (actionCount > 1) {
            throw new IllegalArgumentException(
                "CommandResult should only specify one UI action at a time");
        }

        this.showHelp = showHelp;
        this.exit = exit;
        this.personIndexToSelect = personIndexToSelect;
        this.projectToShow = projectToShow;
        this.showAllProjects = showAllProjects;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null, null, false);
    }

    /**
     * Constructs a {@code CommandResult} for help or exit commands.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, null, null, false);
    }

    /**
     * Constructs a {@code CommandResult} with person index to select (for pshow).
     */
    public CommandResult(String feedbackToUser, Index personIndexToSelect) {
        this(feedbackToUser, false, false, personIndexToSelect, null, false);
    }

    /**
     * Constructs a {@code CommandResult} with project to show (for pdetails).
     */
    public CommandResult(String feedbackToUser, Project projectToShow) {
        this(feedbackToUser, false, false, null, projectToShow, false);
    }

    /**
     * Constructs a {@code CommandResult} for showing all projects (for pshow all).
     */
    public CommandResult(String feedbackToUser, boolean showAllProjects) {
        this(feedbackToUser, false, false, null, null, showAllProjects);
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

    public boolean isShowAllProjects() {
        return showAllProjects;
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
                && Objects.equals(projectToShow, otherCommandResult.projectToShow)
                && showAllProjects == otherCommandResult.showAllProjects;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, personIndexToSelect, projectToShow, showAllProjects);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("personIndexToSelect", personIndexToSelect)
                .add("projectToShow", projectToShow)
                .add("showAllProjects", showAllProjects)
                .toString();
    }

}
