package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Priority((String) null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidPriority = "";
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // null priority
        assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // invalid priority
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("high")); // lower case priority
        assertFalse(Priority.isValidPriority("HiGH")); // alphabets with alternating cases
        assertFalse(Priority.isValidPriority("10238")); // numbers only

        // valid priority
        assertTrue(Priority.isValidPriority("HIGH"));
        assertTrue(Priority.isValidPriority("LOW"));
        assertTrue(Priority.isValidPriority("MEDIUM"));
    }

    @Test
    public void equals() {
        Priority priority = new Priority("LOW");

        // same values -> returns true
        assertTrue(priority.equals(new Priority("LOW")));

        // same object -> returns true
        assertTrue(priority.equals(priority));

        // null -> returns false
        assertFalse(priority.equals(null));

        // different values -> returns false
        assertFalse(priority.equals(new Priority("MEDIUM")));
    }
}
