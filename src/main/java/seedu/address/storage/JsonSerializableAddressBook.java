package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_PROJECT = "Projects list contains duplicate project(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedProject> projects = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and projects.
     * (Both are nullable for backward compatibility.)
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("projects") List<JsonAdaptedProject> projects) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (projects != null) {
            this.projects.addAll(projects);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
        projects.addAll(source.getProjectList().stream()
                .map(JsonAdaptedProject::new)
                .toList());
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        // Build email -> Person map for member resolution
        Map<String, Person> emailMap = new HashMap<>();
        for (Person p : addressBook.getPersonList()) {
            emailMap.put(p.getEmail().value, p);
        }

        for (JsonAdaptedProject jsonAdaptedProject : projects) {
            // Build base project without members
            Project baseProject = jsonAdaptedProject.toModelTypeWithoutMembers();

            // Resolve emails to Person objects
            Set<Person> memberSet = new HashSet<>();
            for (String email : jsonAdaptedProject.getMemberEmails()) {
                Person member = emailMap.get(email);
                if (member != null) {
                    memberSet.add(member);
                }
            }

            Project fullProject = new Project(
                    baseProject.getName(),
                    baseProject.getPriority(),
                    baseProject.getDeadline(),
                    memberSet
            );

            if (addressBook.hasProject(fullProject)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROJECT);
            }
            addressBook.addProject(fullProject);
        }

        return addressBook;
    }
}
