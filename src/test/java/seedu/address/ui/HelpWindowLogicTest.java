package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class HelpWindowLogicTest {

    @Test
    public void prepareWindowForShow_clearsFullscreenFlags() {
        boolean[] fullScreen = new boolean[] {true};
        boolean[] maximized = new boolean[] {true};

        HelpWindowLogic.prepareWindowForShow(value -> fullScreen[0] = value, value -> maximized[0] = value);

        assertFalse(fullScreen[0]);
        assertFalse(maximized[0]);
    }
}
