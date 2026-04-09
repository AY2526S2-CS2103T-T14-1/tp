package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PRESENTATION;
import static seedu.address.logic.commands.CommandTestUtil.DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
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
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTaskCommand.
 */
public class EditTaskCommandTest {

    private static final int TASK_INDEX = 1;
    private static final String ORIGINAL_TASK_NAME = "Finish dry run";
    private static final String ORIGINAL_TASK_DESCRIPTION = "Complete demo dry run by Wednesday";
    private static final int INVALID_TASK_INDEX = 999;

    /**
     * Builds a {@code ModelManager}  with one employee who has one task at {@code TASK_INDEX}.
     */
    private ModelManager buildModelWithTask() {
        Task task = new Task(ORIGINAL_TASK_NAME, ORIGINAL_TASK_DESCRIPTION, TASK_INDEX);
        TaskListStorage taskListStorage = new TaskListStorage(new ArrayList<>());
        taskListStorage.addTask(task);

        Employee employee = new Employee(
                new Name("John Doe"), new Phone("12345678"),
                new Email("johnd@example.com"), new Department("IT"),
                new Position("Developer"), Collections.emptySet(), taskListStorage
        );

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(employee);
        ModelManager model = new ModelManager(addressBook, new UserPrefs());
        model.addTaskOverall(task, employee);
        return model;
    }

    /**
     * Retrieves the first task of the first employee in {@code model} and asserts
     * its name, description and index match the expected values.
     *
     * @param model the model to retrieve the task from.
     * @param expectedName the expected task name.
     * @param expectedDescription the expected task description.
     * @param expectedIndex the expected task index.
     */
    private void assertTaskEquals(ModelManager model, String expectedName,
                                  String expectedDescription, int expectedIndex) {
        Task updatedTask = model.getAddressBook().getPersonList().get(0)
                .getTaskListStorage().getTasks().get(0);
        assertEquals(expectedName, updatedTask.getTaskName());
        assertEquals(expectedDescription, updatedTask.getTaskDescription());
        assertEquals(expectedIndex, updatedTask.getCurrentTaskIndex());
    }


    @Test
    public void execute_nameOnlyEdited_descriptionPreserved() throws Exception {
        ModelManager model = buildModelWithTask();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_REPORT)
                .build();
        EditTaskCommand command = new EditTaskCommand(TASK_INDEX, descriptor);

        CommandResult result = command.execute(model);

        Task expectedTask = new Task(VALID_TASK_NAME_REPORT, ORIGINAL_TASK_DESCRIPTION, TASK_INDEX);
        assertEquals(String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, expectedTask),
                result.getFeedbackToUser());
        assertTaskEquals(model, VALID_TASK_NAME_REPORT, ORIGINAL_TASK_DESCRIPTION, TASK_INDEX);
    }

    @Test
    public void execute_descriptionOnlyEdited_namePreserved() throws Exception {
        ModelManager model = buildModelWithTask();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskDescription(VALID_TASK_DESCRIPTION_REPORT)
                .build();
        EditTaskCommand command = new EditTaskCommand(TASK_INDEX, descriptor);

        CommandResult result = command.execute(model);

        Task expectedTask = new Task(ORIGINAL_TASK_NAME, VALID_TASK_DESCRIPTION_REPORT, TASK_INDEX);
        assertEquals(String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, expectedTask),
                result.getFeedbackToUser());
        assertTaskEquals(model, ORIGINAL_TASK_NAME, VALID_TASK_DESCRIPTION_REPORT, TASK_INDEX);
    }

    @Test
    public void execute_allFieldsEdited_success() throws Exception {
        ModelManager model = buildModelWithTask();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_REPORT)
                .withTaskDescription(VALID_TASK_DESCRIPTION_REPORT)
                .build();
        EditTaskCommand command = new EditTaskCommand(TASK_INDEX, descriptor);

        CommandResult result = command.execute(model);

        Task expectedTask = new Task(VALID_TASK_NAME_REPORT, VALID_TASK_DESCRIPTION_REPORT, TASK_INDEX);
        assertEquals(String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, expectedTask),
                result.getFeedbackToUser());
        assertTaskEquals(model, VALID_TASK_NAME_REPORT, VALID_TASK_DESCRIPTION_REPORT, TASK_INDEX);
    }

    @Test
    public void execute_taskIndexPreservedAfterEdit() throws Exception {
        ModelManager model = buildModelWithTask();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_REPORT)
                .build();
        new EditTaskCommand(TASK_INDEX, descriptor).execute(model);

        assertTaskEquals(model, VALID_TASK_NAME_REPORT, ORIGINAL_TASK_DESCRIPTION, TASK_INDEX);
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() {
        ModelManager model = buildModelWithTask();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_REPORT)
                .build();
        EditTaskCommand command = new EditTaskCommand(INVALID_TASK_INDEX, descriptor);

        assertCommandFailure(command, model, EditTaskCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void equals_sameIndexAndDescriptor_returnsTrue() {
        EditTaskCommand command1 = new EditTaskCommand(TASK_INDEX, DESC_PRESENTATION);
        EditTaskCommand command2 = new EditTaskCommand(TASK_INDEX, DESC_PRESENTATION);
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        EditTaskCommand command = new EditTaskCommand(TASK_INDEX, DESC_PRESENTATION);
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_null_returnsFalse() {
        EditTaskCommand command = new EditTaskCommand(TASK_INDEX, DESC_PRESENTATION);
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        EditTaskCommand command = new EditTaskCommand(TASK_INDEX, DESC_PRESENTATION);
        assertFalse(command.equals(new ClearCommand()));
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        EditTaskCommand command1 = new EditTaskCommand(TASK_INDEX, DESC_PRESENTATION);
        EditTaskCommand command2 = new EditTaskCommand(TASK_INDEX + 1, DESC_PRESENTATION);
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_differentDescriptor_returnsFalse() {
        EditTaskCommand command1 = new EditTaskCommand(TASK_INDEX, DESC_PRESENTATION);
        EditTaskCommand command2 = new EditTaskCommand(TASK_INDEX, DESC_REPORT);
        assertFalse(command1.equals(command2));
    }

    @Test
    public void toStringMethod() {
        EditTaskDescriptor descriptor = new EditTaskDescriptor();
        EditTaskCommand command = new EditTaskCommand(TASK_INDEX, descriptor);
        String expected = EditTaskCommand.class.getCanonicalName()
                + "{taskIndex=" + TASK_INDEX
                + ", editTaskDescriptor=" + descriptor + "}";
        assertEquals(expected, command.toString());
    }

}

