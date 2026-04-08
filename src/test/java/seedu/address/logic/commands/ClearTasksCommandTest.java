package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Department;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.Phone;
import seedu.address.model.employee.Position;
import seedu.address.model.employee.Task;
import seedu.address.model.employee.TaskListStorage;

class ClearTasksCommandTest {

    private ModelManager model;

    @BeforeEach
    void setUp() {
        Task firstTask = new Task("Finish Homework", "Complete math homework by tomorrow", 1);
        Task secondTask = new Task("Review PR", "Review the open pull request", 2);
        TaskListStorage taskListStorage = new TaskListStorage(new ArrayList<>());
        taskListStorage.addTask(firstTask);
        taskListStorage.addTask(secondTask);

        Employee employee = new Employee(
                new Name("John Doe"),
                new Phone("12345678"),
                new Email("johnd@example.com"),
                new Department("IT"),
                new Position("Developer"),
                Collections.emptySet(),
                taskListStorage
        );

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(employee);
        model = new ModelManager(addressBook, new UserPrefs());
        model.addTaskOverall(firstTask, employee);
        model.addTaskOverall(secondTask, employee);
    }

    @Test
    void execute_validIndex_tasksClearedSuccessfully() throws Exception {
        ClearTasksCommand command = new ClearTasksCommand(1);

        CommandResult result = command.execute(model);

        assertEquals("Cleared 2 task(s) for employee: John Doe", result.getFeedbackToUser());
        Employee updatedEmployee = model.getAddressBook().getPersonList().get(0);
        assertTrue(updatedEmployee.getTaskListStorage().getTasks().isEmpty());
    }

    @Test
    void execute_validName_tasksClearedSuccessfully() throws Exception {
        ClearTasksCommand command = new ClearTasksCommand("John Doe");

        CommandResult result = command.execute(model);

        assertEquals("Cleared 2 task(s) for employee: John Doe", result.getFeedbackToUser());
        Employee updatedEmployee = model.getAddressBook().getPersonList().get(0);
        assertTrue(updatedEmployee.getTaskListStorage().getTasks().isEmpty());
    }

    @Test
    void execute_invalidIndex_throwsCommandException() {
        ClearTasksCommand command = new ClearTasksCommand(999);
        assertCommandFailure(command, model, ClearTasksCommand.MESSAGE_INVALID_INDEX);
    }
}
