package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEPARTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEPARTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEPARTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.employee.Department;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.Phone;
import seedu.address.model.employee.Position;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Employee expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Employee expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB
                        + POSITION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple departments
        assertParseFailure(parser, DEPARTMENT_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DEPARTMENT));

        // multiple positions
        assertParseFailure(parser, POSITION_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POSITION));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                                                             PREFIX_DEPARTMENT, PREFIX_POSITION));


        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        //invalid department
        assertParseFailure(parser, INVALID_DEPARTMENT_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DEPARTMENT));

        // invalid position
        assertParseFailure(parser, INVALID_POSITION_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POSITION));

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid department
        assertParseFailure(parser, validExpectedPersonString + INVALID_DEPARTMENT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DEPARTMENT));

        // invalid position
        assertParseFailure(parser, validExpectedPersonString + INVALID_POSITION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POSITION));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Employee expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + EMAIL_DESC_BOB
                        + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB,
                expectedMessage);

        // missing department prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + POSITION_DESC_BOB,
                expectedMessage);

        // missing position prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + DEPARTMENT_DESC_BOB,
                expectedMessage);


        // all prefixes missing
        assertParseFailure(parser, "",
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB
                + POSITION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB
                + POSITION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + DEPARTMENT_DESC_BOB
                + POSITION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid department
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_DEPARTMENT_DESC
                + POSITION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Department.MESSAGE_CONSTRAINTS);

        // invalid position
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB
                + INVALID_POSITION_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Position.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB
                + POSITION_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + DEPARTMENT_DESC_BOB
                + INVALID_POSITION_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DEPARTMENT_DESC_BOB + POSITION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
