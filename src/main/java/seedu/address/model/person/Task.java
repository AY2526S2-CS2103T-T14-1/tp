package seedu.address.model.person;

/**
 * A class to represent a Task.
 */
public class Task {
    public static final String MESSAGE_CONSTRAINTS_TASK_NAME = "Enter a valid task name.";
    public static final String MESSAGE_CONSTRAINTS_TASK_DESCRIPTION = "Enter a valid task description.";
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

    public static boolean isValidTaskName(String taskName) {
        return taskName != null && !taskName.trim().isEmpty();
    }

    public static boolean isValidTaskDescription(String taskDescription) {
        return taskDescription != null && !taskDescription.trim().isEmpty();
    }

    /**
     * Returns the name of the task.
     * @return the name of the task.
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Returns the description of the task.
     * @return the description of the task.
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    @Override
    public String toString() {
        return taskName + ": " + taskDescription;
    }


}
