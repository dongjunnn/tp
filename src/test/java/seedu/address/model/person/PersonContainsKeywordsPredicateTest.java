package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} or {@code Phone} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Alice");
        List<String> secondPredicateKeywordList = Arrays.asList("Alice", "Bob");

        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword matching name
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords, one matches name
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("aLIce"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword matching phone
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("94351253"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("94351253").build()));

        // Multiple keywords, one matches phone
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("94351253", "87654321"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("94351253").build()));
    }

    @Test
    public void test_nameOrPhoneContainsKeywords_returnsTrue() {
        // Keyword matches name
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "94351253"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("12345678").build()));

        // Keyword matches phone
        assertTrue(predicate.test(new PersonBuilder().withName("Carol").withPhone("94351253").build()));

        // Keyword matches both
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("94351253").build()));
    }

    @Test
    public void test_personDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("94351253").build()));

        // Keywords match address and email, but not name or phone
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("94351253")
                .withAddress("Main Street").withEmail("alice@email.com").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("Alice", "94351253");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(keywords);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName()
                + "{namePredicate=" + new NameContainsKeywordsPredicate(keywords)
                + ", phonePredicate=" + new PhoneContainsKeywordsPredicate(keywords) + "}";
        assertEquals(expected, predicate.toString());
    }
}
