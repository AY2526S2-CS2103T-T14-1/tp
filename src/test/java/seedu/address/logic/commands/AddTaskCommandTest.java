package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
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

class AddTaskCommandTest {

    private Model model;
    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        testEmployee = new Employee(
                new Name("John Doe"),
                new Phone("12345678"),
                new Email("johnd@example.com"),
                new Department("IT"),
                new Position("Developer"),
                Collections.emptySet(),
                new TaskListStorage(new ArrayList<>())
        );
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(testEmployee);
        model = new ModelManager(addressBook, new UserPrefs());
    }

    @Test
    void execute_employeeExists_taskAddedSuccessfully() {
        Task task = new Task("Finish Homework", "Complete math homework by tomorrow", 0);
        AddTaskCommand command = new AddTaskCommand(task, 1);

        CommandResult result = command.execute(model);

        // Check message
        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, task), result.getFeedbackToUser());

        // Check that the task was added to the employee
        Employee updatedEmployee = model.getAddressBook().getPersonList().get(0);
        assertTrue(updatedEmployee.getTaskListStorage().getTasks().contains(task));
    }

    @Test
    void execute_employeeNotFound_returnsErrorMessage() {
        Task task = new Task("Finish Homework", "Complete math homework by tomorrow", 0);
        AddTaskCommand command = new AddTaskCommand(task, 50);

        CommandResult result = command.execute(model);

        assertEquals("Person not found in the address book.", result.getFeedbackToUser());
    }

    @Test
    void execute_duplicateTask_returnsDuplicateMessage() {
        Task task = new Task("Finish Homework", "Complete math homework by tomorrow", 0);
        model.addTaskToPerson(testEmployee, task);

        AddTaskCommand command = new AddTaskCommand(task, 1);
        CommandResult result = command.execute(model);

        assertEquals("Task already exists for this user", result.getFeedbackToUser());
    }

    @Test
    void equals() {
        Task task1 = new Task("Task1", "Desc1", 0);
        Task task2 = new Task("Task2", "Desc2", 0);

        AddTaskCommand command1 = new AddTaskCommand(task1, 1);
        AddTaskCommand command2 = new AddTaskCommand(task1, 1);
        AddTaskCommand command3 = new AddTaskCommand(task2, 1);
        AddTaskCommand command4 = new AddTaskCommand(task1, 2);

        assertTrue(command1.equals(command2));

        assertTrue(!command1.equals(command3));

        assertTrue(!command1.equals(command4));

        assertTrue(command1.equals(command1));

        assertTrue(!command1.equals(null));

        assertTrue(!command1.equals(new Object()));
    }

}
