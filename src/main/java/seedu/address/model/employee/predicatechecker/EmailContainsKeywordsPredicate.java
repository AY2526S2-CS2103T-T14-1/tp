package seedu.address.model.employee.predicatechecker;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.employee.Employee;

/**
 * Tests that a {@code Employee}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<Employee> {

    private final List<String> keywords;

    /**
     * Creates an {@code EmailContainsKeywordsPredicate} with the given keywords.
     *
     * @param keywords Keywords to match against an employee's email.
     */
    public EmailContainsKeywordsPredicate(List<String> keywords) {
        assert keywords != null : "Keywords list should not be null";
        this.keywords = keywords;
    }

    /**
     * Returns true if the employee's email contains any of the keywords, ignoring case.
     *
     * @param employee Employee whose email is to be tested.
     * @return True if the employee's email matches any keyword, false otherwise.
     */
    @Override
    public boolean test(Employee employee) {
        return keywords.stream()
                .anyMatch(keyword -> employee.getEmail().value
                        .toLowerCase()
                        .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EmailContainsKeywordsPredicate
                && keywords.equals(((EmailContainsKeywordsPredicate) other).keywords));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}
