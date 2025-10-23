package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.SortPersonsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortPersonsCommand object
 */

public class SortPersonsCommandParser implements Parser<SortPersonsCommand> {
    private static final Set<String> VALID_DIRECTIONS =
            Set.of("asc", "desc");

    @Override
    public SortPersonsCommand parse(String args) throws ParseException {
        List<Prefix> Attributes = List.of(
                PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_PRIORITY, PREFIX_ADDRESS);
        Prefix[] attrArray = Attributes.toArray(new Prefix[0]);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, attrArray);
        argMultimap.verifyNoDuplicatePrefixesFor(attrArray);
        List<Prefix> present = Attributes.stream()
                .filter(p -> hasPrefix(argMultimap, p))
                .toList();
        if (present.size() != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonsCommand.MESSAGE_USAGE));
        }
        String dirToken = Attributes.stream()
                .map(argMultimap::getValue)
                .flatMap(java.util.Optional::stream)
                .findFirst()
                .orElse("");

        boolean ascending = dirToken.isEmpty() || dirToken.equalsIgnoreCase("asc");
        if (!dirToken.isEmpty() && !VALID_DIRECTIONS.contains(dirToken.toLowerCase())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonsCommand.MESSAGE_USAGE));
        }

        Prefix attr = present.get(0);
        return new SortPersonsCommand(attr, ascending);
    }
    /**
     *  Treat a prefix as 'present' even if it has an empty value.
     */
    private boolean hasPrefix(ArgumentMultimap map, Prefix p) {
        return !map.getAllValues(p).isEmpty();
    }
}
