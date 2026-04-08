package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HelpWindowLogicTest {

    @Test
    public void populateContent_setsTextAndMovesCaretToTop() {
        StringBuilder helpText = new StringBuilder();
        int[] caretPosition = new int[] {-1};

        HelpWindowLogic.populateContent(helpText::append, position -> caretPosition[0] = position, "Help text");

        assertEquals("Help text", helpText.toString());
        assertEquals(0, caretPosition[0]);
    }

    @Test
    public void prepareWindowForShow_clearsFullscreenFlags() {
        boolean[] fullScreen = new boolean[] {true};
        boolean[] maximized = new boolean[] {true};

        HelpWindowLogic.prepareWindowForShow(value -> fullScreen[0] = value, value -> maximized[0] = value);

        assertFalse(fullScreen[0]);
        assertFalse(maximized[0]);
    }

    @Test
    public void positionWindow_centersWindowAndMovesCaretToTop() {
        boolean[] centered = new boolean[1];
        int[] caretPosition = new int[] {-1};

        HelpWindowLogic.positionWindow(() -> centered[0] = true, position -> caretPosition[0] = position);

        assertTrue(centered[0]);
        assertEquals(0, caretPosition[0]);
    }

    @Test
    public void showHelpWindow_logsPreparesShowsAndPositions() {
        boolean[] logged = new boolean[1];
        boolean[] fullScreen = new boolean[] {true};
        boolean[] maximized = new boolean[] {true};
        boolean[] shown = new boolean[1];
        boolean[] centered = new boolean[1];
        int[] caretPosition = new int[] {-1};
        Runnable logAction = () -> logged[0] = true;
        Runnable showAction = () -> shown[0] = true;
        Runnable centerAction = () -> centered[0] = true;

        HelpWindowLogic.showHelpWindow(
                logAction,
                value -> fullScreen[0] = value,
                value -> maximized[0] = value,
                showAction,
                centerAction,
                position -> caretPosition[0] = position);

        assertTrue(logged[0]);
        assertFalse(fullScreen[0]);
        assertFalse(maximized[0]);
        assertTrue(shown[0]);
        assertTrue(centered[0]);
        assertEquals(0, caretPosition[0]);
    }

    @Test
    public void openOrFocusHelpWindow_whenHidden_showsWindow() {
        boolean[] shown = new boolean[1];
        boolean[] focused = new boolean[1];

        HelpWindowLogic.openOrFocusHelpWindow(() -> false, () -> shown[0] = true, () -> focused[0] = true);

        assertTrue(shown[0]);
        assertFalse(focused[0]);
    }

    @Test
    public void openOrFocusHelpWindow_whenShowing_focusesWindow() {
        boolean[] shown = new boolean[1];
        boolean[] focused = new boolean[1];

        HelpWindowLogic.openOrFocusHelpWindow(() -> true, () -> shown[0] = true, () -> focused[0] = true);

        assertFalse(shown[0]);
        assertTrue(focused[0]);
    }
}
