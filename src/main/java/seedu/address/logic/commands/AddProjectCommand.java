package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.project.Priority;
import seedu.address.model.project.Project;

/**
 * Adds a project to the address book.
 */
public class AddProjectCommand extends Command {

    public static final String COMMAND_WORD = "padd";

    private static final Logger logger =
            Logger.getLogger(AddProjectCommand.class.getName());

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a project to the address book. "
            + "Parameters: "
            + "n/NAME "
            + "d/DEADLINE(yyyy-MM-dd) "
            + "p/PRIORITY "
            + "[m/MEMBER_INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "n/IndiDex v1.3 "
            + "d/2025-12-31 "
            + "p/HIGH "
            + "m/1 m/3";

    private static final String MESSAGE_SUCCESS = "New project added: %1$s";
    private static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the address book";

    private final String name;
    private final LocalDate deadline;
    private final Priority priority;
    private final List<Index> memberIndexes; // this resolves to Person objects in execute()

    /**
     * Creates an AddProjectCommand with raw fields.
     */
    public AddProjectCommand(String name,
                             LocalDate deadline,
                             Priority priority,
                             List<Index> memberIndexes) {
        this.name = requireNonNull(name);
        this.deadline = requireNonNull(deadline);
        this.priority = requireNonNull(priority);
        this.memberIndexes = requireNonNull(memberIndexes);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Person> persons = model.getFilteredPersonList();
        Set<Person> members = new HashSet<>();
        for (Index idx : memberIndexes) {
            int z = idx.getZeroBased();
            if (z < 0 || z >= persons.size()) {
                logger.log(java.util.logging.Level.WARNING,
                        "Invalid member index provided: " + idx);
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            members.add(persons.get(z));
        }

        Project toAdd = new Project(name, priority, deadline, members);

        if (model.hasProject(toAdd)) {
            logger.log(java.util.logging.Level.WARNING,
                    "Attempted to add duplicate project: " + toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        model.addProject(toAdd);
        logger.log(java.util.logging.Level.INFO,
                "Project added successfully: " + toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddProjectCommand)) {
            return false;
        }
        AddProjectCommand o = (AddProjectCommand) other;
        return name.equals(o.name)
                && deadline.equals(o.deadline)
                && priority.equals(o.priority)
                && memberIndexes.equals(o.memberIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("deadline", deadline)
                .add("priority", priority)
                .add("memberIndexes", memberIndexes)
                .toString();
    }
}
