package seedu.address.model.employee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Position(null));
    }

    @Test
    public void constructor_invalidPosition_throwsIllegalArgumentException() {
        String invalidPosition = "";
        assertThrows(IllegalArgumentException.class, () -> new Position(invalidPosition));
    }

    @Test
    public void isValidPosition() {
        // null position
        assertThrows(NullPointerException.class, () -> Position.isValidPosition(null));

        // invalid position
        assertFalse(Position.isValidPosition("")); // empty string
        assertFalse(Position.isValidPosition(" ")); // spaces only
        assertFalse(Position.isValidPosition("Manager!")); // non-alphanumeric character
        assertFalse(Position.isValidPosition("a".repeat(101))); // exceeds 100 characters limit

        // valid position — single and multiple words
        assertTrue(Position.isValidPosition("Manager")); // single word
        assertTrue(Position.isValidPosition("Software Engineer")); // two words
        assertTrue(Position.isValidPosition("Lead Software Engineer")); // three words

        // valid — within length limit
        assertTrue(Position.isValidPosition("a".repeat(100))); // exactly 100 characters
    }

    @Test
    public void equals() {
        Position position = new Position("Software Engineer");

        assertTrue(position.equals(new Position("Software Engineer"))); // same value
        assertTrue(position.equals(position)); // same object
        assertFalse(position.equals(null)); // null
        assertFalse(position.equals(5.0f)); // different type
        assertFalse(position.equals(new Position("Manager"))); // different value
    }
}
