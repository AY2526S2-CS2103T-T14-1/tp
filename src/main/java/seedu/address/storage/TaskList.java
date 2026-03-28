package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Task;

/**
 * A list of tasks.
 */
public class TaskList {

    private static int latestTaskIndex = 1;
    private final Map<Task, Employee> internalMap = new HashMap<>();

    /**
     * Constructor for TaskList
     * @param employees Filtered List of Employees.
     */
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

    /**
     * Empty TaskList Constructor for testing.
     */
    public TaskList() {

    }


    /**
     * Adds a task to the list with the assigned employee.
     *
     * @param task   the task to be added.
     * @param person the employee to whom the task is assigned.
     */
    public void addTaskOverall(Task task, Employee person) {
        requireNonNull(task);
        requireNonNull(person);
        internalMap.put(task, person);

    }

    /**
     * Returns a string representation of all tasks and their assigned employees.
     *
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

    /**
     * Returns the task with the specified task index
     * or {@code Optional#empty()} if no such task exists.
     *
     * @param taskIndex the task index to look up.
     */
    public Optional<Task> getTaskByIndex(int taskIndex) {
        return internalMap.keySet().stream()
                .filter(task -> task.getCurrentTaskIndex() == taskIndex)
                .findFirst();
    }

    /**
     * Returns the employee assigned to the edited task
     *
     * @param taskIndex the task index of the task to edit.
     * @param newTask   the edited task.
     * @return the {@code Employee} that was assigned to the replaced task.
     */
    public Employee replaceTask(int taskIndex, Task newTask) {
        requireNonNull(newTask);
        Entry<Task, Employee> entry = findEntryByTaskIndex(taskIndex);
        Employee assignedPerson = entry.getValue();
        internalMap.remove(entry.getKey());
        internalMap.put(newTask, assignedPerson);
        return assignedPerson;
    }

    private Entry<Task, Employee> findEntryByTaskIndex(int taskIndex) {
        for (Entry<Task, Employee> entry : internalMap.entrySet()) {
            if (entry.getKey().getCurrentTaskIndex() == taskIndex) {
                return entry;
            }
        }
        throw new NoSuchElementException();
    }

}
