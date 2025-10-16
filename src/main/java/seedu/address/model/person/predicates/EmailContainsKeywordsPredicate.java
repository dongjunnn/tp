package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        String fullEmail = person.getEmail().value.toLowerCase();
        String[] emailParts = fullEmail.split("@");
        String localPart = emailParts[0];
        String domain = emailParts.length > 1 ? emailParts[1] : "";

        return keywords.stream()
                .anyMatch(keyword -> {
                    String lowerKeyword = keyword.toLowerCase();

                    // Only process keywords that contain @
                    if (!lowerKeyword.contains("@")) {
                        return false;
                    }

                    int atIndex = lowerKeyword.indexOf("@");

                    // Pattern 1: local@ (ends with @)
                    if (lowerKeyword.endsWith("@")) {
                        String keywordLocal = lowerKeyword.substring(0, lowerKeyword.length() - 1);
                        return localPart.equals(keywordLocal);
                    }

                    // Pattern 2: @domain.extension (starts with @, has dot after @)
                    if (atIndex == 0 && lowerKeyword.indexOf(".", atIndex) > atIndex) {
                        String keywordDomain = lowerKeyword.substring(1); // Remove @
                        return domain.equals(keywordDomain);
                    }

                    // Pattern 3: Full email match
                    return fullEmail.equals(lowerKeyword);
                });
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailContainsKeywordsPredicate)) {
            return false;
        }

        EmailContainsKeywordsPredicate otherEmailContainsKeywordsPredicate = (EmailContainsKeywordsPredicate) other;
        return keywords.equals(otherEmailContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
