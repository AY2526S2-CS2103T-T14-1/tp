package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.Task;

/**
 * Clears all tasks assigned to one employee identified by name or displayed index.
 */
public class ClearTasksCommand extends Command {

    public static final String COMMAND_WORD = "cleartasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all tasks belonging to the employee identified by their name or index.\n"
            + "Parameters: INDEX or n/NAME\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "         " + COMMAND_WORD + " n/John Doe";

    public static final String MESSAGE_SUCCESS = "Deleted Tasks for %1$s:\n%2$s";
    public static final String MESSAGE_NO_TASKS_CLEARED = "Deleted Tasks for %1$s:\nNo tasks were deleted.";
    public static final String MESSAGE_INVALID_NAME = "Invalid employee name. " + Name.MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_EMPLOYEE_NOT_FOUND = "No employee named '%1$s' was found in the current list.";
    public static final String MESSAGE_DUPLICATE_EMPLOYEE_NAME =
            "More than one employee named '%1$s' was found. Please use the employee index instead.";
    public static final String MESSAGE_INVALID_INDEX =
            "Invalid employee index. Please enter an index shown in the current employee list.";

    private final Integer targetIndex;
    private final String targetName;

    /**
     * Creates a command that clears tasks for the employee at the given displayed index.
     *
     * @param targetIndex displayed employee index
     */
    public ClearTasksCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * Creates a command that clears tasks for the uniquely named employee.
     *
     * @param targetName employee name
     */
    public ClearTasksCommand(String targetName) {
        this.targetIndex = null;
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Employee> lastShownList = model.getFilteredPersonList();

        Employee employeeToClear;
        if (targetIndex != null) {
            if (targetIndex < 1 || targetIndex > lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEX);
            }
            employeeToClear = lastShownList.get(targetIndex - 1);
        } else {
            requireNonNull(targetName);
            String normalizedTarget = normalizeName(targetName);
            if (!Name.isValidName(normalizedTarget)) {
                throw new CommandException(MESSAGE_INVALID_NAME);
            }

            employeeToClear = null;
            int matchCount = 0;
            for (Employee employee : lastShownList) {
                String employeeName = normalizeName(employee.getName().fullName);
                if (employeeName.equals(normalizedTarget)) {
                    employeeToClear = employee;
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

        List<Task> tasksToClear = new ArrayList<>(employeeToClear.getTasks());
        model.clearTasksForPerson(employeeToClear);

        if (tasksToClear.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_TASKS_CLEARED, employeeToClear.getName().fullName));
        }

        String deletedTasks = tasksToClear.stream()
                .map(Task::toString)
                .reduce((first, second) -> first + "\n" + second)
                .orElse("");
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                employeeToClear.getName().fullName, deletedTasks));
    }

    private String normalizeName(String name) {
        return name.trim().replaceAll("^ +", " ")
                .replaceAll(" +", " ")
                .toLowerCase();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ClearTasksCommand)) {
            return false;
        }
        ClearTasksCommand otherCommand = (ClearTasksCommand) other;
        return (targetIndex == null
                ? otherCommand.targetIndex == null
                : targetIndex.equals(otherCommand.targetIndex))
            && (targetName == null
                ? otherCommand.targetName == null
                : targetName.equals(otherCommand.targetName));
    }
}
