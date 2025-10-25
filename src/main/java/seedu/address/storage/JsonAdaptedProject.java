package seedu.address.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.priority.Priority;
import seedu.address.model.project.Project;

/**
 * Jackson-friendly version of {@link Project}.
 * v2: persist name, priority, deadline, and member names.
 */
class JsonAdaptedProject {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";

    private final String name;
    private final String priority;
    private final String deadline;
    /** Member identities stored as names for stable cross-reference. */
    private final List<String> members;

    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("name") String name,
                              @JsonProperty("priority") String priority,
                              @JsonProperty("deadline") String deadline,
                              @JsonProperty("members") List<String> members) {
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        // Backward compatible with old JSON that had no "members"
        this.members = (members == null) ? new ArrayList<>() : new ArrayList<>(members);
    }

    public JsonAdaptedProject(Project source) {
        this.name = source.getName();
        this.priority = source.getPriority().name();
        this.deadline = source.getDeadline().toString();

        this.members = source.getMembers().stream()
                .map(p -> p.getName().fullName)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Builds a Project WITHOUT members; member names can be obtained via {@link #getMemberNames()}
     * and resolved in JsonSerializableAddressBook after persons are loaded.
     */
    public Project toModelTypeWithoutMembers() throws IllegalValueException {
        if (name == null || name.isBlank()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }
        if (priority == null || priority.isBlank()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "priority"));
        }
        if (deadline == null || deadline.isBlank()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "deadline"));
        }

        final Priority pr;
        try {
            pr = Priority.valueOf(priority);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid project priority: " + priority);
        }

        final LocalDate dl;
        try {
            dl = LocalDate.parse(deadline);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid project deadline (expected yyyy-MM-dd): " + deadline);
        }

        // Members are resolved later
        return new Project(name, pr, dl, java.util.Collections.emptySet());
    }

    /** Expose member names so the loader can resolve them to Person objects. */
    public List<String> getMemberNames() {
        return members;
    }
}
