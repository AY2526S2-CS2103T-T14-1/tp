package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.predicatechecker.DepartmentContainsKeywordsPredicate;
import seedu.address.model.employee.predicatechecker.EmailContainsKeywordsPredicate;
import seedu.address.model.employee.predicatechecker.NameContainsKeywordsPredicate;
import seedu.address.model.employee.predicatechecker.PhoneContainsKeywordsPredicate;
import seedu.address.model.employee.predicatechecker.PositionContainsKeywordsPredicate;
import seedu.address.model.employee.predicatechecker.TagContainsKeywordsPredicate;
import seedu.address.model.employee.predicatechecker.TaskContainsKeywordsPredicate;

public class ShowCommandParserTest {

    private static final String INVALID_SHOW_COMMAND_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE);

    private final ShowCommandParser parser = new ShowCommandParser();

    @Test
    public void parse_emptyArg_failure() {
        assertParseFailure(parser, "   ", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_noValidPrefix_failure() {
        assertParseFailure(parser, "Alice", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankPrefixOnly_failure() {
        assertParseFailure(parser, "t/", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankNamePrefix_failure() {
        assertParseFailure(parser, "n/", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankDepartmentPrefix_failure() {
        assertParseFailure(parser, "d/", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankPhonePrefix_failure() {
        assertParseFailure(parser, "p/", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankEmailPrefix_failure() {
        assertParseFailure(parser, "e/", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankPositionPrefix_failure() {
        assertParseFailure(parser, "pos/", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankTagPrefix_failure() {
        assertParseFailure(parser, "t/", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankTaskPrefix_failure() {
        assertParseFailure(parser, "task/", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankTagWithOtherValidPrefix_failure() {
        assertParseFailure(parser, "t/ n/Alex", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankTaskWithOtherValidPrefix_failure() {
        assertParseFailure(parser, "task/ d/IT", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankNameWithOtherValidPrefix_failure() {
        assertParseFailure(parser, "n/ d/Finance", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankDepartmentWithOtherValidPrefix_failure() {
        assertParseFailure(parser, "d/ n/Alex", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankEmailWithOtherValidPrefix_failure() {
        assertParseFailure(parser, "e/ n/Alex", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankPhoneWithOtherValidPrefix_failure() {
        assertParseFailure(parser, "p/ d/IT", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_blankPositionWithOtherValidPrefix_failure() {
        assertParseFailure(parser, "pos/ t/friend", INVALID_SHOW_COMMAND_MESSAGE);
    }

    @Test
    public void parse_namePrefix_success() {
        Predicate<Employee> predicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "n/Alice", expectedCommand);
    }

    @Test
    public void parse_departmentPrefix_success() {
        Predicate<Employee> predicate =
                new DepartmentContainsKeywordsPredicate(Arrays.asList("Finance"));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "d/Finance", expectedCommand);
    }

    @Test
    public void parse_phonePrefix_success() {
        Predicate<Employee> predicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("9123"));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "p/9123", expectedCommand);
    }

    @Test
    public void parse_emailPrefix_success() {
        Predicate<Employee> predicate =
                new EmailContainsKeywordsPredicate(Arrays.asList("gmail"));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "e/gmail", expectedCommand);
    }

    @Test
    public void parse_positionPrefix_success() {
        Predicate<Employee> predicate =
                new PositionContainsKeywordsPredicate(Arrays.asList("Manager"));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "pos/Manager", expectedCommand);
    }

    @Test
    public void parse_tagPrefix_success() {
        Predicate<Employee> predicate =
                new TagContainsKeywordsPredicate(Arrays.asList("friend"));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "t/friend", expectedCommand);
    }

    @Test
    public void parse_taskPrefix_success() {
        Predicate<Employee> predicate =
                new TaskContainsKeywordsPredicate(Arrays.asList("report"));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "task/report", expectedCommand);
    }

    @Test
    public void parse_multipleFields_success() {
        Predicate<Employee> predicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice"))
                        .and(new DepartmentContainsKeywordsPredicate(Arrays.asList("IT")))
                        .and(new EmailContainsKeywordsPredicate(Arrays.asList("gmail")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "n/Alice d/IT e/gmail", expectedCommand);
    }

    @Test
    public void parse_nameAndTag_success() {
        Predicate<Employee> predicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alex"))
                        .and(new TagContainsKeywordsPredicate(Arrays.asList("friend")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "n/Alex t/friend", expectedCommand);
    }

    @Test
    public void parse_tagAndDepartment_success() {
        Predicate<Employee> predicate =
                new TagContainsKeywordsPredicate(Arrays.asList("backend"))
                        .and(new DepartmentContainsKeywordsPredicate(Arrays.asList("IT")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "t/backend d/IT", expectedCommand);
    }

    @Test
    public void parse_nameAndTask_success() {
        Predicate<Employee> predicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alex"))
                        .and(new TaskContainsKeywordsPredicate(Arrays.asList("report")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "n/Alex task/report", expectedCommand);
    }

    @Test
    public void parse_taskAndDepartment_success() {
        Predicate<Employee> predicate =
                new TaskContainsKeywordsPredicate(Arrays.asList("review"))
                        .and(new DepartmentContainsKeywordsPredicate(Arrays.asList("Finance")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "task/review d/Finance", expectedCommand);
    }

    @Test
    public void parse_taskWithTag_success() {
        Predicate<Employee> predicate =
                new TaskContainsKeywordsPredicate(Arrays.asList("meeting"))
                        .and(new TagContainsKeywordsPredicate(Arrays.asList("urgent")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "task/meeting t/urgent", expectedCommand);
    }

    @Test
    public void parse_multipleKeywordsInName_success() {
        Predicate<Employee> predicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alex", "John"));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "n/Alex John", expectedCommand);
    }

    @Test
    public void parse_multipleKeywordsInDepartment_success() {
        Predicate<Employee> predicate =
                new DepartmentContainsKeywordsPredicate(Arrays.asList("Human", "Resource"));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "d/Human Resource", expectedCommand);
    }

    @Test
    public void parse_multipleKeywordsInTag_success() {
        Predicate<Employee> predicate =
                new TagContainsKeywordsPredicate(Arrays.asList("friend", "leader"));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "t/friend leader", expectedCommand);
    }

    @Test
    public void parse_multipleKeywordsInTask_success() {
        Predicate<Employee> predicate =
                new TaskContainsKeywordsPredicate(Arrays.asList("report", "review"));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "task/report review", expectedCommand);
    }

    @Test
    public void parse_prefixesInDifferentOrder_success() {
        Predicate<Employee> predicate =
                new EmailContainsKeywordsPredicate(Arrays.asList("gmail"))
                        .and(new NameContainsKeywordsPredicate(Arrays.asList("Alice")))
                        .and(new DepartmentContainsKeywordsPredicate(Arrays.asList("IT")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "e/gmail n/Alice d/IT", expectedCommand);
    }

    @Test
    public void parse_tagPrefixInFront_success() {
        Predicate<Employee> predicate =
                new TagContainsKeywordsPredicate(Arrays.asList("friend", "leader"))
                        .and(new NameContainsKeywordsPredicate(Arrays.asList("Alex")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "t/friend leader n/Alex", expectedCommand);
    }

    @Test
    public void parse_tagPrefixAtEnd_success() {
        Predicate<Employee> predicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alex"))
                        .and(new DepartmentContainsKeywordsPredicate(Arrays.asList("IT")))
                        .and(new TagContainsKeywordsPredicate(Arrays.asList("friend", "leader")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "n/Alex d/IT t/friend leader", expectedCommand);
    }

    @Test
    public void parse_taskPrefixInFront_success() {
        Predicate<Employee> predicate =
                new TaskContainsKeywordsPredicate(Arrays.asList("meeting", "review"))
                        .and(new NameContainsKeywordsPredicate(Arrays.asList("Alex")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "task/meeting review n/Alex", expectedCommand);
    }

    @Test
    public void parse_multiKeywordNameStopsAtNextPrefix_success() {
        Predicate<Employee> predicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alex", "John"))
                        .and(new TagContainsKeywordsPredicate(Arrays.asList("friend")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "n/Alex John t/friend", expectedCommand);
    }

    @Test
    public void parse_multiKeywordTagStopsAtNextPrefix_success() {
        Predicate<Employee> predicate =
                new TagContainsKeywordsPredicate(Arrays.asList("friend", "leader", "mentor"))
                        .and(new DepartmentContainsKeywordsPredicate(Arrays.asList("IT")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "t/friend leader mentor d/IT", expectedCommand);
    }

    @Test
    public void parse_multipleKeywordsInTaskStopsAtNextPrefix_success() {
        Predicate<Employee> predicate =
                new TaskContainsKeywordsPredicate(Arrays.asList("report", "review"))
                        .and(new NameContainsKeywordsPredicate(Arrays.asList("Alex")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "task/report review n/Alex", expectedCommand);
    }

    @Test
    public void parse_allPrefixesTogether_success() {
        Predicate<Employee> predicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice"))
                        .and(new DepartmentContainsKeywordsPredicate(Arrays.asList("IT")))
                        .and(new PhoneContainsKeywordsPredicate(Arrays.asList("9123")))
                        .and(new EmailContainsKeywordsPredicate(Arrays.asList("gmail")))
                        .and(new PositionContainsKeywordsPredicate(Arrays.asList("Manager")))
                        .and(new TagContainsKeywordsPredicate(Arrays.asList("friend")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "n/Alice d/IT p/9123 e/gmail pos/Manager t/friend", expectedCommand);
    }

    @Test
    public void parse_allPrefixesIncludingTask_success() {
        Predicate<Employee> predicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice"))
                        .and(new DepartmentContainsKeywordsPredicate(Arrays.asList("IT")))
                        .and(new PhoneContainsKeywordsPredicate(Arrays.asList("9123")))
                        .and(new EmailContainsKeywordsPredicate(Arrays.asList("gmail")))
                        .and(new PositionContainsKeywordsPredicate(Arrays.asList("Manager")))
                        .and(new TagContainsKeywordsPredicate(Arrays.asList("friend")))
                        .and(new TaskContainsKeywordsPredicate(Arrays.asList("report")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser,
                "n/Alice d/IT p/9123 e/gmail pos/Manager t/friend task/report",
                expectedCommand);
    }

    @Test
    public void parse_leadingAndTrailingWhitespace_success() {
        Predicate<Employee> predicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "   n/Alice   ", expectedCommand);
    }

    @Test
    public void parse_allPrefixesInDifferentOrderIncludingTask_success() {
        Predicate<Employee> predicate =
                new TaskContainsKeywordsPredicate(Arrays.asList("report"))
                        .and(new EmailContainsKeywordsPredicate(Arrays.asList("gmail")))
                        .and(new NameContainsKeywordsPredicate(Arrays.asList("Alice")))
                        .and(new TagContainsKeywordsPredicate(Arrays.asList("friend")));
        ShowCommand expectedCommand = new ShowCommand(predicate);

        assertParseSuccess(parser, "task/report e/gmail n/Alice t/friend", expectedCommand);
    }

    @Test
    public void parse_nullArgs_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> parser.parse(null));
    }

    @Test
    public void extract_missingPrefix_returnsEmptyString() throws Exception {
        Method method = ShowCommandParser.class.getDeclaredMethod("extract", String.class, String.class);
        method.setAccessible(true);

        String result = (String) method.invoke(parser, "n/Alex", "d/");
        assertEquals("", result);
    }

    @Test
    public void extract_stopsAtNextPrefix_returnsCorrectSubstring() throws Exception {
        Method method = ShowCommandParser.class.getDeclaredMethod("extract", String.class, String.class);
        method.setAccessible(true);

        String result = (String) method.invoke(parser, "n/Alex John d/IT", "n/");
        assertEquals("Alex John", result);
    }

    @Test
    public void extract_lastPrefix_returnsRestOfInput() throws Exception {
        Method method = ShowCommandParser.class.getDeclaredMethod("extract", String.class, String.class);
        method.setAccessible(true);

        String result = (String) method.invoke(parser, "d/Finance task/report review", "task/");
        assertEquals("report review", result);
    }
}
