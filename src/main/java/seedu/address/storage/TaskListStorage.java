package seedu.address.storage;

import java.util.List;

import seedu.address.model.person.Task;

/**
 * A class to represent the storage of TaskList.
 */
public class TaskListStorage {
    private List<Task> tasks;

    /**
     * Constructor for TaskListStorage.
     * @param tasks the list of tasks to be stored.
     */
    TaskListStorage(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }




}
