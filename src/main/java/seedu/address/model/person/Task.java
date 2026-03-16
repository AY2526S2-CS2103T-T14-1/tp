package seedu.address.model.person;

/**
 * A class to represent a Task.
 */
public class Task {
    private String taskName;
    private String taskDescription;

    /**
     * Constructor for Task.
     * @param taskName the name of the task.
     * @param taskDescription the description of the task.
     */
    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    @Override
    public String toString() {
        return taskName + ": " + taskDescription;
    }


}
