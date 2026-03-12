package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        // Try parsing as index
        try {
            int index = Integer.parseInt(trimmedArgs);
            if (index < 1) {
                throw new ParseException(DeleteCommand.MESSAGE_INVALID_INDEX);
            }
            return new DeleteCommand(index);
        } catch (NumberFormatException e) {
            // Not an integer, treat as name
            String normalizedName = trimmedArgs.replaceAll("^ +", " ").replaceAll(" +", " ").toLowerCase();
            if (!normalizedName.matches("[a-zA-Z /]+")) {
                throw new ParseException(DeleteCommand.MESSAGE_INVALID_NAME);
            }
            return new DeleteCommand(trimmedArgs);
        }
    }

}
