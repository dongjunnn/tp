package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Discord;
import seedu.address.model.person.Email;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.LinkedIn;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.YouTube;
import seedu.address.model.project.Priority;
import seedu.address.model.priority.Priority;
import seedu.address.model.tag.Tag;

//UPDATED: Parsing ALL socials handles

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be
     * trimmed.
     * 
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String discordHandle} into a {@code Discord} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @return a {@code Discord} object, or {@code null} if not provided
     * @throws ParseException if the given {@code discordHandle} is invalid
     */
    public static Discord parseDiscordHandle(String discordHandle) throws ParseException {
        if (discordHandle == null || discordHandle.trim().isEmpty()) {
            return null; // no Discord provided
        }

        String trimmedDiscordHandle = discordHandle.trim();
        if (!Discord.isValidDiscord(trimmedDiscordHandle)) {
            throw new ParseException(Discord.MESSAGE_CONSTRAINTS);
        }

        return new Discord(trimmedDiscordHandle);
    }

    /**
     * Parses a {@code String linkedInProfile} into a {@code LinkedIn} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @return a {@code LinkedIn} object, or {@code null} if not provided
     * @throws ParseException if the given {@code linkedInProfile} is invalid
     */
    public static LinkedIn parseLinkedInProfile(String linkedInProfile) throws ParseException {
        if (linkedInProfile == null || linkedInProfile.trim().isEmpty()) {
            return null; // no LinkedIn profile provided
        }

        String trimmedLinkedInProfile = linkedInProfile.trim();
        if (!LinkedIn.isValidLinkedIn(trimmedLinkedInProfile)) {
            throw new ParseException(LinkedIn.MESSAGE_CONSTRAINTS);
        }

        return new LinkedIn(trimmedLinkedInProfile);
    }

    /**
     * Parses a {@code String instagramHandle} into a {@code Instagram} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @return a {@code Instagram} object, or {@code null} if not provided
     * @throws ParseException if the given {@code instagramHandle} is invalid
     */
    public static Instagram parseInstagramHandle(String instagramHandle) throws ParseException {
        if (instagramHandle == null || instagramHandle.trim().isEmpty()) {
            return null; // no Instagram handle provided
        }

        String trimmedInstagramHandle = instagramHandle.trim();
        if (!Instagram.isValidInstagram(trimmedInstagramHandle)) {
            throw new ParseException(Instagram.MESSAGE_CONSTRAINTS);
        }

        return new Instagram(trimmedInstagramHandle);

    }

    /**
     * Parses a {@code String youTubeChannel} into a {@code YouTube} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @return a {@code YouTube} object, or {@code null} if not provided
     * @throws ParseException if the given {@code youTubeChannel} is invalid
     */
    public static YouTube parseYouTubeChannel(String youTubeChannel) throws ParseException {
        if (youTubeChannel == null || youTubeChannel.trim().isEmpty()) {
            return null; // no YouTube channel provided
        }

        String trimmedYouTubeChannel = youTubeChannel.trim();
        if (!YouTube.isValidYouTube(trimmedYouTubeChannel)) {
            throw new ParseException(YouTube.MESSAGE_CONSTRAINTS);
        }

        return new YouTube(trimmedYouTubeChannel);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String s} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code s} is invalid.
     */
    public static LocalDate parseDate(String s) throws ParseException {
        try {
            return LocalDate.parse(s.trim());
        } catch (DateTimeParseException e) {
            throw new ParseException("Deadline must be yyyy-MM-dd");
        }
    }

    /**
     * Parses a {@code String s} into a {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code s} is invalid.
     */
    public static Priority parsePriority(String s) throws ParseException {
        try {
            return Priority.valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException("Priority must be LOW/MEDIUM/HIGH");
        }
    }
}
