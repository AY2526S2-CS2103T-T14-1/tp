package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void getTaskName_taskName_success() {
        Task task = new Task("Task 1", "Description 1");
        assertTrue(task.getTaskName().equals("Task 1"));

    }

    @Test
    public void getTaskDescription_taskDescription_success() {
        Task task = new Task("Task 1", "Description 1");
        assertTrue(task.getTaskDescription().equals("Description 1"));

    }
}
