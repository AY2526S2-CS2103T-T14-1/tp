package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

class DeleteTaskCommandTest {

    private ModelManager model;
    private Task firstTask;
    private Task secondTask;

    @BeforeEach
    void setUp() {
        firstTask = new Task("Finish Homework", "Complete math homework by tomorrow", 1);
        secondTask = new Task("Review PR", "Review the open pull request", 2);
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
    void execute_validIndex_taskDeletedSuccessfully() throws Exception {
        DeleteTaskCommand command = new DeleteTaskCommand(List.of(1));

        CommandResult result = command.execute(model);

        assertEquals(String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, firstTask),
                result.getFeedbackToUser());
        Employee updatedEmployee = model.getAddressBook().getPersonList().get(0);
        assertEquals(1, updatedEmployee.getTaskListStorage().getTasks().size());
    }

    @Test
    void execute_validIndices_tasksDeletedSuccessfully() throws Exception {
        DeleteTaskCommand command = new DeleteTaskCommand(List.of(1, 2));

        CommandResult result = command.execute(model);

        assertEquals(String.format(DeleteTaskCommand.MESSAGE_DELETE_TASKS_SUCCESS,
                firstTask + "\n" + secondTask), result.getFeedbackToUser());
        Employee updatedEmployee = model.getAddressBook().getPersonList().get(0);
        assertTrue(updatedEmployee.getTaskListStorage().getTasks().isEmpty());
    }

    @Test
    void execute_invalidIndex_throwsCommandException() {
        DeleteTaskCommand command = new DeleteTaskCommand(List.of(999));
        assertCommandFailure(command, model, DeleteTaskCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    void execute_duplicateIndices_throwsCommandException() {
        DeleteTaskCommand command = new DeleteTaskCommand(List.of(1, 1));
        assertCommandFailure(command, model, DeleteTaskCommand.MESSAGE_DUPLICATE_INDEX);
    }
}
