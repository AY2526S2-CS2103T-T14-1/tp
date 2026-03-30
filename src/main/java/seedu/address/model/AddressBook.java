package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Task;
import seedu.address.model.employee.UniquePersonList;
import seedu.address.storage.TaskList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameEmployee comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;


    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();

    }


    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Employee> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Employee person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Employee p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Employee target, Employee editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Employee key) {
        persons.remove(key);
    }

    /**
     * Deletes the task with the specified task index from the overall task list
     * and the assigned employee's task list.
     *
     * @param taskIndex the displayed task index
     */
    public void deleteTask(int taskIndex, TaskList tasks) {
        Employee assignedPerson = tasks.getPersonAssignedToTask(taskIndex);
        Task deletedTask = tasks.deleteTask(taskIndex);
        persons.deleteTaskFromPerson(assignedPerson, deletedTask);
    }





    /**
     * Adds a task to the task list.
     */
    public void addTask(Task task, Employee person, TaskList tasks) {
        tasks.addTaskOverall(task, person);
    }

    public void showAllTask(TaskList tasks) {
        tasks.showFullTaskList();
    }

    /**
     * Replaces the task at {@code taskIndex} with {@code newTask} in both the overall
     * task list and the owning employee's individual task list.
     *
     * @param taskIndex the task index of the task to edit.
     * @param newTask   the new edited task.
     * @param tasks     the overall task list to update.
     */
    public void setTask(int taskIndex, Task newTask, TaskList tasks) {
        requireNonNull(newTask);
        Optional<Task> oldTaskOpt = tasks.getTaskByIndex(taskIndex);
        assert oldTaskOpt.isPresent() : "setTask called with non-existent taskIndex: " + taskIndex;
        Task oldTask = oldTaskOpt.get();

        Employee assignedPerson = tasks.replaceTask(taskIndex, newTask);
        persons.replaceTaskForPerson(assignedPerson, oldTask, newTask);
    }



    /// / util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Employee> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    /**
     * Adds a task to the specified person.
     *
     * @param target The person to add the task to.
     * @param task   The task to be added.
     */
    public void addTaskToPerson(Employee target, Task task) {
        requireNonNull(task);
        persons.addTaskToPerson(target, task);
    }
}
