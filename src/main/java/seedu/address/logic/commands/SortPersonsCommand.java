package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
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
            + "  " + PREFIX_NAME + "   - Name (case-insensitive)\n"
            + "  " + PREFIX_EMAIL + "   - Email\n"
            + "  " + PREFIX_PHONE + "   - Phone\n"
            + "  " + PREFIX_PRIORITY + "  - Priority\n"
            + "  " + PREFIX_ADDRESS + "   - Address\n"
            + "Direction (optional): asc | desc (default: asc)\n"
            + "Format: " + COMMAND_WORD + " <attribute-prefix>[asc|desc]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " " + PREFIX_NAME + "asc\n"
            + "  " + COMMAND_WORD + " " + PREFIX_PRIORITY + "desc\n"
            + "  " + COMMAND_WORD + " " + PREFIX_ADDRESS + "\n";

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
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String test = PREFIX_ADDRESS.getPrefix();
        Comparator<Person> cmp;
        if (attribute.equals(PREFIX_NAME)) {
            cmp = Comparator.comparing(p -> p.getName().toString(), String.CASE_INSENSITIVE_ORDER);
        } else if (attribute.equals(PREFIX_EMAIL)) {
            cmp = Comparator.comparing(p -> p.getEmail().toString(), String.CASE_INSENSITIVE_ORDER);
        } else if (attribute.equals(PREFIX_PHONE)) {
            cmp = Comparator.comparing(p -> p.getPhone().toString(), String.CASE_INSENSITIVE_ORDER);
        } else if (attribute.equals(PREFIX_ADDRESS)) {
            cmp = Comparator.comparing(p -> p.getAddress().toString(), String.CASE_INSENSITIVE_ORDER);
        } else if (attribute.equals(PREFIX_PRIORITY)) {
            cmp = Comparator.comparing(p -> priorityRank(p.getPriority().toString()));
        } else {
            throw new CommandException("Unknown sort prefix: " + attribute);
        }
        if (!ascending) {
            cmp = cmp.reversed();
        }
        // tiebreaking by name
        cmp = cmp.thenComparing(p -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER);
        model.sortPersons(cmp);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    static int priorityRank(String s) {
        return switch (s.toUpperCase()) {
        case "LOW" -> 2;
        case "MEDIUM" -> 1;
        case "HIGH" -> 0;
        default -> -1;
        };
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SortPersonsCommand)) {
            return false;
        }
        SortPersonsCommand o = (SortPersonsCommand) other;
        return attribute.equals(o.attribute) && ascending == o.ascending;
    }
}
