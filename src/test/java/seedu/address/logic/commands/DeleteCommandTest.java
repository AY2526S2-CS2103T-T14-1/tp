package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonByName;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() {
        Employee personToDelete = model.getFilteredPersonList().get(0);
        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getName().fullName);

        String expectedMessage = String.format(
                DeleteCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        String invalidName = "Nonexistent Employee";
        DeleteCommand deleteCommand = new DeleteCommand(invalidName);

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_EMPLOYEE_NOT_FOUND, invalidName));
    }

    @Test
    public void execute_validNameFilteredList_success() {
        Employee personToDelete = model.getFilteredPersonList().get(0);
        showPersonByName(model, personToDelete.getName().fullName);

        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getName().fullName);

        String expectedMessage = String.format(
                DeleteCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        Employee personToDelete = model.getFilteredPersonList().get(0);
        showPersonByName(model, personToDelete.getName().fullName);
        String invalidName = "Nonexistent Employee";
        DeleteCommand deleteCommand = new DeleteCommand(invalidName);

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_EMPLOYEE_NOT_FOUND, invalidName));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Employee personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON.getOneBased());

        String expectedMessage = String.format(
                DeleteCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(999); // Out of bounds
        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void equals() {
        Employee person = model.getFilteredPersonList().get(0);
        DeleteCommand deleteFirstCommand = new DeleteCommand(person.getName().fullName);
        DeleteCommand deleteSecondCommand = new DeleteCommand("Another Employee");

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(person.getName().fullName);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Employee person = model.getFilteredPersonList().get(0);
        DeleteCommand deleteCommand = new DeleteCommand(person.getName().fullName);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetName=" + person.getName().fullName + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
