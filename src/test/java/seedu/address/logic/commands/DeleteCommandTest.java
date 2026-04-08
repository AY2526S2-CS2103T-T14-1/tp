package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonByName;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.testutil.PersonBuilder;

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
    public void execute_validAlphanumericNameUnfilteredList_success() {
        Model alphanumericModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Employee personToDelete = new PersonBuilder().withName("John2 Doe").withPhone("81112222")
                .withEmail("john2@example.com").build();
        alphanumericModel.addPerson(personToDelete);

        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getName().fullName);
        String expectedMessage = String.format(
                DeleteCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(alphanumericModel.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, alphanumericModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        String invalidName = "Nonexistent Employee";
        DeleteCommand deleteCommand = new DeleteCommand(invalidName);

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_EMPLOYEE_NOT_FOUND, invalidName));
    }

    @Test
    public void execute_duplicateNameUnfilteredList_throwsCommandException() {
        Model duplicateModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String duplicateName = "Same Name";
        Employee firstDuplicate = new PersonBuilder().withName(duplicateName)
                .withPhone("11111111").withEmail("same1@example.com").build();
        Employee secondDuplicate = new PersonBuilder().withName(duplicateName)
                .withPhone("22222222").withEmail("same2@example.com").build();
        duplicateModel.addPerson(firstDuplicate);
        duplicateModel.addPerson(secondDuplicate);

        DeleteCommand deleteCommand = new DeleteCommand(duplicateName);

        assertCommandFailure(deleteCommand, duplicateModel,
                String.format(DeleteCommand.MESSAGE_DUPLICATE_EMPLOYEE_NAME, duplicateName));
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
    public void execute_duplicateNameFilteredList_throwsCommandException() {
        Model duplicateModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String duplicateName = "Same Name";
        Employee firstDuplicate = new PersonBuilder().withName(duplicateName)
                .withPhone("11111111").withEmail("same1@example.com").build();
        Employee secondDuplicate = new PersonBuilder().withName(duplicateName)
                .withPhone("22222222").withEmail("same2@example.com").build();
        duplicateModel.addPerson(firstDuplicate);
        duplicateModel.addPerson(secondDuplicate);
        duplicateModel.updateFilteredPersonList(employee -> employee.getName().fullName.equals(duplicateName));

        DeleteCommand deleteCommand = new DeleteCommand(duplicateName);

        assertCommandFailure(deleteCommand, duplicateModel,
                String.format(DeleteCommand.MESSAGE_DUPLICATE_EMPLOYEE_NAME, duplicateName));
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
    public void execute_validMultipleIndexesUnfilteredList_success() {
        DeleteCommand deleteCommand = new DeleteCommand(List.of(Index.fromOneBased(1), Index.fromOneBased(3)));
        Employee firstPersonToDelete = model.getFilteredPersonList().get(0);
        Employee thirdPersonToDelete = model.getFilteredPersonList().get(2);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EMPLOYEES_SUCCESS,
                Messages.format(firstPersonToDelete) + "\n" + Messages.format(thirdPersonToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(thirdPersonToDelete);
        expectedModel.deletePerson(firstPersonToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidMultipleIndexesUnfilteredList_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(List.of(Index.fromOneBased(1), Index.fromOneBased(999)));
        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void equals() {
        Employee person = model.getFilteredPersonList().get(0);
        DeleteCommand deleteFirstCommand = new DeleteCommand(person.getName().fullName);
        DeleteCommand deleteSecondCommand = new DeleteCommand("Another Employee");
        DeleteCommand deleteMultipleCommand = new DeleteCommand(List.of(Index.fromOneBased(1), Index.fromOneBased(2)));

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

        // different delete mode -> returns false
        assertFalse(deleteFirstCommand.equals(deleteMultipleCommand));
    }

    @Test
    public void toStringMethod() {
        Employee person = model.getFilteredPersonList().get(0);
        DeleteCommand deleteCommand = new DeleteCommand(person.getName().fullName);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetName=" + person.getName().fullName + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void toStringMethod_multipleIndexes() {
        DeleteCommand deleteCommand = new DeleteCommand(List.of(Index.fromOneBased(1), Index.fromOneBased(2)));
        assertTrue(deleteCommand.toString().contains("targetIndexes="));
        assertTrue(deleteCommand.toString().contains("zeroBasedIndex=0"));
        assertTrue(deleteCommand.toString().contains("zeroBasedIndex=1"));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
