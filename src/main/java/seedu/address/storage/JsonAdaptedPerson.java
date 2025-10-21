package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Discord;
import seedu.address.model.person.Email;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.LinkedIn;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Socials;
import seedu.address.model.person.YouTube;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String discordHandle;
    private final String linkedInProfile;
    private final String instagramHandle;
    private final String youTubeChannel;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("socials") JsonAdaptedSocials socials, // <- nested object
            @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.discordHandle = (socials != null && socials.getDiscordHandle() != null)
                ? socials.getDiscordHandle()
                : "";
        this.linkedInProfile = (socials != null && socials.getLinkedInProfile() != null)
                ? socials.getLinkedInProfile()
                : "";
        this.instagramHandle = (socials != null && socials.getInstagramHandle() != null)
                ? socials.getInstagramHandle()
                : "";
        this.youTubeChannel = (socials != null && socials.getYouTubeChannel() != null)
                ? socials.getYouTubeChannel()
                : "";
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        Socials socials = source.getSocials();
        discordHandle = socials.getDiscord() != null ? socials.getDiscord().value : "";
        linkedInProfile = socials.getLinkedIn() != null ? socials.getLinkedIn().value : "";
        instagramHandle = socials.getInstagram() != null ? socials.getInstagram().value : "";
        youTubeChannel = socials.getYouTube() != null ? socials.getYouTube().value : "";
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Discord modelDiscord;
        if (discordHandle == null || discordHandle.isEmpty()) {
            modelDiscord = null; // no Discord provided
        } else {
            if (!Discord.isValidDiscord(discordHandle)) {
                throw new IllegalValueException(Discord.MESSAGE_CONSTRAINTS);
            }
            modelDiscord = new Discord(discordHandle);
        }

        final LinkedIn modelLinkedIn;
        if (linkedInProfile == null || linkedInProfile.isEmpty()) {
            modelLinkedIn = null; // no LinkedIn provided
        } else {
            if (!LinkedIn.isValidLinkedIn(linkedInProfile)) {
                throw new IllegalValueException(LinkedIn.MESSAGE_CONSTRAINTS);
            }
            modelLinkedIn = new LinkedIn(linkedInProfile);
        }

        final Instagram modelInstagram;
        if (instagramHandle == null || instagramHandle.isEmpty()) {
            modelInstagram = null; // no Instagram provided
        } else {
            if (!Instagram.isValidInstagram(instagramHandle)) {
                throw new IllegalValueException(Instagram.MESSAGE_CONSTRAINTS);
            }
            modelInstagram = new Instagram(instagramHandle);
        }

        final YouTube modelYouTube;
        if (youTubeChannel == null || youTubeChannel.isEmpty()) {
            modelYouTube = null; // no YouTube provided
        } else {
            if (!YouTube.isValidYouTube(youTubeChannel)) {
                throw new IllegalValueException(YouTube.MESSAGE_CONSTRAINTS);
            }
            modelYouTube = new YouTube(youTubeChannel);
        }

        final Socials modelSocials;
        if ((discordHandle == null || discordHandle.isEmpty())
                && (linkedInProfile == null || linkedInProfile.isEmpty())
                && (instagramHandle == null || instagramHandle.isEmpty())
                && (youTubeChannel == null || youTubeChannel.isEmpty())) {
            modelSocials = new Socials(null, null, null, null);
        } else {
            modelSocials = new Socials(modelDiscord, modelLinkedIn, modelInstagram, modelYouTube);
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelPhone, modelEmail, modelSocials, modelAddress, modelTags);
    }

}
