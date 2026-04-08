package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;

/**
 * Clears all tasks for a specified employee identified by name or index.
 */
public class ClearTasksCommand extends Command {

    public static final String COMMAND_WORD = "cleartasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all tasks belonging to the employee identified by their name or index.\n"
            + "Parameters: INDEX or " + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "         " + COMMAND_WORD + " " + PREFIX_NAME + "John Doe";

    public static final String MESSAGE_SUCCESS = "Cleared %1$d task(s) for employee: %2$s";
    public static final String MESSAGE_INVALID_INDEX = "The employee index provided is invalid.";
    public static final String MESSAGE_INVALID_NAME = "Name must contain only alphabets and optional '/'.";
    public static final String MESSAGE_EMPLOYEE_NOT_FOUND = "Employee with name '%1$s' does not exist.";
    public static final String MESSAGE_DUPLICATE_EMPLOYEE_NAME =
            "Multiple employees named '%1$s' found. Please use the index instead.";

    private final Integer targetIndex;
    private final String targetName;

    /**
     * Creates a ClearTasksCommand using employee index.
     *
     * @param targetIndex index of employee whose tasks should be cleared
     */
    public ClearTasksCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * Creates a ClearTasksCommand using employee name.
     *
     * @param targetName name of employee whose tasks should be cleared
     */
    public ClearTasksCommand(String targetName) {
        this.targetIndex = null;
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Employee targetEmployee = null;
        List<Employee> lastShownList = model.getFilteredPersonList();

        if (targetIndex != null) {
            if (targetIndex < 1 || targetIndex > lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEX);
            }
            targetEmployee = lastShownList.get(targetIndex - 1);
        } else {
            requireNonNull(targetName);
            String normalizedTarget = normalizeName(targetName);
            int matchCount = 0;
            if (!isValidName(normalizedTarget)) {
                throw new CommandException(MESSAGE_INVALID_NAME);
            }
            for (Employee employee : lastShownList) {
                String employeeName = normalizeName(employee.getName().fullName);
                if (employeeName.equals(normalizedTarget)) {
                    targetEmployee = employee;
                    matchCount++;
                }
            }
            if (matchCount == 0) {
                throw new CommandException(String.format(MESSAGE_EMPLOYEE_NOT_FOUND, targetName));
            }
            if (matchCount > 1) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_EMPLOYEE_NAME, targetName));
            }
        }

        int clearedCount = model.clearTasksForPerson(targetEmployee);
        return new CommandResult(String.format(MESSAGE_SUCCESS, clearedCount, targetEmployee.getName().fullName));
    }

    private String normalizeName(String name) {
        return name.trim().replaceAll("^ +", " ")
                .replaceAll(" +", " ")
                .toLowerCase();
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z /]+");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ClearTasksCommand)) {
            return false;
        }
        ClearTasksCommand that = (ClearTasksCommand) other;
        return (targetIndex == null
                ? that.targetIndex == null
                : targetIndex.equals(that.targetIndex))
                && (targetName == null
                ? that.targetName == null
                : targetName.equals(that.targetName));
    }
}
