package seedu.address.storage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Task;

class JsonAdaptedTaskTest {

    private static final String VALID_TASK_NAME = "Finish Report";
    private static final String VALID_TASK_DESCRIPTION = "Due by 2024-06-30";
    private static final String INVALID_TASK_NAME = "";
    private static final String INVALID_TASK_DESCRIPTION = "";

    @Test
    void toModelType_validTask_success() throws Exception {
        Task task = new Task(VALID_TASK_NAME, VALID_TASK_DESCRIPTION);
        JsonAdaptedTask adaptedTask = new JsonAdaptedTask(task);
        Task convertedTask = adaptedTask.toModelType();
        assertEquals(task, convertedTask);
    }

    @Test
    void toModelType_nullTaskName_throwsIllegalValueException() {
        JsonAdaptedTask adaptedTask = new JsonAdaptedTask(null, VALID_TASK_DESCRIPTION);
        assertThrows(IllegalValueException.class, adaptedTask::toModelType);
    }

    @Test
    void toModelType_invalidTaskName_throwsIllegalValueException() {
        JsonAdaptedTask adaptedTask = new JsonAdaptedTask(INVALID_TASK_NAME, VALID_TASK_DESCRIPTION);
        assertThrows(IllegalValueException.class, adaptedTask::toModelType);
    }

    @Test
    void toModelType_nullTaskDescription_throwsIllegalValueException() {
        JsonAdaptedTask adaptedTask = new JsonAdaptedTask(VALID_TASK_NAME, null);
        assertThrows(IllegalValueException.class, adaptedTask::toModelType);
    }

    @Test
    void toModelType_invalidTaskDescription_throwsIllegalValueException() {
        JsonAdaptedTask adaptedTask = new JsonAdaptedTask(VALID_TASK_NAME, INVALID_TASK_DESCRIPTION);
        assertThrows(IllegalValueException.class, adaptedTask::toModelType);
    }
}
