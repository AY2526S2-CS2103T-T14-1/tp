package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";
    public static final String MESSAGE_SUCCESS = "Showing details of employee";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        /*
        need to implement model.show(String name);
        decide whether prefix is accepted or full name is required
         */
        return new CommandResult(MESSAGE_SUCCESS);
    }


    // to test if  workflow is correct
}
