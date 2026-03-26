package seedu.address.model.employee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import seedu.address.storage.TaskList;

/**
 * A class to represent a Task.
 */
public class Task {
    public static final String MESSAGE_CONSTRAINTS_TASK_NAME = "Enter a valid task name.";
    public static final String MESSAGE_CONSTRAINTS_TASK_DESCRIPTION = "Enter a valid task description.";

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
                && taskDescription.equals(otherTask.taskDescription)
                && currentTaskIndex == otherTask.currentTaskIndex;
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

    public static int loadTaskIndex() {
        File file = new File("taskindex.txt");

        if (!file.exists()) {
            return 1;

        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            return 1;
        }

        return 1;

    }

    public static int readTaskIndex() {
        try (BufferedReader reader = new BufferedReader(new FileReader("taskindex.txt"))) {
            String line = reader.readLine();
            return Integer.parseInt(line.trim());
        } catch (Exception e) {
            return 1;
        }
    }

    public static void incrementAndSaveTaskIndex() {
        int current = readTaskIndex();
        int updated = current + 1;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("taskindex.txt"))) {
            writer.write(String.valueOf(updated));
        } catch (IOException e) {
            System.err.println("Error writing task index: " + e.getMessage());
        }
    }

    public static void setTaskIndex(int index) {
        taskIndex = index;
    }




}
