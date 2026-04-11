---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# ManageUp Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

ManageUp is adapted from [AddressBook-Level3](https://se-education.org/addressbook-level3/).
The documentation site is built with [MarkBind](https://markbind.org/), and the UML diagrams are rendered using
[PlantUML](https://plantuml.com/).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Employee` objects residing in the `Model`.

The Help Window is implemented as a separate UI feature coordinated by `MainWindow`.
The class diagram below summarises the main classes involved.

<puml src="diagrams/HelpWindowClassDiagram.puml" alt="Structure of the Help Window feature" />

`MainWindow` owns a single `HelpWindow` instance and either shows it or focuses it when the user triggers `help`.
`HelpWindow` extends `UiPart` and uses `HelpWindowContent` to obtain the formatted command reference displayed in the
popup. Before the window is shown, `MainWindow` delegates to `HelpWindowLogic` to reset fullscreen and maximized state
so that the help popup opens consistently even after earlier window-state changes.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Besides employee commands such as `add`, `edit`, `delete`, and `show`, the `Logic` component also handles
task-related commands. In particular, `AddressBookParser` routes `addtask`, `edittask`, and `deletetask`
to `AddTaskCommandParser`, `EditTaskCommandParser`, and `DeleteTaskCommandParser` respectively, which then
construct `AddTaskCommand`, `EditTaskCommand`, and `DeleteTaskCommand` objects for execution by `LogicManager`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Employee` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Employee` objects (e.g., results of a search query) as a separate _filtered_ list
  which is exposed to outsiders as an unmodifiable `ObservableList<Employee>` that can be observed so that the UI
  automatically updates when the data in the list changes.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* stores task data in two levels:
  * each `Employee` owns an individual `TaskListStorage`, which stores the tasks shown on that employee's card in the UI.
  * the `ModelManager` also maintains a separate in-memory overall `TaskList`, which maps `Task -> Employee` and is used to locate the employee that owns a task during task editing and deletion.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

Task indices are part of the task model. Each `Task` stores a task index that is displayed beside the task on the
employee card, and commands such as `edittask` and `deletetask` operate on this task index rather than the employee
list index. Task indices are assigned when tasks are created. The overall in-memory `TaskList` is rebuilt when the
model starts, but is not currently persisted as a separate storage file.

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`,
which `Employee` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of
each `Employee` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Delete employee

ManageUp supports three variants of the `delete` command:

* single employee deletion by employee index
* single employee deletion by unique employee name
* batch employee deletion by multiple employee indices

The activity diagram below shows how `DeleteCommandParser` decides which deletion mode to construct.

<puml src="diagrams/DeleteEmployeeActivityDiagram.puml" alt="Activity diagram for parsing and executing the delete employee command" />

The parser behaves as follows:

1. If the trimmed input is empty, parsing fails with the standard command-usage message.
2. If the input contains multiple whitespace-separated tokens and every token is a positive integer, the parser treats
   the command as batch deletion by indices.
3. If the input contains a single positive integer token, the parser treats the command as single-index deletion.
4. Otherwise, the parser treats the full input as a name-based deletion request and validates the employee name format.

The sequence diagram below illustrates the runtime interactions for the representative single-index flow `delete 1`.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions inside the Logic and Model components for the `delete 1` command" />

During execution:

1. `LogicManager` asks `AddressBookParser` to parse the command.
2. `AddressBookParser` creates `DeleteCommandParser`, which constructs a `DeleteCommand`.
3. `DeleteCommand` calls `Model#deletePerson(employee)`.
4. `ModelManager` first removes all tasks belonging to that employee from the overall in-memory `TaskList`.
5. `ModelManager` then delegates to `AddressBook` to remove the employee from the address book.
6. `AddressBook` removes the employee through `UniquePersonList`.
7. A `CommandResult` is created and returned to `LogicManager`.

Batch deletion follows the same deletion logic after validation, except that all requested indices are first checked,
then deleted in descending order to avoid index-shifting issues in the filtered employee list.

For name-based deletion, `DeleteCommand` normalizes the requested name, matches it against the currently displayed
employee list, and rejects cases where there are zero matches or more than one match.

### Task management

ManageUp supports task assignment and maintenance through four task commands:

* `addtask` adds a task to a specific employee.
* `edittask` edits the name and/or description of an existing task by task index.
* `deletetask` deletes a task by task index.
* `cleartasks` removes all tasks assigned to one employee.

Task management uses both per-employee storage and a global in-memory task structure:

* `Task.java` represents a single task. Each task stores a name, description, and task index. The task index is
  displayed in the UI and is used by task-editing and task-deletion commands.
* `TaskListStorage.java` stores the list of tasks assigned to one employee. This is the task list shown on the
  employee card in the UI.
* `TaskList.java` stores an overall in-memory mapping of `Task -> Employee`. This mapping is used to locate the
  employee that owns a task when `edittask` or `deletetask` is executed.
* `UniquePersonList.java` is responsible for updating the owning employee's individual task list. When a task is
  added, edited, or deleted, the affected employee is replaced with an updated `Employee` object so that the
  observable employee list remains in sync with the UI.

#### Task index design

Task indices are shown directly on the employee card beside each task, for example `#3 Prepare Report: Submit by Friday`.
Unlike employee commands such as `edit 1` or `delete 1`, task commands use the task index instead of the employee index.

When a new task is created, the task is assigned the next available task index. This allows task commands such as
`edittask 3` and `deletetask 3` to refer to the same task regardless of which employee owns it.

Task indices are intentionally not renumbered after deletions. This is a deliberate design choice to keep task
references stable once they have been shown to the user. If task indices were reassigned whenever some other task was
deleted, a command such as `edittask 8` could end up referring to a different task from the one the user previously
saw, which would make task operations harder to reason about and easier to misuse.

This design trades compact numbering for consistency. Over time, task indices can become sparse and larger than the
current number of tasks, but for ManageUp's expected usage scale, stable task references are more valuable than
perfectly consecutive numbering. If the application is extended to support substantially larger long-term task volumes,
the team can revisit this design and consider alternative identifier schemes that preserve both uniqueness and
usability.

If ManageUp is extended to support substantially larger long-term task volumes, a more scalable refinement would be to
separate internal task identity from user-facing display order. Each task can keep a stable internal identifier for
storage and updates, while the UI presents a smaller context-specific display number to the user. This preserves
reliable task tracking internally while avoiding unwieldy visible task numbers in long-running usage.

#### Add task implementation

`addtask` is parsed by `AddTaskCommandParser`, which extracts the task name, task description, and employee index from
the command input before constructing an `AddTaskCommand`.

During execution:

1. `AddressBookParser` recognises the `addtask` command and delegates parsing to `AddTaskCommandParser`.
2. `AddTaskCommandParser` extracts the task name, task description, and employee index from the command input.
3.  A new `Task` is created with the extracted details and an assigned task index.
4. `AddTaskCommandParser` constructs an `AddTaskCommand` with the new `Task` and employee index.
5. `AddTaskCommand` calls `Model#addTask(task, employeeIndex)`.
6. `ModelManager` forwards the request to `AddressBook`, together with the overall in-memory `TaskList`.
7. `AddressBook` updates the overall `TaskList` to include the new task and its owning employee.
8.  The task is also updated to the employee's own `TaskListStorage`.


This two-level update ensures that the task is both visible on the employee card and discoverable by future task
commands that operate by task index.

#### Edit task implementation

`edittask` is parsed by `EditTaskCommandParser`, which reads a task index from the preamble and optional updates from
the `task/` and `desc/` prefixes.

During execution:

1. The model locates the existing task by task index using the overall `TaskList`.
2. A new replacement `Task` is created using the same task index but updated fields.
3. The task is replaced in the overall `TaskList`.
4. The same replacement is propagated to the owning employee through `UniquePersonList`.

This ensures the task remains associated with the same employee while reflecting the updated details in both model
structures.

#### Delete task implementation

`deletetask` is parsed by `DeleteTaskCommandParser`, which validates the task index before creating a
`DeleteTaskCommand`.

The sequence diagram below illustrates the interactions within the task-deletion flow for `deletetask 1`.

<puml src="diagrams/DeleteTaskSequenceDiagram.puml" alt="Interactions Inside the Logic and Model Components for the `deletetask 1` Command" />

The deletion flow works as follows:

1. `AddressBookParser` recognises the `deletetask` command and delegates parsing to `DeleteTaskCommandParser`.
2. `DeleteTaskCommandParser` constructs a `DeleteTaskCommand` with the requested task index.
3. `DeleteTaskCommand` calls `Model#deleteTask(taskIndex)`.
4. `ModelManager` forwards the request to `AddressBook`, together with the overall in-memory `TaskList`.
5. `AddressBook` asks `TaskList` for the employee who owns the task and removes the task from the overall mapping.
6. `AddressBook` then delegates to `UniquePersonList` to remove the same task from the employee's own `TaskListStorage`.

This design allows `deletetask` to work using a task index alone, without requiring the user to specify the employee
who owns the task.

#### Clear tasks implementation

`cleartasks` is parsed by `ClearTasksCommandParser`, which accepts either an employee index or `n/NAME` before
creating a `ClearTasksCommand`.

The sequence diagram below illustrates the interactions within the clear-tasks flow for `cleartasks 1`.

<puml src="diagrams/ClearTasksSequenceDiagram.puml" alt="Interactions Inside the Logic and Model Components for the `cleartasks 1` Command" />

The clear-tasks flow works as follows:

1. `AddressBookParser` recognises the `cleartasks` command and delegates parsing to `ClearTasksCommandParser`.
2. `ClearTasksCommandParser` constructs a `ClearTasksCommand` using the employee index or employee name.
3. `ClearTasksCommand` resolves the target employee from the currently displayed employee list.
4. `ClearTasksCommand` calls `Model#clearTasksForPerson(employee)`.
5. `ModelManager` forwards the request to `AddressBook`, together with the overall in-memory `TaskList`.
6. `AddressBook` removes the employee's tasks from the overall `TaskList`.
7. `AddressBook` then delegates to `UniquePersonList` to clear the same employee's personal `TaskListStorage`.

This keeps the overall task mapping and the employee card in sync after all tasks for one employee are cleared.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

Undo/redo is not currently implemented in ManageUp, but it is a plausible future enhancement because the app already
supports several data-mutating commands such as `add`, `edit`, `delete`, `addtask`, `edittask`, and `deletetask`.

One possible implementation is to introduce a `VersionedAddressBook` that extends `AddressBook` with an undo/redo
history stored internally as an `addressBookStateList` and a `currentStatePointer`. The proposed class would support
the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

If this feature is implemented, the corresponding operations can be exposed through the `Model` interface as
`Model#commitAddressBook()`, `Model#undoAddressBook()`, and `Model#redoAddressBook()`.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The proposed `VersionedAddressBook` is initialized with
the initial address book state, and the `currentStatePointer` points to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th employee in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new employee. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the employee was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the employee being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

This proposal prioritizes implementation simplicity. If the team decides to build undo/redo in future, the main tradeoff
to revisit is whether full-state snapshots remain acceptable as the amount of employee and task data grows.


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* managers in companies
* prefer desktop apps over other types
* responsible for managing different teams of people
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage employees and tasks faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​ | I want to …​ | So that I can…​ |
|----------|--------|--------------|----------------|
| `* * *` | new user | see usage instructions | refer to instructions when I forget how to use the app |
| `* * *` | first-time user | see examples of valid command formats | avoid input errors |
| `* * *` | user | view all my employees | have an overall view of all employees under my management |
| `* * *` | user | view all tasks assigned | understand the current workload of the company |
| `* * *` | user | see my employees' details | quickly access key information to contact them or assign work |
| `* * *` | user | add employee's department | understand where they belong in the organisation and route requests correctly |
| `* * *` | user | add employee's task | assign tasks to specific employees |
| `* * *` | user | delete employees | remove outdated records when someone leaves |
| `* * *` | user | search for an employee using name | find the right person quickly without scrolling through the entire list |
| `* * *` | user | search for an employee using department | find employees within a specific department quickly |
| `* * *` | user | search for an employee using job title | locate the appropriate employee for a specific role |
| `* * *` | administrative user | find employee assigned to task | contact the responsible employee for follow-ups |
| `* *` | user | add employee's address | arrange deliveries, site visits, or confirm work locations |
| `* *` | user | add employee's contact number | call or text employees for urgent coordination |
| `* *` | user | add employee's email | email employees directly regarding tasks and updates |
| `* *` | user | add employee's job title | assign appropriate tasks based on their role |
| `* *` | forgetful user | add employee's profile picture | recognise employees quickly |
| `* *` | user | add employee's task description | include more details for easier future reference |
| `* *` | user with many employees | add notes or facts to an employee's details | remember conversations or important information |
| `* *` | user | edit employee's details | update information when promotions or role changes occur |
| `* *` | user | edit task description | ensure task details remain accurate |
| `* *` | user handling many departments | filter employees by department | quickly find employees in a particular sector |
| `* *` | user | search employees working on a specific task | identify people responsible for that task |
| `* *` | user | search tasks by keywords | quickly locate tasks already assigned |
| `* *` | user | set task deadline | easily refer to when a task is due |
| `* *` | user | see all tasks assigned to a department | allocate work more evenly across departments |
| `* *` | user | sort tasks based on deadlines | identify overdue or upcoming tasks |
| `*` | busy user | batch delete assigned tasks | update records more efficiently |
| `*` | busy user | batch delete employees | remove multiple outdated records quickly |
| `*` | user | sort employees by number of tasks assigned | see who currently has the heaviest workload |
| `*` | frequent user | see command autocomplete suggestions | execute commands more quickly |
| `*` | frequent user | see autocomplete suggestions with existing names or job titles | avoid duplicate or inconsistent entries |
| `*` | frequent user | use shortcuts for commands | save time typing full commands |
| `*` | first-time user | see sample employees when opening the app | understand how the application works |
| `*` | user | undo my last action | correct mistakes quickly |


### Use cases

(For all use cases below, the **System** is the `ManageUp` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add an employee**

**MSS**

1. User enters an add command with the required employee details.
2. ManageUp validates the input fields.
3. ManageUp adds the new employee to the address book.
4. ManageUp shows a confirmation message.

Use case ends.

**Extensions**

* 2a. One or more required fields are missing or invalid.

  * 2a1. ManageUp shows an error message.

  Use case ends.

* 2b. The phone number or email address already belongs to another employee.

  * 2b1. ManageUp shows an error message.

  Use case ends.

**Use case: Search for employees by department**

**MSS**

1. User enters a command to filter employees by department.
2. ManageUp processes the request.
3. ManageUp displays a list of employees belonging to that department.

Use case ends.

**Extensions**

* 1a. No employees belong to the specified department.

  * 1a1. ManageUp displays an empty result message.

  Use case ends.

**Use case: Assign a task to an employee**

**MSS**

1. User requests to view the list of employees.
2. ManageUp displays the employee list.
3. User enters a command to assign a task to a selected employee.
4. ManageUp records the task and links it to the employee.
5. ManageUp displays a confirmation message.

Use case ends.

**Extensions**

* 3a. The given employee index is invalid.

    * 3a1. ManageUp shows an error message.

  Use case resumes at step 2.

* 3b. The task description is missing or invalid.

    * 3b1. ManageUp shows an error message.

  Use case ends.

**Use case: Edit an employee**

**MSS**

1. User requests to list employees.
2. ManageUp shows the list of employees.
3. User enters an edit command for a specific employee together with one or more updated fields.
4. ManageUp validates the edited values.
5. ManageUp updates the employee record.
6. ManageUp shows a confirmation message.

Use case ends.

**Extensions**

* 3a. The given employee index is invalid.

  * 3a1. ManageUp shows an error message.

  Use case resumes at step 2.

* 4a. All edited fields are invalid or missing.

  * 4a1. ManageUp shows an error message.

  Use case resumes at step 2.

* 4b. The edited phone number or email address duplicates another employee's value.

  * 4b1. ManageUp shows an error message.

  Use case resumes at step 2.

**Use case: Delete a task**

**MSS**

1. User requests to view employees with their assigned tasks.
2. ManageUp shows the employee list with tasks.
3. User enters a delete-task command using a task index.
4. ManageUp removes the task from the overall in-memory task list.
5. ManageUp removes the same task from the owning employee's task list.
6. ManageUp shows a confirmation message.

Use case ends.

**Extensions**

* 3a. The given task index is invalid.

  * 3a1. ManageUp shows an error message.

  Use case resumes at step 2.

**Use case: Delete an employee**

**MSS**

1. User requests to list employees.
2. ManageUp shows the list of employees.
3. User enters a delete command using an employee index or unique employee name.
4. ManageUp deletes the specified employee and any tasks associated with that employee.
5. ManageUp shows a confirmation message.

Use case ends.

**Extensions**

* 2a. The employee list is empty.

  Use case ends.

* 3a. The given index is invalid.

  * 3a1. ManageUp shows an error message.

  Use case resumes at step 2.

* 3b. No employee matches the given name.

  * 3b1. ManageUp shows an error message.

  Use case resumes at step 2.

* 3c. More than one employee matches the given name.

  * 3c1. ManageUp shows an error message asking the user to use an employee index instead.

  Use case resumes at step 2.

**Use case: Delete multiple employees**

**MSS**

1. User requests to list employees.
2. ManageUp shows the list of employees.
3. User enters a delete command with multiple employee indices.
4. ManageUp validates all requested indices.
5. ManageUp deletes all specified employees and any tasks associated with them.
6. ManageUp shows a confirmation message listing the deleted employees.

Use case ends.

**Extensions**

* 3a. One or more indices are duplicated.

  * 3a1. ManageUp shows an error message.

  Use case resumes at step 2.

* 4a. One or more indices are invalid.

  * 4a1. ManageUp shows an error message.

  Use case resumes at step 2.

**Use case: View help window**

**MSS**

1. User enters the `help` command.
2. ManageUp opens the Help Window.
3. ManageUp displays supported commands, allowed input formats, and examples.

Use case ends.

**Extensions**

* 2a. The Help Window is already open.

  * 2a1. ManageUp focuses the existing Help Window.

  Use case ends.



### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 employees without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The application should be **usable by a new user within 10 minutes** by referring to the help documentation.
5.  The system should **prevent invalid or inconsistent data entries** through input validation.
6.  The application should **respond to commands within 1 second** under typical operating conditions.




### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private employee detail**: Employee details that are not meant to be shared with others
* **Employee**: A staff member stored in the system whose details and tasks are managed by the application.
* **Task**: A piece of work assigned to an employee, identified by a name and a description.
* **Department**: A category used to group employees within an organisation.
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file.<br>
      Expected: Shows the GUI with a set of sample employees. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Deleting an employee

1. Deleting an employee while all employees are being shown

   1. Prerequisites: List all employees using the `list` command. Multiple employees in the list.

   1. Test case: `delete 1`<br>
      Expected: First employee is deleted from the list. Details of the deleted employee shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete Alex Yeoh`<br>
      Expected: The employee named Alex Yeoh is deleted from the current list. Details of the deleted employee are shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 1 3`<br>
      Expected: Both employees are deleted from the list in a single command. Details of the deleted employees shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No employee is deleted. Error details shown in the status message. Status bar remains the same.

   1. Test case: `delete 2 2`<br>
      Expected: No employee is deleted. Error details shown in the status message because duplicate indexes are not allowed.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Saving data

1. Dealing with missing/corrupted data files

   1. Open the data file at `data/addressbook.json` and edit it manually.

   1. Invalid JSON example:
      delete a closing brace from the file and launch the app.<br>
      Expected: The app starts with an empty address book, and an error is reported in the logs indicating that the
      data file could not be loaded correctly.

   1. Invalid value example:
      change a phone number to an invalid value such as `abc` and launch the app.<br>
      Expected: The app starts with an empty address book, and an error is reported in the logs.
