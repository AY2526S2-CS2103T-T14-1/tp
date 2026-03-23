package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedEmployee.MISSING_FIELD_MESSAGE_FORMAT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.employee.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    private final String taskName;
    private final String taskDescription;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     *
     * @param taskName        the name of the task.
     * @param taskDescription the description of the task.
     */
    @JsonCreator
    public JsonAdaptedTask(
            @JsonProperty("taskName") String taskName,
            @JsonProperty("taskDescription") String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     *
     * @param source the Task to convert. Must not be null.
     */
    public JsonAdaptedTask(Task source) {
        this.taskName = source.getTaskName();
        this.taskDescription = source.getTaskDescription();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @return the Task object converted from this adapted task.
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        if (taskName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Task Name"));
        }
        if (!Task.isValidTaskName(taskName)) {
            throw new IllegalValueException(Task.MESSAGE_CONSTRAINTS_TASK_NAME);
        }
        if (taskDescription == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Task Description"));
        }
        if (!Task.isValidTaskDescription(taskDescription)) {
            throw new IllegalValueException(Task.MESSAGE_CONSTRAINTS_TASK_DESCRIPTION);
        }
        return new Task(taskName, taskDescription);
    }
}
