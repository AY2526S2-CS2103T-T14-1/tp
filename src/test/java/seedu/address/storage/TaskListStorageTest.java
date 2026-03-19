package seedu.address.storage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Task;

class TaskListStorageTest {

    private TaskListStorage taskListStorage;
    private ArrayList<Task> initialTasks;

    @BeforeEach
    void setUp() {
        initialTasks = new ArrayList<>();
        initialTasks.add(new Task("Task 1", "2024-06-30"));
        initialTasks.add(new Task("Task 2", "2024-07-01"));

        taskListStorage = new TaskListStorage(new ArrayList<>(initialTasks));
    }

    @Test
    void getTasks_returnsCorrectList() {
        ArrayList<Task> tasks = taskListStorage.getTasks();
        assertEquals(initialTasks.size(), tasks.size());
        assertTrue(tasks.containsAll(initialTasks));
    }

    @Test
    void addTask_addsTaskSuccessfully() {
        Task newTask = new Task("Task 3", "2024-08-01");
        taskListStorage.addTask(newTask);

        ArrayList<Task> tasks = taskListStorage.getTasks();
        assertEquals(initialTasks.size() + 1, tasks.size());
        assertTrue(tasks.contains(newTask));
    }

    @Test
    void toString_returnsCorrectFormat() {
        String expected = "Task 1: 2024-06-30\nTask 2: 2024-07-01\n";
        assertEquals(expected, taskListStorage.toString());
    }
}
