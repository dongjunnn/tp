package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

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
import seedu.address.model.project.Project;

/**
 * Removes one or more members from a project
 */
public class LeaveProjectCommand extends Command {

    public static final String COMMAND_WORD = "leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the people identified by the index number used in the "
            + "displayed person list from the project identified by its name.\n"
            + "Parameters: "
            + "n/PROJECT_NAME "
            + PREFIX_MEMBER + "MEMBER_INDEX "
            + "[" + PREFIX_MEMBER + "MEMBER_INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "IndiDex v1.3"
            + PREFIX_MEMBER + "1"
            + PREFIX_MEMBER + "3";

    public static final String MESSAGE_LEAVE_SUCCESS = "Removed members from `%1$s`: ";
    public static final String MESSAGE_PROJECT_NOT_FOUND = "Project '%1$s' not found!";
    public static final String MESSAGE_MEMBER_NOT_IN_PROJECT =
            "%1$s can't leave a project they aren't a part of!";
    public static final String MESSAGE_LAST_MEMBER =
            "Project cannot have no members!";

    private final String name;
    private final List<Index> memberIndexes;

    /**
     * @param name of the project to leave
     * @param memberIndexes of the members joining
     */
    public LeaveProjectCommand(String name, List<Index> memberIndexes) {
        requireNonNull(name);
        requireNonNull(memberIndexes);

        this.name = name;
        this.memberIndexes = memberIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Project project = model.getProjectByName(name);
        if (project == null) {
            throw new CommandException(String.format(MESSAGE_PROJECT_NOT_FOUND, name));
        }

        List<Person> personList = model.getFilteredPersonList();
        Set<Person> updatedMembers = new HashSet<>(project.getMembers());

        for (Index index : memberIndexes) {
            if (index.getZeroBased() < 0 || index.getZeroBased() >= personList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToRemove = personList.get(index.getZeroBased());

            if (!updatedMembers.contains(personToRemove)) {
                throw new CommandException(String.format(
                        MESSAGE_MEMBER_NOT_IN_PROJECT,
                        personToRemove.getName()
                ));
            }

            updatedMembers.remove(personToRemove);
        }

        if (updatedMembers.isEmpty()) {
            throw new CommandException(MESSAGE_LAST_MEMBER);
        }

        Project updatedProject = new Project(
                project.getName(),
                project.getPriority(),
                project.getDeadline(),
                updatedMembers
        );

        model.setProject(project, updatedProject);
        String removedNames = memberIndexes.stream()
                .map(i -> personList.get(i.getZeroBased()).getName().toString())
                .collect(Collectors.joining(", "));

        return new CommandResult(String.format(MESSAGE_LEAVE_SUCCESS
                + "\n%2$s", project.getName(), removedNames));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LeaveProjectCommand)) {
            return false;
        }
        LeaveProjectCommand o = (LeaveProjectCommand) other;
        return name.equals(o.name)
                && memberIndexes.equals(o.memberIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("memberIndexes", memberIndexes)
                .toString();
    }
}
