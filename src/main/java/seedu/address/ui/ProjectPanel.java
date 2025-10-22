package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.project.Project;

/**
 * A UI component that displays detailed information about a selected {@code Project}.
 * Shows project name, priority, deadline, and list of members.
 * When no project is selected, displays a placeholder message.
 */
public class ProjectPanel extends UiPart<Region> {

    private static final String FXML = "ProjectPanel.fxml";

    @FXML
    private VBox placeholderContainer;

    @FXML
    private VBox detailsContainer;

    @FXML
    private Label projectName;

    @FXML
    private Label priority;

    @FXML
    private Label deadline;

    @FXML
    private ListView<String> membersList;

    /**
     * Creates a {@code ProjectPanel}.
     * Initializes with placeholder shown and details hidden.
     */
    public ProjectPanel() {
        super(FXML);
        showPlaceholder();
    }

    /**
     * Shows the placeholder message when no project is selected.
     * Hides the project details container.
     */
    private void showPlaceholder() {
        assert placeholderContainer != null : "Placeholder container should not be null";
        assert detailsContainer != null : "Details container should not be null";

        placeholderContainer.setVisible(true);
        placeholderContainer.setManaged(true);
        detailsContainer.setVisible(false);
        detailsContainer.setManaged(false);
    }

    /**
     * Displays the details of the given project.
     * Hides the placeholder and shows the project information including
     * name, priority, deadline, and members.
     *
     * @param project The project to display. Must not be null.
     * @throws NullPointerException if {@code project} is null.
     */
    public void showProject(Project project) {
        requireNonNull(project, "Project to display cannot be null");

        assert placeholderContainer != null : "Placeholder container should not be null";
        assert detailsContainer != null : "Details container should not be null";
        assert projectName != null : "Project name label should not be null";
        assert priority != null : "Priority label should not be null";
        assert deadline != null : "Deadline label should not be null";
        assert membersList != null : "Members list should not be null";

        // Hide placeholder, show details
        placeholderContainer.setVisible(false);
        placeholderContainer.setManaged(false);
        detailsContainer.setVisible(true);
        detailsContainer.setManaged(true);

        // Update project information labels
        projectName.setText(project.getName());
        priority.setText(project.getPriority().toString());
        deadline.setText(project.getDeadline().toString());

        // Display members list
        // Convert Person objects to their names for display
        membersList.setItems(FXCollections.observableArrayList(
                project.getMembers().stream()
                        .map(person -> person.getName().fullName)
                        .toList()
        ));
    }
}
