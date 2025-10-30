package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

/**
 * Adds one or more members to a project.
 */
public class JoinProjectCommand extends Command {

    public static final String COMMAND_WORD = "join";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the people identified by the index number used in the "
            + "displayed person list to the project identified by its name.\n"
            + "Parameters: "
            + "n/PROJECT_NAME "
            + PREFIX_MEMBER + "MEMBER_INDEX "
            + "[" + PREFIX_MEMBER + "MEMBER_INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "IndiDex v1.3 "
            + PREFIX_MEMBER + "1"
            + PREFIX_MEMBER + "3";

    public static final String MESSAGE_JOIN_SUCCESS = "Added new members to `%1$s`: ";
    public static final String MESSAGE_PROJECT_NOT_FOUND = "Project '%1$s' not found!";
    public static final String MESSAGE_NO_NEW_MEMBERS = "All specified members already in `%1$s`";

    private static final Logger logger = LogsCenter.getLogger(JoinProjectCommand.class);

    private final String name;
    private final List<Index> memberIndexes;

    /**
     * @param name of the project to join
     * @param memberIndexes of the members joining
     */
    public JoinProjectCommand(String name, List<Index> memberIndexes) {
        requireNonNull(name);
        requireNonNull(memberIndexes);

        this.name = name;
        this.memberIndexes = memberIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing JoinProjectCommand for project: " + name);

        Project project = model.getProjectByName(name);
        if (project == null) {
            throw new CommandException(String.format(MESSAGE_PROJECT_NOT_FOUND, name));
        }

        List<Person> personList = model.getFilteredPersonList();
        Set<Person> updatedMembers = new HashSet<>(project.getMembers());

        StringBuilder addedNamesBuilder = new StringBuilder();

        for (Index index : memberIndexes) {
            if (index.getZeroBased() >= personList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToAdd = personList.get(index.getZeroBased());

            if (!updatedMembers.contains(personToAdd)) {
                updatedMembers.add(personToAdd);

                if (addedNamesBuilder.length() > 0) {
                    addedNamesBuilder.append(", ");
                }
                addedNamesBuilder.append(personToAdd.getName());
            }
        }

        Project updatedProject = new Project(
                project.getName(),
                project.getPriority(),
                project.getDeadline(),
                updatedMembers
        );

        model.setProject(project, updatedProject);
        String addedNames = addedNamesBuilder.toString();
        if (addedNames.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_NEW_MEMBERS, project.getName()));
        }

        logger.info("Successful executed JoinProjectCommand for project: " + name);

        return new CommandResult(String.format(MESSAGE_JOIN_SUCCESS + "%2$s", project.getName(), addedNames));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JoinProjectCommand)) {
            return false;
        }

        JoinProjectCommand otherJoinProjectCommand = (JoinProjectCommand) other;
        return name.equals(otherJoinProjectCommand.name)
                && memberIndexes.equals(otherJoinProjectCommand.memberIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("memberIndexes", memberIndexes)
                .toString();
    }
}

