package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import seedu.address.logic.commands.EditProjectCommand;
import seedu.address.logic.commands.EditProjectCommand.EditProjectDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditProjectCommand object
 */

public class EditProjectCommandParser implements Parser<EditProjectCommand> {

    private static final int MAX_NAME_LENGTH = 35;
    /**
     * Parses the given {@code String} of arguments in the context of the EditProjectCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DEADLINE, PREFIX_PRIORITY);

        String oldName = argMultimap.getPreamble().trim();
        if (oldName.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditProjectCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DEADLINE, PREFIX_PRIORITY);

        EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String name = argMultimap.getValue(PREFIX_NAME).get();
            if (name.length() > MAX_NAME_LENGTH) {
                throw new ParseException("Invalid parameter: name must be at most " + MAX_NAME_LENGTH + " characters.");
            }
            if (name.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditProjectCommand.MESSAGE_USAGE));
            }
            editProjectDescriptor.setName(argMultimap.getValue(PREFIX_NAME).get());
        }

        if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
            editProjectDescriptor.setDeadline(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DEADLINE).get()));
        }

        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editProjectDescriptor.setPriority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }

        if (!editProjectDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditProjectCommand.MESSAGE_NOT_EDITED);
        }

        return new EditProjectCommand(oldName, editProjectDescriptor);
    }
}
