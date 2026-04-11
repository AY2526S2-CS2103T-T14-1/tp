package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_NAME;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_PRESENTATION;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_PRESENTATION;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_PRESENTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PRESENTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_REPORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Task;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private final EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_nameOnlySpecified_success() {
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_PRESENTATION)
                .build();
        EditTaskCommand expectedCommand = new EditTaskCommand(1, descriptor);

        assertParseSuccess(parser, "1" + TASK_NAME_PRESENTATION, expectedCommand);
    }

    @Test
    public void parse_descriptionOnlySpecified_success() {
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskDescription(VALID_TASK_DESCRIPTION_PRESENTATION)
                .build();
        EditTaskCommand expectedCommand = new EditTaskCommand(1, descriptor);

        assertParseSuccess(parser, "1" + TASK_DESC_PRESENTATION, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_REPORT)
                .withTaskDescription(VALID_TASK_DESCRIPTION_REPORT)
                .build();
        EditTaskCommand expectedCommand = new EditTaskCommand(2, descriptor);

        assertParseSuccess(parser, "2" + TASK_NAME_REPORT + TASK_DESC_REPORT, expectedCommand);
    }

    @Test
    public void parse_extraWhitespaceAroundValues_trimmedSuccessfully() {
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_PRESENTATION)
                .build();
        EditTaskCommand expectedCommand = new EditTaskCommand(1, descriptor);

        // Extra whitespace inside the value should be trimmed by the parser
        assertParseSuccess(parser, "1 " + PREFIX_TASK_NAME
                                   + "  " + VALID_TASK_NAME_PRESENTATION + "  ", expectedCommand);
    }

    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, TASK_NAME_PRESENTATION, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_zeroIndex_failure() {
        assertParseFailure(parser, "0" + TASK_NAME_PRESENTATION,
                           EditTaskCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_negativeIndex_failure() {
        assertParseFailure(parser, "-1" + TASK_NAME_PRESENTATION,
                           EditTaskCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_nonNumericIndex_failure() {
        assertParseFailure(parser, "abc" + TASK_NAME_PRESENTATION, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyArgs_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_noFieldsSpecified_failure() {
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_blankTaskName_failure() {
        assertParseFailure(parser, "1" + INVALID_TASK_NAME,
                           Task.MESSAGE_CONSTRAINTS_TASK_NAME);
    }

    @Test
    public void parse_blankTaskDescription_failure() {
        assertParseFailure(parser, "1" + INVALID_TASK_DESC,
                           Task.MESSAGE_CONSTRAINTS_TASK_DESCRIPTION);
    }

    @Test
    public void parse_duplicateNamePrefix_failure() {
        assertParseFailure(parser, "1" + TASK_NAME_PRESENTATION + TASK_NAME_REPORT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TASK_NAME));
    }

    @Test
    public void parse_duplicateDescriptionPrefix_failure() {
        assertParseFailure(parser, "1" + TASK_DESC_PRESENTATION + TASK_DESC_REPORT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TASK_DESCRIPTION));
    }

    @Test
    void parse_invalidTaskName_throwsParseException() {
        String invalidTaskName = "1 task/" + "A".repeat(51) + " desc/" + VALID_TASK_DESCRIPTION_REPORT;
        assertThrows(ParseException.class, () -> parser.parse(invalidTaskName));
    }

    @Test
    void parse_invalidTaskDescription_throwsParseException() {
        String invalidTaskDescription = "1 task/" + VALID_TASK_NAME_PRESENTATION + " desc/" + "A".repeat(201);
        assertThrows(ParseException.class, () -> parser.parse(invalidTaskDescription));
    }


}
