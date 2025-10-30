package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class InstagramTest {

    @Test
    void constructor_nullOrEmpty_setsEmptyValue() {
        assertEquals("", new Instagram(null).value);
        assertEquals("", new Instagram("").value);
    }

    @Test
    void constructor_validValue_setsValue() {
        String validHandle = "@user.name123";
        Instagram ig = new Instagram(validHandle);
        assertEquals(validHandle, ig.value);
    }

    @Test
    void constructor_invalidValue_throwsIllegalArgumentException() {
        String invalidHandle = "user.name"; // missing '@'
        assertThrows(IllegalArgumentException.class, () -> new Instagram(invalidHandle));
    }

    @Test
    void isValidInstagram_specialCases() {
        assertTrue(Instagram.isValidInstagram(""));
        assertTrue(Instagram.isValidInstagram("-"));
    }

    @Test
    void isValidInstagram_validHandles() {
        // min length
        assertTrue(Instagram.isValidInstagram("@a"));

        // letters only
        assertTrue(Instagram.isValidInstagram("@username"));

        // period allowed
        assertTrue(Instagram.isValidInstagram("@user.name"));

        // underscore + digits
        assertTrue(Instagram.isValidInstagram("@user_name123"));

        // max length
        assertTrue(Instagram.isValidInstagram("@" + "a".repeat(30)));
    }

    @Test
    void isValidInstagram_invalidHandles() {
        // missing '@'
        assertFalse(Instagram.isValidInstagram("username"));

        // too short
        assertFalse(Instagram.isValidInstagram("@"));

        // too long
        assertFalse(Instagram.isValidInstagram("@" + "a".repeat(31)));

        // starts with period
        assertFalse(Instagram.isValidInstagram("@.username"));

        // ends with period
        assertFalse(Instagram.isValidInstagram("@username."));

        // consecutive periods
        assertFalse(Instagram.isValidInstagram("@user..name"));

        // invalid char
        assertFalse(Instagram.isValidInstagram("@user$name"));
    }

    @Test
    void equals_andHashCode_behaviour() {
        Instagram ig1 = new Instagram("@username");
        Instagram ig2 = new Instagram("@username");
        Instagram ig3 = new Instagram("@othername");

        // self
        assertEquals(ig1, ig1);
        // identical value
        assertEquals(ig1, ig2);
        assertEquals(ig1.hashCode(), ig2.hashCode());
        // different value
        assertNotEquals(ig1, ig3);
        // null
        assertNotEquals(ig1, null);
        // other type
        assertNotEquals(ig1, "string");
    }

    @Test
    void toString_returnsValue() {
        String handle = "@myhandle";
        assertEquals(handle, new Instagram(handle).toString());
    }
}
