package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpWindowTest {
    private static boolean isFxToolkitAvailable = true;

    @BeforeAll
    public static void setUpFxToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (UnsupportedOperationException e) {
            isFxToolkitAvailable = false;
            latch.countDown();
        } catch (IllegalStateException e) {
            latch.countDown();
        }
        assertTrue(latch.await(15, TimeUnit.SECONDS));
    }

    @BeforeEach
    public void requireFxToolkit() {
        Assumptions.assumeTrue(isFxToolkitAvailable, "JavaFX toolkit is unavailable in this environment");
    }

    @Test
    public void constructor_withOwner_setsOwnerAndInitialState() throws Exception {
        AtomicReference<Stage> ownerRef = new AtomicReference<>();
        AtomicReference<HelpWindow> helpWindowRef = new AtomicReference<>();

        runOnFxThreadAndWait(() -> {
            Stage owner = new Stage();
            HelpWindow helpWindow = new HelpWindow(new Stage(), owner);
            ownerRef.set(owner);
            helpWindowRef.set(helpWindow);
        });

        HelpWindow helpWindow = helpWindowRef.get();
        Stage root = helpWindow.getRoot();
        assertSame(ownerRef.get(), root.getOwner());
        assertEquals(Modality.NONE, root.getModality());
        assertFalse(helpWindow.isShowing());

        runOnFxThreadAndWait(() -> root.close());
        runOnFxThreadAndWait(() -> ownerRef.get().close());
    }

    @Test
    public void prepareWindowForShow_resetsFullscreenFlags() throws Exception {
        AtomicReference<HelpWindow> helpWindowRef = new AtomicReference<>();

        runOnFxThreadAndWait(() -> {
            HelpWindow helpWindow = new HelpWindow(new Stage(), new Stage());
            helpWindow.getRoot().setFullScreen(true);
            helpWindow.getRoot().setMaximized(true);
            helpWindowRef.set(helpWindow);
        });

        HelpWindow helpWindow = helpWindowRef.get();
        Stage root = helpWindow.getRoot();

        runOnFxThreadAndWait(helpWindow::prepareWindowForShow);

        assertFalse(root.isFullScreen());
        assertFalse(root.isMaximized());

        runOnFxThreadAndWait(helpWindow::positionWindow);
        runOnFxThreadAndWait(helpWindow::focus);

        runOnFxThreadAndWait(() -> root.close());
        runOnFxThreadAndWait(() -> {
            if (root.getOwner() instanceof Stage) {
                Stage owner = (Stage) root.getOwner();
                owner.close();
            }
        });
    }

    @Test
    public void copyUrl_copiesUserGuideUrlToClipboard() throws Exception {
        AtomicReference<HelpWindow> helpWindowRef = new AtomicReference<>();
        AtomicReference<String> clipboardTextRef = new AtomicReference<>();

        runOnFxThreadAndWait(() -> helpWindowRef.set(new HelpWindow(new Stage(), new Stage())));
        HelpWindow helpWindow = helpWindowRef.get();

        runOnFxThreadAndWait(() -> {
            Method copyUrl = HelpWindow.class.getDeclaredMethod("copyUrl");
            copyUrl.setAccessible(true);
            copyUrl.invoke(helpWindow);
        });

        runOnFxThreadAndWait(() -> clipboardTextRef.set(Clipboard.getSystemClipboard().getString()));
        assertEquals(HelpWindow.USERGUIDE_URL, clipboardTextRef.get());

        runOnFxThreadAndWait(() -> helpWindow.getRoot().close());
        runOnFxThreadAndWait(() -> {
            if (helpWindow.getRoot().getOwner() instanceof Stage) {
                Stage owner = (Stage) helpWindow.getRoot().getOwner();
                owner.close();
            }
        });
    }

    private static void runOnFxThreadAndWait(FxRunnable action) throws Exception {
        AtomicReference<Throwable> errorRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable t) {
                errorRef.set(t);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        if (errorRef.get() != null) {
            if (errorRef.get() instanceof Exception) {
                throw (Exception) errorRef.get();
            }
            throw new RuntimeException(errorRef.get());
        }
    }

    @FunctionalInterface
    private interface FxRunnable {
        void run() throws Exception;
    }
}
