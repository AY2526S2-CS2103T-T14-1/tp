package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCompletedTasksCommand}.
 */
public class DeleteCompletedTasksCommandTest {

    @Test
    public void execute_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new DeleteCompletedTasksCommand(), model,
                DeleteCompletedTasksCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

