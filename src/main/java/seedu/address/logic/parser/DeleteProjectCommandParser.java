package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.address.logic.commands.DeleteProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteProjectCommand object
 */
public class DeleteProjectCommandParser implements Parser<DeleteProjectCommand> {

    @Override
    public DeleteProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(DeleteProjectCommand.MESSAGE_USAGE);
        }

        // currently, only single delete is supported
        Optional<String> nameOpt = argMultimap.getValue(PREFIX_NAME);
        if (nameOpt.isEmpty()) {
            throw new ParseException(DeleteProjectCommand.MESSAGE_USAGE);
        }

        if (argMultimap.getAllValues(PREFIX_NAME).size() > 1) {
            throw new ParseException("Provide exactly one project name with n/.");
        }

        String name = nameOpt.get().trim();
        if (name.isEmpty()) {
            throw new ParseException("Project name cannot be empty.");
        }

        return new DeleteProjectCommand(name);
    }
}
