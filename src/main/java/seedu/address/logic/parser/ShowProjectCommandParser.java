package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ShowProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShowProjectCommand object.
 */
public class ShowProjectCommandParser implements Parser<ShowProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowProjectCommand
     * and returns a ShowProjectCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ShowProjectCommand parse(String args) throws ParseException {
        if (args == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowProjectCommand.MESSAGE_USAGE));
        }

        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowProjectCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(trimmed);
            return new ShowProjectCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowProjectCommand.MESSAGE_USAGE), pe);
        }
    }
}
