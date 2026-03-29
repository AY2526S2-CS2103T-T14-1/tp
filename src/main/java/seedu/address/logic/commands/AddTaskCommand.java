package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
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

    public static final String MESSAGE_USAGE =
            COMMAND_WORD
                    + ": Adds a task to the task list. "
                    + "Example:\n"
                    + COMMAND_WORD + " " + PREFIX_TASK_NAME + "Sales Pitch "
                    + PREFIX_TASK_DESCRIPTION + "Complete pitch deck by 02-02-2026 "
                    + PREFIX_NAME + "John Doe";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task task;
    private final String personName;

    /**
     * Creates an AddTaskCommand to add the specified task to the person with the given name.
     *
     * @param task       the task to be added.
     * @param personName the name of the person to whom the task will be added.
     */
    public AddTaskCommand(Task task, String personName) {
        requireNonNull(task);
        requireNonNull(personName);
        this.task = task;
        this.personName = personName;


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
        Employee person = getPerson(personName, model);

        if (person != null) {
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
                && personName.equals(otherCommand.personName);
    }

    /**
     * Retrieves the person with the specified name from the model's address book.
     *
     * @param personName the name of the person to retrieve.
     * @param model      the model containing the address book.
     * @return the Employee object representing the person, or null if not found.
     */
    public Employee getPerson(String personName, Model model) {
        for (Employee p : model.getAddressBook().getPersonList()) {
            if (p.getName().toString().equals(personName)) {
                return p;
            }
        }
        return null;
    }
}
