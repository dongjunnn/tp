package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.Tag;

/**
 * Tests for {@code TagCommandParser}.
 */
public class TagCommandParserTest {

    private final TagCommandParser parser = new TagCommandParser();

    private static final String VALID_TAG_FRIEND = "friend";
    private static final String VALID_TAG_CS2103 = "cs2103";
    private static final String VALID_TAG_IMPORTANT = "important";
    private static final String INVALID_TAG = "bad tag!";

    private static final String TAG_DESC_FRIEND = " t/" + VALID_TAG_FRIEND;
    private static final String TAG_DESC_CS2103 = " t/" + VALID_TAG_CS2103;
    private static final String TAG_DESC_IMPORTANT = " t/" + VALID_TAG_IMPORTANT;
    private static final String TAG_DESC_INVALID = " t/" + INVALID_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);

    // -------------------------------------------------------------------------

    @Test
    public void parse_validSingleIndexSingleTag_success() {
        String userInput = "1" + TAG_DESC_FRIEND;
        TagCommand expectedCommand =
                new TagCommand(List.of(INDEX_FIRST_PERSON), Set.of(new Tag(VALID_TAG_FRIEND)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validMultipleIndexesSingleTag_success() {
        String userInput = "1 2 3" + TAG_DESC_IMPORTANT;
        TagCommand expectedCommand =
                new TagCommand(
                        List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                        Set.of(new Tag(VALID_TAG_IMPORTANT)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validSingleIndexMultipleTags_success() {
        String userInput = "2" + TAG_DESC_FRIEND + TAG_DESC_CS2103;
        TagCommand expectedCommand =
                new TagCommand(List.of(INDEX_SECOND_PERSON),
                        Set.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_CS2103)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validMultipleIndexesMultipleTags_success() {
        String userInput = "1 2" + TAG_DESC_FRIEND + TAG_DESC_CS2103 + TAG_DESC_IMPORTANT;
        TagCommand expectedCommand =
                new TagCommand(List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON),
                        Set.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_CS2103), new Tag(VALID_TAG_IMPORTANT)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // -------------------------------------------------------------------------

    @Test
    public void parse_missingIndex_failure() {
        String userInput = TAG_DESC_FRIEND;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingTags_failure() {
        String userInput = "1 2";
        assertParseFailure(parser, userInput,
                "At least one tag must be provided.\n" + TagCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TAG_DESC_FRIEND, "Index is not a non-zero unsigned integer.");
        // zero
        assertParseFailure(parser, "0" + TAG_DESC_FRIEND, "Index is not a non-zero unsigned integer.");
        // non-numeric
        assertParseFailure(parser, "a" + TAG_DESC_FRIEND, "Index is not a non-zero unsigned integer.");
    }


    @Test
    public void parse_invalidTag_failure() {
        String userInput = "1" + TAG_DESC_INVALID;
        assertParseFailure(parser, userInput, Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_extraWhitespace_success() {
        String userInput = "   1   2   3   " + TAG_DESC_FRIEND + "   " + TAG_DESC_CS2103;
        TagCommand expectedCommand =
                new TagCommand(List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                        Set.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_CS2103)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleTagsWithDuplicates_success() {
        String userInput = "1" + TAG_DESC_FRIEND + TAG_DESC_FRIEND + TAG_DESC_CS2103;
        TagCommand expectedCommand =
                new TagCommand(List.of(INDEX_FIRST_PERSON),
                        Set.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_CS2103)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_extraRandomStrings_failure() {
        assertParseFailure(parser, "1 2 random t/friend", "Index is not a non-zero unsigned integer.");
    }
}
