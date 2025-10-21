package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a collection of a Person's social contacts.
 * This class is immutable and guarantees that each social field is non-null,
 * though some may contain empty values if not provided.
 */
public class Socials {

    private final Discord discord;
    private final LinkedIn linkedIn;
    private final Instagram instagram;
    private final YouTube youTube;

    /**
     * Constructs a {@code Socials} object with the given social handles.
     * Any of the socials can be null if not provided.
     *
     * @param discord the Discord handle (can be null)
     * @param linkedIn the LinkedIn profile (can be null)
     * @param instagram the Instagram handle (can be null)
     * @param youTube the YouTube channel URL (can be null)
     */
    public Socials(Discord discord, LinkedIn linkedIn,
                   Instagram instagram, YouTube youTube) {
        this.discord = discord != null ? discord : new Discord("");
        this.linkedIn = linkedIn != null ? linkedIn : new LinkedIn("");
        this.instagram = instagram != null ? instagram : new Instagram("");
        this.youTube = youTube != null ? youTube : new YouTube("");
    }

    /**
     * Returns the Discord handle. Returns an empty Discord if not set.
     */
    public Discord getDiscord() {
        return discord;
    }

    /**
     * Returns the LinkedIn profile. Returns an empty LinkedIn if not set.
     */
    public LinkedIn getLinkedIn() {
        return linkedIn;
    }

    /**
     * Returns the Instagram handle. Returns an empty Instagram if not set.
     */
    public Instagram getInstagram() {
        return instagram;
    }

    /**
     * Returns the YouTube channel URL. Returns an empty YouTube if not set.
     */
    public YouTube getYouTube() {
        return youTube;
    }

    /**
     * Returns an immutable list of the person's non-empty social handles,
     * formatted for display in a consistent order: Discord → LinkedIn → Instagram → YouTube.
     * Each social handle is represented as a formatted string (e.g., {@code "Discord: @username"}).
     * Handles with empty values are excluded from the returned list.
     *
     * @return an unmodifiable list of formatted social handle strings, excluding empty entries
     */
    public List<String> getAllForDisplay() {
        List<String> list = new ArrayList<>();
        if (!discord.value.isEmpty()) {
            list.add("Discord: " + discord);
        }
        if (!linkedIn.value.isEmpty()) {
            list.add("LinkedIn: " + linkedIn);
        }
        if (!instagram.value.isEmpty()) {
            list.add("Instagram: " + instagram);
        }
        if (!youTube.value.isEmpty()) {
            list.add("YouTube: " + youTube);
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public String toString() {
        return String.join(", ", getAllForDisplay());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Socials)) {
            return false;
        }
        Socials s = (Socials) other;
        return Objects.equals(discord.value, s.discord.value)
                && Objects.equals(linkedIn.value, s.linkedIn.value)
                && Objects.equals(instagram.value, s.instagram.value)
                && Objects.equals(youTube.value, s.youTube.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                discord != null ? discord.value : "",
                linkedIn != null ? linkedIn.value : "",
                instagram != null ? instagram.value : "",
                youTube != null ? youTube.value : ""
        );
    }
}
