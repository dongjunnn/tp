package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LeaveProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class LeaveProjectCommandParserTest {

    private static final String PROJECT_NAME = "Test Project";
    private LeaveProjectCommandParser parser = new LeaveProjectCommandParser();

    @Test
    public void parse_validArgs_success() throws ParseException {
        String userInput = " " + PREFIX_NAME + PROJECT_NAME + " "
                + PREFIX_MEMBER + "1 "
                + PREFIX_MEMBER + "2";

        LeaveProjectCommand expectedCommand = new LeaveProjectCommand(PROJECT_NAME,
                List.of(Index.fromOneBased(1), Index.fromOneBased(2)));

        LeaveProjectCommand result = parser.parse(userInput);

        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        String input = "";
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LeaveProjectCommand.MESSAGE_USAGE), () -> parser.parse(input));
    }
}
