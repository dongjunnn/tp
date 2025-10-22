package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISCORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINKEDIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YOUTUBE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Discord;
import seedu.address.model.person.Email;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.LinkedIn;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Socials;
import seedu.address.model.person.YouTube;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_DISCORD, PREFIX_LINKEDIN, PREFIX_INSTAGRAM, PREFIX_YOUTUBE,
                PREFIX_ADDRESS, PREFIX_PRIORITY, PREFIX_TAG);

        // Check that all required prefixes are present and no extra preamble
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Check for duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_DISCORD,
                PREFIX_LINKEDIN, PREFIX_INSTAGRAM, PREFIX_YOUTUBE, PREFIX_ADDRESS);

        // Parse individual fields
        // Safe to call get() because presence is checked above
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Priority priority;
        if (!arePrefixesPresent(argMultimap, PREFIX_PRIORITY)) {
            priority = new Priority("LOW");
        } else {
            priority = new Priority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }

        Discord discordHandle = null;
        if (argMultimap.getValue(PREFIX_DISCORD).isPresent()) {
            discordHandle = ParserUtil.parseDiscordHandle(argMultimap.getValue(PREFIX_DISCORD).get());
        }
        LinkedIn linkedInProfile = null;
        if (argMultimap.getValue(PREFIX_LINKEDIN).isPresent()) {
            linkedInProfile = ParserUtil.parseLinkedInProfile(argMultimap.getValue(PREFIX_LINKEDIN).get());
        }
        Instagram instagramHandle = null;
        if (argMultimap.getValue(PREFIX_INSTAGRAM).isPresent()) {
            instagramHandle = ParserUtil.parseInstagramHandle(argMultimap.getValue(PREFIX_INSTAGRAM).get());
        }
        YouTube youtubeChannel = null;
        if (argMultimap.getValue(PREFIX_YOUTUBE).isPresent()) {
            youtubeChannel = ParserUtil.parseYouTubeChannel(argMultimap.getValue(PREFIX_YOUTUBE).get());
        }
        Socials socials = new Socials(discordHandle, linkedInProfile, instagramHandle, youtubeChannel);

        Person person = new Person(name, phone, email, socials, address, priority, tagList);

        return new AddCommand(person);
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
