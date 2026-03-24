package seedu.address.model.employee.predicatechecker;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.employee.Employee;
import seedu.address.model.tag.Tag;

/**
 * Tests that an {@code Employee}'s {@code Tag} match any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Employee> {

    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Employee employee) {
        return keywords.stream()
                .anyMatch(keyword -> employee.getTags().stream()
                        .map(Tag::getTagName)
                        .anyMatch(tagName -> tagName.toLowerCase().contains(keyword.toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TagContainsKeywordsPredicate
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
