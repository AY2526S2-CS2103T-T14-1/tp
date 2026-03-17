package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.person.Task;

public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "addtask";

    public static final String MESSAGE_USAGE =
            "Example: " + COMMAND_WORD
                    + ": Adds a task to the task list. "
                    + "Parameters:\n"
                    + COMMAND_WORD + PREFIX_TASK_NAME + "Finish Homework "
                    + PREFIX_TASK_DESCRIPTION + "Complete math and science homework by tomorrow"
                    + PREFIX_NAME + "John Doe";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task task;
    private final String personName;



    public AddTaskCommand(Task task, String personName) {
        requireNonNull(task);
        requireNonNull(personName);
        this.task = task;
        this.personName = personName;


    }

    @Override
    public CommandResult execute(Model model) {

        requireNonNull(model);
        Employee person = getPerson(personName, model);
        if (model.hasPerson(person)) {
            model.addTaskToPerson(person, task);
            return new CommandResult(String.format(MESSAGE_SUCCESS, task));
        } else {
            return new CommandResult("Person not found in the address book.");
        }


    }

    public Employee getPerson(String personName, Model model) {
        for (Employee p : model.getAddressBook().getPersonList()) {
            if (p.getName().toString().equals(personName)) {
                return p;
            }
        }
        return null;
    }
}
