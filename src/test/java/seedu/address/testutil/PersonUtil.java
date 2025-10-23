package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISCORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINKEDIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YOUTUBE;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        if (person.getSocials().getDiscord() != null
                && !person.getSocials().getDiscord().toString().isBlank()) {
            sb.append(PREFIX_DISCORD + person.getSocials().getDiscord().toString() + " ");
        }
        if (person.getSocials().getLinkedIn() != null
                && !person.getSocials().getLinkedIn().toString().isBlank()) {
            sb.append(PREFIX_LINKEDIN + person.getSocials().getLinkedIn().toString() + " ");
        }
        if (person.getSocials().getInstagram() != null
                && !person.getSocials().getInstagram().toString().isBlank()) {
            sb.append(PREFIX_INSTAGRAM + person.getSocials().getInstagram().toString() + " ");
        }
        if (person.getSocials().getYouTube() != null
                && !person.getSocials().getYouTube().toString().isBlank()) {
            sb.append(PREFIX_YOUTUBE + person.getSocials().getYouTube().toString() + " ");
        }
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(PREFIX_PRIORITY + person.getPriority().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getDiscordHandle().ifPresent(discord -> {
            if (!discord.value.isBlank()) {
                sb.append(PREFIX_DISCORD).append(discord.value).append(" ");
            }
        });
        descriptor.getLinkedInProfile().ifPresent(linkedIn -> {
            if (!linkedIn.value.isBlank()) {
                sb.append(PREFIX_LINKEDIN).append(linkedIn.value).append(" ");
            }
        });
        descriptor.getInstagramHandle().ifPresent(instagram -> {
            if (!instagram.value.isBlank()) {
                sb.append(PREFIX_INSTAGRAM).append(instagram.value).append(" ");
            }
        });
        descriptor.getYouTubeChannel().ifPresent(youTube -> {
            if (!youTube.value.isBlank()) {
                sb.append(PREFIX_YOUTUBE).append(youTube.value).append(" ");
            }
        });
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getPriority().ifPresent(priority -> sb.append(PREFIX_PRIORITY).append(priority.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
