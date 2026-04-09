package seedu.address.model.employee;

import java.util.ArrayList;

/**
 * A class to represent the storage of TaskList.
 */
public class TaskListStorage {
    private ArrayList<Task> tasks;

    /**
     * Constructor for TaskListStorage.
     *
     * @param tasks the list of tasks to be stored.
     */
    public TaskListStorage(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the list of tasks stored.
     *
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

    /**
     * Adds a task to the list of tasks stored.
     *
     * @param modelType the task to be added.
     */
    public void addTask(Task modelType) {
        tasks.add(modelType);

    }

    /**
     * Deletes a task from the list of tasks stored.
     *
     * @param task the task to be deleted.
     * @return true if the task was successfully deleted, false otherwise.
     */
    public boolean deleteTask(Task task) {
        return tasks.remove(task);
    }

    /**
     * Removes all tasks from the list of stored tasks.
     *
     * @return the number of tasks removed
     */
    public int clearTasks() {
        int numberOfTasks = tasks.size();
        tasks.clear();
        return numberOfTasks;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TaskListStorage)) {
            return false;
        }

        TaskListStorage otherTaskListStorage = (TaskListStorage) other;
        return tasks.equals(otherTaskListStorage.getTasks());
    }
}
