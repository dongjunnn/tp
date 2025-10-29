package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 * Simple behaviour: split by whitespace and parse each token as an index.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    @Override
    public DeleteCommand parse(String args) throws ParseException {
        if (args == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        String[] tokens = trimmed.split("\\s+"); // Split by whitespace
        List<Index> indexes = new ArrayList<>();

        for (String token : tokens) {
            try {
                indexes.add(ParserUtil.parseIndex(token));
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                            DeleteCommand.MESSAGE_USAGE), pe);
            }
        }

        if (indexes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        long distinct = indexes.stream().map(Index::getZeroBased).distinct().count();
        if (distinct != indexes.size()) {
            throw new ParseException("Duplicate indexes are not allowed.");
        }

        return new DeleteCommand(indexes);
    }
}
