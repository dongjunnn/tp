package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.Priority;

/**
 * Parses input arguments and creates a new AddProjectCommand object
 */
public class AddProjectCommandParser implements Parser<AddProjectCommand> {

    private static final Logger logger =
            Logger.getLogger(AddProjectCommandParser.class.getName());

    @Override
    public AddProjectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_DEADLINE, PREFIX_PRIORITY, PREFIX_MEMBER, PREFIX_REMARKS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DEADLINE, PREFIX_PRIORITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddProjectCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DEADLINE, PREFIX_PRIORITY, PREFIX_REMARKS);

        String name = argMultimap.getValue(PREFIX_NAME).get().trim();
        LocalDate deadline = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DEADLINE).get());
        Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());

        List<Index> memberIndexes = new ArrayList<>();
        for (String raw : argMultimap.getAllValues(PREFIX_MEMBER)) {
            logger.log(java.util.logging.Level.INFO,
                    "Parsing member indexes from raw input: " + raw);
            for (String token : raw.split("[,\\s]+")) {
                if (token.isBlank()) {
                    continue;
                }
                memberIndexes.add(ParserUtil.parseIndex(token));
            }
        }

        return new AddProjectCommand(name, deadline, priority, memberIndexes);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap map, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(p -> map.getValue(p).isPresent());
    }
}
