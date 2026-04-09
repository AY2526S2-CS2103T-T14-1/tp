package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Task;

class AddTaskCommandParserTest {
    public static final String VALID_TASK_NAME_1 = "Task 1";
    public static final String VALID_TASK_DESCRIPTION_1 = "2024-06-30";
    public static final String VALID_NAME_AMY = "Amy Bee";

    private final AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    void parse_allFieldsPresent_success() throws Exception {
        String userInput = "1 task/" + VALID_TASK_NAME_1
                + " desc/" + VALID_TASK_DESCRIPTION_1;

        AddTaskCommand command = parser.parse(userInput);

        Task expectedTask = new Task(VALID_TASK_NAME_1, VALID_TASK_DESCRIPTION_1, Task.getOverallIndex());
        AddTaskCommand expectedCommand = new AddTaskCommand(expectedTask, 1);

        assertEquals(expectedCommand, command);
    }

    @Test
    void parse_missingRequiredField_throwsParseException() {
        String missingTaskName = " desc/" + VALID_TASK_DESCRIPTION_1 + " n/" + VALID_NAME_AMY;
        assertThrows(ParseException.class, () -> parser.parse(missingTaskName));

        String missingTaskDescription = " task/" + VALID_TASK_NAME_1 + " n/" + VALID_NAME_AMY;
        assertThrows(ParseException.class, () -> parser.parse(missingTaskDescription));

        String missingPersonName = " task/" + VALID_TASK_NAME_1 + " desc/" + VALID_TASK_DESCRIPTION_1;
        assertThrows(ParseException.class, () -> parser.parse(missingPersonName));
    }

    @Test
    void parse_invalidTaskName_throwsParseException() {
        String invalidTaskName = "1 task/" + "A".repeat(51) + " desc/" + VALID_TASK_DESCRIPTION_1;
        assertThrows(ParseException.class, () -> parser.parse(invalidTaskName));
    }

    @Test
    void parse_invalidTaskDescription_throwsParseException() {
        String invalidTaskDescription = "1 task/" + VALID_TASK_NAME_1 + " desc/" + "A".repeat(201);
        assertThrows(ParseException.class, () -> parser.parse(invalidTaskDescription));
    }

    @Test
    void parse_emptyTaskName_throwsParseException() {
        String emptyTaskName = "1 task/ desc/" + VALID_TASK_DESCRIPTION_1;
        assertThrows(ParseException.class, () -> parser.parse(emptyTaskName));

        String whitespaceTaskName = "1 task/    desc/" + VALID_TASK_DESCRIPTION_1;
        assertThrows(ParseException.class, () -> parser.parse(whitespaceTaskName));
    }

    @Test
    void parse_emptyTaskDescription_throwsParseException() {
        String emptyTaskDescription = "1 task/" + VALID_TASK_NAME_1 + " desc/";
        assertThrows(ParseException.class, () -> parser.parse(emptyTaskDescription));

        String whitespaceTaskDescription = "1 task/" + VALID_TASK_NAME_1 + " desc/    ";
        assertThrows(ParseException.class, () -> parser.parse(whitespaceTaskDescription));
    }

}
