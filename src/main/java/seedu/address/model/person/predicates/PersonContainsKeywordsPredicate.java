package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s name or phone contains any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final NameContainsKeywordsPredicate namePredicate;
    private final PhoneContainsKeywordsPredicate phonePredicate;
    private final EmailContainsKeywordsPredicate emailPredicate;

    /**
     * Constructs a {@code PersonContainsKeywordsPredicate}.
     *
     * @param keywords A list of keywords to match against a person's name or phone.
     */
    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.namePredicate = new NameContainsKeywordsPredicate(keywords);
        this.phonePredicate = new PhoneContainsKeywordsPredicate(keywords);
        this.emailPredicate = new EmailContainsKeywordsPredicate(keywords);
    }

    @Override
    public boolean test(Person person) {
        // Returns true if EITHER name OR phone matches
        return namePredicate.test(person)
                || phonePredicate.test(person)
                || emailPredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return namePredicate.equals(otherPredicate.namePredicate)
                && phonePredicate.equals(otherPredicate.phonePredicate)
                && emailPredicate.equals(otherPredicate.emailPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("phonePredicate", phonePredicate)
                .add("emailPredicate", emailPredicate)
                .toString();
    }

}

