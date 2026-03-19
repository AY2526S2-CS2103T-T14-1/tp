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
import seedu.address.model.person.Task;
import seedu.address.storage.TaskListStorage;

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
        Task task = new Task("Finish Homework", "Complete math homework by tomorrow");
        AddTaskCommand command = new AddTaskCommand(task, "John Doe");

        CommandResult result = command.execute(model);

        // Check message
        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, task), result.getFeedbackToUser());

        // Check that the task was added to the employee
        Employee updatedEmployee = model.getAddressBook().getPersonList().get(0);
        assertTrue(updatedEmployee.getTaskListStorage().getTasks().contains(task));
    }

}
