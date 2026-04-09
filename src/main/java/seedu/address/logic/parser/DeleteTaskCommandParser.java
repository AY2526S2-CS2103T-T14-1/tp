package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTaskCommand object.
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    public static final String MESSAGE_NON_POSITIVE_INDEX =
            "Invalid task index. Please enter only positive task indices.";

    @Override
    public DeleteTaskCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
        }

        try {
            List<Integer> indices = Arrays.stream(trimmedArgs.split("\\s+"))
                    .map(Integer::parseInt)
                    .toList();

            for (int index : indices) {
                if (index < 1) {
                    throw new ParseException(MESSAGE_NON_POSITIVE_INDEX);
                }
            }
            return new DeleteTaskCommand(indices);
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
        }
    }
}
