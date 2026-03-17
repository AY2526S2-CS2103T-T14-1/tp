package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Task;

/**
 * A class to represent the storage of TaskList.
 */
public class TaskListStorage {
    private ArrayList<Task> tasks;

    /**
     * Constructor for TaskListStorage.
     * @param tasks the list of tasks to be stored.
     */
    public TaskListStorage(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the list of tasks stored.
     * @return the list of tasks stored.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        String result = "";

        for (Task task : tasks) {
            result += task.toString() + "\n";
        }
        return result;
    }

    public void addTask(Task modelType) {
        tasks.add(modelType);
    }
}
