package seedu.address.model.employee.predicatechecker;

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
        List<String> firstPredicateKeywordList = Collections.singletonList("alice");
        List<String> secondPredicateKeywordList = Arrays.asList("alice", "bob");

        EmailContainsKeywordsPredicate firstPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate = new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

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

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(
                Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("alice", "gmail"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").build()));

        // Only one matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("yahoo", "email"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("ALICE", "EMAIL"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("yahoo"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Keywords match name and department, but do not match email
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Alice", "Engineering"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice")
                .withDepartment("Engineering")
                .withEmail("bob@company.com")
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);

        String expected = EmailContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void test_emailContainsKeywordIgnoreCase_returnsTrue() {
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(List.of("ALICE@EXAMPLE.COM"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_emailContainsPartialKeyword_returnsTrue() {
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(List.of("example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

}
