package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.employee.Department;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.Phone;
import seedu.address.model.employee.Position;
import seedu.address.model.employee.Task;
import seedu.address.model.employee.TaskListStorage;
import seedu.address.model.employee.UniquePersonList;
import seedu.address.storage.TaskList;

public class TaskListTest {

    @Test
    public void addsTasksToTaskList_updatesTaskListCorrectly() {
        TaskList taskList = new TaskList(new UniquePersonList());

        Task t1 = new Task("task1", "desc1", 1);

        Employee person = new Employee(new Name("John Doe"), new Phone("96969696"),
                new Email("anirudhnush@gmail.com"), new Department("IT"), new Position("Developer"),
                new HashSet<>(), new TaskListStorage(new ArrayList<>()));

        taskList.addTaskOverall(t1, person);
        assertEquals("Full Task List:\n1. task1 - Assigned to: John Doe\n",
                taskList.showFullTaskList());

    }

    @Test
    public void deleteTask_removesTaskByTaskIndex() {
        TaskList taskList = new TaskList(new UniquePersonList());

        Task t1 = new Task("task1", "desc1", 1);
        Employee person = new Employee(new Name("John Doe"), new Phone("96969696"),
                new Email("anirudhnush@gmail.com"), new Department("IT"), new Position("Developer"),
                new HashSet<>(), new TaskListStorage(new ArrayList<>()));

        taskList.addTaskOverall(t1, person);
        assertEquals(t1, taskList.deleteTask(1));
        assertEquals("No tasks assigned.", taskList.showFullTaskList());
    }
}
