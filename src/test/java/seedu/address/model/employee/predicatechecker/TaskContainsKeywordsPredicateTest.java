package seedu.address.model.employee.predicatechecker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.employee.Task;
import seedu.address.model.employee.TaskListStorage;
import seedu.address.testutil.PersonBuilder;

public class TaskContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("report");
        List<String> secondPredicateKeywordList = Arrays.asList("report", "review");

        TaskContainsKeywordsPredicate firstPredicate = new TaskContainsKeywordsPredicate(firstPredicateKeywordList);
        TaskContainsKeywordsPredicate secondPredicate = new TaskContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TaskContainsKeywordsPredicate firstPredicateCopy =
                new TaskContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_taskContainsKeywords_returnsTrue() {
        TaskListStorage taskListStorage = new TaskListStorage(new ArrayList<>(Arrays.asList(
                new Task("Submit report", "Finish finance report", 1),
                new Task("Client meeting", "Prepare slides", 2)
        )));

        // One keyword
        TaskContainsKeywordsPredicate predicate =
                new TaskContainsKeywordsPredicate(Collections.singletonList("report"));
        assertTrue(predicate.test(new PersonBuilder()
                .withTaskListStorage(taskListStorage)
                .build()));

        // Multiple keywords
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("report", "meeting"));
        assertTrue(predicate.test(new PersonBuilder()
                .withTaskListStorage(taskListStorage)
                .build()));

        // Only one matching keyword
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("audit", "meeting"));
        assertTrue(predicate.test(new PersonBuilder()
                .withTaskListStorage(taskListStorage)
                .build()));

        // Mixed-case keywords
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("REPORT", "MEET"));
        assertTrue(predicate.test(new PersonBuilder()
                .withTaskListStorage(taskListStorage)
                .build()));

        // Substring keyword
        predicate = new TaskContainsKeywordsPredicate(Collections.singletonList("repo"));
        assertTrue(predicate.test(new PersonBuilder()
                .withTaskListStorage(taskListStorage)
                .build()));
    }

    @Test
    public void test_taskDoesNotContainKeywords_returnsFalse() {
        TaskListStorage taskListStorage = new TaskListStorage(new ArrayList<>(Arrays.asList(
                new Task("Submit report", "Finish finance report", 1),
                new Task("Client meeting", "Prepare slides", 2)
        )));

        // Zero keywords
        TaskContainsKeywordsPredicate predicate = new TaskContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withTaskListStorage(taskListStorage)
                .build()));

        // Non-matching keyword
        predicate = new TaskContainsKeywordsPredicate(Collections.singletonList("audit"));
        assertFalse(predicate.test(new PersonBuilder()
                .withTaskListStorage(taskListStorage)
                .build()));

        // Keywords match name/phone/email/position but not task names
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("Amy", "85355255", "amy@gmail.com", "Software"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Amy Bee")
                .withPhone("85355255")
                .withEmail("amy@gmail.com")
                .withPosition("Software developer")
                .withTaskListStorage(taskListStorage)
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("report", "review");
        TaskContainsKeywordsPredicate predicate = new TaskContainsKeywordsPredicate(keywords);

        String expected = TaskContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
