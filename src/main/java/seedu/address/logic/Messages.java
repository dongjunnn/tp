package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.person.Socials;
import seedu.address.model.project.Project;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PROJECT_NOT_FOUND_BY_NAME = "Project not found: %s";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_PROJECT_MUST_HAVE_MEMBERS =
            "Project '%1$s' cannot have no members. pdelete or add new members first.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail());

        // Append socials right after email if they are not empty
        Socials socials = person.getSocials();
        if (!socials.getDiscord().value.isEmpty()) {
            builder.append("; Discord: ").append(socials.getDiscord());
        }
        if (!socials.getLinkedIn().value.isEmpty()) {
            builder.append("; LinkedIn: ").append(socials.getLinkedIn());
        }
        if (!socials.getInstagram().value.isEmpty()) {
            builder.append("; Instagram: ").append(socials.getInstagram());
        }
        if (!socials.getYouTube().value.isEmpty()) {
            builder.append("; YouTube: ").append(socials.getYouTube());
        }

        builder.append("; Address: ")
                .append(person.getAddress())
                .append("; Priority: ")
                .append(person.getPriority())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code project} for display to the user.
     */
    public static String format(Project project) {
        return project.getName() + " (Deadline: " + project.getDeadline() + ", Priority: "
                + project.getPriority() + ")";
    }

}
