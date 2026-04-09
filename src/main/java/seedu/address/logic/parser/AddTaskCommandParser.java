package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_TASK_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Task;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_TASK_DESCRIPTION);
        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_NAME, PREFIX_TASK_DESCRIPTION)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_TASK_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            int index = Integer.parseInt(argMultimap.getPreamble().trim());
            String taskName = argMultimap.getValue(PREFIX_TASK_NAME).get();
            String taskDescription = argMultimap.getValue(PREFIX_TASK_DESCRIPTION).get().trim();
            return new AddTaskCommand(new Task(taskName, taskDescription, Task.getOverallIndex()), index);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_TASK_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
