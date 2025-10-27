package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.priority.Priority;
import seedu.address.model.project.Project;

/**
 * Edit the name, deadline and/or priority of a selected project.
 */
public class EditProjectCommand extends Command {

    public static final String COMMAND_WORD = "pedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the project identified. "
            + "Parameters: "
            + "OLDNAME "
            + "[" + PREFIX_NAME + "NEWNAME] "
            + "[" + PREFIX_DEADLINE + "DEADLINE(yyyy-MM-dd)] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "Example: " + COMMAND_WORD + " "
            + "Razer Collaboration Video "
            + PREFIX_DEADLINE + "2025-12-25 "
            + PREFIX_PRIORITY + "HIGH";

    public static final String MESSAGE_EDIT_PROJECT_SUCCESS = "Edited Project: %s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_PROJECT_NOT_FOUND = "Project '%1$s' not found!";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the address book.";

    private final String oldName;
    private final EditProjectDescriptor editProjectDescriptor;

    /**
     * @param oldName of the project in the filtered project list to edit
     * @param editProjectDescriptor details to edit the project with
     */
    public EditProjectCommand(String oldName, EditProjectDescriptor editProjectDescriptor) {
        requireNonNull(oldName);
        requireNonNull(editProjectDescriptor);

        this.oldName = oldName;
        this.editProjectDescriptor = new EditProjectDescriptor(editProjectDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Project projectToEdit = model.getProjectByName(oldName);
        if (projectToEdit == null) {
            throw new CommandException(String.format(MESSAGE_PROJECT_NOT_FOUND, oldName));
        }

        if (editProjectDescriptor.getName().isPresent()) {
            String newName = editProjectDescriptor.getName().get();
            Project existing = model.getProjectByName(newName);
            if (existing != null && !existing.equals(projectToEdit)) {
                throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
            }
        }

        Project editedProject = createEditedProject(projectToEdit, editProjectDescriptor);

        model.setProject(projectToEdit, editedProject);
        return new CommandResult(String.format(MESSAGE_EDIT_PROJECT_SUCCESS, editedProject));
    }

    /**
     * Creates and returns a {@code Project} with the details of {@code projectToEdit}
     * edited with {@code editProjectDescriptor}.
     */
    private static Project createEditedProject(Project projectToEdit, EditProjectDescriptor editProjectDescriptor) {
        assert projectToEdit != null;

        String updatedName = editProjectDescriptor.getName().orElse(projectToEdit.getName());
        LocalDate updatedDeadline = editProjectDescriptor.getDeadline().orElse(projectToEdit.getDeadline());
        Priority updatedPriority = editProjectDescriptor.getPriority().orElse(projectToEdit.getPriority());
        Set<Person> members = projectToEdit.getMembers();

        return new Project(updatedName, updatedPriority, updatedDeadline, members);
    }

    /**
     * Stores the details to edit the project with. Each non-empty field value will replace the
     * corresponding field value of the project.
     */
    public static class EditProjectDescriptor {
        private String name;
        private LocalDate deadline;
        private Priority priority;

        public EditProjectDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditProjectDescriptor(EditProjectDescriptor toCopy) {
            setName(toCopy.name);
            setDeadline(toCopy.deadline);
            setPriority(toCopy.priority);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, deadline, priority);
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public void setDeadline(LocalDate deadline) {
            this.deadline = deadline;
        }

        public Optional<LocalDate> getDeadline() {
            return Optional.ofNullable(deadline);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditProjectDescriptor)) {
                return false;
            }

            EditProjectDescriptor otherEditProjectDescriptor = (EditProjectDescriptor) other;
            return Objects.equals(name, otherEditProjectDescriptor.name)
                    && Objects.equals(deadline, otherEditProjectDescriptor.deadline)
                    && Objects.equals(priority, otherEditProjectDescriptor.priority);
        }
    }
}
