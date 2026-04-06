package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Task;

/**
 * A UI component that displays information of a {@code Employee}.
 */
public class EmployeeCard extends UiPart<Region> {

    private static final String FXML = "EmployeeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Employee person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label position;
    @FXML
    private Label department;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox tasks;



    /**
     * Creates a {@code EmployeeCode} with the given {@code Employee} and index to display.
     */
    public EmployeeCard(Employee employee, int displayedIndex) {

        super(FXML);
        this.person = employee;
        id.setText(displayedIndex + ". ");
        name.setText(employee.getName().fullName);
        phone.setText(formatPhone(employee.getPhone().value));
        email.setText(formatEmail(employee.getEmail().value));
        department.setText(formatDepartment(employee.getDepartment().value));
        position.setText(formatPosition(employee.getPosition().value));
        employee.getTags().stream()
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getTaskListStorage().getTasks().stream()
                .map(Task::toString)
                .forEach(taskStr -> tasks.getChildren().add(buildTaskLabel(taskStr)));
    }

    /**
     * Builds a styled card label for a single task string
     */
    static Label buildTaskLabel(String taskStr) {
        Label label = new Label(taskStr);
        label.getStyleClass().add("task-card-label");
        label.setWrapText(false);
        return label;
    }

    static String formatPhone(String phone) {
        return "📞  " + phone;
    }

    static String formatEmail(String email) {
        return "📧  " + email;
    }

    static String formatDepartment(String department) {
        return "🏢  " + department;
    }

    static String formatPosition(String position) {
        return "💼  " + position;
    }
}
