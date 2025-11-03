package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class DiscordTest {

    @Test
    void constructor_nullOrEmpty_setsEmptyValue() {
        assertEquals("", new Discord(null).value);
        assertEquals("", new Discord("").value);
    }

    @Test
    void constructor_validValue_setsValue() {
        String validHandle = "valid_name.123";
        Discord discord = new Discord(validHandle);
        assertEquals(validHandle, discord.value);
    }

    @Test
    void constructor_invalidValue_throwsIllegalArgumentException() {
        String invalidHandle = "..invalid"; // starts with period
        assertThrows(IllegalArgumentException.class, () -> new Discord(invalidHandle));
    }

    @Test
    void isValidDiscord_specialCases() {
        // empty allowed
        assertTrue(Discord.isValidDiscord(""));

        // special '-' allowed
        assertTrue(Discord.isValidDiscord("-"));
    }

    @Test
    void isValidDiscord_validHandles() {
        // simple
        assertTrue(Discord.isValidDiscord("abc"));

        // letters + digits
        assertTrue(Discord.isValidDiscord("abc123"));

        // letters + dot + underscore
        assertTrue(Discord.isValidDiscord("a.b_c"));
        assertTrue(Discord.isValidDiscord(".a._c"));
        assertTrue(Discord.isValidDiscord("_a._c"));
        assertTrue(Discord.isValidDiscord("a._c_"));
        assertTrue(Discord.isValidDiscord("a__c"));

        // max length
        assertTrue(Discord.isValidDiscord("a".repeat(32)));

        // min length
        assertTrue(Discord.isValidDiscord("ab"));
    }

    @Test
    void isValidDiscord_invalidHandles() {
        // too short
        assertFalse(Discord.isValidDiscord("a"));

        // too long
        assertFalse(Discord.isValidDiscord("a".repeat(33)));

        // consecutive dots
        assertFalse(Discord.isValidDiscord("ab..cd"));

        // uppercase not allowed
        assertFalse(Discord.isValidDiscord("Abc"));

        // invalid char
        assertFalse(Discord.isValidDiscord("a$b"));
    }

    @Test
    void equals_andHashCode_behaviour() {
        Discord d1 = new Discord("user.name");
        Discord d2 = new Discord("user.name");
        Discord d3 = new Discord("other_name");

        // self
        assertEquals(d1, d1);
        // identical value
        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
        // different value
        assertNotEquals(d1, d3);
        // null
        assertNotEquals(d1, null);
        // other type
        assertNotEquals(d1, "string");
    }

    @Test
    void toString_returnsValue() {
        String handle = "username";
        assertEquals(handle, new Discord(handle).toString());
    }
}
