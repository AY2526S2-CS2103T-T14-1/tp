package seedu.address.model.employee;

import java.util.Objects;


/**
 * A class to represent a Task.
 */
public class Task {
    public static final String MESSAGE_CONSTRAINTS_TASK_NAME = "Task name should not be empty and "
            + "should be between 1 and 40 characters.";
    public static final String MESSAGE_CONSTRAINTS_TASK_DESCRIPTION = "Task description should not be empty and "
            + "should be between 1 and 120 characters.";

    public static final int MAX_TASK_NAME_LENGTH = 40;
    public static final int MAX_TASK_DESCRIPTION_LENGTH = 120;

    private static int taskIndex = 1;

    private String taskName;
    private String taskDescription;
    private int currentTaskIndex;

    /**
     * Constructor for Task.
     *
     * @param taskName        the name of the task.
     * @param taskDescription the description of the task.
     */
    public Task(String taskName, String taskDescription, int currentTaskIndex) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.currentTaskIndex = currentTaskIndex;

    }


    /**
     * Returns true if a given string is a valid task name.
     *
     * @param taskName the task name to validate.
     * @return true if {@code taskName} is non-null, non-blank, and does not exceed
     *         {@value MAX_TASK_NAME_LENGTH} characters.
     */
    public static boolean isValidTaskName(String taskName) {
        return taskName != null && !taskName.trim().isEmpty()
                                && taskName.trim().length() <= MAX_TASK_NAME_LENGTH;
    }

    /**
     * Returns true if a given string is a valid task description.
     *
     * @param taskDescription the task description to validate.
     * @return true if {@code taskDescription} is non-null, non-blank, and does not exceed
     *         {@value MAX_TASK_DESCRIPTION_LENGTH} characters.
     */
    public static boolean isValidTaskDescription(String taskDescription) {
        return taskDescription != null && !taskDescription.trim().isEmpty()
                && taskDescription.trim().length() <= MAX_TASK_DESCRIPTION_LENGTH;
    }

    /**
     * Returns the name of the task.
     *
     * @return The task name.
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    public int getCurrentTaskIndex() {
        return currentTaskIndex;
    }

    /**
     * Returns the string representation of the task.
     *
     * @return A string in the format "taskName: taskDescription".
     */
    @Override
    public String toString() {
        return "#" + currentTaskIndex + " " + taskName + ": " + taskDescription;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return taskName.equals(otherTask.taskName)
                && taskDescription.equals(otherTask.taskDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskDescription, currentTaskIndex);
    }

    public void incrementTaskIndex() {
        taskIndex++;
    }

    public static int getOverallIndex() {
        return taskIndex;
    }


    public static void setTaskIndex(int index) {
        taskIndex = index;
    }


}
