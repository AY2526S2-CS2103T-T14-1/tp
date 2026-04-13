package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.employee.Employee;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsExpectedNumberOfPersons() {
        Employee[] samplePersons = SampleDataUtil.getSamplePersons();
        assertEquals(8, samplePersons.length);
    }

    @Test
    public void getSampleAddressBook_containsAllSamplePersons() {
        Employee[] samplePersons = SampleDataUtil.getSamplePersons();
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();

        assertEquals(samplePersons.length, sampleAddressBook.getPersonList().size());
        for (Employee samplePerson : samplePersons) {
            assertTrue(sampleAddressBook.getPersonList().contains(samplePerson));
        }
    }

    @Test
    public void getTagSet_returnsExpectedTags() {
        Set<Tag> actualTagSet = SampleDataUtil.getTagSet("mentor", "reliable");
        Set<Tag> expectedTagSet = Set.of(new Tag("mentor"), new Tag("reliable"));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
