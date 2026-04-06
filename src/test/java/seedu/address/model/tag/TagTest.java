package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        //valid tags
        assertTrue(Tag.isValidTagName("friend")); // characters only
        assertTrue(Tag.isValidTagName("ABC123")); //characters and digits
        assertTrue(Tag.isValidTagName("a".repeat(Tag.MAX_TAG_LENGTH))); // 50 characters exactly

        //invalid tags
        assertFalse(Tag.isValidTagName("hello world")); // space not allowed
        assertFalse(Tag.isValidTagName("#friend")); //non-alphanumeric character
        assertFalse(Tag.isValidTagName("a".repeat(Tag.MAX_TAG_LENGTH + 1))); // exceeds character limit

    }

}
