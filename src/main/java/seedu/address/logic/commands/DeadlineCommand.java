package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Shows deadlines that are due 7 days from now.
 */

public class DeadlineCommand extends Command {
    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows project deadlines that are due 7 days from now."
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_DEADLINE_MESSAGE = "Opened deadline window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_DEADLINE_MESSAGE, false, true, false);
    }
}
