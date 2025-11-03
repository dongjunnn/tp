package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public TagCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_TAG);

        String preamble = argumentMultimap.getPreamble();
        if (preamble.isEmpty() || !arePrefixesPresent(argumentMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        List<String> indexStrings = Arrays.asList(preamble.trim().split("\\s+"));
        List<Index> indexes = new ArrayList<>();
        for (String s : indexStrings) {
            Index toAdd = ParserUtil.parseIndex(s);
            if (indexes.contains(toAdd)) {
                continue;
            }
            indexes.add(toAdd);
        }

        Set<Tag> tags = ParserUtil.parseTags(argumentMultimap.getAllValues(CliSyntax.PREFIX_TAG));
        if (tags.isEmpty()) {
            throw new ParseException("At least one tag must be provided.\n" + TagCommand.MESSAGE_USAGE);
        }

        return new TagCommand(indexes, tags);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
