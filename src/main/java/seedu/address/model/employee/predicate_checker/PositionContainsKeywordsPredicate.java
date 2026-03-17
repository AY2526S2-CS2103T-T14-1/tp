package seedu.address.model.employee.predicate_checker;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.employee.Employee;

public class PositionContainsKeywordsPredicate implements Predicate<Employee> {

    private final List<String> keywords;

    public PositionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Employee employee) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        employee.getPosition().value, keyword));
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