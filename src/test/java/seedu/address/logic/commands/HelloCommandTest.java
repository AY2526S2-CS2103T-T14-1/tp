package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains integration tests and unit tests for {@code HelloCommand}.
 */
public class HelloCommandTest {

    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void execute_hello_success() {
        assertCommandSuccess(new HelloCommand(), model, HelloCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
