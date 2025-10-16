package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.priority.Priority;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                "alexyeoh#1234", "linkedin.com/in/alexyeoh",
                new Address("Blk 30 Geylang Street 29, #06-40"), new Priority("0"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                "bernice#5678", "linkedin.com/in/berniceyu",
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Priority("0"),
                getTagSet("colleagues", "friends")),
            // Charlotte with no discord handle
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                "", "linkedin.com/in/charlotteoliveiro",
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Priority("0"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                "david#1121", "linkedin.com/in/davidli",
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Priority("0"),
                getTagSet("family")),
            // Irfan with no linkedin profile
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                "irfan#3141", "",
                new Address("Blk 47 Tampines Street 20, #17-35"), new Priority("0"),
                getTagSet("classmates")),
            // Roy with no discord handle and linkedin profile
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                "", "",
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Priority("0"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
