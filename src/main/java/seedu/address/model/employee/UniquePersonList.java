package seedu.address.model.employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.employee.exceptions.DuplicatePersonException;
import seedu.address.model.employee.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Employee#isSameEmployee(Employee)}. As such, adding and
 * updating of persons uses Employee#isSameEmployee(Employee) for equality so as to ensure that the person being added
 * or updated is unique in terms of identity in the UniquePersonList. However, the removal of a person uses
 * Employee#equals(Object) so as to ensure that the person with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Employee#isSameEmployee(Employee)
 */
public class UniquePersonList implements Iterable<Employee> {

    private final ObservableList<Employee> internalList = FXCollections.observableArrayList();
    private final ObservableList<Employee> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Employee toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEmployee);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Employee toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Employee target, Employee editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameEmployee(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Employee toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Employee> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Employee> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Employee> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePersonList)) {
            return false;
        }

        UniquePersonList otherUniquePersonList = (UniquePersonList) other;
        return internalList.equals(otherUniquePersonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Employee> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSameEmployee(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds a task to the specified person in the list.
     *
     * @param target The person to add the task to.
     * @param task   The task to be added.
     */
    public void addTaskToPerson(Employee target, Task task) {
        requireNonNull(task);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }
        Employee personToEdit = internalList.get(index);
        Employee editedPerson = new Employee(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getDepartment(), personToEdit.getPosition(),
                personToEdit.getTags(),
                personToEdit.getTaskListStorage());
        editedPerson.addTask(task);
        setPerson(personToEdit, editedPerson);
    }

    /**
     * Deletes the specified task from the specified person in the list.
     *
     * @param target The person whose task should be deleted.
     * @param task The task to delete.
     */
    public void deleteTaskFromPerson(Employee target, Task task) {
        requireAllNonNull(target, task);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }
        Employee personToEdit = internalList.get(index);
        Employee editedPerson = new Employee(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getDepartment(), personToEdit.getPosition(),
                personToEdit.getTags(),
                personToEdit.getTaskListStorage());
        editedPerson.deleteTask(task);
        setPerson(personToEdit, editedPerson);
    }


    /**
     * Replaces {@code oldTask} with {@code newTask} in the task list of {@code employee}.
     *
     * @param employee  the employee whose task should be edited.
     * @param oldTask the task to be edited
     * @param newTask the new edited task
     * @throws PersonNotFoundException if {@code employee} does not exist in the list.
     */
    public void replaceTaskForPerson(Employee employee, Task oldTask, Task newTask) {
        requireAllNonNull(employee, oldTask, newTask);
        int index = internalList.indexOf(employee);
        if (index == -1) {
            throw new PersonNotFoundException();
        }
        Employee personToEdit = internalList.get(index);
        Employee editedPerson = new Employee(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getDepartment(), personToEdit.getPosition(),
                personToEdit.getTags(), personToEdit.getTaskListStorage());
        editedPerson.deleteTask(oldTask);
        editedPerson.addTask(newTask);
        setPerson(personToEdit, editedPerson);
    }


}
