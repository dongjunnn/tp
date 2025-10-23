package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

/**
 * A UI component that displays information of a {@code Person}.
 * Shows person details including name, contact information, tags, and
 * associated projects.
 * Projects are displayed as clickable hyperlinks if the projects FlowPane
 * exists.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label discordHandle;
    @FXML
    private Label linkedInProfile;
    @FXML
    private Label instagramHandle;
    @FXML
    private Label youTubeChannel;
    @FXML
    private Label priority;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane projects; // May be null

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and display index.
     * This is the original constructor for backward compatibility.
     *
     * @param person         The person to display. Must not be null.
     * @param displayedIndex The 1-based index to display. Must be positive.
     */
    public PersonCard(Person person, int displayedIndex) {
        this(person, displayedIndex, null, null);
    }

    /**
     * Creates a {@code PersonCard} with the given {@code Person}, display index,
     * list of all projects, and callback for project clicks.
     *
     * @param person         The person to display. Must not be null.
     * @param displayedIndex The 1-based index to display. Must be positive.
     * @param allProjects    The list of all projects to check for membership. Can
     *                       be null.
     * @param onProjectClick Callback to execute when a project is clicked. Can be
     *                       null.
     */
    public PersonCard(Person person, int displayedIndex,
            ObservableList<Project> allProjects,
            Consumer<Project> onProjectClick) {
        super(FXML);
        requireNonNull(person, "Person cannot be null");
        assert displayedIndex > 0 : "Displayed index must be positive";

        this.person = person;

        // Set basic person information
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        priority.setText("Priority: " + person.getPriority().value);
        email.setText(person.getEmail().value);

        // Handle optional Discord handle
        handleOptionalField(discordHandle, person.getSocials().getDiscord().value, "Discord: ");

        // Handle optional LinkedIn profile
        handleOptionalField(linkedInProfile, person.getSocials().getLinkedIn().value, "LinkedIn: ");

        // Handle optional Instagram handle
        handleOptionalField(instagramHandle, person.getSocials().getInstagram().value, "Instagram: ");

        // Handle optional Instagram handle
        handleOptionalField(youTubeChannel, person.getSocials().getYouTube().value, "Youtube: ");

        // Display tags sorted alphabetically
        displayTags();

        // Display projects only if both projects FlowPane exists and data is provided
        if (projects != null && allProjects != null && onProjectClick != null) {
            displayProjects(allProjects, onProjectClick);
        }
    }

    /**
     * Handles the display of an optional field (Discord or LinkedIn).
     * If the field has a value, it is shown with a prefix; otherwise, it is hidden.
     *
     * @param label  The label to update. Must not be null.
     * @param value  The field value (can be null or empty).
     * @param prefix The prefix to display before the value (e.g., "Discord: ").
     */
    private void handleOptionalField(Label label, String value, String prefix) {
        if (label == null) {
            return; // skip if label doesn't exist
        }

        if (value != null && !value.isEmpty()) {
            label.setText(prefix + value);
            label.setVisible(true);
            label.setManaged(true);
        } else {
            label.setVisible(false);
            label.setManaged(false);
        }
    }

    /**
     * Displays the person's tags in alphabetical order.
     */
    private void displayTags() {
        if (tags == null) {
            return; // skip if tags FlowPane doesn't exist
        }

        if (person.getSocials().getInstagram() != null
                && !person.getSocials().getInstagram().value.isEmpty()) {
            instagramHandle.setText("Instagram: " + person.getSocials().getInstagram().value);
            instagramHandle.setVisible(true);
            instagramHandle.setManaged(true);
        } else {
            instagramHandle.setVisible(false);
            instagramHandle.setManaged(false);
        }

        if (person.getSocials().getYouTube() != null
                && !person.getSocials().getYouTube().value.isEmpty()) {
            youTubeChannel.setText("YouTube: " + person.getSocials().getYouTube().value);
            youTubeChannel.setVisible(true);
            youTubeChannel.setManaged(true);
        } else {
            youTubeChannel.setVisible(false);
            youTubeChannel.setManaged(false);
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Displays projects that the selected person is a member of as clickable
     * hyperlinks.
     * Filters the project list to find projects containing this person,
     * then creates a hyperlink for each project that triggers the callback when
     * clicked.
     *
     * @param allProjects    The list of all projects to filter. Must not be null.
     * @param onProjectClick The callback to execute when a project is clicked. Must
     *                       not be null.
     */
    private void displayProjects(ObservableList<Project> allProjects, Consumer<Project> onProjectClick) {
        requireNonNull(allProjects, "Project list cannot be null");
        requireNonNull(onProjectClick, "Project click callback cannot be null");

        // projects FlowPane should exist if this method is called
        if (projects == null) {
            return;
        }

        // Filter projects to find ones where this person is a member
        List<Project> personProjects = allProjects.stream()
                .filter(project -> project != null && project.getMembers() != null)
                .filter(project -> project.getMembers().contains(person))
                .collect(Collectors.toList());

        // Create clickable hyperlinks for each project
        for (Project project : personProjects) {
            Hyperlink projectLink = new Hyperlink(project.getName());
            projectLink.getStyleClass().add("project-link");

            // Set click handler to trigger callback with the project
            projectLink.setOnAction(event -> onProjectClick.accept(project));

            projects.getChildren().add(projectLink);
        }
    }
}
