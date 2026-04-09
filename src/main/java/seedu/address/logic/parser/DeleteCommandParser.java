package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Name;

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

        String[] tokens = trimmedArgs.split("\\s+");
        if (tokens.length > 1 && areAllIndexes(tokens)) {
            return new DeleteCommand(parseIndexes(tokens));
        }

        // Try parsing as single index
        try {
            int index = Integer.parseInt(trimmedArgs);
            if (index < 1) {
                throw new ParseException(DeleteCommand.MESSAGE_INVALID_INDEX);
            }
            return new DeleteCommand(index);
        } catch (NumberFormatException e) {
            // Not an integer, treat as name
            String normalizedName = trimmedArgs.replaceAll("^ +", " ").replaceAll(" +", " ").toLowerCase();
            if (!Name.isValidName(normalizedName)) {
                throw new ParseException(DeleteCommand.MESSAGE_INVALID_NAME);
            }
            return new DeleteCommand(trimmedArgs);
        }
    }

    private boolean areAllIndexes(String[] tokens) {
        for (String token : tokens) {
            try {
                int value = Integer.parseInt(token);
                if (value < 1) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private List<Index> parseIndexes(String[] tokens) throws ParseException {
        List<Index> indexes = new ArrayList<>();
        for (String token : tokens) {
            int value = Integer.parseInt(token);
            Index index = Index.fromOneBased(value);
            if (indexes.contains(index)) {
                throw new ParseException(DeleteCommand.MESSAGE_DUPLICATE_INDEX);
            }
            indexes.add(index);
        }
        return indexes;
    }

}
