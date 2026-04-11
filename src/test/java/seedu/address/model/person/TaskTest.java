package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.employee.Task;

class TaskTest {

    @Test
    void constructor_andGetters_workCorrectly() {
        Task task = new Task("Homework", "Finish math exercises", 0);

        assertEquals("Homework", task.getTaskName());
        assertEquals("Finish math exercises", task.getTaskDescription());
        assertEquals(0, task.getCurrentTaskIndex());
    }

    @Test
    void isValidTaskName_validAndInvalidCases() {
        assertTrue(Task.isValidTaskName("Task 1"));
        assertTrue(Task.isValidTaskName("Do homework"));
        assertTrue(Task.isValidTaskName("a".repeat(Task.MAX_TASK_NAME_LENGTH))); //max task name length
        assertFalse(Task.isValidTaskName(null));
        assertFalse(Task.isValidTaskName(""));
        assertFalse(Task.isValidTaskName("   "));
        assertFalse(Task.isValidTaskName("a".repeat(Task.MAX_TASK_NAME_LENGTH + 1))); //exceeds max length
    }

    @Test
    void isValidTaskDescription_validAndInvalidCases() {
        assertTrue(Task.isValidTaskDescription("2024-06-30"));
        assertTrue(Task.isValidTaskDescription("Complete the assignment"));
        assertFalse(Task.isValidTaskDescription(null));
        assertFalse(Task.isValidTaskDescription(""));
        assertFalse(Task.isValidTaskDescription("   "));
    }

    @Test
    void toString_returnsCorrectFormat() {
        Task task = new Task("Task 1", "2024-06-30", 0);
        assertEquals("#0 Task 1: 2024-06-30", task.toString());
    }

    @Test
    void equals_sameObject_returnsTrue() {
        Task firstTask = new Task("Task 1", "2024-06-30", 0);
        Task secondTask = new Task("Task 1", "2024-06-30", 1);
        assertTrue(firstTask.equals(secondTask));
    }
}
