package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;


class SocialsTest {

    @Test
    void constructor_nullInitializesEmptySocials() {
        Socials socials = new Socials(null, null, null, null);

        assertEquals("", socials.getDiscord().value);
        assertEquals("", socials.getLinkedIn().value);
        assertEquals("", socials.getInstagram().value);
        assertEquals("", socials.getYouTube().value);
    }

    @Test
    void constructor_validSocials_storesValues() {
        Discord discord = new Discord("discorduser_72");
        LinkedIn linkedIn = new LinkedIn("linkedin.com/in/john");
        Instagram instagram = new Instagram("@valid_handle");
        YouTube youTube = new YouTube("youtube.com/@channel");

        Socials socials = new Socials(discord, linkedIn, instagram, youTube);

        assertEquals(discord, socials.getDiscord());
        assertEquals(linkedIn, socials.getLinkedIn());
        assertEquals(instagram, socials.getInstagram());
        assertEquals(youTube, socials.getYouTube());
    }

    @Test
    void getAllForDisplay_onlyIncludesNonEmptySocialsInOrder() {
        Socials socials = new Socials(
                new Discord("discorduser_72"),
                new LinkedIn(""),
                new Instagram("@valid_handle"),
                new YouTube("")
        );

        List<String> display = socials.getAllForDisplay();

        assertEquals(2, display.size());
        assertEquals("Discord: discorduser_72", display.get(0));
        assertEquals("Instagram: @valid_handle", display.get(1));

        // list should be unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> display.add("test"));
    }

    @Test
    void getAllForDisplay_allEmpty_returnsEmptyList() {
        Socials socials = new Socials(new Discord(""), new LinkedIn(""),
                new Instagram(""), new YouTube(""));
        assertTrue(socials.getAllForDisplay().isEmpty());
    }

    @Test
    void toString_returnsCommaSeparatedNonEmptySocials() {
        Socials socials = new Socials(
                new Discord("discorduser_72"),
                new LinkedIn("linkedin.com/in/john"),
                new Instagram("@valid_handle"),
                new YouTube("youtube.com/@channel")
        );

        String expected = "Discord: discorduser_72, LinkedIn: linkedin.com/in/john, "
                + "Instagram: @valid_handle, YouTube: youtube.com/@channel";
        assertEquals(expected, socials.toString());
    }

    @Test
    void equals_andHashCode_behaviour() {
        Socials s1 = new Socials(new Discord("discorduser_72"), new LinkedIn("linkedin.com/in/john"),
                new Instagram("@valid_handle"), new YouTube("youtube.com/@channel"));
        Socials s2 = new Socials(new Discord("discorduser_72"), new LinkedIn("linkedin.com/in/john"),
                new Instagram("@valid_handle"), new YouTube("youtube.com/@channel"));
        Socials s3 = new Socials(new Discord("johndoe.21"), new LinkedIn("linkedin.com/in/john"),
                new Instagram("@valid_handle"), new YouTube("youtube.com/@channel"));

        // self
        assertEquals(s1, s1);
        // identical values
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        // different values
        assertNotEquals(s1, s3);
        // null
        assertNotEquals(s1, null);
        // other type
        assertNotEquals(s1, "string");
    }
}
