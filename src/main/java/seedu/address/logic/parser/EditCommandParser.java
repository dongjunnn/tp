package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISCORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINKEDIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YOUTUBE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_DISCORD, PREFIX_LINKEDIN, PREFIX_INSTAGRAM, PREFIX_YOUTUBE,
                        PREFIX_ADDRESS, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_DISCORD, PREFIX_LINKEDIN, PREFIX_INSTAGRAM, PREFIX_YOUTUBE, PREFIX_ADDRESS);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_DISCORD).isPresent()) {
            String discordValue = argMultimap.getValue(PREFIX_DISCORD).get(); // may be ""
            if (discordValue.isEmpty()) {
                // user provided the prefix with no value -> clear field (set to empty string)
                editPersonDescriptor.setDiscordHandle("");
            } else {
                editPersonDescriptor.setDiscordHandle(ParserUtil.parseDiscordHandle(discordValue));
            }
        }
        if (argMultimap.getValue(PREFIX_LINKEDIN).isPresent()) {
            String linkedInValue = argMultimap.getValue(PREFIX_LINKEDIN).get(); // may be ""
            if (linkedInValue.isEmpty()) {
                editPersonDescriptor.setLinkedInProfile("");
            } else {
                editPersonDescriptor.setLinkedInProfile(ParserUtil.parseLinkedInProfile(linkedInValue));
            }
        }
        if (argMultimap.getValue(PREFIX_INSTAGRAM).isPresent()) {
            String instagramHandle = argMultimap.getValue(PREFIX_INSTAGRAM).get(); // may be ""
            if (instagramHandle.isEmpty()) {
                // user provided the prefix with no value -> clear field (set to empty string)
                editPersonDescriptor.setInstagramHandle("");
            } else {
                editPersonDescriptor.setInstagramHandle(ParserUtil.parseInstagramHandle(instagramHandle));
            }
        }
        if (argMultimap.getValue(PREFIX_YOUTUBE).isPresent()) {
            String youtubeURL = argMultimap.getValue(PREFIX_YOUTUBE).get(); // may be ""
            if (youtubeURL.isEmpty()) {
                // user provided the prefix with no value -> clear field (set to empty string)
                editPersonDescriptor.setYouTubeChannel("");
            } else {
                editPersonDescriptor.setYouTubeChannel(ParserUtil.parseYouTubeChannel(youtubeURL));
            }
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
