package seedu.address.ui;

import java.util.function.Consumer;

/**
 * Toolkit-independent behavior shared by the help window UI.
 */
final class HelpWindowLogic {

    private HelpWindowLogic() {}

    static void prepareWindowForShow(Consumer<Boolean> setFullScreen, Consumer<Boolean> setMaximized) {
        setFullScreen.accept(false);
        setMaximized.accept(false);
    }
}
