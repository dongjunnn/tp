package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.address.logic.commands.ShowProjectDetailsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShowProjectDetailsCommand object.
 * Follows the same pattern as DeleteProjectCommandParser - uses n/ prefix for project name.
 */
public class ShowProjectDetailsCommandParser implements Parser<ShowProjectDetailsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowProjectDetailsCommand
     * and returns a ShowProjectDetailsCommand object for execution.
     *
     * @param args The user input arguments
     * @return A ShowProjectDetailsCommand with the parsed project name
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ShowProjectDetailsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        // Check for invalid preamble (text before n/)
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(ShowProjectDetailsCommand.MESSAGE_USAGE);
        }

        // Parse project name from n/ prefix
        Optional<String> nameOpt = argMultimap.getValue(PREFIX_NAME);
        if (nameOpt.isEmpty()) {
            throw new ParseException(ShowProjectDetailsCommand.MESSAGE_USAGE);
        }

        // Check for multiple n/ prefixes
        if (argMultimap.getAllValues(PREFIX_NAME).size() > 1) {
            throw new ParseException("Provide exactly one project name with n/.");
        }

        String name = nameOpt.get().trim();
        if (name.isEmpty()) {
            throw new ParseException("Project name cannot be empty.");
        }

        return new ShowProjectDetailsCommand(name);
    }
}
