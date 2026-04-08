package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Task;

/**
 * Deletes one or more tasks by index.
 */
public class DeleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "deletetask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task(s) identified by their task indices.\n"
            + "Parameters: INDEX [MORE_INDICES...] (each must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 3 5";

    public static final String MESSAGE_SUCCESS = "%d task(s) deleted successfully.";
    public static final String MESSAGE_INVALID_INDEX =
            "Invalid task index. Please enter task indices that are currently shown in ManageUp.";
    public static final String MESSAGE_DUPLICATE_INDEX = "Duplicate task indices are not allowed.";

    private final List<Integer> indices;

    public DeleteTaskCommand(List<Integer> indices) {
        this.indices = List.copyOf(indices);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (hasDuplicateIndices()) {
            throw new CommandException(MESSAGE_DUPLICATE_INDEX);
        }

        if (!allIndicesExist(model)) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        for (int index : indices) {
            model.deleteTask(index);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, indices.size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteTaskCommand)) {
            return false;
        }

        DeleteTaskCommand otherCommand = (DeleteTaskCommand) other;
        return indices.equals(otherCommand.indices);
    }

    private boolean allIndicesExist(Model model) {
        Set<Integer> existingTaskIndices = new HashSet<>();
        for (Employee employee : model.getAddressBook().getPersonList()) {
            for (Task task : employee.getTasks()) {
                existingTaskIndices.add(task.getCurrentTaskIndex());
            }
        }
        return existingTaskIndices.containsAll(indices);
    }

    private boolean hasDuplicateIndices() {
        return new HashSet<>(indices).size() != indices.size();
    }
}
