package seedu.address.ui;

import java.util.List;

/**
 * Provides the help content shown in the help window.
 */
public final class HelpWindowContent {

    private HelpWindowContent() {}

    /**
     * Returns the help sections shown in the help window.
     */
    public static List<HelpSection> getHelpSections() {
        return List.of(
                new HelpSection("help", "Shows this in-app help window.",
                        "No additional parameters.", List.of("help")),
                new HelpSection("add", "Adds an employee.",
                        "n/NAME p/PHONE e/EMAIL d/DEPARTMENT pos/POSITION [t/TAG]...",
                        List.of("add n/John Doe p/98765432 e/johnd@example.com d/IT pos/Software Engineer "
                                + "t/fulltime")),
                new HelpSection("delete", "Deletes one or more employees by unique name or list index.",
                        "NAME or INDEX [MORE_INDEXES]...", List.of("delete John Doe", "delete 2",
                                "delete 1 3 5")),
                new HelpSection("edit", "Edits an employee identified by index.",
                        "INDEX with one or more optional fields: [n/NAME] [p/PHONE] [e/EMAIL] "
                                + "[d/DEPARTMENT] [pos/POSITION] [t/TAG]...",
                        List.of("edit 1 p/91234567 e/johndoe@example.com")),
                new HelpSection("list", "Lists all employees.",
                        "No additional parameters.", List.of("list")),
                new HelpSection("show", "Filters employees by one or more fields.",
                        "At least one of n/ d/ p/ e/ pos/ t/ task/.",
                        List.of("show n/Alex d/IT")),
                new HelpSection("addtask", "Adds a task to an employee by list index.",
                        "INDEX task/TASK_NAME desc/TASK_DESCRIPTION",
                        List.of("addtask 1 task/Sales Pitch desc/UI/UX design")),
                new HelpSection("edittask", "Edits a task identified by task index.",
                        "TASK_INDEX with one or more optional fields: [task/TASK_NAME] [desc/DESCRIPTION]",
                        List.of("edittask 1 task/Close deal", "edittask 2 desc/Follow through with clients")),
                new HelpSection("deletetask", "Deletes one or more tasks by task index.",
                        "INDEX [MORE_INDICES]...", List.of("deletetask 1", "deletetask 1 3 5")),
                new HelpSection("clear", "Clears all employees from the address book.",
                        "No additional parameters.", List.of("clear")),
                new HelpSection("exit", "Exits ManageUp.",
                        "No additional parameters.", List.of("exit"))
        );
    }

    /**
     * Returns display-ready help sections for the help window UI.
     */
    public static List<HelpSectionDisplay> getDisplaySections() {
        return getHelpSections().stream()
                .map(section -> new HelpSectionDisplay(
                        List.of(
                                new DisplayLine(section.commandWord(), "help-command-title"),
                                new DisplayLine(section.description(), "help-command-description"),
                                new DisplayLine("Allowed input: " + section.allowedInput(),
                                        "help-command-allowed-input")),
                        section.examples().stream()
                                .map(example -> new DisplayLine("Example: " + example, "help-command-example"))
                                .toList()))
                .toList();
    }

    /**
     * Returns a formatted help message for the help window body.
     */
    public static String getFormattedHelpText() {
        return getDisplaySections().stream()
                .map(HelpWindowContent::formatDisplaySection)
                .reduce((first, second) -> first + "\n\n" + second)
                .orElse("");
    }

    private static String formatDisplaySection(HelpSectionDisplay section) {
        return concatDisplayLines(section.headerLines(), section.exampleLines());
    }

    private static String concatDisplayLines(List<DisplayLine> headerLines, List<DisplayLine> exampleLines) {
        return List.of(headerLines, exampleLines).stream()
                .flatMap(List::stream)
                .map(DisplayLine::text)
                .reduce((first, second) -> first + "\n" + second)
                .orElse("");
    }

    /**
     * Represents one command section in the help window.
     */
    public record HelpSection(String commandWord, String description, String allowedInput, List<String> examples) { }

    /**
     * Represents one display-ready command section in the help window.
     */
    public record HelpSectionDisplay(
            List<DisplayLine> headerLines,
            List<DisplayLine> exampleLines) { }

    /**
     * Represents one display line in a help section.
     */
    public record DisplayLine(String text, String styleClass) { }
}
