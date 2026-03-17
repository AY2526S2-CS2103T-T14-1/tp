package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Task;

class JsonAdaptedTask {
        private String taskName;
        private String taskDescription;

        /**
        * Constructs a {@code JsonAdaptedTask} with the given {@code taskName} and {@code taskDescription}.
        */
        public JsonAdaptedTask(String taskName, String taskDescription) {
            this.taskName = taskName;
            this.taskDescription = taskDescription;
        }

        /**
        * Converts a given {@code Task} into this class for Jackson use.
        */
        public JsonAdaptedTask(Task source) {
            taskName = source.getTaskName();
            taskDescription = source.getTaskDescription();
        }

        /**
        * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
        *
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
