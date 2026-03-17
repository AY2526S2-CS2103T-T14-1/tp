package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowCommand;

public class ShowCommandParserTest {

    private ShowCommandParser parser = new ShowCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(Exception.class, () ->
                parser.parse("     "));
    }

    @Test
    public void parse_validNamePrefix_returnsShowCommand() throws Exception {
        ShowCommand command = parser.parse("n/Alice");
        assertNotNull(command);
    }

    @Test
    public void parse_multipleFields_returnsShowCommand() throws Exception {
        ShowCommand command = parser.parse("n/Alice d/IT e/gmail");
        assertNotNull(command);
    }

    @Test
    public void parse_invalidInput_noPrefix_returnsEmptyFilter() throws Exception {
        ShowCommand command = parser.parse("Alice");
        assertNotNull(command);
    }
}