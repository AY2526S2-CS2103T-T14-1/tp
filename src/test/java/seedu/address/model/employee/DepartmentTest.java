package seedu.address.model.employee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DepartmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Department(null));
    }

    @Test
    public void constructor_invalidDepartment_throwsIllegalArgumentException() {
        String invalidDepartment = "";
        assertThrows(IllegalArgumentException.class, () -> new Department(invalidDepartment));
    }

    @Test
    public void isValidDepartment() {
        // null department
        assertThrows(NullPointerException.class, () -> Department.isValidDepartment(null));

        // invalid department
        assertFalse(Department.isValidDepartment("")); // empty string
        assertFalse(Department.isValidDepartment(" ")); // spaces only
        assertFalse(Department.isValidDepartment("R&D")); // non-alphanumeric character
        assertFalse(Department.isValidDepartment("a".repeat(101))); // exceeds 100 characters limit

        // valid department
        assertTrue(Department.isValidDepartment("IT")); // short value
        assertTrue(Department.isValidDepartment("Human Resources")); // multiple words
        assertTrue(Department.isValidDepartment("a".repeat(100))); // exactly 100 characters
    }

    @Test
    public void equals() {
        Department department = new Department("IT");

        assertTrue(department.equals(new Department("IT"))); // same value
        assertTrue(department.equals(department)); // same object
        assertFalse(department.equals(null)); // null
        assertFalse(department.equals(5.0f)); // different type
        assertFalse(department.equals(new Department("HR"))); // different value
    }


}


