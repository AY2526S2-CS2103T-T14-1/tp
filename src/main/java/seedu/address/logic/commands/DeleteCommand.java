package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Name;

/**
 * Deletes an employee identified using their name or index from ManageUp.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes one or more employees identified by name or index.\n"
            + "Parameters: NAME or INDEX [MORE_INDEXES]...\n"
            + "Examples: " + COMMAND_WORD + " John Doe\n"
            + "          " + COMMAND_WORD + " 2\n"
            + "          " + COMMAND_WORD + " 1 3 5";

    public static final String MESSAGE_DELETE_EMPLOYEE_SUCCESS = "Employee deleted successfully:\n%1$s";
    public static final String MESSAGE_DELETE_EMPLOYEES_SUCCESS = "Employees deleted successfully:\n%1$s";
    public static final String MESSAGE_INVALID_NAME = "Invalid employee name. " + Name.MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_EMPLOYEE_NOT_FOUND = "No employee named '%1$s' was found in the current list.";
    public static final String MESSAGE_DUPLICATE_EMPLOYEE_NAME =
            "More than one employee named '%1$s' was found. Please use the employee index instead.";
    public static final String MESSAGE_INVALID_INDEX =
            "Invalid employee index. Please enter an index shown in the current employee list.";
    public static final String MESSAGE_DUPLICATE_INDEX = "Duplicate employee indices are not allowed.";

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final Integer targetIndex; // null if not used
    private final String targetName; // null if not used
    private final List<Index> targetIndexes; // empty if not used

    /**
     * Constructor for index-based deletion.
     * @param targetIndex Index of employee to delete.
     */
    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
        this.targetIndexes = List.of();
    }

    /**
     * Constructor for name-based deletion.
     * @param targetName Name of employee to delete.
     */
    public DeleteCommand(String targetName) {
        this.targetIndex = null;
        this.targetName = targetName;
        this.targetIndexes = List.of();
    }

    /**
     * Constructor for batch index-based deletion.
     * @param targetIndexes Indexes of employees to delete.
     */
    public DeleteCommand(List<Index> targetIndexes) {
        requireNonNull(targetIndexes);
        this.targetIndex = null;
        this.targetName = null;
        this.targetIndexes = List.copyOf(targetIndexes);
    }

    /**
     * Executes the delete command, deleting an employee by index or name.
     * @param model The model containing the address book.
     * @return CommandResult indicating success or failure.
     * @throws CommandException if the employee does not exist or input is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Employee> lastShownList = model.getFilteredPersonList();

        if (!targetIndexes.isEmpty()) {
            return executeBatchDelete(model, lastShownList);
        }

        Employee personToDelete = null;
        if (targetIndex != null) {
            // Index-based deletion
            if (targetIndex < 1 || targetIndex > lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEX);
            }
            personToDelete = lastShownList.get(targetIndex - 1);
        } else {
            // Name-based deletion
            requireNonNull(targetName);
            String normalizedTarget = normalizeName(targetName);
            int matchCount = 0;
            if (!isValidName(normalizedTarget)) {
                throw new CommandException(MESSAGE_INVALID_NAME);
            }
            for (Employee employee : lastShownList) {
                String employeeName = normalizeName(employee.getName().fullName);
                if (employeeName.equals(normalizedTarget)) {
                    personToDelete = employee;
                    matchCount++;
                }
            }
            if (matchCount == 0) {
                throw new CommandException(
                        String.format(MESSAGE_EMPLOYEE_NOT_FOUND, targetName));
            }
            if (matchCount > 1) {
                throw new CommandException(
                        String.format(MESSAGE_DUPLICATE_EMPLOYEE_NAME, targetName));
            }
        }

        model.deletePerson(personToDelete);
        logger.info("Deleted employee: " + personToDelete.getName().fullName);
        return new CommandResult(
                String.format(MESSAGE_DELETE_EMPLOYEE_SUCCESS, Messages.format(personToDelete)));
    }

    private CommandResult executeBatchDelete(Model model, List<Employee> lastShownList) throws CommandException {
        assert !targetIndexes.isEmpty();

        List<Employee> employeesToDelete = new ArrayList<>();
        for (Index index : targetIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                logger.warning("Rejected batch delete due to invalid index: " + index.getOneBased());
                throw new CommandException(MESSAGE_INVALID_INDEX);
            }
            employeesToDelete.add(lastShownList.get(index.getZeroBased()));
        }

        targetIndexes.stream()
                .sorted(Comparator.comparingInt(Index::getZeroBased).reversed())
                .forEach(index -> model.deletePerson(lastShownList.get(index.getZeroBased())));

        String deletedEmployeesSummary = employeesToDelete.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));

        logger.info("Deleted " + employeesToDelete.size() + " employees in batch delete command.");
        return new CommandResult(String.format(MESSAGE_DELETE_EMPLOYEES_SUCCESS, deletedEmployeesSummary));
    }

    /**
     * Normalizes a name: trims, collapses spaces, and lowercases.
     * @param name The name to normalize.
     * @return Normalized name.
     */
    private String normalizeName(String name) {
        // Trim and collapse leading spaces to one, then lowercase
        return name.trim().replaceAll("^ +", " ")
                .replaceAll(" +", " ")
                .toLowerCase();
    }

    /**
     * Checks if a name is valid (only alphabets, spaces, and '/').
     * @param name The name to check.
     * @return true if valid, false otherwise.
     */
    private boolean isValidName(String name) {
        return Name.isValidName(name);
    }

    /**
     * Checks if this DeleteCommand is equal to another object.
     * @param other The object to compare.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand that = (DeleteCommand) other;
        return (targetIndex == null
                ? that.targetIndex == null
                : targetIndex.equals(that.targetIndex))
            && (targetName == null
                ? that.targetName == null
                : targetName.equals(that.targetName))
            && targetIndexes.equals(that.targetIndexes);
    }

    /**
     * Returns a string representation of this DeleteCommand.
     * @return String representation.
     */
    @Override
    public String toString() {
        if (targetIndex != null) {
            return DeleteCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + "}";
        } else if (!targetIndexes.isEmpty()) {
            return DeleteCommand.class.getCanonicalName()
                + "{targetIndexes=" + targetIndexes + "}";
        } else {
            return DeleteCommand.class.getCanonicalName()
                + "{targetName=" + targetName + "}";
        }
    }
}
