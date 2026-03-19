package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void constructor_andGetters_workCorrectly() {
        Task task = new Task("Homework", "Finish math exercises");

        assertEquals("Homework", task.getTaskName());
        assertEquals("Finish math exercises", task.getTaskDescription());
    }

    @Test
    void isValidTaskName_validAndInvalidCases() {
        assertTrue(Task.isValidTaskName("Task 1"));
        assertTrue(Task.isValidTaskName("Do homework"));
        assertFalse(Task.isValidTaskName(null));
        assertFalse(Task.isValidTaskName(""));
        assertFalse(Task.isValidTaskName("   "));
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
        Task task = new Task("Task 1", "2024-06-30");
        assertEquals("Task 1: 2024-06-30", task.toString());
    }
}
