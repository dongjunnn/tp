package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a LinkedIn profile for a Person.
 */
public class LinkedIn {

    /** Message to display if validation fails. */
    public static final String MESSAGE_CONSTRAINTS =
            "LinkedIn profile should be a valid URL starting with "
                    + "'(https://www.)linkedin.com/in/'"
                    + " followed by your profile identifier.";

    /** Regex for validating LinkedIn profiles. */
    private static final String VALIDATION_REGEX =
            "^(https?://)?(www\\.)?linkedin\\.com/in/[A-Za-z0-9_-]+/?$";

    /** The LinkedIn profile URL. */
    public final String value;

    /**
     * Constructs a {@code LinkedIn} object.
     *
     * @param profileUrl The LinkedIn profile URL. Must be valid.
     * @throws NullPointerException if profileUrl is null
     * @throws IllegalArgumentException if profileUrl is invalid
     */
    public LinkedIn(String profileUrl) {
        if (profileUrl == null || profileUrl.isEmpty()) {
            this.value = "";
            return;
        }
        checkArgument(isValidLinkedIn(profileUrl), MESSAGE_CONSTRAINTS);
        this.value = profileUrl;
    }

    /**
     * Returns true if a given string is a valid LinkedIn URL.
     *
     * @param test The string to test
     * @return true if valid, false otherwise
     */
    public static boolean isValidLinkedIn(String test) {
        return test.isEmpty() || test.equals("-") || test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LinkedIn)) {
            return false;
        }

        LinkedIn otherLinkedIn = (LinkedIn) other;
        return value.equals(otherLinkedIn.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
