package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Jackson-friendly version of Socials.
 */
class JsonAdaptedSocials {
    private final String discordHandle;
    private final String linkedInProfile;
    private final String instagramHandle;
    private final String youTubeChannel;

    @JsonCreator
    public JsonAdaptedSocials(
            @JsonProperty("discordHandle") String discordHandle,
            @JsonProperty("linkedInProfile") String linkedInProfile,
            @JsonProperty("instagramHandle") String instagramHandle,
            @JsonProperty("youTubeChannel") String youTubeChannel) {
        this.discordHandle = discordHandle;
        this.linkedInProfile = linkedInProfile;
        this.instagramHandle = instagramHandle;
        this.youTubeChannel = youTubeChannel;
    }

    public String getDiscordHandle() {
        return discordHandle;
    }
    public String getLinkedInProfile() {
        return linkedInProfile;
    }
    public String getInstagramHandle() {
        return instagramHandle;
    }
    public String getYouTubeChannel() {
        return youTubeChannel;
    }
}
