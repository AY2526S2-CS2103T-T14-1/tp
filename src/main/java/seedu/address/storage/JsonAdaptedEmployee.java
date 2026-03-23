package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.employee.Department;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.Phone;
import seedu.address.model.employee.Position;
import seedu.address.model.employee.TaskListStorage;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Employee}.
 */
class JsonAdaptedEmployee {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Employee's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String department;
    private final String position;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEmployee} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedEmployee(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                               @JsonProperty("email") String email, @JsonProperty("department") String department,
                               @JsonProperty("position") String position,
                               @JsonProperty("tags") List<JsonAdaptedTag> tags,
                               @JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.department = department;
        this.position = position;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }

    }

    /**
     * Converts a given {@code Employee} into this class for Jackson use.
     */
    public JsonAdaptedEmployee(Employee source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        department = source.getDepartment().value;
        position = source.getPosition().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        tasks.addAll(source.getTaskListStorage().getTasks().stream()
                .map(JsonAdaptedTask::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Employee} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Employee toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (department == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Department.class.getSimpleName()));
        }
        if (!Department.isValidDepartment(department)) {
            throw new IllegalValueException(Department.MESSAGE_CONSTRAINTS);
        }
        final Department modelDepartment = new Department(department);

        if (position == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Position.class.getSimpleName()));
        }
        if (!Position.isValidPosition(position)) {
            throw new IllegalValueException(Position.MESSAGE_CONSTRAINTS);
        }
        final Position modelPosition = new Position(position);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        final TaskListStorage modelTasks = new TaskListStorage(new ArrayList<>());
        for (JsonAdaptedTask task : tasks) {
            modelTasks.addTask(task.toModelType());
        }
        return new Employee(modelName, modelPhone, modelEmail, modelDepartment, modelPosition, modelTags, modelTasks);
    }

}
