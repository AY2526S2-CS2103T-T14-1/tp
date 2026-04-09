package seedu.address.model.employee;

import java.util.Objects;


/**
 * A class to represent a Task.
 */
public class Task {
    public static final String MESSAGE_CONSTRAINTS_TASK_NAME = "Task name should not be empty and "
            + "should be between 1 and 50 characters.";
    public static final String MESSAGE_CONSTRAINTS_TASK_DESCRIPTION = "Task description should not be empty and "
            + "should be between 1 and 200 characters.";

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


    public static boolean isValidTaskName(String taskName) {
        return taskName != null && !taskName.trim().isEmpty();
    }

    public static boolean isValidTaskDescription(String taskDescription) {
        return taskDescription != null && !taskDescription.trim().isEmpty();
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
