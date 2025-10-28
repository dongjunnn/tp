package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.priority.Priority;

class AddProjectCommandParserTest {

    private final AddProjectCommandParser parser = new AddProjectCommandParser();

    @Test
    void parseAllFieldsPresentMembersSpaceSeparated_success() throws Exception {
        String input = " n/IndiDex v1.3 d/2025-12-31 pr/HIGH m/1 m/3";
        AddProjectCommand expected = new AddProjectCommand(
                "IndiDex v1.3",
                LocalDate.of(2025, 12, 31),
                Priority.HIGH,
                List.of(Index.fromOneBased(1), Index.fromOneBased(3))
        );

        AddProjectCommand actual = parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    void parseAllFieldsPresentMembersCommaSeparated_success() throws Exception {
        String input = " n/ProjectX d/2026-01-15 pr/MEDIUM m/1,2,5";
        AddProjectCommand expected = new AddProjectCommand(
                "ProjectX",
                LocalDate.of(2026, 1, 15),
                Priority.MEDIUM,
                List.of(Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(5))
        );

        AddProjectCommand actual = parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    void parseNameTrimmed_success() throws Exception {
        String input = " n/   MyProj   d/2025-10-01 pr/LOW";
        AddProjectCommand expected = new AddProjectCommand(
                "MyProj",
                LocalDate.of(2025, 10, 1),
                Priority.LOW,
                List.of() // no members provided
        );

        AddProjectCommand actual = parser.parse(input);
        assertEquals(expected, actual);
    }

    // -------------------- Missing / invalid (assert only that it fails) --------------------

    @Test
    void parseMissingNamePrefix_failure() {
        String input = " d/2025-12-31 pr/HIGH m/1";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parseMissingDeadlinePrefix_failure() {
        String input = " n/Proj pr/HIGH m/1";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parseMissingPriorityPrefix_failure() {
        String input = " n/Proj d/2025-12-31 m/1";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parseNonEmptyPreamble_failure() {
        String input = "preamble n/Proj d/2025-12-31 pr/HIGH";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parseInvalidDate_failure() {
        // Invalid date; ensures parser reaches date parsing (all required prefixes present)
        String input = " n/Proj d/2025-02-30 pr/HIGH";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parseInvalidPriority_failure() {
        String input = " n/Proj d/2025-12-31 pr/ULTRA";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parseInvalidMemberIndex_failure() {
        String input = " n/Proj d/2025-12-31 pr/HIGH m/0 m/-3";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    // -------------------- Duplicate single-value prefixes --------------------

    @Test
    void parseDuplicateNamePrefix_failure() {
        String input = " n/One n/Two d/2025-12-31 pr/HIGH";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parseDuplicateDeadlinePrefix_failure() {
        String input = " n/Proj d/2025-12-31 d/2025-12-01 pr/HIGH";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parseDuplicatePriorityPrefix_failure() {
        String input = " n/Proj d/2025-12-31 pr/HIGH pr/LOW";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

}
