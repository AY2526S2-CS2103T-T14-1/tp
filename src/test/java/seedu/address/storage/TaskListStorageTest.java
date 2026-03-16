package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class TaskListStorageTest {
    @Test
    public void constructor_validTasks_success() {
        TaskListStorage taskListStorage = new TaskListStorage(null);
        assertTrue(taskListStorage != null);
    }

    @Test
    public void getTasks_validTasks_success() {
        TaskListStorage taskListStorage = new TaskListStorage(null);
        assertTrue(taskListStorage.getTasks() == null);

    }

}
