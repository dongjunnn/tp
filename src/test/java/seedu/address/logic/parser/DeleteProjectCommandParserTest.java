package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteProjectCommandParserTest {

    private final DeleteProjectCommandParser parser = new DeleteProjectCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        String name = "IndiDex Website Revamp";
        DeleteProjectCommand cmd = parser.parse(" n/" + name);
        // equals() is case-insensitive on the name
        assertEquals(new DeleteProjectCommand(name), cmd);
    }

    @Test
    public void parse_withWhitespaceAndTrim_success() throws Exception {
        String name = " My Project ";
        DeleteProjectCommand cmd = parser.parse(" n/" + name);
        assertEquals(new DeleteProjectCommand("My Project"), cmd);
    }

    @Test
    public void parse_missingPrefix_failure() {
        assertThrows(ParseException.class, () -> parser.parse("My Project"));
    }

    @Test
    public void parse_emptyInput_failure() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_multipleNames_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" n/A n/B"));
    }

    @Test
    public void parse_emptyName_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" n/   "));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" preamble n/Proj"));
    }
}
