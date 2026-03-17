package seedu.address.model.employee.predicatechecker;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.employee.Employee;

/**
 * Tests that a {@code Employee}'s {@code Position} matches any of the keywords given.
 */
public class PositionContainsKeywordsPredicate implements Predicate<Employee> {

    private final List<String> keywords;

    public PositionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Employee employee) {
        return keywords.stream()
                .anyMatch(keyword -> employee.getPosition().value
                        .toLowerCase()
                        .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PositionContainsKeywordsPredicate
                && keywords.equals(((PositionContainsKeywordsPredicate) other).keywords));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
