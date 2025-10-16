package seedu.address.model.priority;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Priority in the address book.
 * Guarantees: immutable; value is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_CONSTRAINTS = "Priority should be a non-negative integer.";

    public final String value;

    /**
     * Constructs a {@code Priority}.
     *
     * @param value A valid priority.
     */
    public Priority(String value) {
        checkArgument(isValidPriority(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid priority.
     */

    public static boolean isValidPriority(String test) {
        if (test == null) {
            return false;
        }
        try {
            return Integer.parseInt(test) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.model.priority.Priority)) {
            return false;
        }

        seedu.address.model.priority.Priority otherPriority = (seedu.address.model.priority.Priority) other;
        return this.value == otherPriority.value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.valueOf(value);
    }

}

