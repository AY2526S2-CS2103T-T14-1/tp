package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Task;

/**
 * Adds a task to a person's task list in the address book.
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "addtask";

    public static final String MESSAGE_DUPLICATE_TASK = "Task already exists for this user";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD
                    + ": Adds a task to the task list. "
                    + "Example:\n"
                    + COMMAND_WORD + " " + PREFIX_TASK_NAME + "Sales Pitch "
                    + PREFIX_TASK_DESCRIPTION + "Complete pitch deck by 02-02-2026 "
                    + "1";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task task;
    private final int index;

    /**
     * Constructor for AddTaskCommand.
     * @param task the task to be added.
     * @param index the index of the person to whom the task is to be added.
     */
    public AddTaskCommand(Task task, int index) {
        requireNonNull(task);
        this.task = task;
        this.index = index;


    }

    /**
     * Executes the command to add the task to the specified person's task list.
     *
     * @param model the model which the command should operate on.
     * @return a CommandResult indicating the result of the command execution.
     */
    @Override
    public CommandResult execute(Model model) {

        requireNonNull(model);
        Employee person = getPerson(index - 1, model);


        if (person != null) {

            Task taskWithSameDescription = model.getTaskWithSameDescription(task, person);

            if (taskWithSameDescription != null) {
                return new CommandResult(MESSAGE_DUPLICATE_TASK);
            }

            task.incrementTaskIndex();
            model.addTaskToPerson(person, task);
            model.addTaskOverall(task, person);
            model.showAllTasks();

            return new CommandResult(String.format(MESSAGE_SUCCESS, task));
        } else {
            return new CommandResult("Person not found in the address book.");
        }
    }

    /**
     * Checks if this AddTaskCommand is equal to another object.
     *
     * @param other the object to compare with this AddTaskCommand.
     * @return true if the other object is an AddTaskCommand with the same task and person name, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTaskCommand)) {
            return false;
        }

        AddTaskCommand otherCommand = (AddTaskCommand) other;
        return task.equals(otherCommand.task)
                && index == otherCommand.index;
    }

    /**
     * Returns the employee that is represented by that index.
     * @param index the index of the employee in the filtered person list.
     * @param model the model which the command should operate on.
     * @return the employee that is represented by that index.
     */
    public Employee getPerson(int index, Model model) {
        try {
            Employee person = model.getFilteredPersonList().get(index);
            return person;
        } catch (IndexOutOfBoundsException a) {
            return null;
        }
    }
}
