package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;

/**
 * Integration tests for {@code ShowCommand}.
 */
public class ShowCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Predicate<Employee> p1 = e -> true;
        Predicate<Employee> p2 = e -> false;

        ShowCommand c1 = new ShowCommand(p1);
        ShowCommand c2 = new ShowCommand(p2);

        assertTrue(c1.equals(c1));
        assertTrue(c1.equals(new ShowCommand(p1)));
        assertFalse(c1.equals(null));
        assertFalse(c1.equals(1));
        assertTrue(c1.equals(c2)); // design choice
    }

    @Test
    public void execute_noMatch_returnsEmptyList() {
        Predicate<Employee> predicate = e -> false;

        ShowCommand command = new ShowCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(0, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_someMatch_returnsResults() {
        Predicate<Employee> predicate = e ->
                e.getName().toString().toLowerCase().contains("e");

        ShowCommand command = new ShowCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertFalse(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void toStringMethod() {
        Predicate<Employee> predicate = e -> true;
        ShowCommand command = new ShowCommand(predicate);

        String expected = ShowCommand.class.getCanonicalName()
                + "{predicate=" + predicate + "}";

        assertEquals(expected, command.toString());
    }
}