package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a YouTube channel URL for a Person.
 */
public class YouTube {

    /** Message to display if validation fails. */
    public static final String MESSAGE_CONSTRAINTS =
            "YouTube channel should be a valid URL starting with 'https://www.youtube.com/'";

    /** Regex for validating YouTube URLs. */
    private static final String VALIDATION_REGEX =
            "^(https?://)?(www\\.)?youtube\\.com/(channel/[A-Za-z0-9_-]+|@?[A-Za-z0-9_.-]{1,30})/?$";

    /** The YouTube channel URL. */
    public final String value;

    /**
     * Constructs a {@code YouTube} object.
     *
     * @param channelUrl The YouTube channel URL. Must be valid.
     * @throws NullPointerException if channelUrl is null
     * @throws IllegalArgumentException if channelUrl is invalid
     */
    public YouTube(String channelUrl) {
        if (channelUrl == null || channelUrl.isEmpty()) {
            this.value = "";
            return;
        }
        checkArgument(isValidYouTube(channelUrl), MESSAGE_CONSTRAINTS);
        this.value = channelUrl;
    }

    /**
     * Returns true if a given string is a valid YouTube URL.
     *
     * @param test The string to test
     * @return true if valid, false otherwise
     */
    public static boolean isValidYouTube(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value == null ? "" : value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof YouTube)) {
            return false;
        }

        YouTube otherYouTube = (YouTube) other;
        return (value == null ? otherYouTube.value == null : value.equals(otherYouTube.value));
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }
}
