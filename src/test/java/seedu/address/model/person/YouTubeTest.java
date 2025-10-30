package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class YouTubeTest {

    @Test
    void constructor_nullOrEmpty_setsEmptyValue() {
        assertEquals("", new YouTube(null).value);
        assertEquals("", new YouTube("").value);
    }

    @Test
    void constructor_validValue_setsValue() {
        String validUrl = "youtube.com/@validHandle";
        YouTube yt = new YouTube(validUrl);
        assertEquals(validUrl, yt.value);
    }

    @Test
    void constructor_invalidValue_throwsIllegalArgumentException() {
        String invalidUrl = "https://notyoutube.com/abc";
        assertThrows(IllegalArgumentException.class, () -> new YouTube(invalidUrl));
    }

    @Test
    void getValidationError_specialCases_returnsNull() {
        assertNull(YouTube.getValidationError("-"));
        assertNull(YouTube.getValidationError(""));
    }

    @Test
    void getValidationError_nonYouTubeDomain_returnsError() {
        String input = "https://notyoutube.com/abc";
        assertEquals(
                "URL must be a valid YouTube link starting with '(http(s)://(www.))youtube.com/'.",
                YouTube.getValidationError(input)
        );
    }

    // Handle URL tests
    @Test
    void getValidationError_validHandleUrls() {
        assertNull(YouTube.getValidationError("youtube.com/@myhandleisvalid"));
        assertNull(YouTube.getValidationError("www.youtube.com/@myhandleisvalid"));
        assertNull(YouTube.getValidationError("https://www.youtube.com/@myhandleisvalid"));
    }

    @Test
    void getValidationError_invalidHandleUrls() {
        assertEquals("Handle must be 3–30 characters long.",
                YouTube.getValidationError("youtube.com/@ab")); // too short
        assertEquals("Handle must be 3–30 characters long.",
                YouTube.getValidationError("youtube.com/@" + "a".repeat(31))); // too long
        assertEquals("Handle can only contain letters, digits, underscores, hyphens, or periods, "
                        + "and must not start or end with an underscore or hyphen.",
                YouTube.getValidationError("youtube.com/@abcds$")); // invalid chars
        assertEquals("Handle can only contain letters, digits, underscores, hyphens, or periods, "
                        + "and must not start or end with an underscore or hyphen.",
                YouTube.getValidationError("youtube.com/@abcds-")); // ends with hyphen
        assertEquals("Handle can only contain letters, digits, underscores, hyphens, or periods, "
                        + "and must not start or end with an underscore or hyphen.",
                YouTube.getValidationError("youtube.com/@-abcds")); // starts with hyphen
        assertEquals("Handle can only contain letters, digits, underscores, hyphens, or periods, "
                        + "and must not start or end with an underscore or hyphen.",
                YouTube.getValidationError("youtube.com/@abcds_")); // ends with underscore
        assertEquals("Handle can only contain letters, digits, underscores, hyphens, or periods, "
                        + "and must not start or end with an underscore or hyphen.",
                YouTube.getValidationError("youtube.com/@_abcds")); // starts with underscore
    }

    // Channel ID tests
    @Test
    void getValidationError_validChannelIds() {
        assertNull(YouTube.getValidationError("youtube.com/channel/UCabcdefghijklmnopqrstuv"));
        assertNull(YouTube.getValidationError("www.youtube.com/channel/UCabcdefghijklmnopqrstuv"));
        assertNull(YouTube.getValidationError("https://www.youtube.com/channel/UCabcdefghijklmnopqrstuv"));
    }

    @Test
    void getValidationError_invalidChannelIds() {
        assertEquals("Channel ID must start with 'UC' and be 24 characters long.",
                YouTube.getValidationError("youtube.com/channel/ABabcdefghijklmnopqrstuv")); // wrong start
        assertEquals("Channel ID must start with 'UC' and be 24 characters long.",
                YouTube.getValidationError("youtube.com/channel/UCabcd")); // too short
        assertEquals("Channel ID must start with 'UC' and be 24 characters long.",
                YouTube.getValidationError("youtube.com/channel/UCahsicndhjieksopfjidnskeiw")); // too long
        assertEquals("Channel ID can only contain letters, digits, underscores, or hyphens after 'UC'.",
                YouTube.getValidationError("youtube.com/channel/UCabc!efghijklmnopqrstuv")); // invalid chars
    }

    // Custom URL tests
    @Test
    void getValidationError_validCustomUrls() {
        assertNull(YouTube.getValidationError("youtube.com/c/My_Custom-Name"));
        assertNull(YouTube.getValidationError("www.youtube.com/c/My_Custom-Name"));
        assertNull(YouTube.getValidationError("https://www.youtube.com/c/My_Custom-Name"));
    }

    @Test
    void getValidationError_invalidCustomUrls() {
        assertEquals(YouTube.MESSAGE_CONSTRAINTS,
                YouTube.getValidationError("youtube.com/c/")); // empty
        assertEquals("Custom URL can only contain letters, digits, underscores, or hyphens, "
                        + "and must not start or end with an underscore or hyphen.",
                YouTube.getValidationError("youtube.com/c/Name$")); // invalid chars
        assertEquals("Custom URL can only contain letters, digits, underscores, or hyphens, "
                        + "and must not start or end with an underscore or hyphen.",
                YouTube.getValidationError("youtube.com/c/Name_")); // ends with underscore
        assertEquals("Custom URL can only contain letters, digits, underscores, or hyphens, "
                        + "and must not start or end with an underscore or hyphen.",
                YouTube.getValidationError("youtube.com/c/_Name")); // starts with underscore
        assertEquals("Custom URL can only contain letters, digits, underscores, or hyphens, "
                        + "and must not start or end with an underscore or hyphen.",
                YouTube.getValidationError("youtube.com/c/Name-")); // ends with hyphen
        assertEquals("Custom URL can only contain letters, digits, underscores, or hyphens, "
                        + "and must not start or end with an underscore or hyphen.",
                YouTube.getValidationError("youtube.com/c/-Name")); // starts with hyphen
        assertEquals("Custom URL can only contain letters, digits, underscores, or hyphens, "
                        + "and must not start or end with an underscore or hyphen.",
                YouTube.getValidationError("youtube.com/c/My.Name")); // contains period
    }

    // Legacy username tests
    @Test
    void getValidationError_validUsernames() {
        assertNull(YouTube.getValidationError("youtube.com/user/MyUser_123"));
        assertNull(YouTube.getValidationError("www.youtube.com/user/MyUser_123"));
        assertNull(YouTube.getValidationError("https://www.youtube.com/user/MyUser_123"));
    }

    @Test
    void getValidationError_invalidUsernames() {
        assertEquals(YouTube.MESSAGE_CONSTRAINTS,
                YouTube.getValidationError("youtube.com/user/")); // empty
        assertEquals("Username can only contain letters, digits, underscores, or hyphens.",
                YouTube.getValidationError("youtube.com/user/User$123")); // invalid chars
    }

    // Unsupported path
    @Test
    void getValidationError_unsupportedPath_returnsConstraintMessage() {
        String input = "youtube.com/other/path";
        assertEquals(YouTube.MESSAGE_CONSTRAINTS, YouTube.getValidationError(input));
    }

    @Test
    void equals_andHashCode_behaviour() {
        YouTube yt1 = new YouTube("youtube.com/@handle1");
        YouTube yt2 = new YouTube("youtube.com/@handle1");
        YouTube yt3 = new YouTube("youtube.com/@handle2");

        // self
        assertEquals(yt1, yt1);
        // identical value
        assertEquals(yt1, yt2);
        assertEquals(yt1.hashCode(), yt2.hashCode());
        // different value
        assertNotEquals(yt1, yt3);
        // null
        assertNotEquals(yt1, null);
        // other type
        assertNotEquals(yt1, "string");
    }

    @Test
    void toString_returnsValue() {
        String url = "youtube.com/@handle";
        assertEquals(url, new YouTube(url).toString());
    }
}
