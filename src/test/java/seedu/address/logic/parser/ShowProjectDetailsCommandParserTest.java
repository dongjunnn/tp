package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowProjectDetailsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests for {@link ShowProjectDetailsCommandParser}.
 */
public class ShowProjectDetailsCommandParserTest {

    private final ShowProjectDetailsCommandParser parser = new ShowProjectDetailsCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        String name = "IndiDex Website Revamp";
        ShowProjectDetailsCommand cmd = parser.parse(" n/" + name);
        assertEquals(new ShowProjectDetailsCommand(name), cmd);
    }

    @Test
    public void parse_projectNameWithSpaces_success() throws Exception {
        String name = "My Project With Spaces";
        ShowProjectDetailsCommand cmd = parser.parse(" n/" + name);
        assertEquals(new ShowProjectDetailsCommand(name), cmd);
    }

    @Test
    public void parse_withWhitespaceAndTrim_success() throws Exception {
        // Name has leading/trailing whitespace - should be trimmed
        String name = " My Project ";
        ShowProjectDetailsCommand cmd = parser.parse(" n/" + name);
        assertEquals(new ShowProjectDetailsCommand("My Project"), cmd);
    }

    @Test
    public void parse_missingPrefix_failure() {
        // Missing n/ prefix
        assertThrows(ParseException.class, () -> parser.parse("My Project"));
    }

    @Test
    public void parse_emptyInput_failure() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_whitespaceOnly_failure() {
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }

    @Test
    public void parse_multipleNames_failure() {
        // Multiple n/ prefixes not allowed
        assertThrows(ParseException.class, () -> parser.parse(" n/A n/B"));
    }

    @Test
    public void parse_emptyName_failure() {
        // n/ with empty or whitespace-only value
        assertThrows(ParseException.class, () -> parser.parse(" n/"));
        assertThrows(ParseException.class, () -> parser.parse(" n/   "));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        // Preamble before n/ is not allowed
        assertThrows(ParseException.class, () -> parser.parse(" preamble n/Proj"));
    }
}
