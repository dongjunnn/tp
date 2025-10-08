package seedu.address.model.project;

import seedu.address.model.person.Person;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

//todo: change each field to a class
//v1.3: we deal with members next week
public class Project {
    private String name;
    private Priority priority;
    private LocalDate deadline;
    private Set<Person> members;

    public Project(String name, Priority priority, LocalDate deadline, Set<Person> members) {
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean isSameProject(Project otherProject) {
        if (otherProject == this) {
            return true;
        }

        return otherProject != null
                && otherProject.getName().equals(getName());
    }

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

    @Override
    public String toString() {
        return "Project: " + getName() + " (Priority: " + getPriority() + ", Deadline: " + getDeadline() + ")";
    }
}
