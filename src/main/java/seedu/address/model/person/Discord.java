package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Discord handle for a Person.
 */
public class Discord {

    /** Message to display if validation fails. */
    public static final String MESSAGE_CONSTRAINTS =
            "Discord handle should be in the format username#1234, "
                    + "where username is 3-32 alphanumeric characters and discriminator is exactly 4 digits.";

    /** Regex for validating Discord handles. */
    private static final String VALIDATION_REGEX = "^[\\w]{3,32}#\\d{4}$";

    /** The Discord handle value. */
    public final String value;

    /**
     * Constructs a {@code Discord} object.
     *
     * @param handle The Discord handle to store. Must be valid.
     * @throws NullPointerException if handle is null
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
        if (!(other instanceof Discord)) {
            return false;
        }

        Discord otherDiscord = (Discord) other;
        return (value == null ? otherDiscord.value == null : value.equals(otherDiscord.value));
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }
}
