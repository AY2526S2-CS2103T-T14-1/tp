package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private VBox helpSections;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        populateHelpSections();
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    private void populateHelpSections() {
        assert helpSections != null;
        helpSections.getChildren().clear();

        addSection(new HelpSection("help", "Shows this in-app help window.",
                "No additional parameters.", "help"));
        addSection(new HelpSection("add", "Adds an employee.",
                "n/NAME p/PHONE e/EMAIL d/DEPARTMENT pos/POSITION [t/TAG]...",
                "add n/John Doe p/98765432 e/johnd@example.com d/IT pos/Software Engineer t/fulltime"));
        addSection(new HelpSection("delete", "Deletes an employee by unique name or list index.",
                "NAME or INDEX.", "delete John Doe", "delete 2"));
        addSection(new HelpSection("edit", "Edits an employee identified by index.",
                "INDEX with one or more optional fields: [n/NAME] [p/PHONE] [e/EMAIL] "
                        + "[d/DEPARTMENT] [pos/POSITION] [t/TAG]...",
                "edit 1 p/91234567 e/johndoe@example.com"));
        addSection(new HelpSection("find", "Finds employees whose names contain any keyword.",
                "KEYWORD [MORE_KEYWORDS]...", "find alice bob charlie"));
        addSection(new HelpSection("list", "Lists all employees.",
                "No additional parameters.", "list"));
        addSection(new HelpSection("show", "Filters employees by one or more fields.",
                "At least one of n/ d/ p/ e/ pos/ t/ task/.",
                "show n/Alex d/IT"));
        addSection(new HelpSection("addtask", "Adds a task to an employee by name.",
                "task/TASK_NAME desc/TASK_DESCRIPTION n/EMPLOYEE_NAME",
                "addtask task/Prepare Report desc/Submit by Friday n/John Doe"));
        addSection(new HelpSection("deletetask", "Deletes a task by task index.",
                "INDEX.", "deletetask 1"));
        addSection(new HelpSection("clear", "Clears all employees from the address book.",
                "No additional parameters.", "clear"));
        addSection(new HelpSection("exit", "Exits ManageUp.",
                "No additional parameters.", "exit"));
    }

    private void addSection(HelpSection section) {
        VBox sectionCard = new VBox(8);
        sectionCard.getStyleClass().add("help-section-card");

        Label commandTitle = new Label(section.commandWord());
        commandTitle.getStyleClass().add("help-command-title");

        Label description = new Label(section.description());
        description.setWrapText(true);
        description.getStyleClass().add("help-command-description");

        Label allowedInput = new Label("Allowed input: " + section.allowedInput());
        allowedInput.setWrapText(true);
        allowedInput.getStyleClass().add("help-command-allowed-input");

        sectionCard.getChildren().addAll(commandTitle, description, allowedInput);

        for (String example : section.examples()) {
            Label exampleLabel = new Label("Example: " + example);
            exampleLabel.setWrapText(true);
            exampleLabel.getStyleClass().add("help-command-example");
            sectionCard.getChildren().add(exampleLabel);
        }

        helpSections.getChildren().add(sectionCard);
    }

    private record HelpSection(String commandWord, String description, String allowedInput, String... examples) { }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
