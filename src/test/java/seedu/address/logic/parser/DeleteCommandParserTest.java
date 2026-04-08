package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "John Doe", new DeleteCommand("John Doe"));
        assertParseSuccess(parser, "John2 Doe", new DeleteCommand("John2 Doe"));
        assertParseSuccess(parser, "1", new DeleteCommand(1));
        assertParseSuccess(parser, "1 3 5", new DeleteCommand(List.of(
                Index.fromOneBased(1), Index.fromOneBased(3), Index.fromOneBased(5))));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "John_Doe", DeleteCommand.MESSAGE_INVALID_NAME);
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0", DeleteCommand.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "2 2", DeleteCommand.MESSAGE_DUPLICATE_INDEX);
    }
}
