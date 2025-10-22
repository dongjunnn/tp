package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.JoinProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new JoinProjectCommand object
 */
public class JoinProjectCommandParser implements Parser<JoinProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the JoinProjectCommand
     * and returns an JoinProjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public JoinProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MEMBER);

        String projectName = argMultimap.getValue(PREFIX_NAME).get().trim();
        if (projectName.isEmpty()) {
            throw new ParseException("Missing project name.\n" + JoinProjectCommand.MESSAGE_USAGE);
        }

        List<String> memberStrings = argMultimap.getAllValues(PREFIX_MEMBER);
        if (memberStrings.isEmpty()) {
            throw new ParseException("At least one member index must be provided.\n"
                    + JoinProjectCommand.MESSAGE_USAGE);
        }

        List<Index> memberIndexes = new ArrayList<>();
        for (String s : memberStrings) {
            memberIndexes.add(ParserUtil.parseIndex(s));
        }

        return new JoinProjectCommand(projectName, memberIndexes);
    }
}
