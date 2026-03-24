package seedu.address.model.employee.predicatechecker;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Task;

/**
 * Tests that an {@code Employee}'s task names match any of the keywords given.
 */
public class TaskContainsKeywordsPredicate implements Predicate<Employee> {

    private final List<String> keywords;

    public TaskContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Employee employee) {
        return keywords.stream()
                .anyMatch(keyword -> employee.getTasks().stream()
                        .map(Task::getTaskName)
                        .anyMatch(taskName -> taskName.toLowerCase().contains(keyword.toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskContainsKeywordsPredicate
                && keywords.equals(((TaskContainsKeywordsPredicate) other).keywords));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
