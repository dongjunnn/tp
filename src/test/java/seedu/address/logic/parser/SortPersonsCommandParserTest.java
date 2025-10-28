package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortPersonsCommand;

public class SortPersonsCommandParserTest {

    private SortPersonsCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new SortPersonsCommandParser();
    }

    @Test
    public void parse_nameAscImplicit_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_NAME, // e.g. " n/"
                new SortPersonsCommand(PREFIX_NAME, true)
        );
    }

    @Test
    public void parse_nameAscExplicit_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_NAME + "asc", // " n/asc"
                new SortPersonsCommand(PREFIX_NAME, true)
        );
    }

    @Test
    public void parse_nameDesc_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_NAME + "desc", // " n/desc"
                new SortPersonsCommand(PREFIX_NAME, false)
        );
    }

    @Test
    public void parse_emailAscImplicit_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_EMAIL,
                new SortPersonsCommand(PREFIX_EMAIL, true)
        );
    }

    @Test
    public void parse_emailAscExplicit_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_EMAIL + "asc",
                new SortPersonsCommand(PREFIX_EMAIL, true)
        );
    }

    @Test
    public void parse_emailDesc_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_EMAIL + "desc",
                new SortPersonsCommand(PREFIX_EMAIL, false)
        );
    }

    @Test
    public void parse_phoneAscImplicit_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_PHONE,
                new SortPersonsCommand(PREFIX_PHONE, true)
        );
    }

    @Test
    public void parse_phoneAscExplicit_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_PHONE + "asc",
                new SortPersonsCommand(PREFIX_PHONE, true)
        );
    }

    @Test
    public void parse_phoneDesc_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_PHONE + "desc",
                new SortPersonsCommand(PREFIX_PHONE, false)
        );
    }

    @Test
    public void parse_priorityAscImplicit_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_PRIORITY,
                new SortPersonsCommand(PREFIX_PRIORITY, true)
        );
    }

    @Test
    public void parse_priorityAscExplicit_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_PRIORITY + "asc",
                new SortPersonsCommand(PREFIX_PRIORITY, true)
        );
    }

    @Test
    public void parse_priorityDesc_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_PRIORITY + "desc",
                new SortPersonsCommand(PREFIX_PRIORITY, false)
        );
    }

    @Test
    public void parse_addressAscImplicit_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_ADDRESS,
                new SortPersonsCommand(PREFIX_ADDRESS, true)
        );
    }

    @Test
    public void parse_addressAscExplicit_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_ADDRESS + "asc",
                new SortPersonsCommand(PREFIX_ADDRESS, true)
        );
    }

    @Test
    public void parse_addressDesc_success() {
        assertParseSuccess(
                parser,
                " " + PREFIX_ADDRESS + "desc",
                new SortPersonsCommand(PREFIX_ADDRESS, false)
        );
    }

    @Test
    public void parse_noPrefix_failure() {
        assertParseFailure(
                parser,
                "   ", // user just typed "sort"
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonsCommand.MESSAGE_USAGE)
        );
    }

    @Test
    public void parse_multiplePrefixes_failure() {
        assertParseFailure(
                parser,
                " " + PREFIX_NAME + "asc " + PREFIX_EMAIL + "desc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonsCommand.MESSAGE_USAGE)
        );
    }

    @Test
    public void parse_invalidDirection_failure() {
        assertParseFailure(
                parser,
                " " + PREFIX_NAME + "down", // not asc/desc
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonsCommand.MESSAGE_USAGE)
        );
    }
}
