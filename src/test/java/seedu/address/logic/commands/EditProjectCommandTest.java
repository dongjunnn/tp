package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.priority.Priority;
import seedu.address.model.project.Project;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditProjectCommand.
 */
public class EditProjectCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private static Project createTestProject() {
        Set<Person> members = new HashSet<>();
        members.add(ALICE);
        members.add(BOB);
        return new Project("Test Project", Priority.HIGH,
                LocalDate.of(2025, 12, 31), members);
    }

    @Test
    public void execute_editName_success() throws CommandException {
        Project testProject = createTestProject();
        model.addProject(testProject);

        EditProjectCommand.EditProjectDescriptor editProjectDescriptor = new EditProjectCommand.EditProjectDescriptor();
        editProjectDescriptor.setName("Edited Project");
        EditProjectCommand command = new EditProjectCommand("Test Project", editProjectDescriptor);

        CommandResult result = command.execute(model);

        Project updatedProject = model.getProjectByName("Edited Project");
        assertNotNull(updatedProject);
        assertEquals("Edited Project", updatedProject.getName());
        assertEquals(testProject.getPriority(), updatedProject.getPriority());
        assertEquals(testProject.getDeadline(), updatedProject.getDeadline());
        assertEquals(testProject.getMembers(), updatedProject.getMembers());
    }

    @Test
    public void execute_editPriority_success() throws CommandException {
        Project testProject = createTestProject();
        model.addProject(testProject);

        EditProjectCommand.EditProjectDescriptor editProjectDescriptor = new EditProjectCommand.EditProjectDescriptor();
        editProjectDescriptor.setPriority(Priority.LOW);
        EditProjectCommand command = new EditProjectCommand("Test Project", editProjectDescriptor);

        CommandResult result = command.execute(model);

        Project updatedProject = model.getProjectByName("Test Project");
        assertNotNull(updatedProject);
        assertEquals(testProject.getName(), updatedProject.getName());
        assertEquals(Priority.LOW, updatedProject.getPriority());
        assertEquals(testProject.getDeadline(), updatedProject.getDeadline());
        assertEquals(testProject.getMembers(), updatedProject.getMembers());
    }

    @Test
    public void execute_editDeadline_success() throws CommandException {
        Project testProject = createTestProject();
        model.addProject(testProject);

        EditProjectCommand.EditProjectDescriptor editProjectDescriptor = new EditProjectCommand.EditProjectDescriptor();
        editProjectDescriptor.setDeadline(LocalDate.of(2025, 12, 31));
        EditProjectCommand command = new EditProjectCommand("Test Project", editProjectDescriptor);

        CommandResult result = command.execute(model);

        Project updatedProject = model.getProjectByName("Test Project");
        assertNotNull(updatedProject);
        assertEquals(testProject.getName(), updatedProject.getName());
        assertEquals(testProject.getPriority(), updatedProject.getPriority());
        assertEquals(LocalDate.of(2025, 12, 31), updatedProject.getDeadline());
        assertEquals(testProject.getMembers(), updatedProject.getMembers());
    }
}
