package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts Person objects present in the current address book by order specified by user.
 */

public class SortPersonsCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Address book has been sorted.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the displayed persons by exactly one attribute.\n"
            + "Parameters:\n"
            + "  n/   - Name (case-insensitive)\n"
            + "  e/   - Email\n"
            + "  p/   - Phone\n"
            + "  pr/  - Priority\n"
            + "  a/   - Address\n"
            + "Direction (optional): asc | desc (default: asc)\n"
            + "Format: " + COMMAND_WORD + " <attribute-prefix>[asc|desc]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " n/asc\n"
            + "  " + COMMAND_WORD + " p/desc\n"
            + "  " + COMMAND_WORD + " e/\n";

    private final Prefix attribute;
    private final boolean ascending;
    /**
     *  Creates a SortPersonsCommand object from a Prefix and boolean.
     */
    public SortPersonsCommand(Prefix attr, boolean ascending) {
        this.attribute = attr;
        this.ascending = ascending;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Comparator<Person> cmp = switch (attribute.toString()) {
        case "n/" -> Comparator.comparing(p -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER);
        case "e/" -> Comparator.comparing(p -> p.getEmail().value);
        case "p/" -> Comparator.comparing(p -> p.getPhone().value);
        case "a/" -> Comparator.comparing(p -> p.getAddress().value);
        case "pr/" -> Comparator.comparing(p -> priorityRank(p.getPriority().value));
        default -> throw new IllegalArgumentException("Unsupported attribute: " + attribute);
        };
        if (!ascending) {
            cmp = cmp.reversed();
        }
        // tiebreaking by name
        cmp = cmp.thenComparing(p -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER);
        model.sortPersons(cmp);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @SuppressWarnings("checkstyle:Indentation")
    private static int priorityRank(String s) {
        if (s == null) {
            return -1;
        }
        return switch (s.toUpperCase()) {
        case "LOW" -> 0;
        case "MEDIUM" -> 1;
        case "HIGH" -> 2;
        default -> -1;
        };
    }
}
