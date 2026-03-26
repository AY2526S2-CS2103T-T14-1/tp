package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Task;
import seedu.address.model.employee.UniquePersonList;

/**
 * A list of tasks.
 */
public class TaskList {

    private final Map<Task, Employee> internalMap = new HashMap<>();
    private static int latestTaskIndex = 1;

    public TaskList(FilteredList<Employee> employees) {
        requireNonNull(employees);

        for (Employee person : employees) {
            for (Task task : person.getTasks()) {
                if (task.getCurrentTaskIndex() > latestTaskIndex) {
                    latestTaskIndex = task.getCurrentTaskIndex();
                }
                internalMap.put(task, person);
                Task.setTaskIndex(latestTaskIndex + 1);
            }
        }


    }

    public static int getLatestTaskIndex() {
        return latestTaskIndex;
    }

    /**
     * Adds a task to the list with the assigned employee.
     * @param task the task to be added.
     * @param person the employee to whom the task is assigned.
     */
    public void addTaskOverall(Task task, Employee person) {
        requireNonNull(task);
        requireNonNull(person);
        internalMap.put(task, person);

    }

    /**
     * Returns a string representation of all tasks and their assigned employees.
     * @return a string listing all tasks and their assigned employees.
     */
    public String showFullTaskList() {
        if (internalMap.isEmpty()) {
            return "No tasks assigned.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Full Task List:\n");
        int index = 1;
        for (Map.Entry<Task, Employee> entry : internalMap.entrySet()) {
            Task task = entry.getKey();
            Employee person = entry.getValue();
            sb.append(index)
                    .append(". ")
                    .append(task.getTaskName())
                    .append(" - Assigned to: ")
                    .append(person.getName())
                    .append("\n");

            index++;
        }
        return sb.toString();
    }

    /**
     * Deletes and returns the task with the specified task index.
     *
     * @param taskIndex the displayed task index.
     * @return the deleted task.
     */
    public Task deleteTask(int taskIndex) {
        Entry<Task, Employee> entry = findEntryByTaskIndex(taskIndex);
        internalMap.remove(entry.getKey());
        return entry.getKey();
    }

    /**
     * Returns the employee assigned to the task with the specified task index.
     *
     * @param taskIndex the displayed task index.
     * @return the employee assigned to the task.
     */
    public Employee getPersonAssignedToTask(int taskIndex) {
        return findEntryByTaskIndex(taskIndex).getValue();
    }

    private Entry<Task, Employee> findEntryByTaskIndex(int taskIndex) {
        for (Entry<Task, Employee> entry : internalMap.entrySet()) {
            if (entry.getKey().getCurrentTaskIndex() == taskIndex) {
                return entry;
            }
        }
        throw new NoSuchElementException();
    }

    private String extractPersonDetails(Employee person) {

        return "n/" + person.getName() + " " + "p/" + person.getPhone() + " " + "e/" + person.getEmail() + " " + "d/"
                + person.getDepartment() + " " + "pos/" + person.getPosition() + " " + "t/"
                + person.getTags() + " " + "task/" + person.getTaskListStorage();
    }
}
