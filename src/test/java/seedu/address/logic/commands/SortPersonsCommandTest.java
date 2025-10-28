package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISCORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class SortPersonsCommandTest {
    private Model model;
    private Model expectedModel;
    private Person alice;
    private Person bob;
    private Person charlie;

    @BeforeEach
    public void setUp() {
        alice = new PersonBuilder().withName("Alice").withEmail("alice@example.com")
                .withPhone("111").withAddress("Apple Street")
                .withPriority("HIGH").build();
        bob = new PersonBuilder().withName("Bob").withEmail("bob@example.com")
                .withPhone("222").withAddress("Berry Road")
                .withPriority("LOW").build();
        charlie = new PersonBuilder().withName("Charlie").withEmail("charlie@example.com")
                .withPhone("333").withAddress("Cherry Avenue")
                .withPriority("MEDIUM").build();

        model = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(), new UserPrefs());

        model.addPerson(bob);
        model.addPerson(charlie);
        model.addPerson(alice);
        expectedModel.addPerson(bob);
        expectedModel.addPerson(charlie);
        expectedModel.addPerson(alice);
    }

    private void assertSorted(Prefix prefix, boolean ascending, Comparator<Person> expectedCmp) {
        SortPersonsCommand command = new SortPersonsCommand(prefix, ascending);
        String expectedMessage = SortPersonsCommand.MESSAGE_SUCCESS;

        expectedModel.sortPersons(expectedCmp);
        CommandResult expectedResult = new CommandResult(expectedMessage);

        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_sortByNameAsc_success() {
        assertSorted(PREFIX_NAME, true,
                Comparator.comparing((Person p) -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(p -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER));
    }

    @Test
    public void execute_sortByNameDesc_success() {
        assertSorted(PREFIX_NAME, false,
                Comparator.comparing((Person p) -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER)
                        .reversed()
                        .thenComparing(p -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER));
    }

    @Test
    public void execute_sortByEmailAsc_success() {
        assertSorted(PREFIX_EMAIL, true,
                Comparator.comparing((Person p) -> p.getEmail().toString(), String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(p -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER));
    }

    @Test
    public void execute_sortByEmailDesc_success() {
        assertSorted(PREFIX_EMAIL, false,
                Comparator.comparing((Person p) -> p.getEmail().toString(), String.CASE_INSENSITIVE_ORDER)
                        .reversed()
                        .thenComparing(p -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER));
    }

    @Test
    public void execute_sortByPhoneAsc_success() {
        assertSorted(PREFIX_PHONE, true,
                Comparator.comparing((Person p) -> p.getPhone().toString(), String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(p -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER));
    }

    @Test
    public void execute_sortByPhoneDesc_success() {
        assertSorted(PREFIX_PHONE, false,
                Comparator.comparing((Person p) -> p.getPhone().toString(), String.CASE_INSENSITIVE_ORDER)
                        .reversed()
                        .thenComparing(p -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER));
    }

    @Test
    public void execute_sortByAddressAsc_success() {
        assertSorted(PREFIX_ADDRESS, true,
                Comparator.comparing((Person p) -> p.getAddress().toString(), String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(p -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER));
    }

    @Test
    public void execute_sortByAddressDesc_success() {
        assertSorted(PREFIX_ADDRESS, false,
                Comparator.comparing((Person p) -> p.getAddress().toString(), String.CASE_INSENSITIVE_ORDER)
                        .reversed()
                        .thenComparing(p -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER));
    }

    @Test
    public void execute_sortByPriorityAsc_success() {
        assertSorted(PREFIX_PRIORITY, true,
                Comparator.comparing((Person p) -> SortPersonsCommand.priorityRank(p.getPriority().toString()))
                        .thenComparing(p -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER));
    }

    @Test
    public void execute_sortByPriorityDesc_success() {
        assertSorted(PREFIX_PRIORITY, false,
                Comparator.comparing((Person p) -> SortPersonsCommand.priorityRank(p.getPriority().toString()))
                        .reversed()
                        .thenComparing(p -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER));
    }

    @Test
    public void equals() {
        SortPersonsCommand sortNameAsc = new SortPersonsCommand(PREFIX_NAME, true);
        SortPersonsCommand sortAddressAsc = new SortPersonsCommand(PREFIX_ADDRESS, true);
        SortPersonsCommand sortNameDesc = new SortPersonsCommand(PREFIX_NAME, false);
        SortPersonsCommand noSuchPrefix = new SortPersonsCommand(PREFIX_DISCORD, true);

        // same object -> returns true
        assertEquals(sortNameAsc, sortNameAsc);

        // same values -> returns true
        SortPersonsCommand sortNameAscCopy = new SortPersonsCommand(PREFIX_NAME, true);
        assertEquals(sortNameAsc, sortNameAscCopy);

        // different types -> returns false
        assertFalse(sortNameAsc.equals(1));

        // null -> returns false
        assertNotEquals(null, sortNameAsc);

        // different order -> returns false
        assertNotEquals(sortNameAsc, sortNameDesc);

        // different prefix -> returns false
        assertNotEquals(sortNameAsc, sortAddressAsc);

        // unsupported prefix -> returns true
        CommandException ex = assertThrows(
                CommandException.class, () -> noSuchPrefix.execute(model)
        );
        assertTrue(ex.getMessage().equals("Unknown sort prefix: dc/"));
    }

    @Test
    public void priorityRank_valid_priorities() {
        assertEquals(0, SortPersonsCommand.priorityRank("HIGH"));
        assertEquals(1, SortPersonsCommand.priorityRank("MEDIUM"));
        assertEquals(2, SortPersonsCommand.priorityRank("LOW"));
    }

    @Test
    public void priorityRank_invalidPriority_returnsMinusOne() {
        assertEquals(-1, SortPersonsCommand.priorityRank("..."));
    }
}
