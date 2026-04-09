package seedu.address.model.employee;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;


/**
 * Represents an Employee in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Employee {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Department department;
    private final Position position;
    private final Set<Tag> tags = new LinkedHashSet<>();
    private final TaskListStorage taskListStorage;

    /**
     * Every field must be present and not null.
     */
    public Employee(Name name, Phone phone, Email email, Department department,
                    Position position, Set<Tag> tags, TaskListStorage taskListStorage) {
        requireAllNonNull(name, phone, email, department, position, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.department = department;
        this.position = position;
        this.tags.addAll(tags);
        this.taskListStorage = taskListStorage;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Department getDepartment() {
        return department;
    }

    public Position getPosition() {
        return position;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both employees have the same name (case-insensitive) and
     * same phone or same email.
     */
    public boolean isSameEmployee(Employee otherPerson) {
        if (otherPerson == null) {
            return false;
        }

        if (otherPerson == this) {
            return true;
        }

        boolean isSameName = name.equalsIgnoreCase(otherPerson.getName());
        boolean isSamePhone = phone.equals(otherPerson.phone);
        boolean isSameEmail = email.equals(otherPerson.email);

        return isSameName && (isSamePhone || isSameEmail);
    }

    /**
     * Returns the TaskListStorage associated with the employee.
     *
     * @return the TaskListStorage associated with the employee
     */
    public TaskListStorage getTaskListStorage() {
        return taskListStorage;
    }

    /**
     * Returns true if both employees have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Employee)) {
            return false;
        }

        Employee otherPerson = (Employee) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && position.equals(otherPerson.position)
                && department.equals(otherPerson.department)
                && tags.equals(otherPerson.tags)
                && taskListStorage.equals(otherPerson.taskListStorage);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, position, tags, taskListStorage);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("position", position)
                .add("department", department)
                .add("tags", tags)
                .add("Tasks Assigned:", taskListStorage)
                .toString();
    }

    /**
     * Returns the list of tasks assigned to the employee.
     *
     * @return the list of tasks assigned to the employee
     */
    public ArrayList<Task> getTasks() {
        return taskListStorage.getTasks();
    }

    /**
     * Adds a task to the employee's task list.
     *
     * @param task the task to be added
     */
    public void addTask(Task task) {
        taskListStorage.addTask(task);
    }

    /**
     * Deletes a task from the employee's task list.
     *
     * @param task the task to be deleted
     */
    public void deleteTask(Task task) {
        taskListStorage.deleteTask(task);
    }

    /**
     * Clears all tasks from the employee's task list.
     *
     * @return the number of tasks removed
     */
    public int clearTasks() {
        return taskListStorage.clearTasks();
    }
}
