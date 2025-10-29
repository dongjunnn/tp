package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowProjectCommand;

/**
 * Tests for {@link ShowProjectCommandParser}.
 */
public class ShowProjectCommandParserTest {

    private ShowProjectCommandParser parser = new ShowProjectCommandParser();

    @Test
    public void parse_validIndex_returnsShowProjectCommand() {
        // Test parsing "1"
        assertParseSuccess(parser, "1", new ShowProjectCommand(INDEX_FIRST_PERSON));

        // Test parsing "2"
        assertParseSuccess(parser, "2", new ShowProjectCommand(INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_allLowercase_returnsShowProjectCommand() {
        // Test "all" (lowercase)
        ShowProjectCommand expectedCommand = new ShowProjectCommand(); // showAll mode
        assertParseSuccess(parser, "all", expectedCommand);
    }

    @Test
    public void parse_allUppercase_returnsShowProjectCommand() {
        // Test "ALL" (uppercase) - should be case-insensitive
        ShowProjectCommand expectedCommand = new ShowProjectCommand();
        assertParseSuccess(parser, "ALL", expectedCommand);
    }

    @Test
    public void parse_allMixedCase_returnsShowProjectCommand() {
        // Test "All" (mixed case)
        ShowProjectCommand expectedCommand = new ShowProjectCommand();
        assertParseSuccess(parser, "All", expectedCommand);
    }

    @Test
    public void parse_validIndexWithWhitespace_returnsShowProjectCommand() {
        // Test with leading/trailing whitespace
        assertParseSuccess(parser, "  1  ", new ShowProjectCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(parser, "   all   ", new ShowProjectCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Non-numeric input
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowProjectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        // Zero is invalid (indices are 1-based)
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowProjectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowProjectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_decimalIndex_throwsParseException() {
        assertParseFailure(parser, "1.5", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowProjectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowProjectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowProjectCommand.MESSAGE_USAGE));
    }
}
