package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Discord;
import seedu.address.model.person.Email;
import seedu.address.model.person.Instagram;
import seedu.address.model.person.LinkedIn;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Socials;
import seedu.address.model.person.YouTube;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_DISCORD = "amy#1234";
    public static final String DEFAULT_LINKEDIN = "linkedin.com/in/amy";
    public static final String DEFAULT_INSTAGRAM = "@amy_bstyle";
    public static final String DEFAULT_YOUTUBE = "youtube.com/@amybee";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_PRIORITY = "MEDIUM";

    private Name name;
    private Phone phone;
    private Email email;
    private Socials socials;
    private Address address;
    private Priority priority;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        socials = new Socials(
                new Discord(DEFAULT_DISCORD),
                new LinkedIn(DEFAULT_LINKEDIN),
                new Instagram(DEFAULT_INSTAGRAM),
                new YouTube(DEFAULT_YOUTUBE));
        address = new Address(DEFAULT_ADDRESS);
        priority = new Priority(DEFAULT_PRIORITY);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        socials = personToCopy.getSocials();
        address = personToCopy.getAddress();
        priority = personToCopy.getPriority();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the
     * {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Person} that we are building.
     */
    public PersonBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    /**
     * Sets the {@code Socials} of the {@code Person} that we are building.
     * All parameters can be null.
     */
    public PersonBuilder withSocials(String discordHandle, String linkedInProfile,
            String instagramHandle, String youTubeChannel) {
        this.socials = new Socials(
                discordHandle == null ? null : new Discord(discordHandle),
                linkedInProfile == null ? null : new LinkedIn(linkedInProfile),
                instagramHandle == null ? null : new Instagram(instagramHandle),
                youTubeChannel == null ? null : new YouTube(youTubeChannel));
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, socials, address, priority, tags);
    }

}
