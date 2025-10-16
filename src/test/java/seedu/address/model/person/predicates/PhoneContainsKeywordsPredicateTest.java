package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("91234567");
        List<String> secondPredicateKeywordList = Arrays.asList("91234567", "81234567");

        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different phone keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword - exact match
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("94351253"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("94351253").build()));

        // Multiple keywords - one matches
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("94351253", "87654321"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("94351253").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {

        // Zero keywords
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("94351253").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("87654321"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("94351253").build()));

        // Partial match (should not match since we use equals, not contains)
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("9435"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("94351253").build()));

        // Keywords match name, email and address, but does not match phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("Alice", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("94351253", "87654321");
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(keywords);

        String expected = PhoneContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

}
