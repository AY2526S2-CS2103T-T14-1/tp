package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.ui.HelpWindowContent.DisplayLine;
import seedu.address.ui.HelpWindowContent.HelpSection;
import seedu.address.ui.HelpWindowContent.HelpSectionDisplay;

public class HelpWindowContentTest {

    @Test
    public void getHelpSections_returnsExpectedCommandSections() {
        List<HelpSection> helpSections = HelpWindowContent.getHelpSections();

        assertEquals(11, helpSections.size());
        assertEquals("help", helpSections.get(0).commandWord());
        assertEquals("Shows this in-app help window.", helpSections.get(0).description());
        assertEquals("No additional parameters.", helpSections.get(0).allowedInput());
        assertIterableEquals(List.of("help"), helpSections.get(0).examples());

        assertEquals("delete", helpSections.get(2).commandWord());
        assertEquals("Deletes one or more employees by unique name or list index.",
                helpSections.get(2).description());
        assertEquals("NAME or INDEX [MORE_INDEXES]...", helpSections.get(2).allowedInput());
        assertIterableEquals(List.of("delete John Doe", "delete 2", "delete 1 3 5"),
                helpSections.get(2).examples());

        assertEquals("edittask", helpSections.get(7).commandWord());
        assertEquals("Edits a task identified by task index.", helpSections.get(7).description());
        assertEquals("TASK_INDEX with one or more optional fields: [task/TASK_NAME] [desc/DESCRIPTION]",
                helpSections.get(7).allowedInput());
        assertIterableEquals(List.of("edittask 1 task/Close deal", "edittask 2 desc/Follow through with clients"),
                helpSections.get(7).examples());

        assertEquals("deletetask", helpSections.get(8).commandWord());
        assertEquals("Deletes one or more tasks by task index.",
                helpSections.get(8).description());
        assertEquals("INDEX [MORE_INDICES]...", helpSections.get(8).allowedInput());
        assertIterableEquals(List.of("deletetask 1", "deletetask 1 3 5"),
                helpSections.get(8).examples());

        assertEquals("exit", helpSections.get(helpSections.size() - 1).commandWord());
        assertEquals("No additional parameters.", helpSections.get(helpSections.size() - 1).allowedInput());
    }

    @Test
    public void getDisplaySections_returnsFormattedDisplaySections() {
        List<HelpSectionDisplay> displaySections = HelpWindowContent.getDisplaySections();

        assertEquals(11, displaySections.size());
        assertIterableEquals(List.of(
                new DisplayLine("help", "help-command-title"),
                new DisplayLine("Shows this in-app help window.", "help-command-description"),
                new DisplayLine("Allowed input: No additional parameters.", "help-command-allowed-input")),
                displaySections.get(0).headerLines());
        assertIterableEquals(List.of(new DisplayLine("Example: help", "help-command-example")),
                displaySections.get(0).exampleLines());

        assertIterableEquals(List.of(
                new DisplayLine("Example: delete John Doe", "help-command-example"),
                new DisplayLine("Example: delete 2", "help-command-example"),
                new DisplayLine("Example: delete 1 3 5", "help-command-example")),
                displaySections.get(2).exampleLines());

        assertIterableEquals(List.of(
                new DisplayLine("Example: edittask 1 task/Close deal", "help-command-example"),
                new DisplayLine("Example: edittask 2 desc/Follow through with clients", "help-command-example")),
                displaySections.get(7).exampleLines());

        assertIterableEquals(List.of(
                new DisplayLine("Example: deletetask 1", "help-command-example"),
                new DisplayLine("Example: deletetask 1 3 5", "help-command-example")),
                displaySections.get(8).exampleLines());
    }

    @Test
    public void getFormattedHelpText_returnsFormattedHelpBody() {
        String formattedHelpText = HelpWindowContent.getFormattedHelpText();

        assertEquals("""
                help
                Shows this in-app help window.
                Allowed input: No additional parameters.
                Example: help

                add
                Adds an employee.
                Allowed input: n/NAME p/PHONE e/EMAIL d/DEPARTMENT pos/POSITION [t/TAG]...
                Example: add n/John Doe p/98765432 e/johnd@example.com d/IT pos/Software Engineer t/fulltime
                """.stripTrailing(), formattedHelpText.lines().limit(9).reduce((a, b) -> a + "\n" + b).orElse(""));
        assertTrue(formattedHelpText.contains("Example: delete John Doe"));
        assertTrue(formattedHelpText.contains("Example: delete 2"));
        assertTrue(formattedHelpText.contains("Example: delete 1 3 5"));
        assertTrue(formattedHelpText.contains("addtask"));
        assertTrue(formattedHelpText.contains("Allowed input: INDEX task/TASK_NAME desc/TASK_DESCRIPTION"));
        assertTrue(formattedHelpText.contains("Example: addtask 1 task/Sales Pitch desc/UI/UX design"));
        assertTrue(formattedHelpText.contains("edittask"));
        assertTrue(formattedHelpText.contains("Example: edittask 1 task/Close deal"));
        assertTrue(formattedHelpText.contains("Example: deletetask 1 3 5"));
        assertTrue(formattedHelpText.contains("exit"));
    }
}
