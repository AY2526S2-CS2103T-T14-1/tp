package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.employee.Employee;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND =
            "Unknown command. Use 'help' to view the list of available commands.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT =
            "Invalid command format. Please use the following format:\n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX =
            "Invalid employee index. Please enter an index shown in the current employee list.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d employee(s) matched your filter.";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values were provided for these fields, but each field accepts only one value: ";
    public static final String MESSAGE_INVALID_TASK_COMMAND_FORMAT =
            "Invalid task command format. Please use the following format:\n%1$s";
    public static final String MESSAGE_DUPLICATE_EMPLOYEE =
            "This employee already exists in ManageUp.";
    public static final String MESSAGE_DUPLICATE_PHONE =
            "This phone number is already assigned to another employee:\n%1$s";
    public static final String MESSAGE_DUPLICATE_EMAIL =
            "This email address is already assigned to another employee:\n%1$s";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Employee person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Department: ")
                .append(person.getDepartment())
                .append("; Position: ")
                .append(person.getPosition())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
