package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Employee> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Employee person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Employee target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Employee person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Employee target, Employee editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Employee> getFilteredPersonList();

    /**
     * Returns the existing employee with the same phone number, or null if none exists.
     */
    Employee getEmployeeWithSamePhone(Employee employee);

    /**
     * Returns the existing employee with the same email, or null if none exists.
     */
    Employee getEmployeeWithSameEmail(Employee employee);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Employee> predicate);

    void addTaskToPerson(Employee person, Task task);

    void addTaskOverall(Task task, Employee person);

    void showAllTasks();

    void deleteTask(int taskIndex);

    /**
     * Clears all tasks assigned to the specified employee.
     *
     * @param employee the employee whose tasks should be cleared
     * @return the number of tasks removed
     */
    int clearTasksForPerson(Employee employee);

    /**
     * Returns the task with the given {@code taskIndex}, or {@code Optional#empty()}
     * if no such task exists
     */
    Optional<Task> getTaskByIndex(int taskIndex);

    /**
     * Replaces the task identified by {@code taskIndex} with {@code newTask},
     * updating both the overall task list and the owning employee's task list.
     */
    void setTask(int taskIndex, Task newTask);

    /**
     * Returns existing task in employee if the same task exists, or null if none exists.
     * @param task Task to be checked against.
     * @param person Person as to which the task is to be added.
     * @return Task if it already exists in the employee's record.
     */
    Task getTaskWithSameDescription(Task task, Employee person);
}
