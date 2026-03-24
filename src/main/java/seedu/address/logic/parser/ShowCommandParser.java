package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.predicatechecker.DepartmentContainsKeywordsPredicate;
import seedu.address.model.employee.predicatechecker.EmailContainsKeywordsPredicate;
import seedu.address.model.employee.predicatechecker.NameContainsKeywordsPredicate;
import seedu.address.model.employee.predicatechecker.PhoneContainsKeywordsPredicate;
import seedu.address.model.employee.predicatechecker.PositionContainsKeywordsPredicate;
import seedu.address.model.employee.predicatechecker.TagContainsKeywordsPredicate;
import seedu.address.model.employee.predicatechecker.TaskContainsKeywordsPredicate;

/**
 * Parses input arguments and creates
 * a new ShowCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns a ShowCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        String input = args.trim();

        if (input.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        }

        Predicate<Employee> predicate = employee -> true;
        boolean hasFilter = false;

        // Name
        if (input.contains("n/")) {
            String value = extract(input, "n/");
            if (!value.isEmpty()) {
                predicate = predicate.and(
                        new NameContainsKeywordsPredicate(Arrays.asList(value.split("\\s+")))
                );
                hasFilter = true;
            }
        }

        // Department
        if (input.contains("d/")) {
            String value = extract(input, "d/");
            if (!value.isEmpty()) {
                predicate = predicate.and(
                        new DepartmentContainsKeywordsPredicate(Arrays.asList(value.split("\\s+")))
                );
                hasFilter = true;
            }
        }

        // Phone
        if (input.contains("p/")) {
            String value = extract(input, "p/");
            if (!value.isEmpty()) {
                predicate = predicate.and(
                        new PhoneContainsKeywordsPredicate(Arrays.asList(value.split("\\s+")))
                );
                hasFilter = true;
            }
        }

        // Position
        if (input.contains("pos/")) {
            String value = extract(input, "pos/");
            if (!value.isEmpty()) {
                predicate = predicate.and(
                        new PositionContainsKeywordsPredicate(Arrays.asList(value.split("\\s+")))
                );
                hasFilter = true;
            }
        }

        // Email
        if (input.contains("e/")) {
            String value = extract(input, "e/");
            if (!value.isEmpty()) {
                predicate = predicate.and(
                        new EmailContainsKeywordsPredicate(Arrays.asList(value.split("\\s+")))
                );
                hasFilter = true;
            }
        }

        // Tag
        if (input.contains("t/")) {
            String value = extract(input, "t/");
            if (!value.isEmpty()) {
                predicate = predicate.and(
                        new TagContainsKeywordsPredicate(Arrays.asList(value.split("\\s+")))
                );
                hasFilter = true;
            }
        }

        // Task
        if (input.contains("task/")) {
            String value = extract(input, "task/");
            if (!value.isEmpty()) {
                predicate = predicate.and(
                        new TaskContainsKeywordsPredicate(Arrays.asList(value.split("\\s+")))
                );
                hasFilter = true;
            }
        }

        // If no valid filters → show nothing
        if (!hasFilter) {
            predicate = employee -> false;
        }

        return new ShowCommand(predicate);
    }

    /**
     * Extracts value after a prefix until the next recognised prefix or end of input.
     */
    private String extract(String input, String prefix) {
        int start = input.indexOf(prefix);
        if (start == -1) {
            return "";
        }

        start += prefix.length();
        int end = input.length();

        String[] prefixes = {"n/", "d/", "p/", "e/", "pos/", "t/", "task/"};

        for (String nextPrefix : prefixes) {
            if (nextPrefix.equals(prefix)) {
                continue;
            }

            int index = input.indexOf(" " + nextPrefix, start);
            if (index != -1 && index < end) {
                end = index;
            }
        }

        return input.substring(start, end).trim();
    }
}
