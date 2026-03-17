package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.employee.Employee;

/**
 * A utility class containing a list of {@code Employee} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Employee ALICE = new PersonBuilder().withName("Alice Pauline")
            .withDepartment("IT").withEmail("alice@example.com")
            .withPhone("94351253")
            .withPosition("Product Manager")
            .withTags("friends").build();
    public static final Employee BENSON = new PersonBuilder().withName("Benson Meier")
            .withDepartment("Sales")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withPosition("HR Manager")
            .withTags("owesMoney", "friends").build();
    public static final Employee CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withDepartment("Finance").withPosition("Advisor").build();
    public static final Employee DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withDepartment("Finance").withPosition("Intern")
            .withTags("friends").build();
    public static final Employee ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withDepartment("Finance").withPosition("Supervisor").build();
    public static final Employee FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withDepartment("Finance").withPosition("Manager").build();
    public static final Employee GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withDepartment("Finance").withPosition("Agent").build();

    // Manually added
    public static final Employee HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withDepartment("Sales").withPosition("Intern").build();
    public static final Employee IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withDepartment("Sales").withPosition("UI Designer").build();

    // Manually added - Employee's details found in {@code CommandTestUtil}
    public static final Employee AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withDepartment(VALID_DEPARTMENT_AMY).withPosition(VALID_POSITION_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Employee BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withPosition(VALID_POSITION_BOB).withDepartment(VALID_DEPARTMENT_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Employee person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Employee> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
