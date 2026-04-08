package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.ClearTasksCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearTasksCommand object.
 */
public class ClearTasksCommandParser implements Parser<ClearTasksCommand> {

    @Override
    public ClearTasksCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearTasksCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            if (!argMultimap.getPreamble().trim().isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearTasksCommand.MESSAGE_USAGE));
            }
            return new ClearTasksCommand(argMultimap.getValue(PREFIX_NAME).get().trim());
        }

        try {
            int index = Integer.parseInt(trimmedArgs);
            if (index < 1) {
                throw new ParseException(ClearTasksCommand.MESSAGE_INVALID_INDEX);
            }
            return new ClearTasksCommand(index);
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearTasksCommand.MESSAGE_USAGE));
        }
    }
}
