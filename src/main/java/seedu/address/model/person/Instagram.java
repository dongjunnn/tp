package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Instagram handle for a Person.
 */
public class Instagram {

    /** Message to display if validation fails. */
    public static final String MESSAGE_CONSTRAINTS =
            "Instagram handle should start with @ and be 1-30 alphanumeric characters or underscores.";

    /** Regex for validating Instagram handles. */
    private static final String VALIDATION_REGEX = "^@[A-Za-z0-9_]{1,30}$";

    /** The Instagram handle. */
    public final String value;

    /**
     * Constructs an {@code Instagram} object.
     *
     * @param handle The Instagram handle. Must be valid.
     * @throws NullPointerException if handle is null
     * @throws IllegalArgumentException if handle is invalid
     */
    public Instagram(String handle) {
        if (handle == null || handle.isEmpty()) {
            this.value = "";
            return;
        }
        checkArgument(isValidInstagram(handle), MESSAGE_CONSTRAINTS);
        this.value = handle;
    }

    /**
     * Returns true if a given string is a valid Instagram handle.
     *
     * @param test The string to test
     * @return true if valid, false otherwise
     */
    public static boolean isValidInstagram(String test) {
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
        if (!(other instanceof Instagram)) {
            return false;
        }

        Instagram otherInstagram = (Instagram) other;
        return (value == null ? otherInstagram.value == null : value.equals(otherInstagram.value));
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }
}
