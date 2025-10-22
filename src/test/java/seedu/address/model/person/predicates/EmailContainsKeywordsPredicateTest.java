package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("alice@");
        List<String> secondPredicateKeywordList = Arrays.asList("alice@", "@example.com");

        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailMatchesLocalPartWithAt_returnsTrue() {
        // Local part with @ matches
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("alice@"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Different local part
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("bob@"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("bob@gmail.com").build()));
    }

    @Test
    public void test_emailMatchesDomainWithAt_returnsTrue() {
        // Domain with @ matches
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Complex domain
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("@company.co.uk"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("bob@company.co.uk").build()));
    }

    @Test
    public void test_emailMatchesFullEmail_returnsTrue() {
        // Full email match
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("alice@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Case insensitive
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("ALICE@EXAMPLE.COM"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_emailDoesNotMatchWithoutAt_returnsFalse() {
        // Keywords without @ should not match
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("alice"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("example.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_emailDoesNotMatchIncompletePattern_returnsFalse() {
        // Just @ should not match
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("@"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Domain without extension should not match
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("@example"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Wrong local part
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("bob@"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Wrong domain
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("@gmail.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Wrong full email
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("bob@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_multipleKeywords_returnsTrue() {
        // Multiple keywords, one matches
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Arrays.asList("bob@", "alice@"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("@gmail.com", "@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_zeroKeywords_returnsFalse() {
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("alice@", "@example.com");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);

        String expected = EmailContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
