package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmployeeCardTest {

    @Test
    public void formatPhone_validPhone_returnsFormattedString() {
        assertEquals("📞  98765432", EmployeeCard.formatPhone("98765432"));
    }

    @Test
    public void formatEmail_validEmail_returnsFormattedString() {
        assertEquals("📧  john@example.com", EmployeeCard.formatEmail("john@example.com"));
    }

    @Test
    public void formatDepartment_validDepartment_returnsFormattedString() {
        assertEquals("🏢  Engineering", EmployeeCard.formatDepartment("Engineering"));
    }

    @Test
    public void formatPosition_validPosition_returnsFormattedString() {
        assertEquals("💼  Manager", EmployeeCard.formatPosition("Manager"));
    }
}
