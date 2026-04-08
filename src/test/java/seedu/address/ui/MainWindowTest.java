package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.stage.Stage;

public class MainWindowTest {
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
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @BeforeEach
    public void requireFxToolkit() {
        Assumptions.assumeTrue(isFxToolkitAvailable, "JavaFX toolkit is unavailable in this environment");
    }

    @Test
    public void createHelpWindow_setsOwnerToPrimaryStage() throws Exception {
        AtomicReference<Stage> primaryStageRef = new AtomicReference<>();
        AtomicReference<HelpWindow> helpWindowRef = new AtomicReference<>();

        runOnFxThreadAndWait(() -> {
            Stage primaryStage = new Stage();
            primaryStageRef.set(primaryStage);
            helpWindowRef.set(MainWindow.createHelpWindow(primaryStage));
        });

        HelpWindow helpWindow = helpWindowRef.get();
        assertTrue(helpWindow.getRoot().getOwner() == primaryStageRef.get());

        runOnFxThreadAndWait(() -> helpWindow.getRoot().close());
        runOnFxThreadAndWait(() -> primaryStageRef.get().close());
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
        assertTrue(latch.await(15, TimeUnit.SECONDS));
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
