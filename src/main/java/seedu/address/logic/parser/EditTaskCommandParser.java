package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Task;

/**
 * Parses input arguments and creates a new EditTaskCommand object.
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_TASK_DESCRIPTION);

        int taskIndex = parseTaskIndex(argMultimap.getPreamble());
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TASK_NAME, PREFIX_TASK_DESCRIPTION);

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();

        if (argMultimap.getValue(PREFIX_TASK_NAME).isPresent()) {
            String taskName = argMultimap.getValue(PREFIX_TASK_NAME).get().trim();
            if (taskName.isEmpty() || taskName.length() > 50) {
                throw new ParseException(Task.MESSAGE_CONSTRAINTS_TASK_NAME);
            }
            editTaskDescriptor.setTaskName(taskName);
        }

        if (argMultimap.getValue(PREFIX_TASK_DESCRIPTION).isPresent()) {
            String taskDescription = argMultimap.getValue(PREFIX_TASK_DESCRIPTION).get().trim();
            if (taskDescription.isEmpty() || taskDescription.length() > 200) {
                throw new ParseException(Task.MESSAGE_CONSTRAINTS_TASK_DESCRIPTION);
            }
            editTaskDescriptor.setTaskDescription(taskDescription);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(taskIndex, editTaskDescriptor);

    }

    /**
     * Parses the preamble string into a task index.
     *
     * @param preamble the preamble string from the argument multimap.
     * @return the parsed positive integer task index.
     * @throws ParseException if the preamble is not a positive integer.
     */
    private int parseTaskIndex(String preamble) throws ParseException {
        try {
            int index = Integer.parseInt(preamble.trim());
            if (index < 1) {
                throw new ParseException(EditTaskCommand.MESSAGE_INVALID_INDEX);
            }
            return index;
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }
    }

}
