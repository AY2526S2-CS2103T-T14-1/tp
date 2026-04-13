package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.employee.Department;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.Phone;
import seedu.address.model.employee.Position;
import seedu.address.model.employee.TaskListStorage;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Employee[] getSamplePersons() {
        return new Employee[] {
            new Employee(new Name("Olivia Tan"), new Phone("91234567"), new Email("olivia.tan@manageup.com"),
                    new Department("Engineering"), new Position("Backend engineer"),
                    getTagSet("mentor", "reliable"), new TaskListStorage(new ArrayList<>())),

            new Employee(new Name("Marcus Lee"), new Phone("92345678"), new Email("marcus.lee@manageup.com"),
                    new Department("Engineering"), new Position("Frontend engineer"),
                    getTagSet("ui", "responsive"), new TaskListStorage(new ArrayList<>())),

            new Employee(new Name("Aisha Rahman"), new Phone("93456789"), new Email("aisha.rahman@manageup.com"),
                    new Department("Product"), new Position("Product manager"),
                    getTagSet("organised", "leadership"), new TaskListStorage(new ArrayList<>())),

            new Employee(new Name("Daniel Wong"), new Phone("94567890"), new Email("daniel.wong@manageup.com"),
                    new Department("Operations"), new Position("Operations executive"),
                    getTagSet("detailoriented"), new TaskListStorage(new ArrayList<>())),

            new Employee(new Name("Siti Noor"), new Phone("95678901"), new Email("siti.noor@manageup.com"),
                    new Department("Human Resources"), new Position("HR executive"),
                    getTagSet("peoplefocused"), new TaskListStorage(new ArrayList<>())),

            new Employee(new Name("Ethan Koh"), new Phone("96789012"), new Email("ethan.koh@manageup.com"),
                    new Department("Finance"), new Position("Finance analyst"),
                    getTagSet("accurate"), new TaskListStorage(new ArrayList<>())),

            new Employee(new Name("Cheryl Goh"), new Phone("97890123"), new Email("cheryl.goh@manageup.com"),
                    new Department("Sales"), new Position("Account executive"),
                    getTagSet("clientfacing", "driven"), new TaskListStorage(new ArrayList<>())),

            new Employee(new Name("Ryan Ng"), new Phone("98901234"), new Email("ryan.ng@manageup.com"),
                    new Department("Customer Success"), new Position("Customer success manager"),
                    getTagSet("supportive"), new TaskListStorage(new ArrayList<>()))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Employee samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
