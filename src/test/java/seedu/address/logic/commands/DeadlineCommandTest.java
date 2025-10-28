package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeadlineCommand.SHOWING_DEADLINE_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class DeadlineCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_deadline_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_DEADLINE_MESSAGE, false, true, false);
        assertCommandSuccess(new DeadlineCommand(), model, expectedCommandResult, expectedModel);
    }
}
