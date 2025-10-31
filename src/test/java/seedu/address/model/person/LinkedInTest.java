package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class LinkedInTest {

    @Test
    void constructor_nullOrEmpty_setsEmptyValue() {
        assertEquals("", new LinkedIn(null).value);
        assertEquals("", new LinkedIn("").value);
    }

    @Test
    void constructor_validValue_setsValue() {
        String validUrl = "https://www.linkedin.com/in/john-doe123";
        LinkedIn li = new LinkedIn(validUrl);
        assertEquals(validUrl, li.value);
    }

    @Test
    void constructor_invalidValue_throwsIllegalArgumentException() {
        String invalidUrl = "https://linkedin.com/profile/john"; // wrong path
        assertThrows(IllegalArgumentException.class, () -> new LinkedIn(invalidUrl));
    }

    @Test
    void isValidLinkedIn_specialCases() {
        assertTrue(LinkedIn.isValidLinkedIn(""));
        assertTrue(LinkedIn.isValidLinkedIn("-"));
    }

    @Test
    void isValidLinkedIn_validUrls() {
        // full url domain
        assertTrue(LinkedIn.isValidLinkedIn("https://www.linkedin.com/in/johndoe"));

        // http optional
        assertTrue(LinkedIn.isValidLinkedIn("http://linkedin.com/in/john-doe"));

        // without protocol
        assertTrue(LinkedIn.isValidLinkedIn("linkedin.com/in/john_doe"));

        // with www only
        assertTrue(LinkedIn.isValidLinkedIn("www.linkedin.com/in/john123"));

        // optional trailing slash
        assertTrue(LinkedIn.isValidLinkedIn("linkedin.com/in/john123/"));
    }

    @Test
    void isValidLinkedIn_invalidUrls() {
        // wrong path
        assertFalse(LinkedIn.isValidLinkedIn("https://www.linkedin.com/profile/johndoe"));

        // missing profile id
        assertFalse(LinkedIn.isValidLinkedIn("https://linkedin.com/in/"));

        // invalid char
        assertFalse(LinkedIn.isValidLinkedIn("linkedin.com/in/john$doe"));

        // missing /in/
        assertFalse(LinkedIn.isValidLinkedIn("linkedin.com/john-doe"));
    }

    @Test
    void equals_andHashCode_behaviour() {
        LinkedIn li1 = new LinkedIn("linkedin.com/in/john123");
        LinkedIn li2 = new LinkedIn("linkedin.com/in/john123");
        LinkedIn li3 = new LinkedIn("linkedin.com/in/jane123");

        // self
        assertEquals(li1, li1);
        // identical value
        assertEquals(li1, li2);
        assertEquals(li1.hashCode(), li2.hashCode());
        // different value
        assertNotEquals(li1, li3);
        // null
        assertNotEquals(li1, null);
        // other type
        assertNotEquals(li1, "string");
    }

    @Test
    void toString_returnsValue() {
        String url = "linkedin.com/in/johndoe";
        assertEquals(url, new LinkedIn(url).toString());
    }
}
