package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Task;


/**
 * Edits the name and/or description of an existing task identified by its task index.
 */
public class EditTaskCommand extends Command {

    public static final String COMMAND_WORD = "edittask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the task index. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: TASK INDEX (must be a positive integer) "
            + "[" + PREFIX_TASK_NAME + "TASK NAME] "
            + "[" + PREFIX_TASK_DESCRIPTION + "DESCRIPTION]... \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TASK_NAME + "Close deal "
            + PREFIX_TASK_DESCRIPTION + "Follow through with clients ";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task updated successfully:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "Please provide at least one task field to update.";
    public static final String MESSAGE_INVALID_INDEX =
            "Invalid task index. Please enter a task index that is currently shown in ManageUp.";

    private final int taskIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * Creates a EditTaskCommand to edit the specified task identified by the given {@code taskIndex}
     * @param taskIndex  the task index of the task to edit
     * @param editTaskDescriptor  the task fields to update
     */
    public EditTaskCommand(int taskIndex, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(editTaskDescriptor);

        this.taskIndex = taskIndex;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);

    }

    /**
     * Executes the command to edit the task with the given task index.
     *
     * @param model the model which the command should operate on.
     * @return a CommandResult indicating the result of the command execution.
     * @throws CommandException if no task with {@code taskIndex} exists in the model.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Task oldTask = model.getTaskByIndex(taskIndex)
                            .orElseThrow(() -> new CommandException(MESSAGE_INVALID_INDEX));
        String newName = editTaskDescriptor.getTaskName().orElse(oldTask.getTaskName());
        String newDescription = editTaskDescriptor.getTaskDescription().orElse(oldTask.getTaskDescription());
        Task newTask = new Task(newName, newDescription, oldTask.getCurrentTaskIndex());

        model.setTask(taskIndex, newTask);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, newTask));

    }

    /**
     * Checks if this EditTaskCommand is equal to another object.
     *
     * @param other the object to compare with this EditTaskCommand.
     * @return true if the other object is an EditTaskCommand with the same
     *         {@code taskIndex} and {@code editTaskDescriptor}, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }
        EditTaskCommand otherCommand = (EditTaskCommand) other;
        return taskIndex == otherCommand.taskIndex
                && editTaskDescriptor.equals(otherCommand.editTaskDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("taskIndex", taskIndex)
                .add("editTaskDescriptor", editTaskDescriptor)
                .toString();
    }

    /**
     * Stores the task fields to edit. Each non-null field value will replace the
     * corresponding field value of the existing task.
     */
    public static class EditTaskDescriptor {
        private String taskName;
        private String taskDescription;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setTaskName(toCopy.taskName);
            setTaskDescription(toCopy.taskDescription);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(taskName, taskDescription);
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public Optional<String> getTaskName() {
            return Optional.ofNullable(taskName);
        }

        public void setTaskDescription(String taskDescription) {
            this.taskDescription = taskDescription;
        }

        public Optional<String> getTaskDescription() {
            return Optional.ofNullable(taskDescription);
        }

        /**
         * Returns true if both descriptors carry the same task name and task description values.
         *
         * @param other the object to compare with.
         * @return true if {@code other} is an {@code EditTaskDescriptor} with equal name and description fields.
         */
        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }
            EditTaskDescriptor otherDescriptor = (EditTaskDescriptor) other;
            return Objects.equals(taskName, otherDescriptor.taskName)
                    && Objects.equals(taskDescription, otherDescriptor.taskDescription);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("taskName", taskName)
                    .add("taskDescription", taskDescription)
                    .toString();
        }
    }

}
