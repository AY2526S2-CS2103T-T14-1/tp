package seedu.address.model.employee.predicatechecker;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.employee.Employee;

/**
 * Tests that a {@code Employee}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Employee> {

    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Employee employee) {
        return keywords.stream()
                .anyMatch(keyword -> employee.getPhone().value
                        .contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PhoneContainsKeywordsPredicate
                && keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
