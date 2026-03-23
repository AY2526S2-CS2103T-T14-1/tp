package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Greets the user.
 */
public class HelloCommand extends Command {

    public static final String COMMAND_WORD = "hello";

    public static final String MESSAGE_SUCCESS = "Hello! Tutorial 2 command added successfully.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
