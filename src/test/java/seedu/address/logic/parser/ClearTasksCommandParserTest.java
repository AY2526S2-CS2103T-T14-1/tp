package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearTasksCommand;

public class ClearTasksCommandParserTest {

    private final ClearTasksCommandParser parser = new ClearTasksCommandParser();

    @Test
    public void parse_validArgs_returnsClearTasksCommand() {
        assertParseSuccess(parser, "1", new ClearTasksCommand(1));
        assertParseSuccess(parser, " n/John Doe", new ClearTasksCommand("John Doe"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearTasksCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0", ClearTasksCommand.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearTasksCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 n/John Doe", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearTasksCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/John Doe n/Jane Doe", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearTasksCommand.MESSAGE_USAGE));
    }
}
