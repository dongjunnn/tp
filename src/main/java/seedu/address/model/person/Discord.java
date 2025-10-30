package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Discord handle for a Person.
 */
public class Discord {

    /** Message to display if validation fails. */
    public static final String MESSAGE_CONSTRAINTS =
            "Discord username must be 2-32 characters long, only contain lowercase letters, digits, periods (.) "
                    + "or underscores (_), cannot start or end with a period or underscore, and cannot have "
                    + "consecutive periods or underscores.";

    /** Regex for validating Discord handles. */
    private static final String VALIDATION_REGEX = "^(?=.{2,32}$)(?![_.])(?!.*[_.]{2})[a-z0-9._]+(?<![_.])$";

    /** The Discord handle value. */
    public final String value;

    /**
     * Constructs a {@code Discord} object.
     *
     * @param handle The Discord handle to store. Must be valid.
     * @throws IllegalArgumentException if handle is invalid
     */
    public Discord(String handle) {
        if (handle == null || handle.isEmpty()) {
            this.value = "";
            return;
        }
        checkArgument(isValidDiscord(handle), MESSAGE_CONSTRAINTS);
        this.value = handle;
    }

    /**
     * Returns true if a given string is a valid Discord handle.
     *
     * @param test The string to test
     * @return true if valid, false otherwise
     */
    public static boolean isValidDiscord(String test) {
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
        if (!(other instanceof Discord)) {
            return false;
        }

        Discord otherDiscord = (Discord) other;
        return value.equals(otherDiscord.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
