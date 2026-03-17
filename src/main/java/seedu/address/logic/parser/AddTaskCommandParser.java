package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_TASK_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniquePersonList;

public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_TASK_DESCRIPTION, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_NAME, PREFIX_TASK_DESCRIPTION, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_TASK_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        String taskName = argMultimap.getValue(PREFIX_TASK_NAME).get();
        String taskDescription = argMultimap.getValue(PREFIX_TASK_DESCRIPTION).get();
        String personName = argMultimap.getValue(PREFIX_NAME).get();

        List<Person> personList = UniquePersonList.findPersonsByName(personName);

        Person person = personList.get(0);
        // To be fixed later when we have multiple people with the same name, we can prompt the user to select
        // the correct person from the list of people with the same name.
        // For now, we will just take the first person in the list.

        return new AddTaskCommand(new Task(taskName, taskDescription), person);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
