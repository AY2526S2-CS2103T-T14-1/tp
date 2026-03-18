package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of tasks.
 */
public class TaskList {

    private final List<Task> internalList = new ArrayList<>();

    public void addTask(Task task) {
        requireNonNull(task);
        internalList.add(task);
    }

    public void removeCompletedTasks() {
        internalList.removeIf(Task::isCompleted);
    }
}