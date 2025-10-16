package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Tags the people identified using their displayed index from the address
 * book with the given tags.
 */
public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a tag to all people identified by the index number used in the displayed person list. \n"
            + "Parameters: INDEX [INDEX]... (must be positive integers) "
            + PREFIX_TAG + "TAG "
            + "[" + PREFIX_TAG + "TAG ]..."
            + "Example: " + COMMAND_WORD + " "
            + "1 2 3"
            + PREFIX_TAG + "websiteRedesign "
            + PREFIX_TAG + "highPriority";

    public static final String MESSAGE_SUCCESS = "Tagged %s to %d person(s)";

    private final List<Index> targetIndexes;
    private final Set<Tag> tagsToAdd;

    /**
     * Creates a TagCommand to add the specified Tags to the specified Persons.
     */
    public TagCommand(List<Index> targetIndexes, Set<Tag> tagsToAdd) {
        requireNonNull(targetIndexes);
        this.targetIndexes = targetIndexes;
        requireNonNull(tagsToAdd);
        this.tagsToAdd = tagsToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index index : targetIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (Index index : targetIndexes) {
            Person personToTag = lastShownList.get(index.getZeroBased());
            Set<Tag> updatedTags = new HashSet<>(personToTag.getTags());
            updatedTags.addAll(tagsToAdd);

            Person updatedPerson = new Person(
                    personToTag.getName(),
                    personToTag.getPhone(),
                    personToTag.getEmail(),
                    personToTag.getDiscordHandle(),
                    personToTag.getLinkedInProfile(),
                    personToTag.getAddress(),
                    updatedTags
            );

            model.setPerson(personToTag, updatedPerson);
        }

        return new CommandResult(
                String.format(
                        MESSAGE_SUCCESS,
                        tagsToAdd.stream()
                                .map(Tag::toString)
                                .collect(Collectors.joining(", ")),
                        targetIndexes.size()
                )
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherTagCommand = (TagCommand) other;
        return targetIndexes.equals(otherTagCommand.targetIndexes)
                && tagsToAdd.equals(otherTagCommand.tagsToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexes", targetIndexes)
                .add("tagsToAdd", tagsToAdd)
                .toString();
    }
}
