package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // TODO v1.3: Consider making a Socials class to encapsulate all social/contact fields
    private final String discordHandle;
    private final String linkedInProfile;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    // TODO v1.3: Edit constructor that allows setting discordHandle and linkedInProfile on creation
    public Person(Name name, Phone phone, Email email, String discordHandle,
                  String linkedInProfile, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.discordHandle = discordHandle;
        this.linkedInProfile = linkedInProfile;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public String getDiscordHandle() {
        return discordHandle;
    }

    public String getLinkedInProfile() {
        return linkedInProfile;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    // Do not modify this method yet for discord and LinkedIn, as it is used by PersonCardTest
    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    // Do not modify this method yet for discord and LinkedIn, as it is used by PersonCardTest
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        // TODO v1.3: Consider formatting socials differently for UI display
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("discordHandle", discordHandle)
                .add("linkedInProfile", linkedInProfile)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

    // TODO v1.3: Add validation for Discord and LinkedIn inputs
    // e.g., Discord must start with '@', LinkedIn should be a valid profile URL
    // TODO v1.3: Update AddCommand and EditCommand to accept Discord and LinkedIn parameters
    // TODO v1.3: Update storage (JsonAdaptedPerson) to save and load the new fields
    // TODO v1.3: Update PersonCard UI to display Discord and LinkedIn if present
}
