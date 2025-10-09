package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s name or phone contains any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final NameContainsKeywordsPredicate namePredicate;
    private final PhoneContainsKeywordsPredicate phonePredicate;

    /**
     * Constructs a {@code PersonContainsKeywordsPredicate}.
     *
     * @param keywords A list of keywords to match against a person's name or phone.
     */
    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.namePredicate = new NameContainsKeywordsPredicate(keywords);
        this.phonePredicate = new PhoneContainsKeywordsPredicate(keywords);
    }

    @Override
    public boolean test(Person person) {
        // Returns true if EITHER name OR phone matches
        return namePredicate.test(person) || phonePredicate.test(person);
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
                && phonePredicate.equals(otherPredicate.phonePredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("phonePredicate", phonePredicate)
                .toString();
    }

}

