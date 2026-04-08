package seedu.address.ui;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * Toolkit-independent behavior shared by the help window UI.
 */
final class HelpWindowLogic {

    private HelpWindowLogic() {}

    static void populateContent(Consumer<String> setText, IntConsumer positionCaret, String helpText) {
        setText.accept(helpText);
        positionCaret.accept(0);
    }

    static void prepareWindowForShow(Consumer<Boolean> setFullScreen, Consumer<Boolean> setMaximized) {
        setFullScreen.accept(false);
        setMaximized.accept(false);
    }

    static void positionWindow(Runnable centerOnScreen, IntConsumer positionCaret) {
        centerOnScreen.run();
        positionCaret.accept(0);
    }

    static void showHelpWindow(Runnable logShowAction, Consumer<Boolean> setFullScreen,
            Consumer<Boolean> setMaximized, Runnable showWindow, Runnable centerOnScreen,
            IntConsumer positionCaret) {
        logShowAction.run();
        prepareWindowForShow(setFullScreen, setMaximized);
        showWindow.run();
        positionWindow(centerOnScreen, positionCaret);
    }

    static void openOrFocusHelpWindow(BooleanSupplier isShowing, Runnable showWindow, Runnable focusWindow) {
        if (!isShowing.getAsBoolean()) {
            showWindow.run();
        } else {
            focusWindow.run();
        }
    }
}
