package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;

/**
 * Adds an employee to the application.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an employee to ManageUp.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_DEPARTMENT + "DEPARTMENT "
            + PREFIX_POSITION + "POSITION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_DEPARTMENT + "IT "
            + PREFIX_POSITION + "Junior Software Developer "
            + PREFIX_TAG + "intern "
            + PREFIX_TAG + "partTime";

    public static final String MESSAGE_SUCCESS = "Employee added successfully:\n%1$s";

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class.getName());

    private final Employee toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Employee}
     */
    public AddCommand(Employee person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.log(Level.INFO, "Executing AddCommand for employee: {0}", toAdd.getName());

        checkNoDuplicates(toAdd, model);
        model.addPerson(toAdd);

        logger.log(Level.INFO, "Successfully added employee: {0}", toAdd.getName());
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    /**
     * Checks that {@code employee} does not conflict with any existing employee in {@code model},
     * by name, phone, or email.
     *
     * @param employee the employee to validate.
     * @param model the model to check against.
     * @throws CommandException if a duplicate name, phone, or email is found.
     */
    private static void checkNoDuplicates(Employee employee, Model model) throws CommandException {
        if (model.hasPerson(employee)) {
            logger.log(Level.WARNING, Messages.MESSAGE_DUPLICATE_EMPLOYEE);
            throw new CommandException(Messages.MESSAGE_DUPLICATE_EMPLOYEE);
        }

        Employee employeeWithSameEmail = model.getEmployeeWithSameEmail(employee);
        if (employeeWithSameEmail != null) {
            logger.log(Level.WARNING, "Duplicate email detected for: {0}", employee.getEmail());
            throw new CommandException(String.format(Messages.MESSAGE_DUPLICATE_EMAIL,
                    Messages.format(employeeWithSameEmail)));
        }

        Employee employeeWithSamePhone = model.getEmployeeWithSamePhone(employee);
        if (employeeWithSamePhone != null) {
            logger.log(Level.WARNING, "Duplicate phone detected for: {0}", employee.getPhone());
            throw new CommandException(String.format(Messages.MESSAGE_DUPLICATE_PHONE,
                    Messages.format(employeeWithSamePhone)));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
