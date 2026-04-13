package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.employee.Department;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.Phone;
import seedu.address.model.employee.Position;
import seedu.address.model.employee.Task;
import seedu.address.model.employee.TaskListStorage;
import seedu.address.storage.TaskList;

public class TaskListTest {

    @Test
    public void addsTasksToTaskList_updatesTaskListCorrectly() {
        TaskList taskList = new TaskList();

        Task t1 = new Task("task1", "desc1", 1);

        Employee person = new Employee(new Name("John Doe"), new Phone("96969696"),
                new Email("anirudhnush@gmail.com"), new Department("IT"), new Position("Developer"),
                new HashSet<>(), new TaskListStorage(new ArrayList<>()));

        taskList.addTaskOverall(t1, person);
        assertEquals("Full Task List:\n1. task1 - Assigned to: John Doe\n",
                taskList.showFullTaskList());

    }

    @Test
    public void getTaskByIndex_existingIndex_returnsTask() {
        TaskList taskList = new TaskList();
        Task task = new Task("task1", "desc1", 1);
        Employee person = new Employee(new Name("John Doe"), new Phone("96969696"),
                new Email("johnd@example.com"), new Department("IT"), new Position("Developer"),
                new HashSet<>(), new TaskListStorage(new ArrayList<>()));

        taskList.addTaskOverall(task, person);

        Optional<Task> result = taskList.getTaskByIndex(1);
        assertTrue(result.isPresent());
        assertEquals(task, result.get());
    }

    @Test
    public void getTaskByIndex_nonExistingIndex_returnsEmpty() {
        TaskList taskList = new TaskList();
        assertFalse(taskList.getTaskByIndex(999).isPresent());
    }

    @Test
    public void replaceTask_existingIndex_replacesTaskAndPreservesEmployee() {
        TaskList taskList = new TaskList();
        Task originalTask = new Task("Original", "Original desc", 1);
        Task newTask = new Task("Updated", "Updated desc", 1);
        Employee person = new Employee(new Name("John Doe"), new Phone("96969696"),
                new Email("johnd@example.com"), new Department("IT"), new Position("Developer"),
                new HashSet<>(), new TaskListStorage(new ArrayList<>()));

        taskList.addTaskOverall(originalTask, person);
        Employee assignedPerson = taskList.replaceTask(1, newTask);

        assertEquals(person, assignedPerson);
        assertTrue(taskList.getTaskByIndex(1).isPresent());
        assertEquals(newTask, taskList.getTaskByIndex(1).get());
        assertFalse(taskList.getTaskByIndex(1).get().equals(originalTask));
    }

    @Test
    public void replaceTask_nullNewTask_throwsNullPointerException() {
        TaskList taskList = new TaskList();
        assertThrows(NullPointerException.class, () -> taskList.replaceTask(1, null));
    }

    @Test
    public void deleteTask_removesTaskByTaskIndex() {
        TaskList taskList = new TaskList();

        Task t1 = new Task("task1", "desc1", 1);
        Employee person = new Employee(new Name("John Doe"), new Phone("96969696"),
                new Email("anirudhnush@gmail.com"), new Department("IT"), new Position("Developer"),
                new HashSet<>(), new TaskListStorage(new ArrayList<>()));

        taskList.addTaskOverall(t1, person);
        assertEquals(t1, taskList.deleteTask(1));
        assertEquals("No tasks assigned.", taskList.showFullTaskList());
    }

    @Test
    public void emptyTaskList_showFullTaskList_returnsNoTasksMessage() {
        TaskList taskList = new TaskList();
        Task t1 = new Task("task1", "desc1", 1);
        Employee person = new Employee(new Name("John Doe"), new Phone("96969696"),
                new Email("anirudhnush@gmail.com"), new Department("IT"), new Position("Developer"),
                new HashSet<>(), new TaskListStorage(new ArrayList<>()));

        taskList.addTaskOverall(t1, person);
        taskList.clear();

        assertEquals("No tasks assigned.", taskList.showFullTaskList());
    }
}
