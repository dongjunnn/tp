package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a YouTube channel URL for a Person.
 */
public class YouTube {

    /** Message to display if validation fails. */
    public static final String MESSAGE_CONSTRAINTS =
            "YouTube channel must be a valid URL or handle. Supported formats:\n"
                    + "1. New handle: (https://www.)youtube.com/@YourHandle "
                    + "-- starts with '@', 3–30 letters, digits, underscores, or hyphens allowed\n"
                    + "2. Channel ID: (https://www.)youtube.com/channel/UCXXXXXXXXXXXXXX "
                    + "-- 24 characters, starting with 'UC'\n"
                    + "3. Legacy custom URL: (https://www.)youtube.com/c/CustomName "
                    + "-- letters, digits, underscores, hyphens allowed\n"
                    + "4. Legacy username: (https://www.)youtube.com/user/Username "
                    + "-- letters, digits, underscores, hyphens allowed\n"
                    + "Optional http(s)://, www., and trailing '/' are allowed.";

    /** Regex for validating YouTube URLs. */
    private static final String VALIDATION_REGEX =
            "^(https?://)?(www\\.)?youtube\\.com/((@[-\\w\\.]{3,30})|" // new handle
                    + "(channel/UC[\\w-]{22})|" // channel ID
                    + "(c/[\\w-]+)|" // custom URL
                    + "(user/[\\w-]+))/?$"; // legacy username

    /** The YouTube channel URL. */
    public final String value;

    /**
     * Constructs a {@code YouTube} object.
     *
     * @param channelUrl The YouTube channel URL. Must be valid.
     * @throws IllegalArgumentException if channelUrl is invalid
     */
    public YouTube(String channelUrl) {
        if (channelUrl == null || channelUrl.isEmpty()) {
            this.value = "";
            return;
        }
        String error = getValidationError(channelUrl);
        checkArgument(error == null, error);
        this.value = channelUrl.trim();
    }

    /**
     * Returns null if input is valid; otherwise returns a specific error message.
     */
    public static String getValidationError(String input) {
        input = input.trim();

        // Allow "-" for clearing the field
        if (input.equals("-") || input.isEmpty()) {
            return null;
        }

        // Must be YouTube domain
        if (!input.matches("^(https?://)?(www\\.)?youtube\\.com/.*")) {
            return "URL must be a valid YouTube link starting with 'youtube.com'.";
        }

        // Extract path after domain and remove trailing slash
        String path = input.replaceFirst("^(https?://)?(www\\.)?youtube\\.com/", "")
                .replaceAll("/$", "");

        // Handle format: youtube.com/@handle
        if (path.startsWith("@")) {
            String handle = path.substring(1); // remove '@'
            if (handle.length() < 3 || handle.length() > 30) {
                return "Handle must be 3–30 characters long.";
            }
            if (!handle.matches("[-\\w]+")) {
                return "Handle can only contain letters, digits, underscores, or hyphens.";
            }
            return null; // valid handle URL
        }

        // Channel ID format: youtube.com/channel/UCxxxxxxxxxxxxxxxxxxxxxx
        if (path.startsWith("channel/")) {
            String id = path.substring("channel/".length());
            if (!id.startsWith("UC") || id.length() != 24) {
                return "Channel ID must start with 'UC' and be 24 characters long.";
            }
            if (!id.substring(2).matches("[\\w-]{22}")) {
                return "Channel ID can only contain letters, digits, underscores, or hyphens after 'UC'.";
            }
            return null; // valid channel ID
        }

        // Legacy custom URL: youtube.com/c/...
        if (path.startsWith("c/")) {
            String name = path.substring("c/".length());
            if (name.isEmpty()) {
                return "Custom URL must not be empty.";
            }
            if (!name.matches("[-\\w]+")) {
                return "Custom URL can only contain letters, digits, underscores, or hyphens.";
            }
            return null;
        }

        // Legacy username: youtube.com/user/...
        if (path.startsWith("user/")) {
            String username = path.substring("user/".length());
            if (username.isEmpty()) {
                return "Username must not be empty.";
            }
            if (!username.matches("[-\\w]+")) {
                return "Username can only contain letters, digits, underscores, or hyphens.";
            }
            return null;
        }

        return "Unrecognized YouTube URL format. Must be one of:\n"
                + "- youtube.com/@handle\n"
                + "- youtube.com/channel/UCxxxxxxxxxxxxxxxxxxxxxx\n"
                + "- youtube.com/c/customName\n"
                + "- youtube.com/user/username";
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof YouTube)) {
            return false;
        }

        YouTube otherYouTube = (YouTube) other;
        return value.equals(otherYouTube.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
