package seedu.address.model.project;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.priority.Priority;


//todo: change each field to a class
//v1.3: we deal with members next week
/**
 * Represents a project in the address book.
 * Guarantees: details are non-null and validated where applicable; immutable where possible.
 */
public class Project {
    private String name;
    private Priority priority;
    private LocalDate deadline;
    private Set<Person> members;

    /**
     * Constructs a {@code Project}.
     *
     * @param name     project name
     * @param priority project priority
     * @param deadline project deadline
     * @param members  project members
     */
    public Project(String name, Priority priority, LocalDate deadline, Set<Person> members) {
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        this.members = new HashSet<>(members);
    }

    /** Returns the project name. */
    public String getName() {
        return name;
    }

    /** Returns the project name. */
    public Priority getPriority() {
        return priority;
    }

    /** Returns the project deadline. */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * Returns true if both projects have the same identity (name).
     * This defines a weaker notion of equality between two projects.
     */

    public boolean isSameProject(Project otherProject) {
        if (otherProject == this) {
            return true;
        }

        return otherProject != null
                && otherProject.getName().equals(getName());
    }

    /** Returns true if all fields are equal. */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Project)) {
            return false;
        }

        Project otherProject = (Project) other;
        return name.equals(otherProject.name)
                && priority.equals(otherProject.priority)
                && deadline.equals(otherProject.deadline)
                && members.equals(otherProject.members);
    }

    public int hashCode() {
        return Objects.hash(name, priority, deadline, members);
    }

    /** Returns members of the project. */
    public Set<Person> getMembers() {
        return java.util.Collections.unmodifiableSet(members);
    }

    /**
     * Removes a member from this project.
     *
     * @param person the person to remove
     * @return true if the member was present and removed; false otherwise
     */
    public boolean deleteMember(Person person) {
        Objects.requireNonNull(person);
        return members.remove(person);
    }

    @Override
    public String toString() {
        return "Project: " + getName() + " (Priority: " + getPriority() + ", Deadline: " + getDeadline() + ")";
    }
}
