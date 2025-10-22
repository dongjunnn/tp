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
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts by one attribute.\n"
            + "Format: sort (n/|e/|p/)[asc|desc]\n"
            + "Examples: sort n/asc | sort p/desc | sort e/";
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
        default -> throw new IllegalArgumentException("Unsupported attribute: " + attribute);
        };
        if (!ascending) {
            cmp = cmp.reversed();
        }
        cmp = cmp.thenComparing(p -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER);
        model.sortPersons(cmp);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
