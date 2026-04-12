package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Task;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Employee validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Employee validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, Messages.MESSAGE_DUPLICATE_EMPLOYEE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateEmployeeCaseInsensitive_throwsCommandException() {
        Employee existingPerson = new PersonBuilder().withName("John Doe")
                                                     .withPhone("12345678")
                                                     .withEmail("john@email.com")
                                                     .build();

        Employee samePersonDifferentCase = new PersonBuilder().withName("john doe")
                                                              .withPhone("12345678")
                                                              .withEmail("john@email.com")
                                                              .build();

        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        modelStub.personsAdded.add(existingPerson);

        AddCommand addCommand = new AddCommand(samePersonDifferentCase);

        assertThrows(CommandException.class,
                Messages.MESSAGE_DUPLICATE_EMPLOYEE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Employee alice = new PersonBuilder().withName("Alice").build();
        Employee bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Employee person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Employee person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Employee target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Employee target, Employee editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Employee getEmployeeWithSamePhone(Employee employee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Employee getEmployeeWithSameEmail(Employee employee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Employee> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Employee> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTaskToPerson(Employee person, Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTaskOverall(Task task, Employee person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showAllTasks() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public java.util.Optional<Task> getTaskByIndex(int taskIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTask(int taskIndex, Task newTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Task getTaskWithSameDescription(Task task, Employee person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(int taskIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int clearTasksForPerson(Employee employee) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Employee person;

        ModelStubWithPerson(Employee person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Employee person) {
            requireNonNull(person);
            return this.person.isSameEmployee(person);
        }

        @Override
        public Employee getEmployeeWithSamePhone(Employee employee) {
            return this.person.getPhone().equals(employee.getPhone()) ? this.person : null;
        }

        @Override
        public Employee getEmployeeWithSameEmail(Employee employee) {
            return this.person.getEmail().equals(employee.getEmail()) ? this.person : null;
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Employee> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Employee person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSameEmployee);
        }

        @Override
        public void addPerson(Employee person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public Employee getEmployeeWithSamePhone(Employee employee) {
            return personsAdded.stream()
                    .filter(e -> e.getPhone().equals(employee.getPhone()))
                    .findFirst().orElse(null);
        }

        @Override
        public Employee getEmployeeWithSameEmail(Employee employee) {
            return personsAdded.stream()
                    .filter(e -> e.getEmail().equals(employee.getEmail()))
                    .findFirst().orElse(null);
        }
    }

    @Test
    public void execute_duplicatePhone_throwsCommandException() {
        Employee validPerson = new PersonBuilder().build();
        Employee personWithSamePhone = new PersonBuilder()
                .withName("Different Name")
                .withEmail("different@email.com")
                .build();
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        modelStub.personsAdded.add(validPerson);

        AddCommand addCommand = new AddCommand(personWithSamePhone);
        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_DUPLICATE_PHONE,
                              Messages.format(validPerson)), () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateEmail_throwsCommandException() {
        Employee validPerson = new PersonBuilder().build();
        Employee personWithSameEmail = new PersonBuilder()
                .withName("Different Name")
                .withPhone("99999999")
                .build();
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        modelStub.personsAdded.add(validPerson);

        AddCommand addCommand = new AddCommand(personWithSameEmail);
        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_DUPLICATE_EMAIL,
                              Messages.format(validPerson)), () -> addCommand.execute(modelStub));
    }


}
