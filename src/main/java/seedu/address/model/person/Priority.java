package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Priority in the address book.
 * Guarantees: immutable; value is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_CONSTRAINTS =
            "Priority must be LOW/MEDIUM/HIGH";

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
     * Constructs a {@code Priority} from the Priority enum.
     *
     * @param e A valid priority enum.
     */
    public Priority(seedu.address.model.priority.Priority e) {
        checkArgument(isValidPriority(e.name()), MESSAGE_CONSTRAINTS);
        this.value = e.name();
    }
    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        return test.equals("LOW") || test.equals("MEDIUM") || test.equals("HIGH");
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.model.person.Priority)) {
            return false;
        }

        seedu.address.model.person.Priority otherPriority = (seedu.address.model.person.Priority) other;
        return this.value.equals(otherPriority.value);
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
