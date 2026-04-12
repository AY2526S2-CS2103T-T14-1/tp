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
import seedu.address.testutil.PersonBuilder;

class ClearTasksCommandTest {

    private ModelManager model;
    private Employee employeeWithTasks;
    private Task firstTask;
    private Task secondTask;

    @BeforeEach
    void setUp() {
        firstTask = new Task("Finish Homework", "Complete math homework by tomorrow", 1);
        secondTask = new Task("Review PR", "Review the open pull request", 2);
        TaskListStorage taskListStorage = new TaskListStorage(new ArrayList<>());
        taskListStorage.addTask(firstTask);
        taskListStorage.addTask(secondTask);

        employeeWithTasks = new Employee(
                new Name("John Doe"),
                new Phone("12345678"),
                new Email("johnd@example.com"),
                new Department("IT"),
                new Position("Developer"),
                Collections.emptySet(),
                taskListStorage
        );

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(employeeWithTasks);
        model = new ModelManager(addressBook, new UserPrefs());
        model.addTaskOverall(firstTask, employeeWithTasks);
        model.addTaskOverall(secondTask, employeeWithTasks);
    }

    @Test
    void execute_validIndex_clearsTasksSuccessfully() throws Exception {
        ClearTasksCommand command = new ClearTasksCommand(1);

        CommandResult result = command.execute(model);

        assertEquals(String.format(ClearTasksCommand.MESSAGE_SUCCESS, "John Doe",
                firstTask + "\n" + secondTask),
                result.getFeedbackToUser());
        Employee updatedEmployee = model.getAddressBook().getPersonList().get(0);
        assertTrue(updatedEmployee.getTaskListStorage().getTasks().isEmpty());
    }

    @Test
    void execute_validName_clearsTasksSuccessfully() throws Exception {
        ClearTasksCommand command = new ClearTasksCommand("John Doe");

        CommandResult result = command.execute(model);

        assertEquals(String.format(ClearTasksCommand.MESSAGE_SUCCESS, "John Doe",
                firstTask + "\n" + secondTask),
                result.getFeedbackToUser());
        Employee updatedEmployee = model.getAddressBook().getPersonList().get(0);
        assertTrue(updatedEmployee.getTaskListStorage().getTasks().isEmpty());
    }

    @Test
    void execute_invalidIndex_throwsCommandException() {
        ClearTasksCommand command = new ClearTasksCommand(999);
        assertCommandFailure(command, model, ClearTasksCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    void execute_invalidName_throwsCommandException() {
        ClearTasksCommand command = new ClearTasksCommand("John@Doe");
        assertCommandFailure(command, model, ClearTasksCommand.MESSAGE_INVALID_NAME);
    }

    @Test
    void execute_employeeNotFound_throwsCommandException() {
        ClearTasksCommand command = new ClearTasksCommand("Unknown Person");
        assertCommandFailure(command, model,
                String.format(ClearTasksCommand.MESSAGE_EMPLOYEE_NOT_FOUND, "Unknown Person"));
    }

    @Test
    void execute_duplicateName_throwsCommandException() {
        ModelManager duplicateModel = new ModelManager(new AddressBook(), new UserPrefs());
        Employee firstDuplicate = new PersonBuilder().withName("Same Name")
                .withPhone("11111111").withEmail("same1@example.com").build();
        Employee secondDuplicate = new PersonBuilder().withName("Same Name")
                .withPhone("22222222").withEmail("same2@example.com").build();
        duplicateModel.addPerson(firstDuplicate);
        duplicateModel.addPerson(secondDuplicate);

        ClearTasksCommand command = new ClearTasksCommand("Same Name");
        assertCommandFailure(command, duplicateModel,
                String.format(ClearTasksCommand.MESSAGE_DUPLICATE_EMPLOYEE_NAME, "Same Name"));
    }

    @Test
    void equals() {
        ClearTasksCommand clearByIndex = new ClearTasksCommand(1);
        ClearTasksCommand clearByIndexCopy = new ClearTasksCommand(1);
        ClearTasksCommand clearByName = new ClearTasksCommand("John Doe");
        ClearTasksCommand clearByNameCopy = new ClearTasksCommand("John Doe");

        assertTrue(clearByIndex.equals(clearByIndex));
        assertTrue(clearByIndex.equals(clearByIndexCopy));
        assertTrue(clearByName.equals(clearByNameCopy));
        assertTrue(!clearByIndex.equals(clearByName));
        assertTrue(!clearByIndex.equals(null));
        assertTrue(!clearByIndex.equals(1));
    }
}
