package seedu.address.logic.parser;

import seedu.address.logic.commands.SortPersonsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortPersonsCommand object
 */

public class SortPersonsCommandParser implements Parser<SortPersonsCommand> {

    @Override
    public SortPersonsCommand parse(String args) throws ParseException {
        return new SortPersonsCommand();
    }
}
